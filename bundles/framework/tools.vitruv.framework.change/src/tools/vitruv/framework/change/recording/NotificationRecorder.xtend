package tools.vitruv.framework.change.recording

import org.eclipse.emf.common.notify.Adapter
import java.util.List
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.EChangeIdManager
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import java.util.Set
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import static extension tools.vitruv.framework.change.echange.util.EChangeUtil.*
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import org.eclipse.xtend.lib.annotations.Data
import java.util.HashSet
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static com.google.common.base.Preconditions.checkState
import java.util.ArrayList

/**
 * This {@link Adapter} records changes to the given model elements as {@link CompositeTransactionalChanges}.
 * Recording can be started with {@link #beginRecording} and ended with {@link #endRecording}. It is assumed 
 * that all elements that do not have a container when ending the recording have been deleted, resulting in
 * an appropriate delete change.
 */
class NotificationRecorder implements Adapter {
	val Set<Notifier> rootObjects = new HashSet
	boolean isRecording = false
	List<CompositeTransactionalChange> resultChanges
	val EChangeIdManager idManager
	
	new (EChangeIdManager idManager) {
		this.idManager = idManager;	
	}
	
	override getTarget() {
		return null;
	}

	override isAdapterForType(Object type) {
		return false;
	}

	override notifyChanged(Notification notification) {
		if (notification.newValue instanceof Notifier) {
			recursivelyAddAdapter(notification.newValue as Notifier)
		}
//		val notifier = notification.notifier;
//		if (notifier instanceof EObject) {
//			if (notifier.eContainer?.eResource !== notifier.eResource) {
//				notifier.recursivelyRemoveAdapter;
//			}
//			return;
//		}
		if (!isRecording) {
			return
		}
		val newChanges = new NotificationToEChangeConverter(idManager).convert(new NotificationInfo(notification))
		if (!newChanges.isEmpty) {
			resultChanges += VitruviusChangeFactory.instance.createCompositeTransactionalChange(
				newChanges.map [VitruviusChangeFactory.instance.createConcreteApplicableChange(it)]
			)
		}
	}

	override setTarget(Notifier newTarget) {
		recursivelyAddAdapter(newTarget);
	}
	
	/**
	 * Add the given elements and all its contained elements ({@link Resource}s, {@link EObject}s) to the recorder.
	 * 
	 * @param notifier - the {@link Notifier} to add the recorder to
	 */
	def void addToRecording(Notifier notifier) {
		if (rootObjects += notifier) {
			recursivelyAddAdapter(notifier)
		}
	}
	
	/**
	 * Removes the given elements and all its contained elements (resources, EObjects) from the recorder.
	 * @param notifier - the {@link Notifier} to remove the recorder from
	 */
	def void removeFromRecording(Notifier notifier) {
		if (rootObjects -= notifier) {
			recursivelyRemoveAdapter(notifier)
		}
	}
	
	/**
	 * Starts recording changes on the registered elements.
	 */
	def beginRecording() {
		checkState(!isRecording, "This recorder is already recording!")
		isRecording = true
		resultChanges = new ArrayList
	}

	/**
	 * Ends recording changes on the registered elements.
	 * All elements that were removed from their container and not inserted into another one
	 * are treated as deleted and a delete change is created for them, inserted right after
	 * the change describing the removal from the container.
	 */
	def endRecording() {
		isRecording = false;
		resultChanges = postprocessRemovals(resultChanges);
	}
	
	@Data
	private static class PotentialRemoval {
		CompositeTransactionalChange transactionalChange;
		ConcreteChange removeChange;
		EObjectSubtractedEChange<?> atomicRemoveChange;
	}
	
	def postprocessRemovals(List<CompositeTransactionalChange> changes) {
		val Set<String> removedIds = new HashSet
		for (eChange : changes.flatMap [EChanges]) {
			switch(eChange) {
				EObjectSubtractedEChange<?> case eChange.isContainmentRemoval:
					removedIds += eChange.oldValueID
				EObjectAddedEChange<?> case eChange.isContainmentInsertion:
					removedIds -= eChange.newValueID
			}
		}
		if (removedIds.isEmpty) {
			return changes
		} 
		
		return changes.mapFixed [ compositeChange |
			val newChanges = compositeChange.changes.flatMapFixed [ change |
				if (change instanceof ConcreteChange) {
					val eChange = change.EChange
					switch (eChange) {
						EObjectSubtractedEChange<?> case 
							eChange.isContainmentRemoval && removedIds.contains(eChange.oldValueID): {
							val deleteChange = VitruviusChangeFactory.instance.createConcreteApplicableChange(
								new NotificationToEChangeConverter(idManager).createDeleteChange(eChange)
							)
							List.of(change, deleteChange)
						}
						default: List.of(change)
					}
				} else {
					List.of(change)
				}
			]
			// this condition relies on the fact that we only ever *add* changes
			if (newChanges.size == compositeChange.changes.size) {
				compositeChange
			} else {
				VitruviusChangeFactory.instance.createCompositeTransactionalChange(newChanges)
			}
		]
	}
	
	def List<TransactionalChange> getChanges() {
		return List.copyOf(resultChanges)
	}

	def isRecording() {
		return isRecording;
	}
	
	private def dispatch void recursivelyRemoveAdapter(Notifier notifier) {
		notifier.removeAdapter
	}
	
	private def dispatch void recursivelyRemoveAdapter(EObject object) {
		object.eContents().forEach[recursivelyRemoveAdapter];
		object.removeAdapter;
	}
	
	private def dispatch void recursivelyRemoveAdapter(Resource resource) {
		resource.contents.forEach[recursivelyRemoveAdapter]
		resource.removeAdapter;
	}
	
	private def dispatch void recursivelyRemoveAdapter(ResourceSet resourceSet) {
		resourceSet.resources.forEach[recursivelyRemoveAdapter]
		resourceSet.removeAdapter;
	}

	private def void removeAdapter(Notifier notifier) {
		notifier.eAdapters() -= this
	}
		
	private def dispatch void recursivelyAddAdapter(Notifier notifier) {
		notifier.addAdapter
	}
	
	private def dispatch void recursivelyAddAdapter(EObject object) {
		object.eContents().filter[eResource == object.eResource].forEach[recursivelyAddAdapter]
		object.addAdapter
	}
	
	private def dispatch void recursivelyAddAdapter(Resource resource) {
		resource.contents.forEach[recursivelyAddAdapter]
		resource.addAdapter
	}
	
	private def dispatch void recursivelyAddAdapter(ResourceSet resourceSet) {
		resourceSet.resources.forEach[recursivelyAddAdapter]
		resourceSet.addAdapter
	}

	private def void addAdapter(Notifier notifier) {
		val eAdapters = notifier.eAdapters()
		if (!eAdapters.contains(this)) {
			eAdapters.add(this)
		}
	}
}
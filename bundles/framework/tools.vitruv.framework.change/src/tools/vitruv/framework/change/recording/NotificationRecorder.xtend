package tools.vitruv.framework.change.recording

import org.eclipse.emf.common.notify.Adapter
import java.util.List
import tools.vitruv.framework.change.echange.EChange
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

/**
 * This {@link Adapter} records changes to the given model elements as {@link CompositeTransactionalChanges}.
 * Recording can be started with {@link #beginRecording} and ended with {@link #endRecording}. It is assumed 
 * that all elements that do not have a container when ending the recording have been deleted, resulting in
 * an appropriate delete change.
 */
class NotificationRecorder implements Adapter {
	Set<Notifier> rootObjects;
	boolean isRecording = false
	List<EChange> changes;
	List<CompositeTransactionalChange> resultChanges;
	val EChangeIdManager idManager
	
	new (EChangeIdManager idManager) {
		this.idManager = idManager;	
		rootObjects = newHashSet();
	}
	
	override getTarget() {
		return null;
	}

	override isAdapterForType(Object type) {
		return false;
	}

	override notifyChanged(Notification notification) {
		if (notification.newValue instanceof Notifier) {
			addToRecording(notification.newValue as Notifier);
		}
//		val notifier = notification.notifier;
//		if (notifier instanceof EObject) {
//			if (notifier.eContainer?.eResource !== notifier.eResource) {
//				notifier.recursivelyRemoveAdapter;
//			}
//			return;
//		}
		if (!isRecording) {
			return;
		}
		val newChanges = new NotificationToEChangeConverter(idManager).convert(new NotificationInfo(notification))
		changes += newChanges
		if (!newChanges.empty) {
			val transactionalChange = VitruviusChangeFactory.instance.createCompositeTransactionalChange
			newChanges.forEach[transactionalChange.addChange(VitruviusChangeFactory.instance.createConcreteApplicableChange(it))]
			resultChanges += transactionalChange
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
		rootObjects += notifier;
		recursivelyAddAdapter(notifier);
	}
	
	/**
	 * Removes the given elements and all its contained elements (resources, EObjects) from the recorder.
	 * @param notifier - the {@link Notifier} to remove the recorder from
	 */
	def void removeFromRecording(Notifier notifier) {
		rootObjects -= notifier;
		recursivelyRemoveAdapter(notifier);
	}
	
	/**
	 * Starts recording changes on the registered elements.
	 */
	def beginRecording() {
		if (!isRecording) {
			changes = newArrayList();
			resultChanges = newArrayList();
			isRecording = true;
		} else {
			throw new IllegalStateException
		}
	}

	/**
	 * Ends recording changes on the registered elements.
	 * All elements that were removed from their container and not inserted into another one
	 * are treated as deleted and a delete change is created for them, inserted right after
	 * the change describing the removal from the container.
	 */
	def endRecording() {
		isRecording = false;
		postprocessRemovals();
	}
	
	@Data
	private static class PotentialRemoval {
		CompositeTransactionalChange transactionalChange;
		ConcreteChange removeChange;
		EObjectSubtractedEChange<?> atomicRemoveChange;
	}
	
	def postprocessRemovals() {
		val potentialRemovals = newArrayList;
		for (resultChange : resultChanges) {
			for (concreteChange : resultChange.changes.filter(ConcreteChange)) {
				val eChange = concreteChange.EChange;
				if (eChange instanceof EObjectSubtractedEChange<?>) {
					if (eChange.isContainmentRemoval) {
						potentialRemovals.add(new PotentialRemoval(resultChange, concreteChange, eChange));
					}
				}
				if (eChange instanceof EObjectAddedEChange<?>) {
					if (eChange.isContainmentInsertion) {
						potentialRemovals.removeIf[it.atomicRemoveChange.oldValueID == eChange.newValueID]
					}
				}
			}
		}
		for (removal : potentialRemovals) {
			val deleteChange = new NotificationToEChangeConverter(idManager).createDeleteChange(removal.atomicRemoveChange);
			val currentChanges = removal.transactionalChange.changes;
			val indexOfRemoveChange = currentChanges.indexOf(removal.removeChange);
			val packagedDeleteChange = VitruviusChangeFactory.instance.createConcreteApplicableChange(deleteChange);
			currentChanges.add(indexOfRemoveChange + 1, packagedDeleteChange);
		}
	}

	def List<TransactionalChange> getChanges() {
		return resultChanges.filter(TransactionalChange).toList;
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
		val eAdapters = notifier.eAdapters();
		eAdapters.remove(this);
	}
		
	private def dispatch void recursivelyAddAdapter(Notifier notifier) {
		notifier.addAdapter
	}
	
	private def dispatch void recursivelyAddAdapter(EObject object) {
		object.eContents().filter[eResource == object.eResource].forEach[recursivelyAddAdapter];
		object.addAdapter;
	}
	
	private def dispatch void recursivelyAddAdapter(Resource resource) {
		resource.contents.forEach[recursivelyAddAdapter]
		resource.addAdapter;
	}
	
	private def dispatch void recursivelyAddAdapter(ResourceSet resourceSet) {
		resourceSet.resources.forEach[recursivelyAddAdapter]
		resourceSet.addAdapter;
	}

	private def void addAdapter(Notifier notifier) {
		val eAdapters = notifier.eAdapters();
		if (!eAdapters.contains(this)) {
			eAdapters.add(this);
		}
	}

}

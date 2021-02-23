package tools.vitruv.framework.change.recording

import java.util.ArrayList
import java.util.HashSet
import java.util.List
import java.util.Set
import org.eclipse.emf.common.notify.Adapter
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.ConcreteChange
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.echange.EChangeIdManager
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver

import static com.google.common.base.Preconditions.checkState
import static org.eclipse.emf.common.notify.Notification.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import static extension tools.vitruv.framework.change.echange.util.EChangeUtil.*
import org.eclipse.emf.ecore.EReference
import static com.google.common.base.Preconditions.checkNotNull
import static com.google.common.base.Preconditions.checkArgument
import static extension org.eclipse.emf.ecore.resource.Resource.RESOURCE__CONTENTS
import static extension org.eclipse.emf.ecore.resource.ResourceSet.RESOURCE_SET__RESOURCES

/**
 * Records changes to model elements as {@link CompositeTransactionalChanges}.
 * Recording can be started with {@link #beginRecording} and ended with {@link #endRecording}. The recorder assumes 
 * that all objects that have been removed from their containment reference without being added to a new containment
 * reference while changes were being recorded have been deleted, resulting in an appropriate delete change.
 */
class ChangeRecorder implements AutoCloseable {
	// invariant: if the recording adapter is installed on a notifier, it is also installed on all children
	// of the notifier.
	val NotificationRecorder recordingAdapter = new NotificationRecorder(this)
	val Set<Notifier> rootObjects = new HashSet
	boolean isRecording = false
	// not recording: unmodifiable list with results of last recording.
	// recording: modifiable list collecting the changes. Must never be handed out.
	// closed: null
	List<CompositeTransactionalChange> resultChanges = emptyList
	val NotificationToEChangeConverter converter
	val UuidGeneratorAndResolver uuidGeneratorAndResolver
	
	new (UuidGeneratorAndResolver uuidGeneratorAndResolver) {
		this.uuidGeneratorAndResolver = uuidGeneratorAndResolver
		this.converter = new NotificationToEChangeConverter(new EChangeIdManager(uuidGeneratorAndResolver))
	}
	
	/**
	 * Add the given elements and all its contained elements ({@link Resource}s, {@link EObject}s) to the recorder.
	 * 
	 * @param notifier - the {@link Notifier} to add the recorder to
	 */
	def void addToRecording(Notifier notifier) {
		checkNotDisposed()
		checkNotNull(notifier, "notifier")
		checkArgument(notifier.isInOurResourceSet,
			"cannot record changes in a different resource set than that of our UUID resolver!")
		
		if (rootObjects += notifier) {
			notifier.recursively [
				if (it instanceof EObject) uuidGeneratorAndResolver.registerEObject(it)
				addAdapter()
			]
		}
	}
	
	/**
	 * Removes the given elements and all its contained elements (resources, EObjects) from the recorder.
	 * @param notifier - the {@link Notifier} to remove the recorder from
	 */
	def void removeFromRecording(Notifier notifier) {
		checkNotDisposed()
		checkNotNull(notifier, "notifier")
		rootObjects -= notifier
		notifier.recursively [removeAdapter()]
	}
	
	/**
	 * Starts recording changes on the registered elements.
	 */
	def beginRecording() {
		checkNotDisposed()
		checkState(!isRecording, "This recorder is already recording!")
		isRecording = true
		resultChanges = new ArrayList
	}
	
	override close() {
		isRecording = false
		resultChanges = null
		val rootCopy = Set.copyOf(rootObjects)
		rootObjects.clear()
		rootCopy.forEach [recursively [removeAdapter()]]
	}
	
	def private checkNotDisposed() {
		checkState(resultChanges !== null, "This recorder has already been disposed!")
	}

	/**
	 * Ends recording changes on the registered elements.
	 * All elements that were removed from their container and not inserted into another one
	 * are treated as deleted and a delete change is created for them, inserted right after
	 * the change describing the removal from the container.
	 */
	def endRecording() {
		checkNotDisposed()
		isRecording = false
		resultChanges = List.copyOf(postprocessRemovals(resultChanges))
	}
	
	def private postprocessRemovals(List<CompositeTransactionalChange> changes) {
		if (changes.isEmpty) return changes
		
		val Set<String> removedIds = new HashSet
		for (eChange : changes.flatMap [EChanges]) {
			switch(eChange) {
				EObjectSubtractedEChange<?> case eChange.isContainmentRemoval:
					removedIds += eChange.oldValueID
				EObjectAddedEChange<?> case eChange.isContainmentInsertion:
					removedIds -= eChange.newValueID
			}
		}
	
		return if (removedIds.isEmpty) {
		 	changes
		} else {
			changes.mapFixed [
				insertChanges [ innerChange |
					switch (eChange: innerChange.EChange) {
						EObjectSubtractedEChange<?> case 
							eChange.isContainmentRemoval && removedIds.contains(eChange.oldValueID): 
							VitruviusChangeFactory.instance.createConcreteApplicableChange(
								converter.createDeleteChange(eChange)
							)
						default: null
					}
				]
			]
		}
	}
	
	/**
	 * Iterates over the {@code target} change tree and returns a modified tree, where all new changes
	 * provided by {@code inserter} have been inserted.
	 *  
	 * @param inserter a function that receives a {@link ConcreteChange} and returns a change to insert directly
	 * 		after the received {@link ConcreteChange}. Can return {@code null} to not insert a change.
	 */
	def private static CompositeTransactionalChange insertChanges(
		CompositeTransactionalChange target,
		(ConcreteChange)=>TransactionalChange inserter
	) {
		var List<TransactionalChange> resultChanges = null
		for (val subchanges = target.changes, var i = 0; i < subchanges.size; i += 1) {
			switch (change: subchanges.get(i)) {
				ConcreteChange: {
					resultChanges?.add(change)
					val additional = inserter.apply(change)
					if (additional !== null) {
						if (resultChanges === null) {
							resultChanges = new ArrayList(subchanges.size + 1)
							resultChanges.addAll(subchanges.subList(0, i + 1))
						}
						resultChanges.add(additional)
					}
				}
				CompositeTransactionalChange: {
					val result = insertChanges(change, inserter)
					if (result !== change && resultChanges === null) {
						resultChanges = new ArrayList(subchanges.size)
						resultChanges.addAll(subchanges.subList(0, i))
					}
					resultChanges?.add(result)
				}
				default: throw new IllegalStateException('''unexpected change type «change.class.simpleName»: «change»''')
			}
		}
		return if (resultChanges !== null) {
			VitruviusChangeFactory.instance.createCompositeTransactionalChange(resultChanges)
		} else target
	}
	
	def List<? extends TransactionalChange> getChanges() {
		checkNotDisposed()
		checkState(!isRecording, "This recorder is still recording!")
		resultChanges
	}

	def isRecording() {
		isRecording
	}
	
	// action indicates with the return value whether we should continue on the children.
	def private static dispatch void recursively(ResourceSet resourceSet, (Notifier)=>boolean action) {
		if (action.apply(resourceSet)) {
			resourceSet.resources.forEach [recursively(action)]
		}
	}
	
	def private static dispatch void recursively(Resource resource, (Notifier)=>boolean action) {
		if (action.apply(resource)) {
			resource.contents.forEach [recursively(action)]
		}
	}
	 
	def private static dispatch void recursively(EObject object, (Notifier)=>boolean action) {
		if (action.apply(object)) {
			for (val properContents = object.getAllProperContents(true); properContents.hasNext;) {
				if (!action.apply(properContents.next)) {
					properContents.prune()
				}
			}
		}
	}

	def private boolean removeAdapter(Notifier notifier) {
		// as long as a notifier is still registered as root object, we do not stop recording for it
		!rootObjects.contains(notifier) && (notifier.eAdapters -= recordingAdapter)
	}

	def private boolean addAdapter(Notifier notifier) {
		val eAdapters = notifier.eAdapters
		!eAdapters.contains(recordingAdapter) && (eAdapters += recordingAdapter)
	}
	
	def private boolean isInOurResourceSet(Notifier notifier) {
		switch (notifier) {
			case null: true
			EObject: isInOurResourceSet(notifier?.eResource)
			Resource: isInOurResourceSet(notifier?.resourceSet)
			ResourceSet: notifier == uuidGeneratorAndResolver.resourceSet
			default: throw new IllegalStateException("Unexpected notifier type: " + notifier.class.simpleName)
		}
	}
	
	@FinalFieldsConstructor
	private static class NotificationRecorder implements Adapter {
		extension val ChangeRecorder outer
		
		override notifyChanged(Notification notification) {
			switch (feature: notification.feature) {
				EReference case feature.isContainment,
				case notification.notifier instanceof Resource 
					&& notification.getFeatureID(Resource) === RESOURCE__CONTENTS,
				case notification.notifier instanceof ResourceSet 
					&& notification.getFeatureID(ResourceSet) === RESOURCE_SET__RESOURCES: {
					switch (notification.eventType) {
						case SET,
						case REMOVE: desinfect(notification.oldValue)
						case REMOVE_MANY: (notification.oldValue as Iterable<?>).forEach [desinfect()]
					}
					switch (notification.eventType) {
						case ADD,
						case SET: infect(notification.newValue)
						case ADD_MANY: (notification.newValue as Iterable<?>).forEach [infect()]
						// We currently resolve all containment references in #recursively, which is why we don’t
						// need to react to RESOLVE notifications here.
					}
				}
			}
			
			if (isRecording) {
				val newChanges = converter.convert(new NotificationInfo(notification))
				if (!newChanges.isEmpty) {
					resultChanges += VitruviusChangeFactory.instance.createCompositeTransactionalChange(
						newChanges.map [VitruviusChangeFactory.instance.createConcreteApplicableChange(it)]
					)
				}
			}
		}
		
		private def infect(Object newValue) {
			(newValue as Notifier)?.recursively [addAdapter()]
		}
		
		private def desinfect(Object oldValue) {
			(oldValue as Notifier)?.recursively [removeAdapter()]
		}
		
		override getTarget() { null }
	
		override isAdapterForType(Object type) { false }
	
		override setTarget(Notifier newTarget) {}
	}
}
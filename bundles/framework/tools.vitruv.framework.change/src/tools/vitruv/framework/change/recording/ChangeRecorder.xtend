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
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.echange.EChangeIdManager
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange

import static com.google.common.base.Preconditions.checkState
import static org.eclipse.emf.common.notify.Notification.*

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import static extension tools.vitruv.framework.change.echange.EChangeUtil.*
import org.eclipse.emf.ecore.EReference
import static com.google.common.base.Preconditions.checkNotNull
import static com.google.common.base.Preconditions.checkArgument
import static extension org.eclipse.emf.ecore.resource.Resource.RESOURCE__CONTENTS
import static extension org.eclipse.emf.ecore.resource.Resource.RESOURCE__IS_LOADED
import static extension org.eclipse.emf.ecore.resource.ResourceSet.RESOURCE_SET__RESOURCES
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.applyForward
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.applyBackward
import tools.vitruv.framework.change.echange.id.IdResolver
import tools.vitruv.framework.change.echange.feature.reference.UpdateReferenceEChange
import tools.vitruv.framework.change.echange.EChange

/**
 * Records changes to model elements as a {@link TransactionalChange}.
 * Recording can be started with {@link #beginRecording} and ended with {@link #endRecording}. The recorder assumes 
 * that all objects that have been removed from their containment reference without being added to a new containment
 * reference while changes were being recorded have been deleted, resulting in an appropriate delete change.
 * The recorder considers resources being loaded as existing and does thus not produce changes for it.
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
	List<EChange> resultChanges = emptyList
	val NotificationToEChangeConverter converter
	val IdResolver idResolver
	val EChangeIdManager eChangeIdManager
	val Set<EObject> existingObjects = new HashSet
	val Set<Notifier> toDesinfect = new HashSet
	val ResourceSet resourceSet

	new(ResourceSet resourceSet) {
		this.resourceSet = resourceSet
		this.idResolver = IdResolver.create(resourceSet)
		this.eChangeIdManager = new EChangeIdManager(idResolver)
		this.converter = new NotificationToEChangeConverter([ affectedObject, addedObject |
			isCreateChange(affectedObject, addedObject)
		])
	}

	private def boolean isCreateChange(EObject affectedObject, EObject addedObject) {
		// We do not check the containment of the reference, because an element may be inserted into a non-containment
		// reference before inserting it into a containment reference so that the create change has to be added
		// for the insertion into the non-containment reference
		var create = addedObject !== null && !existingObjects.contains(addedObject)
		// Look if the new value has no resource or if it is a reference change, if the resource of the affected
		// object is the same. Otherwise, the create has to be handled by an insertion/reference in that resource, as
		// it can be potentially a reference to a third party model, for which no create shall be instantiated		
		create = create && (addedObject.eResource === null || affectedObject === null ||
			addedObject.eResource == affectedObject.eResource)
		if(create) existingObjects += addedObject
		return create;
	}

	/**
	 * Add the given elements and all its contained elements ({@link Resource}s, {@link EObject}s) to the recorder.
	 * Register all existing elements at the {@link IdResolverAndRepository} with existing UUIDs.
	 * 
	 * @param notifier - the {@link Notifier} to add the recorder to
	 * @throws IllegalStateException if the recorder is already disposed
	 */
	def void addToRecording(Notifier notifier) {
		checkNotDisposed()
		checkNotNull(notifier, "notifier")
		checkArgument(notifier.isInOurResourceSet,
			"cannot record changes in a different resource set than that of our ID resolver!")

		if (rootObjects += notifier) {
			notifier.recursively [
				if(it instanceof EObject) existingObjects.add(it)
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
		notifier.recursively[removeAdapter()]
	}

	/**
	 * Starts recording changes on the registered elements.
	 */
	def beginRecording() {
		checkNotDisposed()
		checkState(!isRecording, "This recorder is already recording!")
		toDesinfect.forEach[recursively [removeAdapter()]]
		toDesinfect.clear()
		isRecording = true
		resultChanges = new ArrayList
	}

	override close() {
		isRecording = false
		resultChanges = null
		val rootCopy = Set.copyOf(rootObjects)
		rootObjects.clear()
		rootCopy.forEach[recursively [removeAdapter()]]
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
	def TransactionalChange endRecording() {
		checkNotDisposed()
		checkState(isRecording, "This recorder is not recording")
		isRecording = false
		resultChanges = List.copyOf(resultChanges.postprocessRemovals().assignIds())
		idResolver.endTransaction()
		return getChange()
	}

	def private List<EChange> assignIds(List<EChange> changes) {
		changes.toList.reverseView.forEach[applyBackward]
		changes.forEach[assignIds]
		changes
	}

	def private void assignIds(EChange change) {
		eChangeIdManager.setOrGenerateIds(change)
		change.applyForward(idResolver)
	}

	def private postprocessRemovals(List<EChange> changes) {
		if(changes.isEmpty) return changes

		val Set<EObject> removedElements = new HashSet
		for (eChange : changes) {
			switch (eChange) {
				EObjectSubtractedEChange<?> case eChange.isContainmentRemoval:
					removedElements += eChange.oldValue
				EObjectAddedEChange<?> case eChange.isContainmentInsertion:
					removedElements -= eChange.newValue
			}
		}

		return if (removedElements.isEmpty) {
			changes
		} else {
			changes.insertChanges [ eChange |
				switch (eChange) {
					EObjectSubtractedEChange<?> case eChange.isContainmentRemoval &&
						removedElements.contains(eChange.oldValue):
						converter.createDeleteChange(eChange)
					default:
						null
				}
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
	def private static List<EChange> insertChanges(
		List<EChange> changes,
		(EChange)=>EChange inserter
	) {
		var List<EChange> resultEChanges = null
		for (var k = 0; k < changes.size; k++) {
			val eChange = changes.get(k)
			resultEChanges?.add(eChange)
			val additional = inserter.apply(eChange)
			if (additional !== null) {
				if (resultEChanges === null) {
					resultEChanges = new ArrayList(changes.size + 1)
					resultEChanges.addAll(changes.subList(0, k + 1))
				}
				resultEChanges.add(additional)
			}
		}
		return if (resultEChanges !== null) {
			resultEChanges
		} else {
			changes
		}
	}

	def TransactionalChange getChange() {
		checkNotDisposed()
		checkState(!isRecording, "This recorder is still recording!")
		VitruviusChangeFactory.instance.createTransactionalChange(resultChanges)
	}

	def isRecording() {
		isRecording
	}

	// action indicates with the return value whether we should continue on the children.
	def private static dispatch void recursively(ResourceSet resourceSet, (Notifier)=>boolean action) {
		if (action.apply(resourceSet)) {
			resourceSet.resources.forEach[recursively(action)]
		}
	}

	def private static dispatch void recursively(Resource resource, (Notifier)=>boolean action) {
		if (action.apply(resource)) {
			resource.contents.forEach[recursively(action)]
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
			ResourceSet: notifier == resourceSet
			default: throw new IllegalStateException("Unexpected notifier type: " + notifier.class.simpleName)
		}
	}

	@FinalFieldsConstructor
	private static class NotificationRecorder implements Adapter {
		extension val ChangeRecorder outer

		val Set<Resource> currentlyLoadingResources = new HashSet

		override notifyChanged(Notification notification) {
			notification.handleAdaptersForResourceAndResourceSetChanges()
			val newChanges = notification.extractRelevantChanges()
			if (isRecording) {
				resultChanges += newChanges
			}
		}

		private def handleAdaptersForResourceAndResourceSetChanges(Notification notification) {
			// EMF sets the "loaded" flag of resources to true before notification for adding the loaded contents are
			// emitted and then emits the notification for setting the "loaded" flag afterwards.
			// Thus, we prevent the notification recorder to be injected until the according notification for setting
			// the "loaded" flag is sent
			if (notification.notifier instanceof ResourceSet &&
				notification.getFeatureID(ResourceSet) === RESOURCE_SET__RESOURCES) {
				switch (notification.eventType) {
					case ADD:
						(notification.newValue as Resource).startLoadingResource()
					case ADD_MANY:
						(notification.newValue as Iterable<? extends Resource>).forEach [
							startLoadingResource()
						]
				}
			}
			switch (feature: notification.feature) {
				EReference case feature.isContainment,
				case notification.notifier instanceof Resource &&
					notification.getFeatureID(Resource) === RESOURCE__CONTENTS,
				case notification.notifier instanceof ResourceSet &&
					notification.getFeatureID(ResourceSet) === RESOURCE_SET__RESOURCES: {
					switch (notification.eventType) {
						case SET,
						case REMOVE: desinfect(notification.oldValue)
						case REMOVE_MANY: (notification.oldValue as Iterable<?>).forEach[desinfect()]
					}
					switch (notification.eventType) {
						case ADD,
						case SET: infect(notification.newValue)
						case ADD_MANY: (notification.newValue as Iterable<?>).forEach[infect()]
					// We currently resolve all containment references in #recursively, which is why we don’t
					// need to react to RESOLVE notifications here.
					}
				}
				case notification.notifier instanceof Resource &&
					notification.getFeatureID(Resource) === RESOURCE__IS_LOADED:
					(notification.notifier as Resource).finishLoadingResource()
			}
		}

		private def Iterable<? extends EChange> extractRelevantChanges(Notification notification) {
			val changes = if (notification.affectsLoadingResource || notification.affectsUnloadingResource) {
					// If resource is being loaded or unloaded, do not process the changes 
					emptyList
				} else {
					converter.convert(new NotificationInfo(notification))
				}
			if (!changes.isEmpty) {
				// Register any added object as existing, even if we are not recording
				changes.forEach [
					if (it instanceof EObjectAddedEChange<?>) {
						existingObjects += newValue
						if(it instanceof UpdateReferenceEChange<?>) existingObjects += affectedEObject
					}
				]
			}
			return changes
		}

		private def void startLoadingResource(Resource resource) {
			currentlyLoadingResources += resource
		}

		private def void finishLoadingResource(Resource resource) {
			currentlyLoadingResources -= resource
			resource.recursively [
				if(it instanceof EObject) existingObjects.add(it)
				addAdapter()
			]
		}

		private def boolean affectsLoadingResource(Notification notification) {
			val newEObject = if(notification.newValue instanceof EObject) notification.newValue as EObject else null
			return newEObject?.eResource !== null && currentlyLoadingResources.contains(newEObject.eResource)
		}

		private def boolean affectsUnloadingResource(Notification notification) {
			if (notification.notifier instanceof Resource) {
				val resource = notification.notifier as Resource
				val resourceSet = resource.resourceSet
				// for an unload, resource must not be flagged as loaded and there must still be a persistence at the URI
				// (otherwise resource was deleted)
				return !resource.isLoaded && resourceSet !== null &&
					resourceSet.URIConverter.exists(resource.URI, emptyMap)
			} else {
				return false
			}
		}

		private def infect(Object newValue) {
			(newValue as Notifier)?.recursively[toDesinfect -= it; addAdapter()]
		}

		private def desinfect(Object oldValue) {
			// Defer desinfect to ensure that elements moved from removed element containments to new containments are recognized properly
			if(oldValue instanceof Notifier) toDesinfect += oldValue
		}

		override getTarget() { null }

		override isAdapterForType(Object type) { false }

		override setTarget(Notifier newTarget) {}
	}
}

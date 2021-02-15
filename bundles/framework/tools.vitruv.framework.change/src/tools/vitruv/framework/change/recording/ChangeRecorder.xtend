package tools.vitruv.framework.change.recording

import java.util.ArrayList
import java.util.HashSet
import java.util.List
import java.util.Map
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
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver

import static com.google.common.base.Preconditions.checkState
import static org.eclipse.emf.common.notify.Notification.*

import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import static extension org.eclipse.emf.ecore.util.EcoreUtil.*
import static extension tools.vitruv.framework.change.echange.util.EChangeUtil.*
import org.eclipse.emf.ecore.EReference
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import java.util.HashMap

/**
 * Records changes to the given model elements as {@link CompositeTransactionalChanges}.
 * Recording can be started with {@link #beginRecording} and ended with {@link #endRecording}. It is assumed 
 * that all elements that do not have a container when ending the recording have been deleted, resulting in
 * an appropriate delete change.
 */
class ChangeRecorder implements AutoCloseable {
	val Set<Notifier> rootObjects = new HashSet
	boolean isRecording = false
	// not recording: unmodifiable list with results of last recording.
	// recording: modifiable list collecting the changes. Must never be handed out.
	// last changes discarded: null
	List<CompositeTransactionalChange> resultChanges = emptyList
	val NotificationToEChangeConverter converter
	val NotificationRecorder recordingAdapter = new NotificationRecorder(this)
	
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
		if (rootObjects += notifier) {
			notifier.recursively [
				addAdapter()
				if (it instanceof EObject) uuidGeneratorAndResolver.registerEObject(it)
			]
		}
	}
	
	/**
	 * Removes the given elements and all its contained elements (resources, EObjects) from the recorder.
	 * @param notifier - the {@link Notifier} to remove the recorder from
	 */
	def void removeFromRecording(Notifier notifier) {
		checkNotDisposed()
		notifier.recursively [
			removeAdapter()
			rootObjects -= notifier
		]
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
		rootObjects.forEach [recursively [removeAdapter()]]
		rootObjects.clear()
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
	
		val withDeletes = if (removedIds.isEmpty) {
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
		
		// FIXME this is legacy behaviour that some applications, unfortunately, started to rely on. This
		// post-processing is *not* necessary anymore because there are no wrong delete+create sequences created
		// by change recording anymore. All applications should be fixed to not wrongly produce such sequences and
		// then the code below should be removed. The problem with this code is that recreation can carry semantic
		// value and must not simply be ignored.
		val createdObjects = withDeletes.lastOccurencePerAffectedObject(trickTypeSystem(CreateEObject))
		val deletedObjects = withDeletes.lastOccurencePerAffectedObject(trickTypeSystem(DeleteEObject))
		val ignoreDeletions = deletedObjects.entrySet.filter [
			createdObjects.getOrDefault(key, Integer.MIN_VALUE) > value
		].toSet
		
		return if (ignoreDeletions.isEmpty) {
			withDeletes
		} else {
			withDeletes.mapFixed [
				filterChanges [ innerChange |
					switch (eChange: innerChange.EChange) {
						EObjectExistenceEChange<?>: !ignoreDeletions.contains(eChange.affectedEObjectID)
						default: true
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
	
	def private static CompositeTransactionalChange filterChanges(
		CompositeTransactionalChange target,
		(ConcreteChange)=>boolean filter
	) {
		var List<TransactionalChange> resultChanges = null
		for (val subchanges = target.changes, var i = 0; i < subchanges.size; i += 1) {
			switch (change: subchanges.get(i)) {
				ConcreteChange: {
					if (filter.apply(change)) {
						resultChanges?.add(change)
					} else {
						if (resultChanges === null) {
							resultChanges = new ArrayList(subchanges.size - 1)
							resultChanges.addAll(subchanges.subList(0, i))
						}
					}
				}
				CompositeTransactionalChange: {
					val result = filterChanges(change, filter)
					if (result !== change && resultChanges === null) {
						resultChanges = new ArrayList(subchanges.size)
						resultChanges.addAll(subchanges.subList(0, i))
					}
					if (resultChanges !== null && result.containsConcreteChange) {
						resultChanges.add(result)
					}
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
		checkNotDisposed()
		isRecording
	}
	
	def private static dispatch void recursively(ResourceSet resourceSet, (Notifier)=>void action) {
		resourceSet.resources.forEach [recursively(action)]
		action.apply(resourceSet)
	}
	
	def private static dispatch void recursively(Resource resource, (Notifier)=>void action) {
		resource.contents.forEach [recursively(action)]
		action.apply(resource)
	}
	 
	def private static dispatch void recursively(EObject object, (Notifier)=>void action) {
		object.getAllProperContents(true).forEach(action)
		action.apply(object)
	}

	def private void removeAdapter(Notifier notifier) {
		notifier.eAdapters -= recordingAdapter
	}

	def private void addAdapter(Notifier notifier) {
		val eAdapters = notifier.eAdapters
		if (!eAdapters.contains(recordingAdapter)) {
			eAdapters.add(recordingAdapter)
		}
	}
	
 	def private static Map<String, Integer> lastOccurencePerAffectedObject(
 		List<? extends TransactionalChange> changes,
 		Class<? extends EObjectExistenceEChange<?>> changeType
 	) {
 		new HashMap(
 			changes.flatMap [EChanges]
 				.indexed
 				.filter [changeType.isInstance(value)]
	 			.groupBy [(value as EObjectExistenceEChange<?>).affectedEObjectID]
	 			.mapValues [maxBy [key].key]
 		)
	}
	
	def private static <T> Class<T> trickTypeSystem(Class<?> clazz) {
		clazz as Class<T>
	}
	
	@FinalFieldsConstructor
	private static class NotificationRecorder implements Adapter {
		extension val ChangeRecorder outer
		
		override notifyChanged(Notification notification) {
			switch (feature: notification.feature) {
				case notification.notifier instanceof Resource,
				case notification.notifier instanceof ResourceSet,
				EReference case feature.isContainment: {
					switch (notification.eventType) {
						case SET,
						case MOVE,
						case REMOVE,
						case REMOVE_MANY: desinfect(notification.oldValue)
					}
					switch (notification.eventType) {
						case ADD,
						case ADD_MANY,
						case SET,
						case RESOLVE,
						case MOVE: infect(notification.newValue)
					}
				}
			}

			if (!isRecording) {
				return
			}
			val newChanges = converter.convert(new NotificationInfo(notification))
			if (!newChanges.isEmpty) {
				resultChanges += VitruviusChangeFactory.instance.createCompositeTransactionalChange(
					newChanges.map [VitruviusChangeFactory.instance.createConcreteApplicableChange(it)]
				)
			}
		}
		
		private def infect(Object newValue) {
			if (newValue instanceof Notifier) {
				newValue.recursively [addAdapter()]
			}
		}
		
		private def desinfect(Object oldValue) {
			if (oldValue instanceof Notifier) {
				oldValue.recursively [removeAdapter()]
			}
		}
		
		override getTarget() { null }
	
		override isAdapterForType(Object type) { false }
	
		override setTarget(Notifier newTarget) {}
	}
}
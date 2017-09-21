package tools.vitruv.framework.change.recording

import java.util.Collection
import java.util.List
import java.util.Set
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.change.util.ChangeRecorder
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.echange.EChangeIdManager
import tools.vitruv.framework.change.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.change.uuid.UuidResolver
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import com.google.common.collect.Multimap
import org.eclipse.emf.ecore.EObject
import com.google.common.collect.ArrayListMultimap
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.impl.ConcreteApplicableChangeImpl
import tools.vitruv.framework.change.echange.eobject.DeleteEObject

class AtomicEmfChangeRecorder {
	private static val USE_LEGACY_RECORDER = true;

	val Set<Notifier> elementsToObserve
	val boolean updateTuids;
	val NotificationRecorder changeRecorder;
	val AtomicChangeRecorder legacyChangeRecorder;
	var List<TransactionalChange> changes;
	val UuidResolver globalUuidResolver;
	val UuidGeneratorAndResolver localUuidGeneratorAndResolver;
	val EChangeIdManager eChangeIdManager;

	/**
	 * Constructors which updates {@link Tuid}s.
	 * 
	 * @param uuidProviderAndResolver -
	 * 		the {@link UuidProviderAndResolver} for ID generation
	 * @param strictMode -
	 * 		specifies whether exceptions shall be thrown if no ID exists for an element that should already have one.
	 * 		Should be set to <code>false</code> if model is not recorded from beginning
	 */
	new(UuidResolver globalUuidResolver, UuidGeneratorAndResolver localUuidGeneratorAndResolver, boolean strictMode) {
		this(globalUuidResolver, localUuidGeneratorAndResolver, strictMode, true);
	}

	/**
	 * Constructor for AtomicEMFChangeRecorder.
	 * 
	 * @param uuidProviderAndResolver -
	 * 		the {@link UuidProviderAndResolver} for ID generation
	 * @param strictMode -
	 * 		specifies whether exceptions shall be thrown if no ID exists for an element that should already have one.
	 * 		Should be set to <code>false</code> if model is not recorded from beginning
	 * @param updateTuids -
	 * 		specifies whether TUIDs shall be updated or not.
	 */
	new(UuidResolver globalUuidResolver, UuidGeneratorAndResolver localUuidGeneratorAndResolver, boolean strictMode,
		boolean updateTuids) {
		this.elementsToObserve = newHashSet();
		this.updateTuids = updateTuids;
		this.legacyChangeRecorder = new AtomicChangeRecorder();
		this.globalUuidResolver = globalUuidResolver;
		this.localUuidGeneratorAndResolver = localUuidGeneratorAndResolver;
		this.eChangeIdManager = new EChangeIdManager(globalUuidResolver, localUuidGeneratorAndResolver, strictMode)
		this.changeRecorder = new NotificationRecorder(eChangeIdManager);
	}

	def void beginRecording() {
		if (USE_LEGACY_RECORDER) {
			legacyChangeRecorder.reset;
			legacyChangeRecorder.beginRecording(this.elementsToObserve);
		} else {
			changeRecorder.beginRecording(this.elementsToObserve);
		}
	}

	def void addToRecording(Notifier elementToObserve) {
		this.elementsToObserve += elementToObserve;
		if (isRecording) {
			if (USE_LEGACY_RECORDER) {
//			val elements = newArrayList;
//			elements += elementsToObserve;
//			if (elementToObserve instanceof Resource) {
//				val iter = elementToObserve.allContents
//				while (iter.hasNext) {
//					elements += iter.next;	
//				}
//			}
			// changeRecorder.beginRecording(elements);
				legacyChangeRecorder.beginRecording(elementsToObserve);
			} else {
				elementsToObserve.forEach[changeRecorder.addAdapter(it)];
			}
		}
	}

	def void removeFromRecording(Notifier elementToObserve) {
		this.elementsToObserve -= elementToObserve;
		if (isRecording) {
			if (USE_LEGACY_RECORDER) {
				legacyChangeRecorder.beginRecording(elementsToObserve);
			} else {
				elementToObserve.eAdapters.remove(changeRecorder);
			}
		}
	}

	/** Stops recording without returning a result */
	def void stopRecording() {
		if(!isRecording) {
			throw new IllegalStateException();
		}
		if (USE_LEGACY_RECORDER) {
			legacyChangeRecorder.endRecording();
		} else {
			changeRecorder.endRecording();
		}
	}

	def void endRecording() {
		if(!isRecording) {
			throw new IllegalStateException();
		}
		var List<TransactionalChange> changes;
		if (USE_LEGACY_RECORDER) {
			legacyChangeRecorder.endRecording();
			// Only take those that do not contain only objectsToAttach (I don't know why)
			val relevantChangeDescriptions = legacyChangeRecorder.changeDescriptions.filter [
				!(objectChanges.isEmpty && resourceChanges.isEmpty)
			].toList
			relevantChangeDescriptions.reverseView.forEach[applyAndReverse];
			changes = relevantChangeDescriptions.filterNull.map[createModelChange(updateTuids)].filterNull.toList;
		} else {
			changeRecorder.endRecording();
			changes = changeRecorder.changes.map[VitruviusChangeFactory.instance.createConcreteApplicableChange(it)];
		}

		
		removeDuplicateCreates(changes);
		removeCreateFollowedByDelete(changes);
		reorderChanges(changes);
		// Allow null provider and resolver for test purposes
		if(localUuidGeneratorAndResolver !== null && globalUuidResolver !== null) {
			changes.forEach[EChanges.forEach[eChangeIdManager.setOrGenerateIds(it)]]
		}
		this.changes = changes;
	}
	
	def removeCreateFollowedByDelete(List<TransactionalChange> changes) {
		val createdObjects = changes.generateCreateChangesMultimap
		val deletedObjects = changes.generateDeleteChangesMultimap
		for (var i = 0; i < changes.size; i++) {
			val currentDeletedObjects = deletedObjects.get(changes.get(i))
			for (var k = i + 1; k < changes.size; k++) {
				for (var object = 0; object < currentDeletedObjects.size; object++) {
					if (createdObjects.get(changes.get(k)).contains(currentDeletedObjects.get(object))) {
						removeCreateOrDelete(changes.get(i) as CompositeTransactionalChange, currentDeletedObjects.get(object), false);
						removeCreateOrDelete(changes.get(k) as CompositeTransactionalChange, currentDeletedObjects.get(object), true);
					}
				}			
			}
		}
	}

	def boolean isRecording() {
		if (USE_LEGACY_RECORDER) {
			return legacyChangeRecorder.isRecording()
		} else {
			return changeRecorder.isRecording()
		}
	}

	def void dispose() {
		if (USE_LEGACY_RECORDER) {
			legacyChangeRecorder.dispose()
		}
	}
	
	// Necessary because of stupid Xtend generic type inference
	private static def Iterable<CreateEObject<?>> typeCreateChanges(Iterable<?> iterable) {
		val result = newArrayList
		for (item : iterable) {
			result += item as CreateEObject<?>;
		}
		return result
	}
	
	// Necessary because of stupid Xtend generic type inference
	private static def Iterable<DeleteEObject<?>> typeDeleteChanges(Iterable<?> iterable) {
		val result = newArrayList
		for (item : iterable) {
			result += item as DeleteEObject<?>;
		}
		return result
	}
	
	/**
	 * Removes duplicate create operations, which can occur, if an element is created and inserted into 
	 * a resource (CreateAndInsertRoot) and afterwards also inserted into a containment relation
	 * (CreateAndInsertNonRoot), because the resource creation is performed lazy and thus the containment
	 * insertion operation is interpreted as a creation as well.
	 */
	 // TODO In fact, we have the same problem with remove changes. We have to monitor the concrete models instead
	 // of the resources to get rid of that
	private def void removeDuplicateCreates(List<TransactionalChange> changes) {
		val createdObjects = changes.generateCreateChangesMultimap
		for (var i = 0; i < changes.size; i++) {
			val currentCreatedObjects = createdObjects.get(changes.get(i))
			for (var k = i + 1; k < changes.size; k++) {
				for (var object = 0; object < currentCreatedObjects.size; object++) {
					if (createdObjects.get(changes.get(k)).contains(currentCreatedObjects.get(object))) {
						val affectedChange = changes.get(k)
						removeCreateOrDelete(affectedChange as CompositeTransactionalChange, currentCreatedObjects.get(object), true);
					}
				}			
			}
		}
	}
	
	private def void removeCreateOrDelete(CompositeTransactionalChange change, EObject createdObject, boolean create) {
		for (var i = 0; i < change.changes.size; i++) {
			val currentChange = change.changes.get(i);
			if (currentChange instanceof ConcreteApplicableChangeImpl) {
				val eChange = currentChange.EChange
				if (create) {
					if (eChange instanceof CreateEObject<?>) {
						if (eChange.affectedEObject == createdObject) {
							change.changes.remove(i);
						}
					}
				} else {
					if (eChange instanceof DeleteEObject<?>) {
						if (eChange.affectedEObject == createdObject) {
							change.changes.remove(i);
						}
					}
				}
			} else if (currentChange instanceof CompositeTransactionalChange) {
				removeCreateOrDelete(currentChange, createdObject, create);			
			}
		} 
	}
	
	/** 
	 * Reorders changes so that create changes are always present before insertion changes of the same element.
	 * Some metamodels produce insertions of elements before inserting them into a containment, resulting in a
	 * create change after an insertion, which would not be resolvable.
	 */
	private def void reorderChanges(List<TransactionalChange> changes) {
		val createdObjects = changes.generateCreateChangesMultimap
		var boolean reordered = true;
		var counter = 0;
		while(reordered) {
			reordered = false;
			for (var i = 0; i < changes.size && !reordered; i++) {	
				val affectedEObjects = changes.get(i).affectedEObjects;
				for (var k = i + 1; k < changes.size && !reordered; k++) {
					for (var object = 0; object < affectedEObjects.size && !reordered; object++) {
						if (createdObjects.get(changes.get(k)).contains(affectedEObjects.get(object))) {
							changes.add(i, changes.remove(k));
							reordered = true;
						}
					}
				}			
			}
			// We are playing ping-pong: There must be duplicate create changes so changes are repeatedly reordered
			if (counter > changes.size * changes.size) {
				throw new IllegalStateException("Reordering is in endless loop")
			}
			counter++;
		}
		
		
 	}
 	
 	private def generateDeleteChangesMultimap(List<TransactionalChange> changes) {
		val Multimap<TransactionalChange, EObject> deletedObjects = ArrayListMultimap.create;
		for (change : changes) {
			val deleteChange = change.EChanges.filter(DeleteEObject).typeDeleteChanges;
			deletedObjects.putAll(change, deleteChange.map[affectedEObject]);
		}
		return deletedObjects;
	}
 	
	private def generateCreateChangesMultimap(List<TransactionalChange> changes) {
		val Multimap<TransactionalChange, EObject> createdObjects = ArrayListMultimap.create;
		for (change : changes) {
			val createChanges = change.EChanges.filter(CreateEObject).typeCreateChanges;
			createdObjects.putAll(change, createChanges.map[affectedEObject]);
		}
		return createdObjects;
	}

	public def List<TransactionalChange> getChanges() {
		return changes;
	}

	private def createModelChange(ChangeDescription changeDescription, boolean updateTuids) {
		var TransactionalChange result = null;
		result = VitruviusChangeFactory.instance.createTransactionalChange(changeDescription)
		changeDescription.applyAndReverse()
		return result;
	}

	/**
	 * A change recorder that restarts after each change notification to get atomic change descriptions.
	 */
	static class AtomicChangeRecorder extends ChangeRecorder {
		private Collection<?> rootObjects;
		private boolean isDisposed = false;
		@Accessors(PUBLIC_GETTER)
		private var List<ChangeDescription> changeDescriptions;

		new() {
			setRecordingTransientFeatures(false);
			setResolveProxies(true);
			reset();
		}

		public def void reset() {
			this.changeDescriptions = newArrayList;
		}

		override dispose() {
			this.isDisposed = true;
			super.dispose()
		}

		override notifyChanged(Notification notification) {
			if(isRecording && !isDisposed) {
				super.notifyChanged(notification);
				endRecording();
				beginRecording(rootObjects);
			}
		}

		override beginRecording(Collection<?> rootObjects) {
			if(!isDisposed) {
				this.rootObjects = rootObjects;
				super.beginRecording(rootObjects);
			}
		}

		override endRecording() {
			if(!isDisposed) {
				changeDescriptions += super.endRecording();
			}
			return changeDescription;
		}
	}

}

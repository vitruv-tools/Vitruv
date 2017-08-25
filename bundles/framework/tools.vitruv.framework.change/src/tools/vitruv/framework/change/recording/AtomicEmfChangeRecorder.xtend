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
import tools.vitruv.framework.change.echange.compound.CreateAndInsertEObject
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.impl.ConcreteApplicableChangeImpl
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteEObject
import tools.vitruv.framework.change.echange.eobject.DeleteEObject

class AtomicEmfChangeRecorder {
	val Set<Notifier> elementsToObserve
	val boolean updateTuids;
	var List<TransactionalChange> changes;
	val AtomicChangeRecorder changeRecorder;
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
		this.changeRecorder = new AtomicChangeRecorder();
		this.globalUuidResolver = globalUuidResolver;
		this.localUuidGeneratorAndResolver = localUuidGeneratorAndResolver;
		this.eChangeIdManager = new EChangeIdManager(globalUuidResolver, localUuidGeneratorAndResolver, strictMode)
	}

	def void beginRecording() {
		changeRecorder.reset;
		changeRecorder.beginRecording(this.elementsToObserve);
	}

	def void addToRecording(Notifier elementToObserve) {
		this.elementsToObserve += elementToObserve;
		if(isRecording) {
//			val elements = newArrayList;
//			elements += elementsToObserve;
//			if (elementToObserve instanceof Resource) {
//				val iter = elementToObserve.allContents
//				while (iter.hasNext) {
//					elements += iter.next;	
//				}
//			}
			// changeRecorder.beginRecording(elements);
			changeRecorder.beginRecording(elementsToObserve);

		}
	}

	def void removeFromRecording(Notifier elementToObserve) {
		this.elementsToObserve -= elementToObserve;
		if(isRecording) {
			changeRecorder.beginRecording(elementsToObserve);
		}
	}

	/** Stops recording without returning a result */
	def void stopRecording() {
		if(!isRecording) {
			throw new IllegalStateException();
		}
		changeRecorder.endRecording();
	}

	def void endRecording() {
		if(!isRecording) {
			throw new IllegalStateException();
		}
		changeRecorder.endRecording();
		// Only take those that do not contain only objectsToAttach (I don't know why)
		val relevantChangeDescriptions = changeRecorder.changeDescriptions.filter [
			!(objectChanges.isEmpty && resourceChanges.isEmpty)
		].toList
		relevantChangeDescriptions.reverseView.forEach[applyAndReverse];
		val changes = relevantChangeDescriptions.filterNull.map[createModelChange(updateTuids)].filterNull.toList;
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

	// Necessary because of stupid Xtend generic type inference
	private static def Iterable<CreateAndInsertEObject<?, ?>> typeCreateAndInsertChanges(Iterable<?> iterable) {
		val result = newArrayList
		for (item : iterable) {
			result += item as CreateAndInsertEObject<?, ?>;
		}
		return result
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
	private static def Iterable<RemoveAndDeleteEObject<?, ?>> typeRemoveAndDeleteChanges(Iterable<?> iterable) {
		val result = newArrayList
		for (item : iterable) {
			result += item as RemoveAndDeleteEObject<?, ?>;
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
					if (eChange instanceof CreateAndInsertEObject<?,?>) {
						if (eChange.createChange.affectedEObject == createdObject) {
							change.changes.remove(i);
							change.changes.add(VitruviusChangeFactory.instance.createConcreteApplicableChange(eChange.insertChange));
						}
					}
				} else {
					if (eChange instanceof RemoveAndDeleteEObject<?,?>) {
						if (eChange.deleteChange.affectedEObject == createdObject) {
							change.changes.remove(i);
							change.changes.add(VitruviusChangeFactory.instance.createConcreteApplicableChange(eChange.removeChange));
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
		val Multimap<TransactionalChange, EObject> createdObjects = ArrayListMultimap.create;
		for (change : changes) {
			val createAndInsertChanges = change.EChanges.filter(RemoveAndDeleteEObject).typeRemoveAndDeleteChanges
			val createChanges = change.EChanges.filter(DeleteEObject).typeDeleteChanges + createAndInsertChanges.map[deleteChange];
			createdObjects.putAll(change, createChanges.map[affectedEObject]);
		}
		return createdObjects;
	}
 	
	private def generateCreateChangesMultimap(List<TransactionalChange> changes) {
		val Multimap<TransactionalChange, EObject> createdObjects = ArrayListMultimap.create;
		for (change : changes) {
			val createAndInsertChanges = change.EChanges.filter(CreateAndInsertEObject).typeCreateAndInsertChanges
			val createChanges = change.EChanges.filter(CreateEObject).typeCreateChanges + createAndInsertChanges.map[createChange];
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

	def boolean isRecording() {
		return changeRecorder.isRecording()
	}

	def void dispose() {
		changeRecorder.dispose()
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

package tools.vitruv.framework.change.recording

import java.util.List
import java.util.Set
import org.eclipse.emf.common.notify.Notifier
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.echange.EChangeIdManager
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import com.google.common.collect.Multimap
import org.eclipse.emf.ecore.EObject
import com.google.common.collect.ArrayListMultimap
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.impl.ConcreteApplicableChangeImpl
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.Resource

class AtomicEmfChangeRecorder {
	val Set<Notifier> elementsToObserve
	val NotificationRecorder changeRecorder;
	var List<TransactionalChange> changes;
	val UuidGeneratorAndResolver uuidGeneratorAndResolver;
	val EChangeIdManager eChangeIdManager;

	/**
	 * Constructor for AtomicEMFChangeRecorder.
	 * 
	 * @param uuidGeneratorAndResolver -
	 * 		the {@link UuidGeneratorAndResolver} for ID generation
	 * @param strictMode -
	 * 		specifies whether exceptions shall be thrown if no ID exists for an element that should already have one.
	 * 		Should be set to <code>false</code> if model is not recorded from beginning
	 */
	new(UuidGeneratorAndResolver uuidGeneratorAndResolver) {
		this.elementsToObserve = newHashSet();
		this.uuidGeneratorAndResolver = uuidGeneratorAndResolver;
		this.eChangeIdManager = new EChangeIdManager(uuidGeneratorAndResolver)
		this.changeRecorder = new NotificationRecorder(eChangeIdManager);
	}

	def void beginRecording() {
		changeRecorder.beginRecording();
	}

	def void addToRecording(Notifier elementToObserve) {
		if (elementToObserve === null) {
			throw new IllegalArgumentException("Element to observe must not be null");
		}
		this.elementsToObserve += elementToObserve;
		elementsToObserve.forEach[changeRecorder.addToRecording(it)];
		elementToObserve.registerContentsAtUuidResolver
	}
	
	
	private def dispatch void registerContentsAtUuidResolver(ResourceSet resourceSetToObserve) {
		val resources = resourceSetToObserve.getResources()
		for (resource : resources) {
			resource.registerContentsAtUuidResolver
		}
	}
	
	private def dispatch void registerContentsAtUuidResolver(Resource resourceToObserve) {
		val rootObjects = resourceToObserve.getContents()
		for (rootObject : rootObjects) {
			rootObject.registerContentsAtUuidResolver
		}
	}
	
	private def dispatch void registerContentsAtUuidResolver(EObject eObjectToObserve) {
		uuidGeneratorAndResolver.registerEObject(eObjectToObserve)
		val containedObjects = eObjectToObserve.eContents()
		for (containedObject : containedObjects) {
			containedObject.registerContentsAtUuidResolver
		}
	}
	
	def void removeFromRecording(Notifier elementToObserve) {
		this.elementsToObserve -= elementToObserve;
		changeRecorder.removeFromRecording(elementToObserve)
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
		val changes = changeRecorder.changes;
		removeCreateFollowedByDelete(changes);
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
		return changeRecorder.isRecording()
	}

	def void dispose() {
		// Do nothin at the moment
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

}

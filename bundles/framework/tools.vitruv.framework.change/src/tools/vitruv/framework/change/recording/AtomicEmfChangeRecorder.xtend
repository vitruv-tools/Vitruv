package tools.vitruv.framework.change.recording

import java.util.List
import org.eclipse.emf.common.notify.Notifier
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.echange.EChangeIdManager
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.description.CompositeTransactionalChange
import tools.vitruv.framework.change.description.impl.ConcreteApplicableChangeImpl
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.Resource
import static com.google.common.base.Preconditions.checkNotNull
import static com.google.common.base.Preconditions.checkState
import org.eclipse.xtend.lib.annotations.Accessors

class AtomicEmfChangeRecorder {
	val NotificationRecorder changeRecorder
	@Accessors
	var List<TransactionalChange> changes
	val UuidGeneratorAndResolver uuidGeneratorAndResolver

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
		this.uuidGeneratorAndResolver = uuidGeneratorAndResolver
		this.changeRecorder = new NotificationRecorder(new EChangeIdManager(uuidGeneratorAndResolver))
	}

	def void beginRecording() {
		changeRecorder.beginRecording()
	}

	def void addToRecording(Notifier elementToObserve) {
		checkNotNull(elementToObserve, "elementToObserve")
		changeRecorder.addToRecording(elementToObserve)
		registerContentsAtUuidResolver(elementToObserve)
	}
	
	private def dispatch void registerContentsAtUuidResolver(ResourceSet resourceSetToObserve) {
		resourceSetToObserve.resources.forEach [registerContentsAtUuidResolver()]
	}
	
	private def dispatch void registerContentsAtUuidResolver(Resource resourceToObserve) {
		resourceToObserve.contents.forEach [registerContentsAtUuidResolver()]
	}
	
	private def dispatch void registerContentsAtUuidResolver(EObject eObjectToObserve) {
		uuidGeneratorAndResolver.registerEObject(eObjectToObserve)
		eObjectToObserve.eAllContents.forEach [uuidGeneratorAndResolver.registerEObject(it)]
	}
	
	def void removeFromRecording(Notifier elementToObserve) {
		changeRecorder.removeFromRecording(elementToObserve)
	}

	/** Stops recording without returning a result */
	def void stopRecording() {
		checkState(isRecording, "This recorder is currently not recording!")
		changeRecorder.endRecording()
	}

	def void endRecording() {
		stopRecording()
		val changes = new ArrayList(changeRecorder.changes)
		removeCreateFollowedByDelete(changes)
		this.changes = changes
	}
	
	def removeCreateFollowedByDelete(List<TransactionalChange> changes) {
		val createdObjects = changes.generateCreateChangesMultimap
		val deletedObjects = changes.generateDeleteChangesMultimap
		for (var i = 0; i < changes.size; i++) {
			val currentDeletedObjects = deletedObjects.get(changes.get(i))
			for (var k = i + 1; k < changes.size; k++) {
				for (var object = 0; object < currentDeletedObjects.size; object++) {
					if (createdObjects.get(changes.get(k)).contains(currentDeletedObjects.get(object))) {
						removeCreateOrDelete(changes.get(i) as CompositeTransactionalChange, currentDeletedObjects.get(object), false)
						removeCreateOrDelete(changes.get(k) as CompositeTransactionalChange, currentDeletedObjects.get(object), true)
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

	private def void removeCreateOrDelete(CompositeTransactionalChange change, EObject createdObject, boolean create) {
		for (var i = 0; i < change.changes.size; i++) {
			val currentChange = change.changes.get(i)
			if (currentChange instanceof ConcreteApplicableChangeImpl) {
				val eChange = currentChange.EChange
				if (create) {
					if (eChange instanceof CreateEObject<?>) {
						if (eChange.affectedEObject == createdObject) {
							change.changes.remove(i)
						}
					}
				} else {
					if (eChange instanceof DeleteEObject<?>) {
						if (eChange.affectedEObject == createdObject) {
							change.changes.remove(i)
						}
					}
				}
			} else if (currentChange instanceof CompositeTransactionalChange) {
				removeCreateOrDelete(currentChange, createdObject, create)
			}
		} 
	}
	
 	private def generateDeleteChangesMultimap(List<TransactionalChange> changes) {
 		changes.toMap([it]) [EChanges.filter(DeleteEObject).toList as List<?> as List<DeleteEObject<?>>]
	}
 	
	private def generateCreateChangesMultimap(List<TransactionalChange> changes) {
		changes.toMap([it]) [EChanges.filter(CreateEObject).toList as List<?> as List<CreateEObject<?>>]
	}
}

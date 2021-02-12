package tools.vitruv.framework.change.recording

import java.util.List
import org.eclipse.emf.common.notify.Notifier
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.echange.EChangeIdManager
import tools.vitruv.framework.uuid.UuidGeneratorAndResolver
import org.eclipse.emf.ecore.EObject
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
		this.changes = changeRecorder.changes
	}
	
	def boolean isRecording() {
		return changeRecorder.isRecording()
	}

	def void dispose() {
		// Do nothing at the moment
	}
}

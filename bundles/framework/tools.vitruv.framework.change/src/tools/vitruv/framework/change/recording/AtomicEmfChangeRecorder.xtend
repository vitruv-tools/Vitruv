package tools.vitruv.framework.change.recording

import java.util.ArrayList
import java.util.Collection
import java.util.List
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.change.util.ChangeRecorder
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.resolve.EChangeUnresolver
import tools.vitruv.framework.change.echange.resolve.StagingArea
import tools.vitruv.framework.util.datatypes.VURI

class AtomicEmfChangeRecorder {
	var List<ChangeDescription> changeDescriptions;
	var VURI modelVURI;
	var Collection<Notifier> elementsToObserve
	var boolean unresolveRecordedChanges
	
	/**
	 * Constructor for the AtmoicEMFChangeRecorder, which does not unresolve
	 * the recorded changes.
	 */
	new() {
		this(false)
	}
	
	/**
	 * Constructor for AtomicEMFChangeRecorder
	 * @param 	unresolveRecordedChanges The recorded changes will be replaced
	 * 			by unresolved changes, which referenced EObjects are proxy objects.
	 */
	new(boolean unresolveRecordedChanges) {
		this.elementsToObserve = new ArrayList<Notifier>();
		changeRecorder.setRecordingTransientFeatures(false)
		changeRecorder.setResolveProxies(true)
		this.unresolveRecordedChanges = unresolveRecordedChanges
	}
	
	def void beginRecording(VURI modelVURI, Collection<? extends Notifier> elementsToObserve) {
		this.modelVURI = modelVURI;
		this.elementsToObserve.clear();
		this.elementsToObserve += elementsToObserve;
		this.changeDescriptions = new ArrayList<ChangeDescription>();
		changeRecorder.beginRecording(elementsToObserve)
	}
	
	def List<TransactionalChange> endRecording() {
		if (!isRecording) {
			throw new IllegalStateException();
		}
		changeRecorder.endRecording();
		changeDescriptions.reverseView.forEach[applyAndReverse];
		var transactionalChanges = changeDescriptions.filterNull.map[createModelChange].filterNull.toList;
		if (unresolveRecordedChanges)
			correctChanges(transactionalChanges)
		return transactionalChanges
	}
	
	private def createModelChange(ChangeDescription changeDescription) {
		if (!(changeDescription.objectChanges.isEmpty && changeDescription.resourceChanges.isEmpty)) {
			var TransactionalChange result = null;
			if (unresolveRecordedChanges) { 
				result = VitruviusChangeFactory.instance.createEMFModelChange(changeDescription, modelVURI)
				changeDescription.applyAndReverse()
			} else {
				result = VitruviusChangeFactory.instance.createLegacyEMFModelChange(changeDescription, modelVURI);
				result.applyForward();
			}
			return result;
		}
		changeDescription.applyAndReverse()
		return null;
	}
	
	def List<TransactionalChange> restartRecording() {
		val modelChanges = endRecording()
		beginRecording(modelVURI, elementsToObserve)
		return modelChanges;
	}
	
	def boolean isRecording() {
		return changeRecorder.isRecording()
	}
	
	def void dispose() {
		changeRecorder.dispose()
	}
	
	
	/*
	 * The recorder doesn't produce the correct changes, because all created changes of one change
	 * description are resolved to the same state before all changes were applied. Every atomic change must be resolved 
	 * directly to the state before itself is applied => roll everything back and re-apply all changes, while
	 * correcting the wrong changes.
	 */		
	def private void correctChanges(List<TransactionalChange> changes) {
		var eChanges = changes.map [
			val echanges = it.EChanges;
			return echanges;
		].flatten.toList;
		// Roll back
		for (c : eChanges.reverseView) {
			updateStagingArea(c) // corrects the missing or wrong staging area of CreateEObject changes.
			if (c.isResolved) {
				c.applyBackward
			}
		}
		// Apply again and unresolve the results if necessary
		for (c : eChanges) {
			val EChange copy = EcoreUtil.copy(c)
			EChangeUnresolver.unresolve(c)
			if (copy.isResolved) {
				copy.applyForward
			}
		}
	}
	
	/*
	 * Updates the staging area to the current state of the model.
	 */
	def private dispatch void updateStagingArea(EChange change) {
		// Is needed to create a dispatch method which is applicable for EChange base class.
	}
	
	def private dispatch void updateStagingArea(CreateEObject<EObject> change) {
		// The newly created object is in an resource after the change, so
		// the correct staging area can be chosen, before the change
		// is applied backward.
		change.stagingArea = StagingArea.getStagingArea(change.affectedEObject.eResource)
	}
	
	def private dispatch void updateStagingArea(CompoundEChange change) {
		for (a : change.atomicChanges) {
			updateStagingArea(a)
		}
	}
	
	

	/**
	 * A change recorder that restarts after each change notification to get atomic change descriptions.
	 */
	ChangeRecorder changeRecorder = new ChangeRecorder() {
		private Collection<?> rootObjects;
		private boolean isDisposed = false;
		
		override dispose() {
			this.isDisposed = true;
			super.dispose()
		}
		
		override notifyChanged(Notification notification) {
			if (isRecording && !isDisposed) {
				super.notifyChanged(notification);
				endRecording();
				beginRecording(rootObjects);
			}
		}
		
		override beginRecording(Collection<?> rootObjects) {
			if (!isDisposed) { 
				this.rootObjects = rootObjects;
				super.beginRecording(rootObjects);
			}
		}
		
		override endRecording() {
			if (!isDisposed) { 
				changeDescriptions += super.endRecording();
			}
			return changeDescription;
		}
	}
	
}
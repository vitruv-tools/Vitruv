package tools.vitruv.framework.change.recording

import java.util.Collection
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.change.util.ChangeRecorder
import org.eclipse.emf.common.notify.Notification
import java.util.List
import java.util.ArrayList
import org.eclipse.emf.ecore.change.ChangeDescription
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.description.TransactionalChange

class AtomicEMFChangeRecorder {
	var List<ChangeDescription> changeDescriptions;
	var VURI modelVURI;
	var Collection<Notifier> elementsToObserve
	
	new() {
		this.elementsToObserve = new ArrayList<Notifier>();
		changeRecorder.setRecordingTransientFeatures(false)
		changeRecorder.setResolveProxies(true)
	}
	
	def void beginRecording(VURI modelVURI, Collection<? extends Notifier> elementsToObserve) {
		this.modelVURI = modelVURI;
		this.elementsToObserve.clear();
		this.elementsToObserve += elementsToObserve;
		this.changeDescriptions =new ArrayList<ChangeDescription>();
		changeRecorder.beginRecording(elementsToObserve)
	}
	
	def List<TransactionalChange> endRecording() {
		changeRecorder.endRecording();
		changeDescriptions.reverseView.forEach[applyAndReverse];
		return changeDescriptions.filterNull.map[createModelChange].filterNull.toList;
	}
	
	private def createModelChange(ChangeDescription changeDescription) {
		if (!(changeDescription.objectChanges.isEmpty && changeDescription.resourceChanges.isEmpty)) {
			val result = VitruviusChangeFactory.instance.createEMFModelChange(changeDescription, modelVURI);
			result.applyForward();
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
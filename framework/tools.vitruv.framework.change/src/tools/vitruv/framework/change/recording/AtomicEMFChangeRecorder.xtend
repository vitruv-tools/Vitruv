package tools.vitruv.framework.change.recording

import java.util.Collection
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.change.util.ChangeRecorder
import org.eclipse.emf.common.notify.Notification
import java.util.List
import java.util.ArrayList
import org.eclipse.emf.ecore.change.ChangeDescription
import tools.vitruv.framework.util.datatypes.VURI
import tools.vitruv.framework.change.description.EMFModelChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory

class AtomicEMFChangeRecorder {
	var List<EMFModelChange> modelChanges;
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
		this.modelChanges = new ArrayList<EMFModelChange>();
		changeRecorder.beginRecording(elementsToObserve)
	}
	
	def List<EMFModelChange> endRecording() {
		changeRecorder.endRecording()
		return modelChanges;
	}
	
	def List<EMFModelChange> restartRecording() {
		val cds = endRecording()
		beginRecording(modelVURI, elementsToObserve)
		return cds;
	}
	
	def boolean isRecording() {
		return changeRecorder.isRecording()
	}
	
	def void dispose() {
		changeRecorder.dispose()
	}

	def void addChange(ChangeDescription changeDescription) {
		if (changeDescription != null && !(changeDescription.objectChanges.isEmpty && changeDescription.resourceChanges.isEmpty)) {
			modelChanges += VitruviusChangeFactory.instance.createEMFModelChange(changeDescription, modelVURI)
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
				addChange(super.endRecording());
			}
			return changeDescription;
		}
	}
	
}
package tools.vitruvius.framework.change.recording

import java.util.Collection
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.ecore.change.util.ChangeRecorder
import org.eclipse.emf.common.notify.Notification
import java.util.List
import java.util.ArrayList
import org.eclipse.emf.ecore.change.ChangeDescription
import tools.vitruvius.framework.util.datatypes.VURI
import tools.vitruvius.framework.change.description.EMFModelChange
import tools.vitruvius.framework.change.description.VitruviusChangeFactory

class AtomicEMFChangeRecorder {
	val List<ChangeDescription> changeDescriptions;
	var VURI modelVURI;
	var Collection<Notifier> elementsToObserve
	
	new() {
		this.changeDescriptions = new ArrayList<ChangeDescription>();
		this.elementsToObserve = new ArrayList<Notifier>();
		changeRecorder.setRecordingTransientFeatures(false)
		changeRecorder.setResolveProxies(true)
	}
	
	def void beginRecording(VURI modelVURI, Collection<? extends Notifier> elementsToObserve) {
		this.modelVURI = modelVURI;
		this.elementsToObserve.clear();
		this.elementsToObserve += elementsToObserve;
		this.changeDescriptions.clear();
		changeRecorder.beginRecording(elementsToObserve)
	}
	
	def List<EMFModelChange> endRecording() {
		changeRecorder.endRecording()
		val nonNullChangeDescriptions = changeDescriptions.filterNull.filter[!(objectChanges.isEmpty && resourceChanges.isEmpty)].toList;
		val result = nonNullChangeDescriptions.map[VitruviusChangeFactory.instance.createEMFModelChange(it, modelVURI)];
		changeDescriptions.clear();
		return result;
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
			super.notifyChanged(notification);
			if (!isDisposed) { 
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
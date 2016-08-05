package edu.kit.ipd.sdq.vitruvius.framework.util.changes

import java.util.Collection
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.util.ChangeRecorder
import org.eclipse.emf.common.notify.Notification
import java.util.List
import java.util.ArrayList
import org.eclipse.emf.ecore.change.ChangeDescription

class ForwardChangeRecorder {
	val List<ChangeDescription> changeDescriptions;
	var Map<EObject, URI> eObjectToProxyURIMap
	var Collection<Notifier> elementsToObserve
	var copyDefault = false
	
	new() {
		this.changeDescriptions = new ArrayList<ChangeDescription>();
		this.elementsToObserve = new ArrayList<Notifier>();
		changeRecorder.setRecordingTransientFeatures(false)
		changeRecorder.setResolveProxies(true)
		changeRecorder.setEObjectToProxyURIMap(this.eObjectToProxyURIMap = new HashMap())
	}
	
	def void beginRecording(Collection<? extends Notifier> elementsToObserve) {
		this.elementsToObserve.clear();
		this.elementsToObserve += elementsToObserve;
		this.changeDescriptions.clear();
		changeRecorder.beginRecording(elementsToObserve)
	}
	
	def List<ForwardChangeDescription> endRecording() {
		return endRecording(this.copyDefault)
	}
		
	def List<ForwardChangeDescription> endRecording(boolean copy) {
		changeRecorder.endRecording()
		if (copy) {
			return new ArrayList(changeDescriptions).reverse.map[new ForwardChangeDescription(it, this.eObjectToProxyURIMap)].reverse;
		} else {
			val nonNullChangeDescriptions = changeDescriptions.filterNull;
			val result = new ArrayList<ForwardChangeDescription>();
			for (var i = nonNullChangeDescriptions.length-1; i>=0; i--) {
				result.add(0, new ForwardChangeDescription(nonNullChangeDescriptions.get(i)));
			}
			changeDescriptions.clear();
			return result
		}
	}
	
	def List<ForwardChangeDescription> restartRecording() {
		return restartRecording(this.copyDefault)
	}
		
	def List<ForwardChangeDescription> restartRecording(boolean copy) {
		val cds = endRecording(copy)
		beginRecording(elementsToObserve)
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
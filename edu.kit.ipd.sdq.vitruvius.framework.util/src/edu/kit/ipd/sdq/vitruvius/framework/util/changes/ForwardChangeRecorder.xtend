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
	AtomicChangeRecorder cr;
	var Map<EObject, URI> eObjectToProxyURIMap
	var Collection<Notifier> elementsToObserve
	var copyDefault = false
	
	new() {
		this.cr = new AtomicChangeRecorder()
		this.elementsToObserve = new ArrayList<Notifier>();
		cr.setRecordingTransientFeatures(false)
		cr.setResolveProxies(true)
		cr.setEObjectToProxyURIMap(this.eObjectToProxyURIMap = new HashMap())
	}
	
	def void beginRecording(Collection<? extends Notifier> elementsToObserve) {
		this.elementsToObserve += elementsToObserve;
		cr.startRecordingChangeDescriptions(elementsToObserve)
	}
	
	def List<ForwardChangeDescription> endRecording() {
		return endRecording(this.copyDefault)
	}
		
	def List<ForwardChangeDescription> endRecording(boolean copy) {
		val cds = cr.endRecordingAndGetChangeDescriptions()
		if (cds == null) {
			return null;
		}
		if (copy) {
			return new ArrayList(cds).reverse.map[new ForwardChangeDescription(it, this.eObjectToProxyURIMap)].reverse;
		} else {
			//return cds.filterNull.map[new ForwardChangeDescription(it)].toList;
			val nonNullChangeDescriptions = cds.filterNull;
			val result = new ArrayList<ForwardChangeDescription>();
			for (var i = nonNullChangeDescriptions.length-1; i>=0; i--) {
				result.add(0, new ForwardChangeDescription(nonNullChangeDescriptions.get(i)));
			}
			//return new ArrayList(new ArrayList(cds).reverse.map[new ForwardChangeDescription(it)]).reverse;
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
		return cr.isRecording()
	}
	
	def void dispose() {
		cr.dispose()
		
	}
	
	private static class AtomicChangeRecorder extends ChangeRecorder {
		private List<ChangeDescription> cds;
		private Collection<?> rootObjects;
		private boolean isDisposed;
		
		new() {
			super();
			cds = new ArrayList<ChangeDescription>();
			isDisposed = false;
		}
		
		override dispose() {
			this.isDisposed = true;
			super.dispose()
		}
		
		override notifyChanged(Notification notification) {
			super.notifyChanged(notification);
			if (!isDisposed) { 
				cds += endRecording();
				beginRecording(rootObjects);
			}
		}
		
		public def startRecordingChangeDescriptions(Collection<?> rootObjects) {
			if (!isDisposed) { 
				cds.clear();
				this.rootObjects = rootObjects;
				this.beginRecording(rootObjects);
			}
		}
		
		public def List<ChangeDescription> endRecordingAndGetChangeDescriptions() {
			if (!isDisposed) { 
				cds += endRecording();
			}
			val result = new ArrayList<ChangeDescription>(cds);
			cds.clear();
			return result;
		}
		
	}
}
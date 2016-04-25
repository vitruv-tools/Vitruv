package edu.kit.ipd.sdq.vitruvius.framework.util.changes

import java.util.Collection
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.util.ChangeRecorder

class ForwardChangeRecorder {
	ChangeRecorder cr;
	var Map<EObject, URI> eObjectToProxyURIMap
	val Collection<Notifier> elementsToObserve
	var copyDefault = false
	
	new(Collection<Notifier> elementsToObserve) {
		this.cr = new ChangeRecorder()
		this.elementsToObserve = elementsToObserve
		cr.setRecordingTransientFeatures(false)
		cr.setResolveProxies(true)
		cr.setEObjectToProxyURIMap(this.eObjectToProxyURIMap = new HashMap())
	}
	
	def void beginRec() {
		cr.beginRecording(this.elementsToObserve)
	}
	
	def ForwardChangeDescription endRec() {
		return endRec(this.copyDefault)
	}
		
	def ForwardChangeDescription endRec(boolean copy) {
		val cd = cr.endRecording()
		if (copy) {
			return new ForwardChangeDescription(cd, this.eObjectToProxyURIMap)
		} else {
			return new ForwardChangeDescription(cd)
		}
	}
	
	def ForwardChangeDescription restartRec() {
		return restartRec(this.copyDefault)
	}
		
	def ForwardChangeDescription restartRec(boolean copy) {
		val cd = endRec(copy)
		beginRec()
		return cd
	}
	
	def void startObserving(Notifier elementToObserve) {
		this.elementsToObserve.add(elementToObserve)
	}
	
	def boolean isRecording() {
		return cr.isRecording()
	}
	
	def void dispose() {
		cr.dispose()
	}
}
package edu.kit.ipd.sdq.vitruvius.framework.util.doers

import java.util.Collection
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.change.ChangeDescription
import org.eclipse.emf.ecore.change.util.ChangeRecorder

class ForwardChangeRecorder {
	private ChangeRecorder cr;
	private var Map<EObject, URI> eObjectToProxyURIMap
	private val Collection<Notifier> elementsToObserve
	
	new(Collection<Notifier> elementsToObserve) {
		this.cr = new ChangeRecorder()
		this.elementsToObserve = elementsToObserve
		cr.setRecordingTransientFeatures(false)
	}
	
	def void beginRec() {
		cr.setEObjectToProxyURIMap(this.eObjectToProxyURIMap = new HashMap())
		cr.beginRecording(this.elementsToObserve)
	}
	
	def ChangeDescription endRec() {
		return endRec(true)
	}
		
	def ChangeDescription endRec(boolean copy) {
		val cd = cr.endRecording()
		if (copy) {
			cd.copyAndReverse(this.eObjectToProxyURIMap)
		} else {
			cd.applyAndReverse()
		}
		return cd
	}
	
	def ChangeDescription restartRec() {
		return restartRec(true)
	}
		
	def ChangeDescription restartRec(boolean copy) {
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
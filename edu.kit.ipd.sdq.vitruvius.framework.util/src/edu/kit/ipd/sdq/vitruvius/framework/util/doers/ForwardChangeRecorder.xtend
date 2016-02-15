package edu.kit.ipd.sdq.vitruvius.framework.util.doers

import org.eclipse.emf.ecore.change.util.ChangeRecorder
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.URI
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.change.ChangeDescription

class ForwardChangeRecorder extends ChangeRecorder {
	private var Map<EObject, URI> eObjectToProxyURIMap
	private val ResourceSet resourceSet
	
	new(ResourceSet resourceSet) {
		this.resourceSet = resourceSet
		setRecordingTransientFeatures(false)
	}
	
	def void beginRec() {
		setEObjectToProxyURIMap(this.eObjectToProxyURIMap = new HashMap())
		beginRecording(#[this.resourceSet])
	}
	
	def ChangeDescription endRec() {
		return endRec(true)
	}
		
	def ChangeDescription endRec(boolean copy) {
		val cd = endRecording()
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
}
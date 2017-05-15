package tools.vitruv.framework.util.datatypes

import java.util.HashMap
import java.util.Map
import org.eclipse.core.resources.IResource
import org.eclipse.emf.common.CommonPlugin
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.util.bridges.EMFBridge

/** 
 * Implements the multiton design pattern.
 * @author kramerm
 */
class VURI implements Comparable<VURI> {
	static final Map<String, VURI> INSTANCES = new HashMap<String, VURI>()
	org.eclipse.emf.common.util.URI emfURI

	/** 
	 * Multiton classes should not have a public or default constructor. 
	 */
	private new(String uriString) {
		this.emfURI = EMFBridge.createURI(uriString)
	}

	def static synchronized VURI getInstance(String key) {
		var VURI instance = INSTANCES.get(key)
		if (instance === null) {
			instance = new VURI(key)
			var String newKey = instance.toString()
			var VURI oldInstance = INSTANCES.get(newKey)
			if (oldInstance !== null) {
				INSTANCES.put(key, oldInstance)
				return oldInstance
			} else {
				// we also have to map the actual string representation of the key after the vuri
				// was
				// created because a prefix may be prepended to the key while the VURI is created
				INSTANCES.put(newKey, instance)
			}
			INSTANCES.put(key, instance)
		}
		return instance
	}

	def static VURI getInstance(Resource resource) {
		return getInstance(resource.getURI())
	}

	def static VURI getInstance(URI uri) {
		if (null === uri.toFileString()) {
			return getInstance(uri.toString())
		}
		return getInstance(uri.toFileString())
	}

	def static VURI getInstance(IResource iResource) {
		var String[] keyStrSegments = iResource.getFullPath().segments()
		var StringBuilder keyString = new StringBuilder()
		for (var int i = 0; i < keyStrSegments.length; i++) {
			if (i > 0) {
				keyString.append("/")
			}
			keyString.append({
				val _rdIndx_keyStrSegments = i
				keyStrSegments.get(_rdIndx_keyStrSegments)
			})
		}
		return getInstance(keyString.toString())
	}

	override String toString() {
		return this.emfURI.toString()
	}

	def String toResolvedAbsolutePath() {
		return CommonPlugin.resolve(this.emfURI).toFileString()
	}

	def URI getEMFUri() {
		return this.emfURI
	}

	def String getFileExtension() {
		return this.emfURI.fileExtension()
	}

	def String getLastSegment() {
		var String lastSegment = this.emfURI.lastSegment()
		return (if (lastSegment === null) "" else lastSegment )
	}

	/** 
	 * Returns a new VURI that is created from the actual VURI by replacing its file extension with
	 * newFileExt
	 * @param newFileExt
	 * @return the new VURI with the replaced file extension
	 */
	def VURI replaceFileExtension(String newFileExt) {
		return VURI.getInstance(this.emfURI.trimFileExtension().appendFileExtension(newFileExt))
	}

	override int compareTo(VURI otherVURI) {
		return this.emfURI.toString().compareTo(otherVURI.toString())
	}
}

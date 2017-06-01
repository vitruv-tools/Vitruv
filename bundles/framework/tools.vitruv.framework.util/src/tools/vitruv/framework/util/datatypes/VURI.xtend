package tools.vitruv.framework.util.datatypes

import java.io.Serializable
import java.util.HashMap
import java.util.Map

import org.eclipse.core.resources.IResource
import org.eclipse.emf.common.CommonPlugin
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.Accessors

import tools.vitruv.framework.util.bridges.EMFBridge

/** 
 * Implements the multiton design pattern.
 * @author kramerm
 */
class VURI implements Comparable<VURI>, Serializable {
	@Accessors(PUBLIC_GETTER)
	static val serialVersionUID = 1L
	static val Map<String, VURI> INSTANCES = new HashMap<String, VURI>
	@Accessors(PUBLIC_GETTER)
	transient org.eclipse.emf.common.util.URI emfURI

	/** 
	 * Multiton classes should not have a public or default constructor. 
	 */
	private new(String uriString) {
		emfURI = EMFBridge::createURI(uriString)
	}

	override compareTo(VURI otherVURI) {
		emfURI.toString.compareTo(otherVURI.toString)
	}

	override toString() {
		emfURI.toString
	}

	def static synchronized VURI getInstance(String key) {
		if (!INSTANCES.containsKey(key)) {
			val instance = new VURI(key)
			val newKey = instance.toString
			if (INSTANCES.containsKey(newKey)) {
				val VURI oldInstance = INSTANCES.get(newKey)
				INSTANCES.put(key, oldInstance)
				oldInstance
			} else {
				// we also have to map the actual string representation of the
				// key after the vuri
				// created because a prefix may be prepended to the key while
				// the VURI is created
				INSTANCES.put(newKey, instance)
				INSTANCES.put(key, instance)
				instance
			}
		} else
			INSTANCES.get(key)
	}

	def static VURI getInstance(Resource resource) {
		resource.URI.getInstance
	}

	def static VURI getInstance(URI uri) {
		if (null === uri.toFileString)
			uri.toString.getInstance
		else
			uri.toFileString.getInstance
	}

	def static VURI getInstance(IResource iResource) {
		val keyStrSegments = iResource.fullPath.segments
		val keyString = new StringBuilder
		keyStrSegments.forEach [ x, i |
			if (i > 0)
				keyString.append("/")
			keyString.append(x)
		]
		getInstance(keyString.toString)
	}

	def String toResolvedAbsolutePath() {
		CommonPlugin::resolve(emfURI).toFileString
	}

	def URI getEMFUri() {
		emfURI
	}

	def String getFileExtension() {
		emfURI.fileExtension
	}

	def String getLastSegment() {
		val lastSegment = emfURI.lastSegment
		if (null === lastSegment)
			""
		else
			lastSegment
	}

	/** 
	 * Returns a new VURI that is created from the actual VURI by replacing its
	 * file extension with newFileExt
	 * @param newFileExt
	 * @return the new VURI with the replaced file extension
	 */
	def VURI replaceFileExtension(String newFileExt) {
		VURI::getInstance(emfURI.trimFileExtension.appendFileExtension(newFileExt))
	}
}

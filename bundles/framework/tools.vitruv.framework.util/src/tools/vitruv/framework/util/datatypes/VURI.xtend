package tools.vitruv.framework.util.datatypes

import java.util.Map
import org.eclipse.core.resources.IResource
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.util.datatypes.impl.VURIImpl

/** 
 * Implements the multiton design pattern.
 * To xtend: Patrick Stoeckle
 * 
 * @author kramerm
 * @version 0.2.0
 */
abstract class VURI implements Comparable<VURI> {
	static val Map<String, VURI> INSTANCES = newHashMap

	def static synchronized VURI getInstance(String key) {
		if (!INSTANCES.containsKey(key)) {
			val instance = new VURIImpl(key)
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
		resource.URI.instance
	}

	def static VURI getInstance(URI uri) {
		if (null === uri.toFileString)
			uri.toString.instance
		else
			uri.toFileString.instance
	}

	def static VURI getInstance(IResource iResource) {
		val keyStrSegments = iResource.fullPath.segments
		val keyString = new StringBuilder
		keyStrSegments.forEach [ x, i |
			if (i > 0)
				keyString.append("/")
			keyString.append(x)
		]
		keyString.toString.instance
	}

	def String toResolvedAbsolutePath()

	def URI getEMFUri()

	def String getFileExtension()

	def String getLastSegment()

	def VURI replaceFileExtension(String newFileExt)

}

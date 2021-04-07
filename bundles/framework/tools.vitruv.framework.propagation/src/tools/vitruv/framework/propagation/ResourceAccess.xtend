package tools.vitruv.framework.propagation

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.common.util.URI

/**
 * Offers possibilities for resource persistence to change propagation
 * executors. 
 * 
 * @author Joshua Gleitze
 */
interface ResourceAccess {

	/**
	 * Gets the {@link URI} of a model that stores metadata.
	 * 
	 * @param metadataKey
	 * 		The key uniquely identifying the metadata model. The different
	 * 		parts of the key can be used to convey some sort of hierarchy in
	 * 		the metadata. The key may contain arbitrary characters. The last
	 * 		key part contains the metadata model's file name and extension.
	 */
	def URI getMetadataModelURI(String... metadataKey)

	/**
	 * Provides the resource for storing the specified model in it.
	 * 
	 * @param uri
	 * 		The model's uri.
	 */
	def Resource getModelResource(URI uri)

	/**
	 * Persists the given {@code rootObject} at the given {@code uri}.
	 */
	def void persistAsRoot(EObject rootObject, URI uri)
}
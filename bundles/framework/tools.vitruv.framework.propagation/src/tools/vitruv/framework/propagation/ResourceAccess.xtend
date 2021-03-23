package tools.vitruv.framework.propagation

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.util.datatypes.VURI

/**
 * Offers possibilities for resource persistence to change propagation
 * executors. 
 * 
 * @author Joshua Gleitze
 */
interface ResourceAccess {

	/**
	 * Gets the {@link VURI} of a model that stores metadata.
	 * 
	 * @param metadataKey
	 * 		The key uniquely identifying the metadata model. The different
	 * 		parts of the key can be used to convey some sort of hierarchy in
	 * 		the metadata. The key may contain arbitrary characters. The last
	 * 		key part contains the metadata model's file name and extension.
	 */
	def VURI getMetadataModelURI(String... metadataKey)

	/**
	 * Provides the resource for storing the specified model in it.
	 * 
	 * @param vuri
	 * 		The model's vuri.
	 */
	def Resource getModelResource(VURI vuri)

	/**
	 * Persists the given {@code rootObject} at the given {@code vuri}.
	 */
	def void persistAsRoot(EObject rootObject, VURI vuri)
}
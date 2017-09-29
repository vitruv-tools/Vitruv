package tools.vitruv.framework.util.command

import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.util.datatypes.VURI
import org.eclipse.emf.ecore.EObject

/**
 * Offers possibilities for resource persistence to change propagation
 * executors. 
 * 
 * @author Joshua Gleitze
 */
interface ResourceAccess {
	
	/**
	 * Provides a resource that can be used to store metadata in it.
	 * 
	 * @param storageKey
	 * 		The key uniquely identifying the portion of metadata that is to be
	 * 		stored in the returned resource. The different parts of the key
	 * 		can be used to convey some sort of hierarchy in the metadata. The
	 * 		key may contain arbitrary characters.
	 */
	def Resource getResourceForMetadataStorage(String... storageKey)
	
	/**
	 * Persists the given {@code rootObject} at the given {@code vuri}.
	 */
	def void persistAsRoot(EObject rootObject, VURI vuri)
	
}
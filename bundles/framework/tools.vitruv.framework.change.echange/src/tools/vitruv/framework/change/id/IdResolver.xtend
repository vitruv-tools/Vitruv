package tools.vitruv.framework.change.id

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource

interface IdResolver {
	/**
	 * Returns whether the given {@link EObject} has a registered ID or not. 
	 */
	def boolean hasId(EObject eObject)

	/**
	 * Returns whether an {@link EObject} is registered for the given ID or not. 
	 */
	def boolean hasEObject(String id)

	/**
	 * Calculates and returns the ID of the given {@link EObject} and updates the stored ID.
	 */
	def String getAndUpdateId(EObject eObject)
	
	/**
	 * Returns the {@link EObject} for the given ID. If more than one object was registered
	 * for the ID, the last one is returned.
	 * If no element was registered, an {@link IllegalStateException} is thrown.
	 */
	def EObject getEObject(String id) throws IllegalStateException

	/**
	 * Registers the given {@link EObject} for the given ID.
	 */
	def void register(String id, EObject eObject)

	/**
	 * Returns the {@link Resource} for the given {@link URI}. If the resource does not exist yet,
	 * it gets created.
	 */
	def Resource getResource(URI uri);

	/**
	 * Returns whether there is a {@link Resource} for the given {@link URI} loaded within the managed
	 * {@link ResourceSet}.
	 */
	def boolean hasResource(URI uri);

}

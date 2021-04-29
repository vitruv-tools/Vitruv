package tools.vitruv.framework.change.echange.id

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet

interface IdResolver {
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
	 * @throws IllegalStateException if no element was registered for the ID
	 */
	def EObject getEObject(String id) throws IllegalStateException

	/**
	 * Returns the {@link Resource} for the given {@link URI}. If the resource does not exist yet,
	 * it gets created.
	 */
	def Resource getResource(URI uri)

	/**
	 * Ends a transactions such that all {@link EObject}s not being contained in a resource, which is
	 * contained in a resource set, are removed from the ID mapping.
	 */
	def void endTransaction()
	
	/**
	 * Instantiates a {@link IdResolverAndRepository} with the given {@link ResourceSet}
	 * for resolving objects.
	 * 
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be {@code null}
	 * @throws IllegalArgumentException if given {@link ResourceSet} is {@code null}
	 */
	static def IdResolver create(ResourceSet resourceSet) {
		return new IdResolverImpl(resourceSet)
	}
}

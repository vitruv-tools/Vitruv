package tools.vitruv.framework.change.id

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.common.notify.Notifier

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
	 * @throws IllegalStateException if no element was registered for the ID
	 */
	def EObject getEObject(String id) throws IllegalStateException

	/**
	 * Returns the {@link Resource} for the given {@link URI}. If the resource does not exist yet,
	 * it gets created.
	 */
	def Resource getResource(URI uri)

	/**
	 * Returns whether this {@link IdResolver} can calculate IDs for {@link EObject}s within the given
	 * {@link Notifier}.
	 */
	def boolean canCalculateIdsIn(Notifier notifier)
	
	/**
	 * Ends a transactions such that all {@link EObject}s not being contained in a resource, which is
	 * contained in a resource set, are removed from the ID mapping.
	 */
	def void endTransaction()

}

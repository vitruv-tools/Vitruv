package tools.vitruv.framework.uuid

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource

interface UuidResolver {
	/**
	 * Returns whether the given {@link EObject} has a registered UUID or not. 
	 */
	def boolean hasUuid(EObject eObject)

	/**
	 * Returns whether an {@link EObject} is registered for the given UUID or not. 
	 */
	def boolean hasEObject(String uuid)

	/**
	 * Returns the UUID for the given {@link EObject}.
	 * If no UUID is registered for it, an {@link IllegalStateException} is thrown.
	 */
	def String getUuid(EObject eObject) throws IllegalStateException

	/**
	 * Returns the {@link EObject} for the given UUID. If more than one object was registered
	 * for the UUID, the last one is returned.
	 * If no element was registered, an {@link IllegalStateException} is thrown.
	 */
	def EObject getEObject(String uuid) throws IllegalStateException

	/**
	 * Registers the given {@link EObject} for the given UUID.
	 * @throws IllegalStateException if there is already a UUID registered for the given {@link EObject}
	 * or vice versa
	 */
	def void registerEObject(String uuid, EObject eObject) throws IllegalStateException

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

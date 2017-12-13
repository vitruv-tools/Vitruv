package tools.vitruv.framework.change.uuid

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.common.util.URI

interface UuidResolver {
	public static final UuidResolver EMPTY = new EmptyUuidResolver();
	
	/**
	 * Returns whether the given {@link EObject} has a registered UUID or not. 
	 */
	def boolean hasUuid(EObject eObject);
	
	/**
	 * Returns the UUID for the given {@link EObject}.
	 * If no UUID is registered for it, an {@link IllegalStateException} is thrown.
	 */
	def String getUuid(EObject eObject) throws IllegalStateException;
	
	/**
	 * Returns the {@link EObject} for the given UUID. If more than one object was registered
	 * for the UUID, the last one is returned.
	 */
	def EObject getEObject(String uuid);

	/**
	 * Registers the given {@link EObject} for the given UUID.
	 */
	def void registerEObject(String uuid, EObject eObject);
	
	/**
	 * Registers the given UUID for the element at the given {@link URI} in the
	 * {@link ResourceSet} of this resolver.
	 * If the object cannot be resolved in the {@link ResourceSet} of this resolver,
	 * <code>false</code> is returned, otherwise <code>true</code>.
	 */
	def boolean registerUuidForGlobalUri(String uuid, URI uri);
	
	def ResourceSet getResourceSet();
	
}
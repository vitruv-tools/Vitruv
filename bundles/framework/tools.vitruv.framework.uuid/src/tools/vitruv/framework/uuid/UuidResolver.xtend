package tools.vitruv.framework.uuid

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
	 * Returns whether an {@link EObject} is registered for the given UUID or not. 
	 */
	def boolean hasEObject(String uuid);
	
	/**
	 * Returns the UUID for the given {@link EObject}.
	 * If no UUID is registered for it, an {@link IllegalStateException} is thrown.
	 */
	def String getUuid(EObject eObject) throws IllegalStateException;
	
	/**
	 * Returns the {@link EObject} for the given UUID. If more than one object was registered
	 * for the UUID, the last one is returned.
	 * If no element was registered, an {@link IllegalStateException} is thrown.
	 */
	def EObject getEObject(String uuid) throws IllegalStateException;
	
	/** 
	 * Registers the given {@link EObject} and uses the already created UUID for that object.
	 * The UUID is retrieved from the local or a parent resolver. This is necessary, if an object
	 * in a new instance of the same model shall be registered.
	 * If no UUID was registered yet, an {@link IllegalStateException} is thrown.
	 */
	def void registerEObject(EObject eObject);
		
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
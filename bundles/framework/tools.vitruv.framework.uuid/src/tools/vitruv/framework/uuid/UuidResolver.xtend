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
	
	/**
	 * Returns the {@link ResourceSet} used in this UUID resolver.
	 */
	def ResourceSet getResourceSet();
	
	/**
	 * Returns the {@link EObject} for the given UUID from the repository, or from the cache
	 * if it was added using {@link #registerCachedEObject(EObject}.
	 * If no object is found, an {@link IllegalStateException} is thrown.
	 */
	def EObject getPotentiallyCachedEObject(String uuid);
	
	/**
	 * Returns whether an {@link EObject} was registered for the given UUID in the repository,
	 * or in the cache if it was added using {@link #registerCachedEObject(EObject}.
	 */
	def boolean hasPotentiallyCachedEObject(String uuid);
	
	/**
	 * Returns the UUID for the given {@link EObject} from the repository, or from the cache
	 * if it was added using {@link #registerCachedEObject(EObject}.
	 * If no UUID is found, an {@link IllegalStateException} is thrown.
	 */
	def String getPotentiallyCachedUuid(EObject eObject);
	
	/**
	 * Returns whether a UUID was generated and registered for the given {@link EObject} in the repository,
	 * or in the cache if it was added using {@link #registerCachedEObject(EObject}.
	 */
	def boolean hasPotentiallyCachedUuid(EObject eObject);
	
	/**
	 * Registers the given {@link EObject} as cached object.
	 * This means that is not resolved by the ordinary {@link #getEObject} and {@link #getUuid} methods.
	 * This can be useful, if having a UUID assigned to an object is used as an indicator.
	 * For example, objects having a UUID may be treated as also created and those having none are newly created.
	 * Nevertheless, UUIDs must sometimes be referenced (e.g. in correspondences created before the element was
	 * inserted into a monitored model) before checking for creating. So long, the object can be placed in the cache.
	 * When {@link UuidGeneratorAndResolver#generateUuid(EObject)} is called, the object will be moved from the
	 * cache to the ordinary resolution mechanism.
	 */
	def String registerCachedEObject(EObject eObject);
	
	/**
	 * Loads the UUIDs for the resource of the given {@link URI} into the given
	 * child {@link UuidResolver}.
	 */
	def void loadUuidsToChild(UuidResolver childResolver, URI uri);
}
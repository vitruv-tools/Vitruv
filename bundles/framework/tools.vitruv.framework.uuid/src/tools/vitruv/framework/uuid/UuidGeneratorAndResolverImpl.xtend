package tools.vitruv.framework.uuid

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.uuid.UuidToEObjectRepository
import tools.vitruv.framework.uuid.UuidFactory
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.resource.Resource
import org.apache.log4j.Logger
import static extension tools.vitruv.framework.util.bridges.EcoreResourceBridge.*
import static extension tools.vitruv.framework.util.bridges.JavaBridge.*
import static extension tools.vitruv.framework.util.command.EMFCommandBridge.executeVitruviusRecordingCommandAndFlushHistory
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.*
import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import static extension tools.vitruv.framework.util.ResourceSetUtil.getTransactionalEditingDomain

/**
 * {@link UuidGeneratorAndResolver}
 */
class UuidGeneratorAndResolverImpl implements UuidGeneratorAndResolver {
	static val logger = Logger.getLogger(UuidGeneratorAndResolverImpl)
	final ResourceSet resourceSet
	final UuidResolver parentUuidResolver
	final boolean strictMode
	UuidToEObjectRepository repository
	UuidToEObjectRepository cache

	/**
	 * Instantiates a UUID generator and resolver with no parent resolver, 
	 * the given {@link ResourceSet} for resolving objects
	 * and no {@link Resource} in which the mapping is stored.
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be null
	 * @param strictMode -
	 * 		defines if the generator should run in strict mode, which throws {@link IllegalStateException}s 
	 * 		if an element that should already have an ID as it was created before does no have one. 
	 * 		Using non-strict mode can be necessary if model changes are not recorded from beginning of model creation.
	 * 		Third party library are handled correctly (there is never a create change).
	 * @throws IllegalArgumentException if given {@link ResourceSet} is null
	 */
	new(ResourceSet resourceSet, boolean strictMode) {
		this(null, resourceSet, null, strictMode)
	}

	/**
	 * Instantiates a UUID generator and resolver with the given parent resolver, used when
	 * this resolver cannot resolve a UUID, the given {@link ResourceSet} for resolving objects
	 * and no {@link Resource} in which the mapping is stored.
	 * @param parentUuidResolver -
	 * 		the parent {@link UuidResolver} used to resolve UUID if this contains no appropriate mapping, may be null
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be null
	 * @param strictMode -
	 * 		defines if the generator should run in strict mode, which throws {@link IllegalStateException}s 
	 * 		if an element that should already have an ID as it was created before does no have one. 
	 * 		Using non-strict mode can be necessary if model changes are not recorded from beginning of model creation.
	 * 		Third party library are handled correctly (there is never a create change).
	 * @throws IllegalArgumentException if given {@link ResourceSet} is null
	 */
	new(UuidResolver parentUuidResolver, ResourceSet resourceSet, boolean strictMode) {
		this(parentUuidResolver, resourceSet, null, strictMode)
	}

	/**
	 * Instantiates a UUID generator and resolver with no parent resolver, 
	 * the given {@link ResourceSet} for resolving objects
	 * and the given {@link Resource} for storing the mapping.
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be null
	 * @param uuidResource -
	 * 		the {@link Resource} to store the mapping in, may be null
	 * @param strictMode -
	 * 		defines if the generator should run in strict mode, which throws {@link IllegalStateException}s 
	 * 		if an element that should already have an ID as it was created before does no have one. 
	 * 		Using non-strict mode can be necessary if model changes are not recorded from beginning of model creation.
	 * 		Third party library are handled correctly (there is never a create change).
	 * @throws IllegalArgumentException if given {@link ResourceSet} is null
	 */
	new(ResourceSet resourceSet, Resource uuidResource, boolean strictMode) {
		this(null, resourceSet, uuidResource, strictMode)
	}

	/**
	 * Instantiates a UUID generator and resolver with the given parent resolver, used when
	 * this resolver cannot resolve a UUID, the given {@link ResourceSet} for resolving objects
	 * and the given {@link Resource} for storing the mapping.
	 * @param parentUuidResolver -
	 * 		the parent {@link UuidResolver} used to resolve UUID if this contains no appropriate mapping, may be null
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be null
	 * @param uuidResource -
	 * 		the {@link Resource} to store the mapping in, may be null
	 * @param strictMode -
	 * 		defines if the generator should run in strict mode, which throws {@link IllegalStateException}s 
	 * 		if an element that should already have an ID as it was created before does no have one. 
	 * 		Using non-strict mode can be necessary if model changes are not recorded from beginning of model creation.
	 * 		Third party library are handled correctly (there is never a create change).
	 * @throws IllegalArgumentException if given {@link ResourceSet} is null
	 */
	new(UuidResolver parentUuidResolver, ResourceSet resourceSet, Resource uuidResource, boolean strictMode) {
		checkArgument(resourceSet !== null, "Resource set may not be null")
		this.resourceSet = resourceSet
		this.strictMode = strictMode
		this.parentUuidResolver = parentUuidResolver ?: UuidResolver.EMPTY
		loadAndRegisterUuidProviderAndResolver(uuidResource)
		this.resourceSet.eAdapters += new ResourceRegistrationAdapter[resource|loadUuidsFromParent(resource)]
	}

	def private loadAndRegisterUuidProviderAndResolver(Resource uuidResource) {
		var UuidToEObjectRepository repository = uuidResource?.resourceContentRootIfUnique
			?.dynamicCast(UuidToEObjectRepository, "uuid provider and resolver model")
		if (repository === null) {
			repository = UuidFactory.eINSTANCE.createUuidToEObjectRepository
			if (uuidResource !== null) {
				uuidResource.contents += repository
			}
		}
		this.repository = repository
		this.cache = UuidFactory.eINSTANCE.createUuidToEObjectRepository
	}

	override getUuid(EObject eObject) {
		val result = internalGetUuid(eObject)
		checkState(result !== null, '''No UUID registered for EObject: «eObject»''')
		return result
	}

	private def internalGetUuid(EObject eObject) {
		val localResult = repository.EObjectToUuid.get(eObject)
		if (localResult !== null) {
			return localResult
		}
		
		// If the object already has a resource, but is not from the resolver’s resource set, resolve it and try again
		val objectResource = eObject.eResource
		if (objectResource !== null && objectResource.resourceSet != resourceSet) {
			val resolvedObject = resolve(eObject.resolvableUri)
			// The EClass check avoids that an objects of another type with the same URI is resolved
			// This is, for example, the case if a modifier in a UML model is changed, as it is only a
			// marker class that is replaced, having always the same URI on the same model element.
			if (resolvedObject !== null && resolvedObject.eClass == eObject.eClass) {
				val resolvedObjectUuid = repository.EObjectToUuid.get(resolvedObject)
				if (resolvedObjectUuid !== null) {
					return resolvedObjectUuid
				}
			}
		}

		val objectUri = EcoreUtil.getURI(eObject)
		// Finally look for a proxy in the repository (due to a deleted object) and match the URI
		val uuidByProxy = repository.EObjectToUuid.entrySet
			.filter [key !== null && key.eIsProxy]
			.findFirst [EcoreUtil.getURI(key) == objectUri]
			?.value
		if (uuidByProxy !== null) {
			return uuidByProxy
		}

		if (eObject instanceof EClass) {
			return generateUuid(eObject)
		}

		return null
	}

	override getEObject(String uuid) {
		val eObject = internalGetEObject(uuid)
		if (eObject === null) {
			throw new IllegalStateException("No EObject could be found for UUID: " + uuid)
		}
		return eObject
	}

	override getPotentiallyCachedEObject(String uuid) {
		cache.uuidToEObject.get(uuid)
			?: getEObject(uuid)
	}

	private def EObject internalGetEObject(String uuid) {
		val eObject = repository.uuidToEObject.get(uuid)
		if (eObject === null) {
			return null
		}
		if (eObject.eIsProxy) {
			// Try to resolve the proxy. This can still lead to a valid proxy element if it is a proxy on purpose
			val resolvedObject = EcoreUtil.resolve(eObject, resourceSet)
			return resolvedObject
		} else {
			return eObject
		}
	}

	override String generateUuid(EObject eObject) {
		val cachedUuid = cache.EObjectToUuid.removeKey(eObject)
		if (cachedUuid !== null) {
			cache.uuidToEObject.remove(cachedUuid)
		}
		val uuid = cachedUuid ?: generateUuid()
		registerEObject(uuid, eObject)
		return uuid
	}

	override String generateUuidWithoutCreate(EObject eObject) {
		val uuid = generateUuid(eObject)
		// Register UUID globally for third party elements that are statically accessible and are never created.
		// Since this is called in the moment when an element gets created, the object can only be globally resolved
		// if it a third party element.
		if (!parentUuidResolver.registerUuidForGlobalUri(uuid, EcoreUtil.getURI(eObject))) {
			if (strictMode) {
				throw new IllegalStateException("Object has no UUID and is not globally accessible: " + eObject)
			} else {
				logger.warn("Object is not statically accessible but also has no globally mapped UUID: " + eObject)
			}
		}
		return uuid
	}

	private def String generateUuid() {
		return EcoreUtil.generateUUID()
	}

	override registerEObject(String uuid, EObject eObject) {
		checkState(eObject !== null, "Object must not be null")
		logger.debug('''Adding UUID «uuid» for EObject: «eObject»''')
		repository.eResource?.resourceSet.runAsCommandIfNecessary [
			val uuidMapped = repository.uuidToEObject.put(uuid, eObject)
			if (uuidMapped !== null) {
				repository.EObjectToUuid.remove(uuidMapped)
			}
			repository.EObjectToUuid.put(eObject, uuid)
		]
	}

	override hasPotentiallyCachedEObject(String uuid) {
		cache.uuidToEObject.containsKey(uuid)
			|| internalGetEObject(uuid) !== null
	}

	override hasUuid(EObject object) {
		return internalGetUuid(object) !== null
	}

	override hasEObject(String uuid) {
		return internalGetEObject(uuid) !== null
	}

	override getResourceSet() {
		return resourceSet
	}

	override registerUuidForGlobalUri(String uuid, URI uri) {
		val localObject = resolve(uri)
		if (localObject !== null) {
			registerEObject(uuid, localObject)
			// Recursively do that
			return parentUuidResolver.registerUuidForGlobalUri(uuid, uri)
		}
		return false
	}

	override registerEObject(EObject eObject) {
		if (parentUuidResolver.hasUuid(eObject)) {
			registerEObject(parentUuidResolver.getUuid(eObject), eObject)
		} else if (hasUuid(eObject)) {
			registerEObject(getUuid(eObject), eObject)
		} else {
			// throw new IllegalStateException("Given EObject has no UUID yet: " + eObject)
		}
	}

	override getPotentiallyCachedUuid(EObject eObject) {
		return cache.EObjectToUuid.get(eObject)
			?: cache.EObjectToUuid.findFirst [EcoreUtil.equals(eObject, key)]?.value
			?: getUuid(eObject)
	}

	override hasPotentiallyCachedUuid(EObject eObject) {
		cache.EObjectToUuid.containsKey(eObject) 
			|| hasUuid(eObject)
	}

	override registerCachedEObject(EObject eObject) {
		checkState(eObject !== null, "Object must not be null")
		val uuid = generateUuid()
		cache.EObjectToUuid.put(eObject, uuid)
		cache.uuidToEObject.put(uuid, eObject)
		return uuid
	}

	override void loadUuidsToChild(UuidResolver childResolver, URI uri) {
		// Only load UUIDs if resource exists (a pathmap resource always exists)
		if (((uri.isFile || uri.isPlatform) && uri.existsResourceAtUri) || uri.isPathmap) {
			val childContents = childResolver.resourceSet.runAsCommandIfNecessary [getResource(uri, true)].allContents
			val ourContents = this.resourceSet.runAsCommandIfNecessary [getResource(uri, true)].allContents
			while (childContents.hasNext) {
				val childObject = childContents.next
				checkState(ourContents.hasNext, "Cannot find %s in our resource set!", childObject)
				val ourObject = ourContents.next
				
				var objectUuid = repository.EObjectToUuid.get(ourObject)
				// Not having a UUID is only supported for pathmap resources
				if (objectUuid === null && uri.isPathmap) {
					objectUuid = generateUuid(ourObject)
				}		
				if (objectUuid === null) {
					throw new IllegalStateException('''Element does not have a UUID but should have one: «ourObject»''')
				}
				
				childResolver.registerEObject(objectUuid, childObject)
			}
		}
	}

	def void loadUuidsFromParent(Resource resource) {
		parentUuidResolver.loadUuidsToChild(this, resource.URI)
	}
	
	private def EObject resolve(URI uri) {
		// TODO running as command should never be necessary for non-Java domains.
		// Running in a command should probably be removed after domains can modify the UUID behaviour (see #326)
		resourceSet.runAsCommandIfNecessary [getEObject(uri, true)]
	}

	// If there is a TransactionalEditingDomain registered on the resource set, we have
	// to also execute our command on that domain, otherwise (e.g. in change tests),
	// there is no need to execute the command on a TransactionalEditingDomain. It can even
	// lead to errors if the ResourceSet is also modified by the test, as these modifications
	// would also have to be made on the TransactionalEditingDomain once it was created.
	def private <T> T runAsCommandIfNecessary(ResourceSet resourceSet, (ResourceSet)=>T callable) {
		val domain = resourceSet?.transactionalEditingDomain
		return if (domain !== null) {
			domain.executeVitruviusRecordingCommandAndFlushHistory [callable.apply(resourceSet)]
		} else {
			callable.apply(resourceSet)
		}
	}
	
	def private static getResolvableUri(EObject object) {
		val resourceUri = object.eResource.URI
		// we cannot simply use EcoreUtil#getURI, because object’s domain might use XMI	UUIDs. Since
		// XMI UUIDs can be different for different resource sets, we cannot use URIs with XMI UUIDs to identify objects
		// across resource sets. Hence, we force hierarchical URIs. This assumes that the resolved object’s graph
		// has the same topology in the resolving resource set. This assumption holds when we use this method.  
		val fragmentPath = EcoreUtil.getRelativeURIFragmentPath(null, object)
		if (fragmentPath.isEmpty) {
			resourceUri.appendFragment('/')
		} else {
			resourceUri.appendFragment('//' + fragmentPath)
		}
	}
}

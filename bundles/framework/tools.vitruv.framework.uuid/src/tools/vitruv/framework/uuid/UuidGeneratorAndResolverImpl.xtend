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
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.*
import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import tools.vitruv.framework.util.ResourceRegistrationAdapter

/**
 * {@link UuidGeneratorAndResolver}
 */
class UuidGeneratorAndResolverImpl implements UuidGeneratorAndResolver {
	static val logger = Logger.getLogger(UuidGeneratorAndResolverImpl)
	final ResourceSet resourceSet
	final UuidResolver parentUuidResolver
	UuidToEObjectRepository repository
	UuidToEObjectRepository cache

	/**
	 * Instantiates a UUID generator and resolver with no parent resolver, 
	 * the given {@link ResourceSet} for resolving objects
	 * and no {@link Resource} in which the mapping is stored.
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be null
	 * @throws IllegalArgumentException if given {@link ResourceSet} is null
	 */
	new(ResourceSet resourceSet) {
		this(null, resourceSet, null)
	}

	/**
	 * Instantiates a UUID generator and resolver with the given parent resolver, used when
	 * this resolver cannot resolve a UUID, the given {@link ResourceSet} for resolving objects
	 * and no {@link Resource} in which the mapping is stored.
	 * @param parentUuidResolver -
	 * 		the parent {@link UuidResolver} used to resolve UUID if this contains no appropriate mapping, may be null
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be null
	 * @throws IllegalArgumentException if given {@link ResourceSet} is null
	 */
	new(UuidResolver parentUuidResolver, ResourceSet resourceSet) {
		this(parentUuidResolver, resourceSet, null)
	}

	/**
	 * Instantiates a UUID generator and resolver with no parent resolver, 
	 * the given {@link ResourceSet} for resolving objects
	 * and the given {@link Resource} for storing the mapping.
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be null
	 * @param uuidResource -
	 * 		the {@link Resource} to store the mapping in, may be null
	 * @throws IllegalArgumentException if given {@link ResourceSet} is null
	 */
	new(ResourceSet resourceSet, Resource uuidResource) {
		this(null, resourceSet, uuidResource)
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
	 * @throws IllegalArgumentException if given {@link ResourceSet} is null
	 */
	new(UuidResolver parentUuidResolver, ResourceSet resourceSet, Resource uuidResource) {
		checkArgument(resourceSet !== null, "Resource set may not be null")
		this.resourceSet = resourceSet
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
			throw new IllegalStateException("Object has no UUID and is not globally accessible: " + eObject)
		}
		return uuid
	}

	private def String generateUuid() {
		return EcoreUtil.generateUUID()
	}

	override registerEObject(String uuid, EObject eObject) {
		checkState(eObject !== null, "Object must not be null")
		if (logger.isDebugEnabled) logger.debug('''Adding UUID «uuid» for EObject: «eObject»''')
		
		val uuidMapped = repository.uuidToEObject.put(uuid, eObject)
		if (uuidMapped !== null) {
			repository.EObjectToUuid.remove(uuidMapped)
		}
		repository.EObjectToUuid.put(eObject, uuid)
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
			val childContents = childResolver.resourceSet.getResource(uri, true).allContents
			val ourContents = this.resourceSet.getResource(uri, true).allContents
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
		resourceSet.getEObject(uri, true)
	}

	def private static getResolvableUri(EObject object) {
		// we cannot simply use EcoreUtil#getURI, because object’s domain might use XMI	UUIDs. Since
		// XMI UUIDs can be different for different resource sets, we cannot use URIs with XMI UUIDs to identify objects
		// across resource sets. Hence, we force hierarchical URIs. This assumes that the resolved object’s graph
		// has the same topology in the resolving resource set. This assumption holds when we use this method.
		val resource = object.eResource
		var rootElementIndex = 0;
		val resourceRoot = if (resource.contents.size <= 1) {
			object.eResource.firstRootEObject
		} else {
			// move up containment hierarchy until some container is one of the resource's root elements
			var container = object
			while (container !== null && (rootElementIndex = resource.contents.indexOf(container)) == -1) {
    			container = container.eContainer
			}
			checkState(container !== null, "some container of %s must be a root element of its resource", object)
			container
		}  
		val fragmentPath = EcoreUtil.getRelativeURIFragmentPath(resourceRoot, object)
		if (fragmentPath.isEmpty) {
			resource.URI.appendFragment('/' +  rootElementIndex)
		} else {
			resource.URI.appendFragment('/' +  rootElementIndex + '/' + fragmentPath)
		}
	}
	
}

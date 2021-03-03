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
import static com.google.common.base.Preconditions.checkNotNull
import tools.vitruv.framework.util.ResourceRegistrationAdapter
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import static extension tools.vitruv.framework.util.ResourceSetUtil.withGlobalFactories

/**
 * {@link UuidGeneratorAndResolver}
 */
package class UuidGeneratorAndResolverImpl implements UuidGeneratorAndResolver {
	static val logger = Logger.getLogger(UuidGeneratorAndResolverImpl)
	val ResourceSet resourceSet
	val Resource uuidResource
	val UuidResolver parentUuidResolver
	val UuidToEObjectRepository cache
	UuidToEObjectRepository repository

	/**
	 * Instantiates a UUID generator and resolver with the given parent resolver, used when
	 * this resolver cannot resolve a UUID, the given {@link ResourceSet} for resolving objects
	 * and a resource at the given {@link URI} for storing the mapping in.
	 * 
	 * @param parentUuidResolver -
	 * 		the parent {@link UuidResolver} used to resolve UUID if this contains no appropriate 
	 * 		mapping, may be {@code null}
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be {@code null}
	 * @param resourceUri -
	 * 		the {@link URI} to place a resource for storing the mapping in, may be {@code null}
	 * @throws IllegalArgumentExceptionif given {@link ResourceSet} is {@code null}
	 */
	new(UuidResolver parentUuidResolver, ResourceSet resourceSet, URI resourceUri) {
		checkArgument(resourceSet !== null, "Resource set may not be null")
		this.resourceSet = resourceSet
		this.parentUuidResolver = parentUuidResolver ?: UuidResolver.EMPTY
		this.cache = UuidFactory.eINSTANCE.createUuidToEObjectRepository
		this.repository = UuidFactory.eINSTANCE.createUuidToEObjectRepository
		this.uuidResource = if (resourceUri !== null)
			new ResourceSetImpl().withGlobalFactories.createResource(resourceUri) => [
				contents += repository
			]
		this.resourceSet.eAdapters += new ResourceRegistrationAdapter[resource|loadUuidsFromParent(resource)]
	}

	/**
	 * Loads the mapping between UUIDs and {@link EObject}s from the persistence at the {@link URI}
	 * given in the constructor and resolves the referenced {@link EObject}s in the {@link ResourceSet}
	 * given in the constructor.
	 */
	def package loadUuidsAndModelsFromSerializedUuidRepository() {
		checkState(uuidResource !== null, "UUID resource must be specified to load existing UUIDs")
		val loadedResource = new ResourceSetImpl().withGlobalFactories.loadOrCreateResource(uuidResource.URI)
		val loadedRepository = loadedResource.resourceContentRootIfUnique?.dynamicCast(UuidToEObjectRepository,
			"UUID provider and resolver model")
		if (loadedRepository !== null) {
			for (proxyEntry : loadedRepository.EObjectToUuid.entrySet) {
				val resolvedObject = EcoreUtil.resolve(proxyEntry.key, resourceSet)
				if (resolvedObject.eIsProxy) {
					throw new IllegalStateException("Object " + proxyEntry.key +
						" has a UUID but could not be resolved")
				}
				registerEObject(proxyEntry.value, resolvedObject)
			}
		}
	}

	override getUuid(EObject eObject) {
		val result = internalGetUuid(eObject)
		checkState(result !== null, "No UUID registered for EObject: %s", eObject)
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
		checkState(eObject !== null, "No EObject could be found for UUID: %s", uuid)
		return eObject
	}

	override getPotentiallyCachedEObject(String uuid) {
		cache.uuidToEObject.get(uuid) ?: getEObject(uuid)
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
		if(logger.isTraceEnabled) logger.trace('''Adding UUID «uuid» for EObject: «eObject»''')

		val uuidMapped = repository.uuidToEObject.put(uuid, eObject)
		if (uuidMapped !== null) {
			repository.EObjectToUuid.remove(uuidMapped)
		}
		repository.EObjectToUuid.put(eObject, uuid)
	}

	override hasPotentiallyCachedEObject(String uuid) {
		cache.uuidToEObject.containsKey(uuid) || internalGetEObject(uuid) !== null
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
			throw new IllegalStateException("EObject '" + eObject + "' cannot be registered because it does not have a UUID yet")
		}
	}

	override getPotentiallyCachedUuid(EObject eObject) {
		return cache.EObjectToUuid.get(eObject) ?:
			cache.EObjectToUuid.findFirst[EcoreUtil.equals(eObject, key)]?.value ?: getUuid(eObject)
	}

	override hasPotentiallyCachedUuid(EObject eObject) {
		cache.EObjectToUuid.containsKey(eObject) || hasUuid(eObject)
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
			resource.URI.appendFragment('/' + rootElementIndex)
		} else {
			resource.URI.appendFragment('/' + rootElementIndex + '/' + fragmentPath)
		}
	}

	override save() {
		cleanupRemovedElements()
		uuidResource?.save(null)
	}

	private def cleanupRemovedElements() {
		for (val iterator = repository.EObjectToUuid.keySet.iterator(); iterator.hasNext();) {
			val object = iterator.next()
			if (object.eResource === null) {
				val uuid = repository.EObjectToUuid.get(object)
				repository.uuidToEObject.removeKey(uuid)
				iterator.remove()
			}
		}
	}

	override close() {
		this.uuidResource.unload
	}

}

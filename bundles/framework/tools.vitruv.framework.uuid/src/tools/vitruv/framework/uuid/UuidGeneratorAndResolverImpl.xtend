package tools.vitruv.framework.uuid

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.uuid.UuidToEObjectRepository
import tools.vitruv.framework.uuid.UuidFactory
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.resource.Resource
import org.apache.log4j.Logger
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.loadOrCreateResource
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.withGlobalFactories
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.*
import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import static com.google.common.base.Preconditions.checkNotNull
import tools.vitruv.framework.util.ResourceRegistrationAdapter
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceUtil.getFirstRootEObject

/**
 * {@link UuidGeneratorAndResolver}
 */
package class UuidGeneratorAndResolverImpl implements UuidGeneratorAndResolver {
	static val logger = Logger.getLogger(UuidGeneratorAndResolverImpl)
	static val NON_READONLY_PREFIX = "ord_"
	
	val ResourceSet resourceSet
	val Resource uuidResource
	val UuidResolver parentUuidResolver
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
		this.repository = UuidFactory.eINSTANCE.createUuidToEObjectRepository
		this.uuidResource = if (resourceUri !== null)
			new ResourceSetImpl().withGlobalFactories.createResource(resourceUri) => [
				contents += repository
			]
		this.resourceSet.eAdapters += new ResourceRegistrationAdapter[resource|loadUuidsFromParent(resource.URI)]
	}

	override loadUuidsAndModelsFromSerializedUuidRepository() {
		checkState(uuidResource !== null, "UUID resource must be specified to load existing UUIDs")
		val loadedResource = new ResourceSetImpl().withGlobalFactories.loadOrCreateResource(uuidResource.URI)
		if (!loadedResource.contents.empty) {
			val loadedRepository = loadedResource.contents.get(0) as UuidToEObjectRepository
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

	override save() {
		cleanupRemovedElements()
		uuidResource?.save(null)
	}

	private def cleanupRemovedElements() {
		for (val iterator = repository.EObjectToUuid.keySet.iterator(); iterator.hasNext();) {
			val object = iterator.next()
			if (object.eResource === null || object.eResource.resourceSet === null) {
				val uuid = repository.EObjectToUuid.get(object)
				repository.uuidToEObject.removeKey(uuid)
				iterator.remove()
			}
		}
	}

	override close() {
		this.uuidResource.unload()
	}

	override getUuid(EObject eObject) {
		val result = eObject.getUuidOrNull()
		checkState(result !== null, "No UUID registered for EObject: %s", eObject)
		return result
	}
	
	private def getUuidOrNull(EObject eObject) {
		return eObject.getUuidIfObjectReadonly()
			?: eObject.getUuidIfStoredEObject()
			?: eObject.getUuidIfEClass()
			?: null
	}
	
	private def getUuidIfObjectReadonly(EObject eObject) {
		val uri = EcoreUtil.getURI(eObject)
		if (uri.isReadOnly()) {
			return uri.toString
		}
	}
	
	private def getUuidIfStoredEObject(EObject eObject) {
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
	}
	
	private def getUuidIfEClass(EObject eObject) {
		if (eObject instanceof EClass) {
			return generateUuid(eObject)
		}
	}
	
	override getEObject(String uuid) {
		val eObject = uuid.getEObjectOrNull()
		checkState(eObject !== null, "No EObject could be found for UUID: %s", uuid)
		return eObject
	}

	private def EObject getEObjectOrNull(String uuid) {
		return uuid.getEObjectIfReadonlyUri()
			?: uuid.getStoredEObject()
			?: null
	}
	
	private def getEObjectIfReadonlyUri(String uuid) {
		if (!uuid.startsWith(NON_READONLY_PREFIX)) {
			return resolve(URI.createURI(uuid))
		}
	}
	
	private def getStoredEObject(String uuid) {
		return repository.uuidToEObject.get(uuid)
	}

	override String generateUuid(EObject eObject) {
		checkState(!eObject.eIsProxy, "Cannot generate UUID for proxy object: " + eObject)
		val uuid = generateUuid()
		registerEObject(uuid, eObject)
		return uuid
	}

	private def String generateUuid() {
		return NON_READONLY_PREFIX + EcoreUtil.generateUUID()
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

	override hasUuid(EObject object) {
		return object.getUuidOrNull() !== null
	}

	override hasEObject(String uuid) {
		return uuid.getEObjectOrNull() !== null
	}

	override getResourceSet() {
		return resourceSet
	}

	private def void loadUuidsFromParent(URI uri) {
		// Only load UUIDs if resource exists and is not read only
		if (parentUuidResolver !== EMPTY && !uri.readOnly && uri.existsResourceAtUri) {
			val childContents = this.resourceSet.getResource(uri, true).allContents
			val parentResource = parentUuidResolver.resourceSet.getResource(uri, false)
			checkState(parentResource !== null, "no matching resource at '%s' in parent resolver", uri)
			val parentContents = parentResource.allContents
			while (childContents.hasNext) {
				val childObject = childContents.next
				checkState(parentContents.hasNext, "Cannot find %s in our resource set!", childObject)
				val ourObject = parentContents.next

				var objectUuid = parentUuidResolver.getUuid(ourObject)
				if (objectUuid === null) {
					throw new IllegalStateException('''Element does not have a UUID but should have one: «ourObject»''')
				}

				registerEObject(objectUuid, childObject)
			}
		}
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
	
	private static def isReadOnly(URI uri) {
		uri.isPathmap || uri.isArchive
	}

}

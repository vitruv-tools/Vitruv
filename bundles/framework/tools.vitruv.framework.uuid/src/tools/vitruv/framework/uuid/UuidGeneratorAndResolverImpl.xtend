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
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.getOrCreateResource

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
		this.parentUuidResolver = parentUuidResolver
		this.repository = UuidFactory.eINSTANCE.createUuidToEObjectRepository
		this.uuidResource = if (resourceUri !== null)
			new ResourceSetImpl().withGlobalFactories.createResource(resourceUri) => [
				contents += repository
			]
		this.resourceSet.eAdapters += new ResourceRegistrationAdapter[resource|loadUuidsFromParent(resource.URI)]
		this.resourceSet.resources.forEach[resource|loadUuidsFromParent(resource.URI)]
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
	
	override getResourceSet() {
		return resourceSet
	}

	override getResource(URI uri) {
		return resourceSet.getOrCreateResource(uri)
	}
	
	override hasResource(URI uri) {
		return resourceSet.getResource(uri, false) !== null
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
		val resourceURI = eObject.eResource?.URI
		if (resourceURI !== null && resourceURI.isReadOnly) {
			return EcoreUtil.getURI(eObject).toString
		}
	}
	
	private def getUuidIfStoredEObject(EObject eObject) {
		return repository.EObjectToUuid.get(eObject)
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
			val uri = URI.createURI(uuid)
			if (uri.hasFragment) {
				return resourceSet.getEObject(uri, true)
			}
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

	private def void loadUuidsFromParent(URI uri) {
		// Only load UUIDs if resource exists and is not read only
		if (parentUuidResolver !== null && !uri.readOnly && parentUuidResolver.hasResource(uri)) {
			val childContents = this.resourceSet.loadOrCreateResource(uri).allContents
			val parentResource = parentUuidResolver.getResource(uri)
			val parentContents = parentResource.allContents
			while (childContents.hasNext) {
				val childObject = childContents.next
				checkState(parentContents.hasNext, "Cannot find %s in our resource set!", childObject)
				val ourObject = parentContents.next

				var objectUuid = parentUuidResolver.getUuid(ourObject)
				if (objectUuid === null) {
					throw new IllegalStateException('''Element does not have a UUID but should have one: «ourObject»''')
				}

				// There may be proxy references across resources leading to duplicate processing of elements that were
				// already loaded
				if (objectUuid.hasEObject) {
					checkState(objectUuid.EObject == childObject)
				} else {
					registerEObject(objectUuid, childObject)
				}
			}
		}
	}

	private static def isReadOnly(URI uri) {
		uri.isPathmap || uri.isArchive
	}

}

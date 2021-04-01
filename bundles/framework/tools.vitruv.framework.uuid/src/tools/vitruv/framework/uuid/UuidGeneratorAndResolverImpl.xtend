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
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import java.util.PriorityQueue
import static extension tools.vitruv.framework.util.ObjectResolutionUtil.getHierarchicUriFragment
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.getOrCreateResource

/**
 * {@link UuidGeneratorAndResolver}
 */
package class UuidGeneratorAndResolverImpl implements UuidGeneratorAndResolver {
	static val logger = Logger.getLogger(UuidGeneratorAndResolverImpl)

	static val CACHE_PREFIX = "cache:/"
	
	val ResourceSet resourceSet
	val Resource uuidResource
	UuidToEObjectRepository repository

	/**
	 * Instantiates a UUID generator and resolver, the given {@link ResourceSet} for resolving objects
	 * and a resource at the given {@link URI} for storing the mapping in.
	 * 
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be {@code null}
	 * @param resourceUri -
	 * 		the {@link URI} to place a resource for storing the mapping in, may be {@code null}
	 * @throws IllegalArgumentExceptionif given {@link ResourceSet} is {@code null}
	 */
	new(ResourceSet resourceSet, URI resourceUri) {
		checkArgument(resourceSet !== null, "Resource set may not be null")
		this.resourceSet = resourceSet
		this.repository = UuidFactory.eINSTANCE.createUuidToEObjectRepository
		this.uuidResource = if (resourceUri !== null)
			new ResourceSetImpl().withGlobalFactories.createResource(resourceUri) => [
				contents += repository
			]
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
		checkState(cacheIds.noneMissing, "there are still elements in cache although a transaction has been closed")
	}

	private def cleanupRemovedElements() {
		for (val iterator = repository.EObjectToUuid.keySet.iterator(); iterator.hasNext();) {
			val object = iterator.next()
			if (object.eResource === null || object.eResource.resourceSet === null) {
				val uuid = repository.EObjectToUuid.get(object)
				repository.uuidToEObject.removeKey(uuid)
				if (uuid.isCache) {
					cacheIds.push(uuid)
				}
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
	
	private def getUuidOrNull(EObject eObject) {
		return eObject.getUuidIfObjectReadonly()
			?: eObject.getUuidIfStoredEObject()
			?: eObject.getUuidIfEClass()
			?: null
	}
	
	private def getUuidIfObjectReadonly(EObject eObject) {
		val resourceURI = eObject.eResource?.URI
		if (resourceURI.isReadOnly) {
			return EcoreUtil.getURI(eObject).toString
		}
	}
	
	private def getUuidIfStoredEObject(EObject eObject) {
		return repository.EObjectToUuid.get(eObject)
	}
	
	private def getUuidIfEClass(EObject eObject) {
		if (eObject instanceof EClass) {
			return getAndUpdateId(eObject)
		}
	}
	
	override getEObject(String uuid) {
		val eObject = uuid.getEObjectOrNull()
		checkState(eObject !== null, "No EObject could be found for UUID: %s", uuid)
		return eObject
	}

	private def EObject getEObjectOrNull(String uuid) {
		val uri = URI.createURI(uuid)
		return uri.getEObjectIfReadonlyUri()
			?: uri.getStoredEObject()
			?: null
	}
	
	private def getEObjectIfReadonlyUri(URI uri) {
		if (uri.readOnly) {
			if (uri.hasFragment) {
				return resourceSet.getEObject(uri, true)
			}
		}
	}
	
	private def getStoredEObject(URI uri) {
		return repository.uuidToEObject.get(uri.toString)
	}

	override String getAndUpdateId(EObject eObject) {
		return if (eObject.eResource !== null) {
			eObject.registerObjectInResource()
		} else {
			eObject.getOrRegisterCachedObject()
		}
	}
	
	private def String registerObjectInResource(EObject eObject) {
		val uuid = eObject.eResource.URI.appendFragment(eObject.hierarchicUriFragment).toString
		registerEObject(uuid, eObject)
		return uuid
	}
	
	private def getOrRegisterCachedObject(EObject eObject) {
		if (getUuidIfStoredEObject(eObject).isCache) {
			return getUuidIfStoredEObject(eObject)
		} else {
			val uuid = cacheIds.peek()
			registerEObject(uuid, eObject)
			return uuid
		}
	}
	
	override registerEObject(String uuid, EObject eObject) {
		checkState(eObject !== null, "Object must not be null")
		if(logger.isTraceEnabled) logger.trace('''Adding UUID «uuid» for EObject: «eObject»''')

		val oldObject = repository.uuidToEObject.put(uuid, eObject)
		val oldUuid = repository.EObjectToUuid.put(eObject, uuid)
		if (oldObject !== null && oldObject !== eObject) {
			repository.EObjectToUuid.remove(oldObject)
		}
		if (oldUuid !== null && oldUuid !== uuid) {
			repository.uuidToEObject.remove(oldUuid)
		}
		if (oldUuid.isCache) {
			cacheIds.push(oldUuid)
		}
		if (uuid.isCache) {
			val entry = cacheIds.pop()
			checkState(uuid == entry, "expected cache ID was %s but actually gave %s", uuid, entry)
		}
	}

	override hasUuid(EObject object) {
		return object.getUuidOrNull() !== null
	}

	override hasEObject(String uuid) {
		return uuid.getEObjectOrNull() !== null
	}

	private static def isReadOnly(URI uri) {
		uri !== null && (uri.isPathmap || uri.isArchive)
	}
	
	private static def isCache(String uuid) {
		uuid !== null && uuid.startsWith(CACHE_PREFIX)
	}
	
	val cacheIds = new CacheIdsRepository()
	
	static class CacheIdsRepository {
		val entries = new PriorityQueue<String>
		var int maxValue
		
		def pop() {
			if (entries.isEmpty) {
				push(CACHE_PREFIX + maxValue++)
			}
			entries.poll()
		}
		
		def peek() {
			if (entries.isEmpty) {
				return CACHE_PREFIX + maxValue
			} else {
				entries.peek()
			}
		}
		
		def push(String value) {
			checkState(value.isCache, "%s is a not a cache UUID", value)
			entries.add(value)
		}
		
		def boolean isNoneMissing() {
			return entries.length == maxValue
		}

	}
	
}

package tools.vitruv.framework.change.id

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.id.IdEObjectRepository
import tools.vitruv.framework.change.echange.id.IdFactory
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
 * {@link IdResolverAndRepository}
 */
package class IdResolverAndRepositoryImpl implements IdResolverAndRepository {
	static val logger = Logger.getLogger(IdResolverAndRepositoryImpl)

	static val CACHE_PREFIX = "cache:/"
	
	val ResourceSet resourceSet
	val Resource idResource
	IdEObjectRepository repository

	/**
	 * Instantiates an ID resolver and repository, the given {@link ResourceSet} for resolving objects
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
		this.repository = IdFactory.eINSTANCE.createIdEObjectRepository
		this.idResource = if (resourceUri !== null)
			new ResourceSetImpl().withGlobalFactories.createResource(resourceUri) => [
				contents += repository
			]
	}

	override loadIdsAndModelsFromSerializedIdRepository() {
		checkState(idResource !== null, "ID resource must be specified to load existing IDs")
		val loadedResource = new ResourceSetImpl().withGlobalFactories.loadOrCreateResource(idResource.URI)
		if (!loadedResource.contents.empty) {
			val loadedRepository = loadedResource.contents.get(0) as IdEObjectRepository
			for (proxyEntry : loadedRepository.EObjectToId.entrySet) {
				val resolvedObject = EcoreUtil.resolve(proxyEntry.key, resourceSet)
				if (resolvedObject.eIsProxy) {
					throw new IllegalStateException("Object " + proxyEntry.key +
						" has an ID but could not be resolved")
				}
				register(proxyEntry.value, resolvedObject)
			}
		}
	}

	override save() {
		cleanupRemovedElements()
		idResource?.save(null)
		checkState(cacheIds.noneMissing, "there are still elements in cache although a transaction has been closed")
	}

	private def cleanupRemovedElements() {
		for (val iterator = repository.EObjectToId.keySet.iterator(); iterator.hasNext();) {
			val object = iterator.next()
			if (object.eResource === null || object.eResource.resourceSet === null) {
				val id = repository.EObjectToId.get(object)
				repository.idToEObject.removeKey(id)
				if (id.isCache) {
					cacheIds.push(id)
				}
				iterator.remove()
			}
		}
	}

	override close() {
		this.idResource.unload()
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
	
	private def getIdOrNull(EObject eObject) {
		return eObject.getIdIfObjectReadonly()
			?: eObject.getIdIfStoredEObject()
			?: eObject.getIdIfEClass()
			?: null
	}
	
	private def getIdIfObjectReadonly(EObject eObject) {
		val resourceURI = eObject.eResource?.URI
		if (resourceURI.isReadOnly) {
			return EcoreUtil.getURI(eObject).toString
		}
	}
	
	private def getIdIfStoredEObject(EObject eObject) {
		return repository.EObjectToId.get(eObject)
	}
	
	private def getIdIfEClass(EObject eObject) {
		if (eObject instanceof EClass) {
			return getAndUpdateId(eObject)
		}
	}
	
	override getEObject(String id) {
		val eObject = id.getEObjectOrNull()
		checkState(eObject !== null, "no EObject could be found for ID: %s", id)
		return eObject
	}

	private def EObject getEObjectOrNull(String id) {
		val uri = URI.createURI(id)
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
		return repository.idToEObject.get(uri.toString)
	}

	override String getAndUpdateId(EObject eObject) {
		return if (eObject.eResource !== null) {
			eObject.registerObjectInResource()
		} else {
			eObject.getOrRegisterCachedObject()
		}
	}
	
	private def String registerObjectInResource(EObject eObject) {
		val id = eObject.eResource.URI.appendFragment(eObject.hierarchicUriFragment).toString
		register(id, eObject)
		return id
	}
	
	private def getOrRegisterCachedObject(EObject eObject) {
		if (getIdIfStoredEObject(eObject).isCache) {
			return getIdIfStoredEObject(eObject)
		} else {
			val id = cacheIds.peek()
			register(id, eObject)
			return id
		}
	}
	
	override register(String id, EObject eObject) {
		checkState(eObject !== null, "object must not be null")
		if(logger.isTraceEnabled) logger.trace('''Adding ID «id» for EObject: «eObject»''')

		val oldObject = repository.idToEObject.put(id, eObject)
		val oldId = repository.EObjectToId.put(eObject, id)
		if (oldObject !== null && oldObject !== eObject) {
			repository.EObjectToId.remove(oldObject)
		}
		if (oldId !== null && oldId !== id) {
			repository.idToEObject.remove(oldId)
		}
		if (oldId.isCache) {
			cacheIds.push(oldId)
		}
		if (id.isCache) {
			val entry = cacheIds.pop()
			checkState(id == entry, "expected cache ID was %s but actually gave %s", id, entry)
		}
	}

	override hasId(EObject object) {
		return object.getIdOrNull() !== null
	}

	override hasEObject(String id) {
		return id.getEObjectOrNull() !== null
	}

	private static def isReadOnly(URI uri) {
		uri !== null && (uri.isPathmap || uri.isArchive)
	}
	
	private static def isCache(String id) {
		id !== null && id.startsWith(CACHE_PREFIX)
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
			checkState(value.isCache, "%s is a not a cache ID", value)
			entries.add(value)
		}
		
		def boolean isNoneMissing() {
			return entries.length == maxValue
		}

	}
	
}

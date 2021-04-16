package tools.vitruv.framework.change.echange.id

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.*
import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import static com.google.common.base.Preconditions.checkNotNull
import java.util.PriorityQueue
import static extension tools.vitruv.framework.util.ObjectResolutionUtil.getHierarchicUriFragment
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.getOrCreateResource
import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap

/**
 * {@link IdResolver}
 */
package class IdResolverImpl implements IdResolver {
	static val logger = Logger.getLogger(IdResolverImpl)
	static val CACHE_PREFIX = "cache:/"
	
	val ResourceSet resourceSet
	val BiMap<EObject, String> eObjectToId = HashBiMap.create()
	

	/**
	 * Instantiates an ID resolver with the given {@link ResourceSet} for resolving objects.
	 * 
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be {@code null}
	 * @throws IllegalArgumentExceptionif given {@link ResourceSet} is {@code null}
	 */
	new(ResourceSet resourceSet) {
		checkArgument(resourceSet !== null, "Resource set may not be null")
		this.resourceSet = resourceSet
	}
	
	override endTransaction() {
		cleanupRemovedElements()
		checkState(cacheIds.noneMissing, "there are still elements in cache although a transaction has been closed")
	}
	
	private def cleanupRemovedElements() {
		for (val iterator = eObjectToId.keySet.iterator(); iterator.hasNext();) {
			val object = iterator.next()
			if (object.eResource === null || object.eResource.resourceSet === null) {
				val id = eObjectToId.get(object)
				if (id.isCache) {
					cacheIds.push(id)
				}
				iterator.remove()
			}
		}
	}
	
	override getResource(URI uri) {
		return resourceSet.getOrCreateResource(uri)
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
		val storedId = eObjectToId.get(eObject)
		if (storedId.isCache) {
			return storedId
		} else {
			val id = cacheIds.peek()
			register(id, eObject)
			return id
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
			?: uri.getAndRegisterNonStoredEObject()
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
		return eObjectToId.inverse.get(uri.toString)
	}
	
	private def getAndRegisterNonStoredEObject(URI uri) {
		val candidate = resourceSet.getEObject(uri, false)
		if (candidate !== null) getAndUpdateId(candidate)
		return candidate
	}
	
	private def register(String id, EObject eObject) {
		checkState(eObject !== null, "object must not be null")
		if(logger.isTraceEnabled) logger.trace('''Adding ID «id» for EObject: «eObject»''')

		val oldObject = eObjectToId.inverse.get(id)
		val oldId = eObjectToId.get(eObject)
		if (oldObject !== null && oldObject !== eObject) {
			eObjectToId.remove(oldObject)
		}
		if (oldId !== null && oldId !== id) {
			eObjectToId.inverse.remove(oldId)
		}
		eObjectToId.put(eObject, id)
		if (oldId.isCache) {
			cacheIds.push(oldId)
		}
		if (id.isCache) {
			val entry = cacheIds.pop()
			checkState(id == entry, "expected cache ID was %s but actually gave %s", id, entry)
		}
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
	
	/**
	 * The cache IDs repository provides cache IDs with values starting from 0, always providing the
	 * one with the lowest index first. It ensures that the same sequence of taking and pushing entries
	 * always gives the same values.
	 */
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

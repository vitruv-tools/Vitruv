package tools.vitruv.framework.change.echange.id

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import java.util.HashMap
import java.util.UUID
import org.apache.log4j.Logger
import org.eclipse.emf.common.notify.impl.AdapterImpl
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet

import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState

import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.*
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.getOrCreateResource
import static extension tools.vitruv.framework.util.ObjectResolutionUtil.getHierarchicUriFragment

/**
 * {@link IdResolver}
 */
package class IdResolverImpl extends AdapterImpl implements IdResolver {
	static val logger = Logger.getLogger(IdResolverImpl)
//	static val CACHE_PREFIX = "cache:/"
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
//		checkState(cacheIds.noneMissing, "there are still elements in cache although a transaction has been closed")
	}

	override HashMap<String, String> cleanupOutsideElements() {
		val oldToNewIdsMap = new HashMap<String, String>()
		val pathToObjectMap = new HashMap<String, EObject>()
		for (val iterator = eObjectToId.keySet.iterator(); iterator.hasNext();) {
			val newObject = iterator.next()
			if (newObject.eResource !== null && newObject.eResource.resourceSet !== null &&
				newObject.eResource.resourceSet == this.resourceSet) {
				val newObjectPathId = newObject.eResource.URI.appendFragment(newObject.hierarchicUriFragment).toString
				pathToObjectMap.put(newObjectPathId, newObject)
			}
		}
		for (val iterator = eObjectToId.keySet.iterator(); iterator.hasNext();) {
			val oldObject = iterator.next()
			if (oldObject.eResource === null) {
//				System.out.println("DELETED ID3: " + eObjectToId.get(oldObject) + " / " + oldObject)
//				iterator.remove
			} else if (oldObject.eResource !== null && oldObject.eResource.resourceSet !== null &&
				oldObject.eResource.URI !== null && oldObject.eResource.resourceSet != this.resourceSet) {
				val oldObjectPathId = oldObject.eResource.URI.appendFragment(oldObject.hierarchicUriFragment).toString
				val newObject = pathToObjectMap.get(oldObjectPathId)
				if (newObject !== null) {
					val oldObjectId = eObjectToId.get(oldObject)
					val newObjectId = eObjectToId.get(newObject)
					oldToNewIdsMap.put(oldObjectId, newObjectId)
				} else {
					throw new IllegalStateException("TODO")
				}
//				System.out.println("DELETED ID: " + eObjectToId.get(oldObject) + " / " + oldObject)
//				iterator.remove
			}
		}
		return oldToNewIdsMap
	}

	private def cleanupRemovedElements() {
		for (val iterator = eObjectToId.keySet.iterator(); iterator.hasNext();) {
			val object = iterator.next()
			if (object.eResource === null || object.eResource.resourceSet === null) {
				val id = eObjectToId.get(object)
//				if (id.isCache) {
//					cacheIds.push(id)
//				}
//				System.out.println("DELETED ID2: " + eObjectToId.get(object) + " / " + object)
//				iterator.remove()
			}
		}
	}

	override getResource(URI uri) {
		return resourceSet.getOrCreateResource(uri)
	}

	override String getAndUpdateId(EObject eObject, String id) {
		checkState(id !== null, "ID must not be null!")
		checkState(eObject !== null, "EObject must not be null!")
		checkState(eObjectToId.get(eObject) === null, "Object already had ID!")
		checkState(eObjectToId.inverse.get(id) === null, "ID already had Object!")

//		System.out.println("SET ID: " + id + " / " + (eObject.eResource !== null
//			? eObject.eResource.URI.appendFragment(eObject.hierarchicUriFragment).toString
//			: "nores") + " / " + this)
		eObjectToId.put(eObject, id)

		return id
	}

	override String getAndUpdateId(EObject eObject) {
		checkState(eObject !== null, "EObject must not be null!")

		if (eObject.eResource !== null && eObject.eResource.URI.readOnly) {
			val id = eObject.eResource.URI.appendFragment(eObject.hierarchicUriFragment).toString
			return id
		}

		var id = eObjectToId.get(eObject)
		if (id === null) {
			id = UUID.randomUUID.toString
			while (eObjectToId.inverse.containsKey(id))
				id = UUID.randomUUID.toString
//			System.out.println("GENERATED ID: " + id + " / " + (eObject.eResource !== null
//				? eObject.eResource.URI.appendFragment(eObject.hierarchicUriFragment).toString
//				: "nores") + " / " + this)
//			System.out.println("FOR OBJECT: " + eObject)
			eObjectToId.put(eObject, id)
		}
		return id

//		var id = EcoreUtil.getID(eObject)
//		val storedId = eObjectToId.get(eObject)
//		
////		checkState(storedId == id, "ID %s of object and ID %s in id resolver should be equal!", id, storedId)
//		
////		if (id !== storedId) {
////			System.out.println("WARNING: object ID " + id + " and stored ID " + storedId + " are not equal.")
////			EcoreUtil.setID(eObject, storedId)
////		}
//		
//		if (id !== null) {
//			if (id == "root") {
//				if (storedId !== null) {
//					EcoreUtil.setID(eObject, storedId)
//				} else {
//					id = UUID.randomUUID.toString
//					System.out.println("GENERATED ID: " + id)
//					EcoreUtil.setID(eObject, id)
//					eObjectToId.put(eObject, id)
//				}
//			} else {
//				eObjectToId.put(eObject, id)
//			}
//		} else if (id === null) {
//			id = UUID.randomUUID.toString
//			System.out.println("GENERATED ID: " + id)
//			EcoreUtil.setID(eObject, id)
//			eObjectToId.put(eObject, id)
//		}
//		
//		return id
//		return if (eObject.eResource !== null) {
//			eObject.registerObjectInResource()
//		} else {
//			eObject.getOrRegisterCachedObject()
//		}
	}

// NOTE: the following is not used anymore
//	private def String registerObjectInResource(EObject eObject) {
//		val id = eObject.eResource.URI.appendFragment(eObject.hierarchicUriFragment).toString
//		register(id, eObject)
//		return id
//	}
//
//	private def getOrRegisterCachedObject(EObject eObject) {
//		val storedId = eObjectToId.get(eObject)
//		if (storedId.isCache) {
//			return storedId
//		} else {
//			val id = cacheIds.peek()
//			register(id, eObject)
//			return id
//		}
//	}
	override getEObject(String id) {
		val eObject = id.getEObjectOrNull()

//		if (eObject === null)
//			System.out.println("FAIL: " + id)
		checkState(eObject !== null, "no EObject could be found for ID: %s", id)
		return eObject
	}

	private def EObject getEObjectOrNull(String id) {
		val uri = URI.createURI(id)
		if (uri.readOnly) {
			if (uri.hasFragment) {
				return resourceSet.getEObject(uri, true)
			}
		}

		return eObjectToId.inverse.get(id)

//		// NOTE: still use the map! because: resources that are created are not immediately inserted into a resource!!!
//		val object = eObjectToId.inverse.get(id)
//		if (object !== null) {
//			checkState(EcoreUtil.getID(object) == id, "ID of object and ID in id resolver should be equal!")
//			return object
//		} else {
//			// this should not be necessary!
//			//throw new IllegalArgumentException("This should not happen!");
//			return null
////			//val candidate = resourceSet.getEObject(uri, false)
////			for (Resource resource : resourceSet.resources) {
////				val candidate = resource.getEObject(id) 
////				if (candidate !== null) {
////					getAndUpdateId(candidate)
////					return candidate
////				}
////			}
////			//throw new IllegalArgumentException("EObject with ID " + id + " does not exist in any resource in the resource set.")
//		}
//		val uri = URI.createURI(id)
//		return uri.getEObjectIfReadonlyUri()
//			?: uri.getStoredEObject()
//			?: uri.getAndRegisterNonStoredEObject()
//			?: null
	}

// NOTE: the following is not used anymore
//	private def getEObjectIfReadonlyUri(URI uri) {
//		if (uri.readOnly) {
//			if (uri.hasFragment) {
//				return resourceSet.getEObject(uri, true)
//			}
//		}
//	}
//
//	private def getStoredEObject(URI uri) {
//		return eObjectToId.inverse.get(uri.toString)
//	}
//
//	private def getAndRegisterNonStoredEObject(URI uri) {
//		val candidate = resourceSet.getEObject(uri, false)
//		if(candidate !== null) getAndUpdateId(candidate)
//		return candidate
//	}
//
//	private def register(String id, EObject eObject) {
//		checkState(eObject !== null, "object must not be null")
//		if(logger.isTraceEnabled) logger.trace('''Adding ID «id» for EObject: «eObject»''')
//
//		val oldObject = eObjectToId.inverse.get(id)
//		val oldId = eObjectToId.get(eObject)
//		if (oldObject !== null && oldObject !== eObject) {
//			eObjectToId.remove(oldObject)
//		}
//		if (oldId !== null && oldId !== id) {
//			eObjectToId.inverse.remove(oldId)
//		}
//		eObjectToId.put(eObject, id)
//		if (oldId.isCache) {
//			cacheIds.push(oldId)
//		}
//		if (id.isCache) {
//			val entry = cacheIds.pop()
//			checkState(id == entry, "expected cache ID was %s but actually gave %s", id, entry)
//		}
//	}
	override hasEObject(String id) {
		return id.getEObjectOrNull() !== null
	}

	private static def isReadOnly(URI uri) {
		uri !== null && (uri.isPathmap || uri.isArchive)
	}

// NOTE: the following is not used anymore
//	private static def isCache(String id) {
//		id !== null && id.startsWith(CACHE_PREFIX)
//	}
//
//	val cacheIds = new CacheIdsRepository()
//
//	/**
//	 * The cache IDs repository provides cache IDs with values starting from 0, always providing the
//	 * one with the lowest index first. It ensures that the same sequence of taking and pushing entries
//	 * always gives the same values.
//	 */
//	static class CacheIdsRepository {
//		val entries = new PriorityQueue<String>
//		var int maxValue
//
//		def pop() {
//			if (entries.isEmpty) {
//				push(CACHE_PREFIX + maxValue++)
//			}
//			entries.poll()
//		}
//
//		def peek() {
//			if (entries.isEmpty) {
//				return CACHE_PREFIX + maxValue
//			} else {
//				entries.peek()
//			}
//		}
//
//		def push(String value) {
//			checkState(value.isCache, "%s is a not a cache ID", value)
//			entries.add(value)
//		}
//
//		def boolean isNoneMissing() {
//			return entries.length == maxValue
//		}
//
//	}
}

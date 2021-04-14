package tools.vitruv.framework.change.id

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.id.IdEObjectRepository
import tools.vitruv.framework.change.echange.id.IdFactory
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.resource.Resource
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil.*
import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import static com.google.common.base.Preconditions.checkNotNull
import java.util.PriorityQueue
import static extension tools.vitruv.framework.util.ObjectResolutionUtil.getHierarchicUriFragment
import static extension edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil.getOrCreateResource
import org.eclipse.emf.common.notify.Notifier
import org.eclipse.xtend.lib.annotations.Accessors

/**
 * {@link IdResolver}
 */
package class IdResolverImpl implements IdResolver {
	static val logger = Logger.getLogger(IdResolverImpl)
	static val CACHE_PREFIX = "cache:/"
	
	@Accessors(PROTECTED_GETTER)
	val ResourceSet resourceSet
	@Accessors(PROTECTED_GETTER)
	IdEObjectRepository repository

	/**
	 * Instantiates an ID resolver and repository, the given {@link ResourceSet} for resolving objects.
	 * 
	 * @param resourceSet -
	 * 		the {@link ResourceSet} to load model elements from, may not be {@code null}
	 * @throws IllegalArgumentExceptionif given {@link ResourceSet} is {@code null}
	 */
	new(ResourceSet resourceSet) {
		checkArgument(resourceSet !== null, "Resource set may not be null")
		this.resourceSet = resourceSet
		this.repository = IdFactory.eINSTANCE.createIdEObjectRepository
	}
	
	override endTransaction() {
		cleanupRemovedElements()
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
	
	override canCalculateIdsIn(Notifier notifier) {
		switch (notifier) {
			case null: true
			EObject: canCalculateIdsIn(notifier?.eResource)
			Resource: canCalculateIdsIn(notifier?.resourceSet)
			ResourceSet: notifier == resourceSet
			default: throw new IllegalStateException("Unexpected notifier type: " + notifier.class.simpleName)
		}
	}
	
	override getResource(URI uri) {
		return resourceSet.getOrCreateResource(uri)
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
		return repository.idToEObject.get(uri.toString)
	}
	
	private def getAndRegisterNonStoredEObject(URI uri) {
		val candidate = resourceSet.getEObject(uri, false)
		if (candidate !== null) getAndUpdateId(candidate)
		return candidate
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
	
	private def register(String id, EObject eObject) {
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

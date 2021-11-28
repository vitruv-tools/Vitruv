package tools.vitruv.framework.change.echange.id

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import java.util.HashMap
import java.util.UUID
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
					throw new IllegalStateException(
						"There was a reference to an outside object (from another resource set) in the id resolver that did not have a corresponding object in the resource set of the id resolver.")
				}
//				iterator.remove
			}
		}
		return oldToNewIdsMap
	}

	private def cleanupRemovedElements() {
		for (val iterator = eObjectToId.keySet.iterator(); iterator.hasNext();) {
			val object = iterator.next()
			if (object.eResource === null || object.eResource.resourceSet === null) {
//				iterator.remove()
			}
		}
	}

	override getResource(URI uri) {
		return resourceSet.getOrCreateResource(uri)
	}

	override String setAndGetId(EObject eObject, String id) {
		checkState(id !== null, "ID must not be null!")
		checkState(eObject !== null, "EObject must not be null!")
		checkState(eObjectToId.get(eObject) === null, "Object already had ID!")
		checkState(eObjectToId.inverse.get(id) === null, "ID already had Object!")

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
			eObjectToId.put(eObject, id)
		}
		return id
	}

	override getEObject(String id) {
		val eObject = id.getEObjectOrNull()

//		checkState(eObject !== null, "no EObject could be found for ID: %s", id)

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
	}

	override hasEObject(String id) {
		return id.getEObjectOrNull() !== null
	}

	private static def isReadOnly(URI uri) {
		uri !== null && (uri.isPathmap || uri.isArchive)
	}

}

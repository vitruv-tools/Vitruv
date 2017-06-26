package tools.vitruv.framework.change.echange.resolve

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.echange.resolve.impl.StagingAreaImpl

/**
 * Class represents a staging area for EObjects which are in
 * the state of being inserted or deleted. Newly created objects
 * are stored in the staging area, until they are inserted.
 * The staging area can be used as a queue.
 */
interface StagingArea {
	/**
	 * Returns the staging area resource of a resource set.
	 * If there is no staging area, a new one will be created.
	 * @param resourceSet The resource set.
	 * @returns The staging area resource of the resource. If the
	 * 		resource set is {@code null} it returns {@code null}
	 */
	static def StagingArea getStagingArea(ResourceSet resourceSet) {
		StagingAreaImpl::getStagingArea(resourceSet)
	}

	/**
	 * Returns the staging area of a resource' resource set.
	 * If there is no staging area, a new one will be created.
	 * @param resource The resource.
	 * @returns The staging area resource of the resources resource set.
	 * 		If the resource is {@code null} it returns {@code null}
	 */
	static def StagingArea getStagingArea(Resource resource) {
		StagingAreaImpl::getStagingArea(resource?.resourceSet)
	}

	/**
	 * Removes and returns the first object of the staging area.
	 * @returns 						The first object of the staging area.
	 * @throws IllegalStateException 	The staging area is empty.
	 */
	def EObject poll()

	/**
	 * Returns the first object of the staging area.
	 * @returns							The first object of the staging area.
	 * @throws IllegalStateException	The staging area is empty.
	 */
	def EObject peek()

	/**
	 * Adds a new object to the back of the staging area.
	 * @param object	The inserted EObject::
	 */
	def void add(EObject object)

	/**
	 * Checks if the staging area is empty.
	 * @returns	The staging area is empty.
	 */
	def boolean isEmpty()

	/**
	 * Clears the staging area.
	 */
	def void clear()
}

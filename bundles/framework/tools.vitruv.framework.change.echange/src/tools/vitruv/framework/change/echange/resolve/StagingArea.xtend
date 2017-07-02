package tools.vitruv.framework.change.echange.resolve

import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceImpl

/**
 * Class represents a staging area for EObjects which are in
 * the state of being inserted or deleted. Newly created objects
 * are stored in the staging area, until they are inserted.
 * The staging area can be used as a queue.
 */
final public class StagingArea {
	private Resource stagingAreaContent

	private static val Map<ResourceSet, StagingArea> stagingAreas = new HashMap<ResourceSet, StagingArea>()
	
	/**
	 * Returns the staging area resource of a resource set.
	 * If there is no staging area, a new one will be created.
	 * @param resourceSet The resource set.
	 * @returns The staging area resource of the resource. If the
	 * 		resource set is {@code null} it returns {@code null}
	 */
	def public static StagingArea getStagingArea(ResourceSet resourceSet) {
		if (resourceSet === null) {
			return null
		}
		
		if (stagingAreas.get(resourceSet) === null) {
			stagingAreas.put(resourceSet, new StagingArea())
		}
		return stagingAreas.get(resourceSet)
	}
	
	/**
	 * Returns the staging area of a resource' resource set.
	 * If there is no staging area, a new one will be created.
	 * @param resource The resource.
	 * @returns The staging area resource of the resources resource set.
	 * 		If the resource is {@code null} it returns {@code null}
	 */
	def public static StagingArea getStagingArea(Resource resource) {
		if (resource === null) {
			return null
		}
		return getStagingArea(resource.resourceSet)
	}
	
	private new() {
		this.stagingAreaContent = new ResourceImpl()
	}
	
	/**
	 * Removes and returns the first object of the staging area.
	 * @returns 						The first object of the staging area.
	 * @throws IllegalStateException 	The staging area is empty.
	 */
	def public EObject poll() {
		if (stagingAreaContent.contents.empty) {
			throw new IllegalStateException
		}
		return stagingAreaContent.contents.remove(0)		
	}
	
	/**
	 * Returns the first object of the staging area.
	 * @returns							The first object of the staging area.
	 * @throws IllegalStateException	The staging area is empty.
	 */
	def public EObject peek() {
		if (stagingAreaContent.contents.empty) {
			throw new IllegalStateException
		}
		return stagingAreaContent.contents.get(0)
	}
	
	/**
	 * Adds a new object to the back of the staging area.
	 * @param object	The inserted EObject.
	 */
	def public void add(EObject object) {
		stagingAreaContent.contents.add(object)
	}
	
	/**
	 * Checks if the staging area is empty.
	 * @returns	The staging area is empty.
	 */
	def public boolean isEmpty() {
		return stagingAreaContent.contents.empty
	}
	
	/**
	 * Clears the staging area.
	 */
	def public void clear() {
		stagingAreaContent.contents.clear
	}
}
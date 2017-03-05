package tools.vitruv.framework.change.echange.util

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet

// TODO
public class StagingArea {
	public static val DEFAULT_RESOURCE_NAME = "stagingArea.staging"
	public static val DEFAULT_RESOURCE_EXTENSION = "staging"
	
	/**
	 * Returns the staging area resource of a resource set.
	 * If there is no staging area, a new one will be created.
	 * @param resourceSet The resource set.
	 * @returns The staging area resource of the resource. If the
	 * 		resource set is {@code null} it returns {@code null}
	 */
	public static def Resource getStagingArea(ResourceSet resourceSet) {
		if (resourceSet == null) {
			return null
		}
		val stagingAreaURI = URI.createURI(DEFAULT_RESOURCE_NAME)
		// Check if there is already a staging area
		val stagingArea = resourceSet.getResource(stagingAreaURI, false)
		if (stagingArea == null) {
			// Create new one
			return resourceSet.createResource(stagingAreaURI)
		}
		return stagingArea
	}
	
	/**
	 * Returns the staging area of a resource' resource set.
	 * If there is no staging area, a new one will be created.
	 * @param resource The resource.
	 * @returns The staging area resource of the resources resource set.
	 * 		If the resource is {@code null} it returns {@code null}
	 */
	public static def Resource getStagingArea(Resource resource) {
		if (resource == null) {
			return null
		}
		return getStagingArea(resource.resourceSet)
	}
}
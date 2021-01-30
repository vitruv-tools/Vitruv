package tools.vitruv.framework.util

import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.Resource
import java.util.Map

/**
 * A resource set that offers a {@link #save} operation.
 */
interface SavingResourceSet extends ResourceSet {
	def void save(Resource resource, Map<?, ?> saveOptions)
	def void save(Resource resource) {
		save(resource, emptyMap)
	}
}
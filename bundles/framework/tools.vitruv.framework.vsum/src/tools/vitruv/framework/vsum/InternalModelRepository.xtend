package tools.vitruv.framework.vsum

import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.domains.repository.ModelRepository

interface InternalModelRepository extends ModelRepository {
	def ResourceSet getResourceSet()
}

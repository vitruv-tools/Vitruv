package tools.vitruv.framework.vsum

import org.eclipse.emf.ecore.resource.ResourceSet

interface InternalModelRepository extends ModelRepository {
	def ResourceSet getResourceSet()

	def void setIsCorrespondencesFilterActive(boolean x)
}

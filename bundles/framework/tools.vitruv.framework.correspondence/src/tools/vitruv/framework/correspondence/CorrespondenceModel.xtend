package tools.vitruv.framework.correspondence

import tools.vitruv.framework.correspondence.Correspondence
import org.eclipse.emf.ecore.resource.Resource

interface CorrespondenceModel extends GenericCorrespondenceModel<Correspondence> {
	// TODO HK Remove and define by Model interface!
	def Resource getResource()

	def void saveModel()
}

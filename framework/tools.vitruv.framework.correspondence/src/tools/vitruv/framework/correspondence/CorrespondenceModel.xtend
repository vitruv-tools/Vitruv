package tools.vitruv.framework.correspondence

import tools.vitruv.framework.correspondence.Correspondence
import org.eclipse.emf.ecore.resource.Resource

interface CorrespondenceModel extends GenericCorrespondenceModel<Correspondence> {
	// TODO HK Remove and define by Model interface!
	public def Resource getResource();
	public def void saveModel();
}

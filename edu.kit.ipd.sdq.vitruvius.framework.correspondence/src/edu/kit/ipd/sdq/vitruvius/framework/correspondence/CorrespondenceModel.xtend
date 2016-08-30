package edu.kit.ipd.sdq.vitruvius.framework.correspondence

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.Correspondence
import org.eclipse.emf.ecore.resource.Resource

interface CorrespondenceModel extends GenericCorrespondenceModel<Correspondence> {
	// TODO HK Remove and define by Model interface!
	public def Resource getResource();
	public def void saveModel();
}

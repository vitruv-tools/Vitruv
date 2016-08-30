package edu.kit.ipd.sdq.vitruvius.framework.correspondence

import edu.kit.ipd.sdq.vitruvius.framework.correspondence.Correspondence

interface CorrespondenceModel extends GenericCorrespondenceModel<Correspondence> {
	public def void saveModel();
}

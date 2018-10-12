package tools.vitruv.framework.correspondence.impl

import tools.vitruv.framework.correspondence.Correspondence
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.correspondence.InternalCorrespondenceModel
import tools.vitruv.framework.correspondence.CorrespondenceFactory

class GenericCorrespondenceModelViewImpl extends CorrespondenceModelViewImpl<Correspondence> implements CorrespondenceModel {
	
	new(InternalCorrespondenceModel correspondenceModel) {
		// FIXME Finally the CorrespondenceModel should not be editable
		super(Correspondence, correspondenceModel, [CorrespondenceFactory.eINSTANCE.createManualCorrespondence]);
	}
	
}
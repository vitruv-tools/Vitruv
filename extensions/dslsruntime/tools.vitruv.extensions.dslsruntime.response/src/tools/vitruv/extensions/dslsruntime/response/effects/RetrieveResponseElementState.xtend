package tools.vitruv.extensions.dslsruntime.response.effects

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.tuid.TUID

class RetrieveResponseElementState extends AbstractResponseElementState {
		
	new(EObject element, CorrespondenceModel correspondenceModel) {
		super(element, correspondenceModel);
		TUID.registerObjectUnderModification(element);
	}
	
}
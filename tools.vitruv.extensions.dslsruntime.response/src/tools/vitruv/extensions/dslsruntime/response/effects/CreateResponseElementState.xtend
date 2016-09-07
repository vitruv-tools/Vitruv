package tools.vitruv.extensions.dslsruntime.response.effects

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.correspondence.CorrespondenceModel

class CreateResponseElementState extends AbstractResponseElementState {
	
	new(EObject element, CorrespondenceModel correspondenceModel) {
		super(element, correspondenceModel)
	}
	
	override updateTUID() {
		// Do nothing
	}
	
}
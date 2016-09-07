package tools.vitruvius.extensions.dslsruntime.response.effects

import org.eclipse.emf.ecore.EObject
import tools.vitruvius.framework.correspondence.CorrespondenceModel

class CreateResponseElementState extends AbstractResponseElementState {
	
	new(EObject element, CorrespondenceModel correspondenceModel) {
		super(element, correspondenceModel)
	}
	
	override updateTUID() {
		// Do nothing
	}
	
}
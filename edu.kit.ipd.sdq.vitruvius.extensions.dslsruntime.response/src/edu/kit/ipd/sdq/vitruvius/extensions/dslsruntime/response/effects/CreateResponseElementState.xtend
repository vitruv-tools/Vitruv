package edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.effects

import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel

class CreateResponseElementState extends AbstractResponseElementState {
	
	new(EObject element, CorrespondenceModel correspondenceModel) {
		super(element, correspondenceModel)
	}
	
	override updateTUID() {
		// Do nothing
	}
	
}
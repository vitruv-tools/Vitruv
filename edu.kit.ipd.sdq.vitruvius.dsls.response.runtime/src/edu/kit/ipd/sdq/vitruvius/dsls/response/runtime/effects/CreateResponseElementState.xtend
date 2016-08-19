package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects

import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel

class CreateResponseElementState extends AbstractResponseElementState {
	
	new(EObject element, CorrespondenceModel correspondenceInstance) {
		super(element, correspondenceInstance)
	}
	
	override updateTUID() {
		// Do nothing
	}
	
}
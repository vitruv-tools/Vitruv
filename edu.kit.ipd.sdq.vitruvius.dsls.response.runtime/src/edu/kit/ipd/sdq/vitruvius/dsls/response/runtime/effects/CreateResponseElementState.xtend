package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects

import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence

class CreateResponseElementState extends AbstractResponseElementState {
	
	new(EObject element, CorrespondenceInstance<Correspondence> correspondenceInstance) {
		super(element, correspondenceInstance)
	}
	
	override updateTUID() {
		// Do nothing
	}
	
}
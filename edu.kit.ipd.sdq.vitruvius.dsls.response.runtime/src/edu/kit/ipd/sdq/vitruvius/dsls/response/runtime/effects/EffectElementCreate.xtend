package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects.EffectElement
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence

class EffectElementCreate extends EffectElement {
	
	new(EObject element, CorrespondenceInstance<Correspondence> correspondenceInstance) {
		super(element, correspondenceInstance)
	}
	
	override updateTUID() {
		// Do nothing
	}
	
}
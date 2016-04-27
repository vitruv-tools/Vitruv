package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects

import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.helper.CorrespondenceHelper
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState

class EffectCorrespondenceCreation extends EffectCorrespondenceModification {
	private final String tag;
	
	new(EObject firstElement, EObject secondElement, ResponseExecutionState executionState, String tag) {
		super(firstElement, secondElement, executionState);
		this.tag = tag;
	}

	private def void initializeCorrespondence() {
		if (firstElement == null || secondElement == null) {
			return;
		}
		CorrespondenceHelper.addCorrespondence(blackboard.getCorrespondenceInstance(), 
				firstElement, secondElement, tag);
	}
	
	override public preProcess() {
		// Do nothing
	}
	
	override public postProcess() {
		initializeCorrespondence();
	}
	
}
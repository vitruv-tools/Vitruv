package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects.EffectElement
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState

abstract class EffectCorrespondenceModification extends EffectElement {
	protected final EObject firstElement;
	protected final EObject secondElement;
	
	new(EObject firstElement, EObject secondElement, ResponseExecutionState executionState) {
		super(executionState);
		this.firstElement = firstElement;
		this.secondElement = secondElement;
	}
}
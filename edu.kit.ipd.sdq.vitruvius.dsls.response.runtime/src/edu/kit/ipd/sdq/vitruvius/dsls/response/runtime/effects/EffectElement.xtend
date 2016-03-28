package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects

import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.Loggable

abstract class EffectElement extends Loggable {
	protected final EObject element;
	protected final EObject correspondenceSource;
	protected final extension ResponseExecutionState _executionState;
	
	public new(EObject element, EObject correspondenceSource, ResponseExecutionState executionState) {
		this.element = element;
		this.correspondenceSource = correspondenceSource;
		this._executionState = executionState;
	}
	
	public def void preProcess();
	public def void postProcess();
	public def void updateTUIDs();
}
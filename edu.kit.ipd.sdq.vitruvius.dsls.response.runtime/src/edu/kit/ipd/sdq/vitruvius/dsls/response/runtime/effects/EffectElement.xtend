package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.Loggable

abstract class EffectElement extends Loggable {
	protected final extension ResponseExecutionState _executionState;
	
	public new(ResponseExecutionState executionState) {
		this._executionState = executionState;
	}
	
	public def void preProcess();
	public def void postProcess();
}
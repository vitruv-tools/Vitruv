package edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment

import edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseExecutionState

class AbstractEffectsFacade extends Loggable {
	protected val ResponseExecutionState executionState;
	
	public new(ResponseExecutionState executionState) {
		this.executionState = executionState;
	}
}
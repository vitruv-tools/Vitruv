package edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.effects

import edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseExecutionState
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.Loggable

class AbstractEffectsFacade extends Loggable {
	protected val ResponseExecutionState executionState;
	
	public new(ResponseExecutionState executionState) {
		this.executionState = executionState;
	}
}
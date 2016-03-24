package edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.effects

import edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseExecutionState
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.Loggable
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment.CallHierarchyHaving

class AbstractEffectsFacade extends Loggable {
	protected val ResponseExecutionState executionState;
	protected val CallHierarchyHaving calledBy;
	
	public new(ResponseExecutionState executionState, CallHierarchyHaving calledBy) {
		this.executionState = executionState;
		this.calledBy = calledBy;
	}
}
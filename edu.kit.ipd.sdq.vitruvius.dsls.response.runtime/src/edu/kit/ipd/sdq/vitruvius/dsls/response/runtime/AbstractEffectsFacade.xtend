package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.Loggable

class AbstractEffectsFacade extends Loggable {
	protected val ResponseExecutionState executionState;
	protected val CallHierarchyHaving calledBy;
	
	public new(ResponseExecutionState executionState, CallHierarchyHaving calledBy) {
		this.executionState = executionState;
		this.calledBy = calledBy;
	}
}
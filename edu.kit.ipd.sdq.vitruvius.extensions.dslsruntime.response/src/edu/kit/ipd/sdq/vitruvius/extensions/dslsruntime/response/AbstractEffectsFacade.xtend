package edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response

import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.structure.Loggable

class AbstractEffectsFacade extends Loggable {
	protected val ResponseExecutionState executionState;
	protected val CallHierarchyHaving calledBy;
	
	public new(ResponseExecutionState executionState, CallHierarchyHaving calledBy) {
		this.executionState = executionState;
		this.calledBy = calledBy;
	}
}
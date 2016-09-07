package tools.vitruv.extensions.dslsruntime.response

import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving
import tools.vitruv.extensions.dslsruntime.response.structure.Loggable

class AbstractEffectsFacade extends Loggable {
	protected val ResponseExecutionState executionState;
	protected val CallHierarchyHaving calledBy;
	
	public new(ResponseExecutionState executionState, CallHierarchyHaving calledBy) {
		this.executionState = executionState;
		this.calledBy = calledBy;
	}
}
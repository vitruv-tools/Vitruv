package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving
import tools.vitruv.extensions.dslsruntime.reactions.structure.Loggable

class AbstractRepairRoutinesFacade extends Loggable {
	protected val ReactionExecutionState executionState;
	protected val CallHierarchyHaving calledBy;
	
	public new(ReactionExecutionState executionState, CallHierarchyHaving calledBy) {
		this.executionState = executionState;
		this.calledBy = calledBy;
	}
}
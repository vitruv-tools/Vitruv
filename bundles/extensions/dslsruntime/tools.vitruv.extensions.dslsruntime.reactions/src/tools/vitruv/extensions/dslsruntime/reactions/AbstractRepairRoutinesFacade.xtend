package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving
import tools.vitruv.extensions.dslsruntime.reactions.structure.Loggable

class AbstractRepairRoutinesFacade extends Loggable {
	protected val AbstractReactionsExecutor executor;
	protected val ReactionExecutionState executionState;
	protected val CallHierarchyHaving calledBy;
	
	public new(AbstractReactionsExecutor executor, ReactionExecutionState executionState, CallHierarchyHaving calledBy) {
		this.executor = executor;
		this.executionState = executionState;
		this.calledBy = calledBy;
	}
}
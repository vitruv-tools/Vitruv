package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving

/**
 * Stores the common execution state shared among all routines facades of an import hierarchy.
 * <p>
 * The stored reaction execution state and caller get passed to called routines by the routines facades using this execution state.
 */
class RoutinesFacadeExecutionState {

	private var ReactionExecutionState reactionExecutionState = null;
	private var CallHierarchyHaving caller = null;

	public new() {
	}

	private new(ReactionExecutionState reactionExecutionState, CallHierarchyHaving caller) {
		this.reactionExecutionState = reactionExecutionState;
		this.caller = caller;
	}

	public def ReactionExecutionState getReactionExecutionState() {
		return reactionExecutionState;
	}

	public def CallHierarchyHaving getCaller() {
		return caller;
	}

	public def void setExecutionState(ReactionExecutionState reactionExecutionState, CallHierarchyHaving caller) {
		this.reactionExecutionState = reactionExecutionState;
		this.caller = caller;
	}

	public def void reset() {
		this.setExecutionState(null, null);
	}

	// creates a snapshot of the current execution state:
	public def RoutinesFacadeExecutionState capture() {
		return new RoutinesFacadeExecutionState(reactionExecutionState, caller);
	}

	// restores a previously captured execution state:
	public def void restore(RoutinesFacadeExecutionState executionState) {
		this.setExecutionState(executionState.reactionExecutionState, executionState.caller);
	}
}

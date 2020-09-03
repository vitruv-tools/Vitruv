package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving

/**
 * Stores the common execution state shared among all routines facades of an import hierarchy.
 * <p>
 * The stored reaction execution state and caller get passed to called routines by the routines facades using this execution state.
 */
class RoutinesFacadeExecutionState {

	var ReactionExecutionState reactionExecutionState = null;
	var CallHierarchyHaving caller = null;

	new() {
	}

	private new(ReactionExecutionState reactionExecutionState, CallHierarchyHaving caller) {
		this.reactionExecutionState = reactionExecutionState;
		this.caller = caller;
	}

	def ReactionExecutionState getReactionExecutionState() {
		return reactionExecutionState;
	}

	def CallHierarchyHaving getCaller() {
		return caller;
	}

	def void setExecutionState(ReactionExecutionState reactionExecutionState, CallHierarchyHaving caller) {
		this.reactionExecutionState = reactionExecutionState;
		this.caller = caller;
	}

	def void reset() {
		this.setExecutionState(null, null);
	}

	// creates a snapshot of the current execution state:
	def RoutinesFacadeExecutionState capture() {
		return new RoutinesFacadeExecutionState(reactionExecutionState, caller);
	}

	// restores a previously captured execution state:
	def void restore(RoutinesFacadeExecutionState executionState) {
		this.setExecutionState(executionState.reactionExecutionState, executionState.caller);
	}
}

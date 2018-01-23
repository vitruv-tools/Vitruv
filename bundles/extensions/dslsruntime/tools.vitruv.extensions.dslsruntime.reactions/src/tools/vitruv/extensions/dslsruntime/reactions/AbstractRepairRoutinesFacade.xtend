package tools.vitruv.extensions.dslsruntime.reactions

import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving
import tools.vitruv.extensions.dslsruntime.reactions.structure.Loggable
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath

class AbstractRepairRoutinesFacade extends Loggable {
	protected val RoutinesFacadesProvider routinesFacadesProvider;
	protected val ReactionsImportPath reactionsImportPath; // absolute path inside the import hierarchy

	// execution state:
	protected var ReactionExecutionState reactionExecutionState;
	protected var CallHierarchyHaving caller;

	public new(RoutinesFacadesProvider routinesFacadesProvider, ReactionsImportPath reactionsImportPath) {
		this.routinesFacadesProvider = routinesFacadesProvider;
		this.reactionsImportPath = reactionsImportPath;
	}

	// sets the reaction execution state and caller to pass to called routines:
	protected def void _setExecutionState(ReactionExecutionState reactionExecutionState, CallHierarchyHaving caller) {
		this.reactionExecutionState = reactionExecutionState;
		this.caller = caller;
	}

	// captures the current execution state:
	protected def ExecutionState _captureExecutionState() {
		return new ExecutionState(reactionExecutionState, caller);
	}

	// restores a previously captured execution state:
	protected def void _restoreExecutionState(ExecutionState executionState) {
		_setExecutionState(executionState.reactionExecutionState, executionState.caller);
	}

	@Data
	protected static class ExecutionState {
		val ReactionExecutionState reactionExecutionState;
		val CallHierarchyHaving caller;
	}
}

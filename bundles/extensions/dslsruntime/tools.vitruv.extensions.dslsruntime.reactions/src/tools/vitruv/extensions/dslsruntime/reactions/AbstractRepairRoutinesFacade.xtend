package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.extensions.dslsruntime.reactions.structure.Loggable
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath

/**
 * Note: All methods start with an underscore here to not conflict with the methods that are generated from the routines by
 * concrete implementations.
 */
class AbstractRepairRoutinesFacade extends Loggable {
	// used by concrete implementations to request routines facades of executed routines: 
	private val RoutinesFacadesProvider routinesFacadesProvider;
	// absolute path inside the import hierarchy:
	private val ReactionsImportPath reactionsImportPath;
	// shared execution state among all routines facades in the import hierarchy:
	private val RoutinesFacadeExecutionState executionState;

	public new(RoutinesFacadesProvider routinesFacadesProvider, ReactionsImportPath reactionsImportPath, RoutinesFacadeExecutionState executionState) {
		this.routinesFacadesProvider = routinesFacadesProvider;
		this.reactionsImportPath = reactionsImportPath;
		this.executionState = executionState;
	}

	protected def RoutinesFacadesProvider _getRoutinesFacadesProvider() {
		return routinesFacadesProvider;
	}

	protected def ReactionsImportPath _getReactionsImportPath() {
		return reactionsImportPath;
	}

	protected def RoutinesFacadeExecutionState _getExecutionState() {
		return executionState;
	}
}

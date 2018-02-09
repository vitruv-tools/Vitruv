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
	// absolute path inside the import hierarchy to the parent segment (the segment importing the segment this routines facade belongs to),
	// or null if there is no parent:
	private val ReactionsImportPath parentImportPath;
	// shared execution state among all routines facades in the import hierarchy:
	private val RoutinesFacadeExecutionState executionState;

	public new(RoutinesFacadesProvider routinesFacadesProvider, ReactionsImportPath parentImportPath, RoutinesFacadeExecutionState executionState) {
		this.routinesFacadesProvider = routinesFacadesProvider;
		this.parentImportPath = parentImportPath;
		this.executionState = executionState;
	}

	protected def RoutinesFacadesProvider _getRoutinesFacadesProvider() {
		return routinesFacadesProvider;
	}

	protected def ReactionsImportPath _getParentImportPath() {
		return parentImportPath;
	}

	protected def RoutinesFacadeExecutionState _getExecutionState() {
		return executionState;
	}
}

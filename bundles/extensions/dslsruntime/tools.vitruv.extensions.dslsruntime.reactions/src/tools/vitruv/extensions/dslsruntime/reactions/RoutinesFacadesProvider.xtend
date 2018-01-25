package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath

/**
 * Provides the routines facades for all reactions segments of one specific import hierarchy.
 */
interface RoutinesFacadesProvider {

	// generic return type for convenience; the requested type has to match the actual type of the provided facade
	// the import path is absolute: starts with the root of the import hierarchy
	public def <T extends AbstractRepairRoutinesFacade> T getRoutinesFacade(ReactionsImportPath reactionsImportPath);
}

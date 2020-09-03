package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath

/**
 * Provides the routines facades for all reactions segments of one specific import hierarchy.
 */
interface RoutinesFacadesProvider {

	/**
	 * Gets the routines facade for the specified absolute reactions import path.
	 * 
	 * @param <T>
	 *            the type of the requested routines facade
	 * @param reactionsImportPath
	 *            the absolute import path (starting with the root of the import hierarchy)
	 * @return the routines facade
	 * @throws IllegalArgumentException if the specified import path is not valid (for ex. does not exist in the import hierarchy)
	 * @throws ClassCastException if the specified routines facade type is not applicable to the actually returned routines facade
	 */
	def <T extends AbstractRepairRoutinesFacade> T getRoutinesFacade(ReactionsImportPath reactionsImportPath);
}

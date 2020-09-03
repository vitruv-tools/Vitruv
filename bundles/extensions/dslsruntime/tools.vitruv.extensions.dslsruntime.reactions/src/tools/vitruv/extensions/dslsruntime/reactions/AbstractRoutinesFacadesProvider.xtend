package tools.vitruv.extensions.dslsruntime.reactions

import java.util.HashMap
import java.util.Map
import tools.vitruv.extensions.dslsruntime.reactions.structure.ReactionsImportPath

import static com.google.common.base.Preconditions.*

/**
 * A RoutinesFacadesProvider which caches created routines facades.
 * <p>
 * Implementations are required to override {@link #createRoutinesFacade(ReactionsImportPath)} to create the routines facades of
 * the handled import hierarchy there.
 */
abstract class AbstractRoutinesFacadesProvider implements RoutinesFacadesProvider {

	val RoutinesFacadeExecutionState sharedRoutinesFacadeExecutionState = new RoutinesFacadeExecutionState();
	// the routines facades that were created so far:
	val Map<ReactionsImportPath, AbstractRepairRoutinesFacade> routinesFacades = new HashMap<ReactionsImportPath, AbstractRepairRoutinesFacade>();

	new() {
	}

	// creates the specified routines facade:
	protected def abstract AbstractRepairRoutinesFacade createRoutinesFacade(ReactionsImportPath reactionsImportPath, RoutinesFacadeExecutionState sharedExecutionState);

	override <T extends AbstractRepairRoutinesFacade> T getRoutinesFacade(ReactionsImportPath reactionsImportPath) {
		checkNotNull(reactionsImportPath, "reactionsImportPath is null");
		// check if we already created the requested routines facade:
		var T routinesFacade = routinesFacades.get(reactionsImportPath) as T;
		if (routinesFacade !== null) return routinesFacade;
		// create the routines facade:
		routinesFacade = this.createRoutinesFacade(reactionsImportPath, sharedRoutinesFacadeExecutionState) as T;
		if (routinesFacade !== null) {
			// store created routines facade:
			routinesFacades.put(reactionsImportPath, routinesFacade);
			return routinesFacade;
		}
		return routinesFacade;
	}
}

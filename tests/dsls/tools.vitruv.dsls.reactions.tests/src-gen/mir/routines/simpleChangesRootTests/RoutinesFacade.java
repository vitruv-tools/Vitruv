package mir.routines.simpleChangesRootTests;

import allElementTypes.Root;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public void createRoot(final Root rootElement) {
    mir.routines.simpleChangesRootTests.CreateRootRoutine effect = new mir.routines.simpleChangesRootTests.CreateRootRoutine(this.executionState, calledBy,
    	rootElement);
    effect.applyRoutine();
  }
  
  public void deleteRoot(final Root rootElement) {
    mir.routines.simpleChangesRootTests.DeleteRootRoutine effect = new mir.routines.simpleChangesRootTests.DeleteRootRoutine(this.executionState, calledBy,
    	rootElement);
    effect.applyRoutine();
  }
}

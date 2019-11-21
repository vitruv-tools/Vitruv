package mir.routines.adXre_R2L;

import java.io.IOException;
import mir.routines.adXre_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.mappings.addressesXrecipients.tests.mappings.aXr_all_or_nothing.AdRootXReRootMapping;
import tools.vitruv.dsls.mappings.addressesXrecipients.tests.mappings.aXr_all_or_nothing.halves.RightAdRootXReRootInstanceHalf;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

/**
 * ********* BEGIN AdRootXReRoot DELETE ROUTINES *********
 */
@SuppressWarnings("all")
public class DeleteAdRootXReRootMappingInstancesRoutine extends AbstractRepairRoutineRealization {
  private DeleteAdRootXReRootMappingInstancesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(@Extension final RoutinesFacade _routinesFacade) {
      final Iterable<RightAdRootXReRootInstanceHalf> instances = AdRootXReRootMapping.adRootXReRootMapping().getRightInstanceHalvesClone();
      for (final RightAdRootXReRootInstanceHalf instance : instances) {
        _routinesFacade.deleteAdRootXReRootMappingInstance(instance.getRRoot());
      }
    }
  }
  
  public DeleteAdRootXReRootMappingInstancesRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_R2L.DeleteAdRootXReRootMappingInstancesRoutine.ActionUserExecution(getExecutionState(), this);
  }
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteAdRootXReRootMappingInstancesRoutine with input:");
    
    userExecution.callRoutine1(this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}

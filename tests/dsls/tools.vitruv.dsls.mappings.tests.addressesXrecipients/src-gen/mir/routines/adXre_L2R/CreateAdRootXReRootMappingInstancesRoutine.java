package mir.routines.adXre_L2R;

import java.io.IOException;
import mir.routines.adXre_L2R.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.AdRootXReRootMapping;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.halves.LeftAdRootXReRootInstanceHalf;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

/**
 * ********* BEGIN AdRootXReRoot CREATE ROUTINES *********
 */
@SuppressWarnings("all")
public class CreateAdRootXReRootMappingInstancesRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateAdRootXReRootMappingInstancesRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(@Extension final RoutinesFacade _routinesFacade) {
      final Iterable<LeftAdRootXReRootInstanceHalf> candidates = AdRootXReRootMapping.adRootXReRootMapping().getLeftCandidatesClone();
      for (final LeftAdRootXReRootInstanceHalf candidate : candidates) {
        _routinesFacade.createAdRootXReRootMappingInstance(candidate.getARoot());
      }
    }
  }
  
  public CreateAdRootXReRootMappingInstancesRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_L2R.CreateAdRootXReRootMappingInstancesRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_L2R.RoutinesFacade(getExecutionState(), this);
  }
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateAdRootXReRootMappingInstancesRoutine with input:");
    
    userExecution.callRoutine1(actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}

package mir.routines.adXre_L2R;

import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import mir.routines.adXre_L2R.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class EnforceAdRootXReRootMappingConditionsFromLeftToRightRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private EnforceAdRootXReRootMappingConditionsFromLeftToRightRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Addresses aRoot, final Recipients rRoot, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.enforceRightAdRootXReRootMappingConditions(rRoot);
    }
  }
  
  public EnforceAdRootXReRootMappingConditionsFromLeftToRightRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Addresses aRoot, final Recipients rRoot) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_L2R.EnforceAdRootXReRootMappingConditionsFromLeftToRightRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_L2R.RoutinesFacade(getExecutionState(), this);
    this.aRoot = aRoot;this.rRoot = rRoot;
  }
  
  private Addresses aRoot;
  
  private Recipients rRoot;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine EnforceAdRootXReRootMappingConditionsFromLeftToRightRoutine with input:");
    getLogger().debug("   aRoot: " + this.aRoot);
    getLogger().debug("   rRoot: " + this.rRoot);
    
    userExecution.callRoutine1(aRoot, rRoot, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}

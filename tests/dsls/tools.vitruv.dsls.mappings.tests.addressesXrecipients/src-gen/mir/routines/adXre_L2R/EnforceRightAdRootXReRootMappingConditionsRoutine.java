package mir.routines.adXre_L2R;

import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import mir.routines.adXre_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class EnforceRightAdRootXReRootMappingConditionsRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private EnforceRightAdRootXReRootMappingConditionsRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Recipients rRoot) {
      return rRoot;
    }
    
    public void update0Element(final Recipients rRoot) {
    }
  }
  
  public EnforceRightAdRootXReRootMappingConditionsRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Recipients rRoot) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_L2R.EnforceRightAdRootXReRootMappingConditionsRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_L2R.RoutinesFacade(getExecutionState(), this);
    this.rRoot = rRoot;
  }
  
  private Recipients rRoot;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine EnforceRightAdRootXReRootMappingConditionsRoutine with input:");
    getLogger().debug("   rRoot: " + this.rRoot);
    
    // val updatedElement userExecution.getElement1(rRoot);
    userExecution.update0Element(rRoot);
    
    postprocessElements();
    
    return true;
  }
}

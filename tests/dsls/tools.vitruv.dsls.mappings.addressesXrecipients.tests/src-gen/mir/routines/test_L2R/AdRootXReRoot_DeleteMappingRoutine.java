package mir.routines.test_L2R;

import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AdRootXReRoot_DeleteMappingRoutine extends AbstractRepairRoutineRealization {
  private AdRootXReRoot_DeleteMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Recipients rRoot_) {
      return rRoot_;
    }
  }
  
  public AdRootXReRoot_DeleteMappingRoutine(final mir.routines.test_L2R.RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Recipients rRoot_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.test_L2R.AdRootXReRoot_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.rRoot_ = rRoot_;
  }
  
  private Recipients rRoot_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AdRootXReRoot_DeleteMappingRoutine with input:");
    getLogger().debug("   rRoot_: " + this.rRoot_);
    
    deleteObject(userExecution.getElement1(rRoot_));
    
    postprocessElements();
    
    return true;
  }
}

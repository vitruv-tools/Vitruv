package mir.routines.test_R2L;

import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AdRootXReRoot_BidirectionalUpdateRoutine extends AbstractRepairRoutineRealization {
  private AdRootXReRoot_BidirectionalUpdateRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void executeAction1(final Recipients rRoot_, @Extension final mir.routines.test_R2L.RoutinesFacade _routinesFacade) {
      return;
    }
  }
  
  public AdRootXReRoot_BidirectionalUpdateRoutine(final mir.routines.test_R2L.RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Recipients rRoot_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.test_R2L.AdRootXReRoot_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.rRoot_ = rRoot_;
  }
  
  private Recipients rRoot_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AdRootXReRoot_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   rRoot_: " + this.rRoot_);
    
    userExecution.executeAction1(rRoot_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}

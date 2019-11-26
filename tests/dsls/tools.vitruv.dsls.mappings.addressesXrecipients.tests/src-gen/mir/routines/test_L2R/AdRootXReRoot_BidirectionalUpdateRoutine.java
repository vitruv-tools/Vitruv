package mir.routines.test_L2R;

import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
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
    
    public void executeAction1(final Addresses aRoot_, @Extension final mir.routines.test_L2R.RoutinesFacade _routinesFacade) {
      return;
    }
  }
  
  public AdRootXReRoot_BidirectionalUpdateRoutine(final mir.routines.test_L2R.RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Addresses aRoot_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.test_L2R.AdRootXReRoot_BidirectionalUpdateRoutine.ActionUserExecution(getExecutionState(), this);
    this.aRoot_ = aRoot_;
  }
  
  private Addresses aRoot_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AdRootXReRoot_BidirectionalUpdateRoutine with input:");
    getLogger().debug("   aRoot_: " + this.aRoot_);
    
    userExecution.executeAction1(aRoot_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}

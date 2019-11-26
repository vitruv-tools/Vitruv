package mir.routines.test_R2L;

import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
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
    
    public EObject getElement1(final Addresses aRoot_) {
      return aRoot_;
    }
  }
  
  public AdRootXReRoot_DeleteMappingRoutine(final mir.routines.test_R2L.RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Addresses aRoot_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.test_R2L.AdRootXReRoot_DeleteMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.aRoot_ = aRoot_;
  }
  
  private Addresses aRoot_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AdRootXReRoot_DeleteMappingRoutine with input:");
    getLogger().debug("   aRoot_: " + this.aRoot_);
    
    deleteObject(userExecution.getElement1(aRoot_));
    
    postprocessElements();
    
    return true;
  }
}

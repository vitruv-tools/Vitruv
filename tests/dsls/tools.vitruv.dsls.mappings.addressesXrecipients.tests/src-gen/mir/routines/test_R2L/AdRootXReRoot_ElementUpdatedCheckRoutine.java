package mir.routines.test_R2L;

import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AdRootXReRoot_ElementUpdatedCheckRoutine extends AbstractRepairRoutineRealization {
  private AdRootXReRoot_ElementUpdatedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, @Extension final mir.routines.test_R2L.RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof Recipients)) {
        Recipients rRoot_ = ((Recipients)affectedEObject);
        _routinesFacade.adRootXReRoot_CreateMapping(rRoot_);
      }
      _routinesFacade.adRootXReRoot_ElementDeletedCheck(affectedEObject);
    }
  }
  
  public AdRootXReRoot_ElementUpdatedCheckRoutine(final mir.routines.test_R2L.RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.test_R2L.AdRootXReRoot_ElementUpdatedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AdRootXReRoot_ElementUpdatedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    userExecution.callRoutine1(affectedEObject, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}

package mir.routines.test_L2R;

import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import java.io.IOException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AdRootXReRoot_ElementCreatedCheckRoutine extends AbstractRepairRoutineRealization {
  private AdRootXReRoot_ElementCreatedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EObject affectedEObject, @Extension final mir.routines.test_L2R.RoutinesFacade _routinesFacade) {
      if ((affectedEObject instanceof Addresses)) {
        Addresses aRoot_ = ((Addresses)affectedEObject);
        _routinesFacade.adRootXReRoot_CreateMapping(aRoot_);
      }
    }
  }
  
  public AdRootXReRoot_ElementCreatedCheckRoutine(final mir.routines.test_L2R.RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.test_L2R.AdRootXReRoot_ElementCreatedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AdRootXReRoot_ElementCreatedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    userExecution.callRoutine1(affectedEObject, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}

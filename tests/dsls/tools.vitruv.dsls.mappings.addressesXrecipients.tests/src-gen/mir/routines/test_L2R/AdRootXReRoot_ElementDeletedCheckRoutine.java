package mir.routines.test_L2R;

import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import java.util.Optional;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AdRootXReRoot_ElementDeletedCheckRoutine extends AbstractRepairRoutineRealization {
  private AdRootXReRoot_ElementDeletedCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceRRoot_correspondingTo_aRoot(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "test_map_Addresses_and_Recipients_correspondence_Addresses:aRoot_with_Recipients:rRoot";
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<Recipients> rRoot_correspondingTo_aRoot, @Extension final mir.routines.test_L2R.RoutinesFacade _routinesFacade) {
      Recipients rRoot_ = null;
      boolean _isPresent = rRoot_correspondingTo_aRoot.isPresent();
      if (_isPresent) {
        rRoot_ = rRoot_correspondingTo_aRoot.get();
      }
      if ((rRoot_ != null)) {
        _routinesFacade.adRootXReRoot_DeleteMapping(rRoot_);
      }
    }
  }
  
  public AdRootXReRoot_ElementDeletedCheckRoutine(final mir.routines.test_L2R.RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.test_L2R.AdRootXReRoot_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AdRootXReRoot_ElementDeletedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    	Optional<edu.kit.ipd.sdq.metamodels.recipients.Recipients> rRoot_correspondingTo_aRoot = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceRRoot_correspondingTo_aRoot(affectedEObject), // correspondence source supplier
    		edu.kit.ipd.sdq.metamodels.recipients.Recipients.class,
    		(edu.kit.ipd.sdq.metamodels.recipients.Recipients _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(affectedEObject), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(rRoot_correspondingTo_aRoot.isPresent() ? rRoot_correspondingTo_aRoot.get() : null);
    userExecution.callRoutine1(affectedEObject, rRoot_correspondingTo_aRoot, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}

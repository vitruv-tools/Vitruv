package mir.routines.test_R2L;

import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
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
    
    public String getRetrieveTag1(final EObject affectedEObject) {
      return "test_map_Addresses_and_Recipients_correspondence_Addresses:aRoot_with_Recipients:rRoot";
    }
    
    public EObject getCorrepondenceSourceARoot_correspondingTo_rRoot(final EObject affectedEObject) {
      return affectedEObject;
    }
    
    public void callRoutine1(final EObject affectedEObject, final Optional<Addresses> aRoot_correspondingTo_rRoot, @Extension final mir.routines.test_R2L.RoutinesFacade _routinesFacade) {
      Addresses aRoot_ = null;
      boolean _isPresent = aRoot_correspondingTo_rRoot.isPresent();
      if (_isPresent) {
        aRoot_ = aRoot_correspondingTo_rRoot.get();
      }
      if ((aRoot_ != null)) {
        _routinesFacade.adRootXReRoot_DeleteMapping(aRoot_);
      }
    }
  }
  
  public AdRootXReRoot_ElementDeletedCheckRoutine(final mir.routines.test_R2L.RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final EObject affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.test_R2L.AdRootXReRoot_ElementDeletedCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private EObject affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AdRootXReRoot_ElementDeletedCheckRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    	Optional<edu.kit.ipd.sdq.metamodels.addresses.Addresses> aRoot_correspondingTo_rRoot = Optional.ofNullable(getCorrespondingElement(
    		userExecution.getCorrepondenceSourceARoot_correspondingTo_rRoot(affectedEObject), // correspondence source supplier
    		edu.kit.ipd.sdq.metamodels.addresses.Addresses.class,
    		(edu.kit.ipd.sdq.metamodels.addresses.Addresses _element) -> true, // correspondence precondition checker
    		userExecution.getRetrieveTag1(affectedEObject), 
    		false // asserted
    		)
    );
    registerObjectUnderModification(aRoot_correspondingTo_rRoot.isPresent() ? aRoot_correspondingTo_rRoot.get() : null);
    userExecution.callRoutine1(affectedEObject, aRoot_correspondingTo_rRoot, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}

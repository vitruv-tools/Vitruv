package mir.routines.test_R2LSegment;

import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import mir.routines.test_R2LSegment.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OnAdRootXReRootRecipientsDeletedRepairRoutine extends AbstractRepairRoutineRealization {
  private OnAdRootXReRootRecipientsDeletedRepairRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Recipients affectedEObject, final Addresses removeAddresses_aRoot) {
      return removeAddresses_aRoot;
    }
    
    public EObject getCorrepondenceSourceRemoveAddresses_aRoot(final Recipients affectedEObject) {
      return affectedEObject;
    }
    
    public String getRetrieveTag1(final Recipients affectedEObject) {
      return "test_map_Addresses_and_Recipients_correspondence_Addresses:aRoot_with_Recipients:rRoot";
    }
    
    public EObject getElement2(final Recipients affectedEObject, final Addresses removeAddresses_aRoot) {
      return affectedEObject;
    }
    
    public EObject getElement3(final Recipients affectedEObject, final Addresses removeAddresses_aRoot) {
      return removeAddresses_aRoot;
    }
    
    public String getTag1(final Recipients affectedEObject, final Addresses removeAddresses_aRoot) {
      return "test_map_Addresses_and_Recipients_correspondence_Addresses:aRoot_with_Recipients:rRoot";
    }
  }
  
  public OnAdRootXReRootRecipientsDeletedRepairRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Recipients affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.test_R2LSegment.OnAdRootXReRootRecipientsDeletedRepairRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private Recipients affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OnAdRootXReRootRecipientsDeletedRepairRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    edu.kit.ipd.sdq.metamodels.addresses.Addresses removeAddresses_aRoot = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRemoveAddresses_aRoot(affectedEObject), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.addresses.Addresses.class,
    	(edu.kit.ipd.sdq.metamodels.addresses.Addresses _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(affectedEObject), 
    	false // asserted
    	);
    if (removeAddresses_aRoot == null) {
    	return false;
    }
    registerObjectUnderModification(removeAddresses_aRoot);
    removeCorrespondenceBetween(userExecution.getElement1(affectedEObject, removeAddresses_aRoot), userExecution.getElement2(affectedEObject, removeAddresses_aRoot), userExecution.getTag1(affectedEObject, removeAddresses_aRoot));
    
    deleteObject(userExecution.getElement3(affectedEObject, removeAddresses_aRoot));
    
    postprocessElements();
    
    return true;
  }
}

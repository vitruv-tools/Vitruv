package mir.routines.test_L2RSegment;

import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import mir.routines.test_L2RSegment.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class OnAdRootXReRootAddressesDeletedRepairRoutine extends AbstractRepairRoutineRealization {
  private OnAdRootXReRootAddressesDeletedRepairRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Addresses affectedEObject, final Recipients removeRecipients_rRoot) {
      return removeRecipients_rRoot;
    }
    
    public EObject getCorrepondenceSourceRemoveRecipients_rRoot(final Addresses affectedEObject) {
      return affectedEObject;
    }
    
    public String getRetrieveTag1(final Addresses affectedEObject) {
      return "test_map_Addresses_and_Recipients_correspondence_Addresses:aRoot_with_Recipients:rRoot";
    }
    
    public EObject getElement2(final Addresses affectedEObject, final Recipients removeRecipients_rRoot) {
      return affectedEObject;
    }
    
    public EObject getElement3(final Addresses affectedEObject, final Recipients removeRecipients_rRoot) {
      return removeRecipients_rRoot;
    }
    
    public String getTag1(final Addresses affectedEObject, final Recipients removeRecipients_rRoot) {
      return "test_map_Addresses_and_Recipients_correspondence_Addresses:aRoot_with_Recipients:rRoot";
    }
  }
  
  public OnAdRootXReRootAddressesDeletedRepairRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Addresses affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.test_L2RSegment.OnAdRootXReRootAddressesDeletedRepairRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private Addresses affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OnAdRootXReRootAddressesDeletedRepairRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    edu.kit.ipd.sdq.metamodels.recipients.Recipients removeRecipients_rRoot = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRemoveRecipients_rRoot(affectedEObject), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.recipients.Recipients.class,
    	(edu.kit.ipd.sdq.metamodels.recipients.Recipients _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(affectedEObject), 
    	false // asserted
    	);
    if (removeRecipients_rRoot == null) {
    	return false;
    }
    registerObjectUnderModification(removeRecipients_rRoot);
    removeCorrespondenceBetween(userExecution.getElement1(affectedEObject, removeRecipients_rRoot), userExecution.getElement2(affectedEObject, removeRecipients_rRoot), userExecution.getTag1(affectedEObject, removeRecipients_rRoot));
    
    deleteObject(userExecution.getElement3(affectedEObject, removeRecipients_rRoot));
    
    postprocessElements();
    
    return true;
  }
}

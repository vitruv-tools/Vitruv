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
public class OnAdRootXReRootAddressesInsertedAsRootRepairRoutine extends AbstractRepairRoutineRealization {
  private OnAdRootXReRootAddressesInsertedAsRootRepairRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Addresses affectedEObject, final Recipients newRecipients_rRoot) {
      return newRecipients_rRoot;
    }
    
    public EObject getCorrepondenceSource1(final Addresses affectedEObject) {
      return affectedEObject;
    }
    
    public String getRetrieveTag1(final Addresses affectedEObject) {
      return "test_map_Addresses_and_Recipients_correspondence_Addresses:aRoot_with_Recipients:rRoot";
    }
    
    public EObject getElement2(final Addresses affectedEObject, final Recipients newRecipients_rRoot) {
      return affectedEObject;
    }
    
    public String getTag1(final Addresses affectedEObject, final Recipients newRecipients_rRoot) {
      return "test_map_Addresses_and_Recipients_correspondence_Addresses:aRoot_with_Recipients:rRoot";
    }
  }
  
  public OnAdRootXReRootAddressesInsertedAsRootRepairRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Addresses affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.test_L2RSegment.OnAdRootXReRootAddressesInsertedAsRootRepairRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private Addresses affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OnAdRootXReRootAddressesInsertedAsRootRepairRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(affectedEObject), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.recipients.Recipients.class,
    	(edu.kit.ipd.sdq.metamodels.recipients.Recipients _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(affectedEObject)
    ).isEmpty()) {
    	return false;
    }
    edu.kit.ipd.sdq.metamodels.recipients.Recipients newRecipients_rRoot = edu.kit.ipd.sdq.metamodels.recipients.impl.RecipientsFactoryImpl.eINSTANCE.createRecipients();
    notifyObjectCreated(newRecipients_rRoot);
    
    addCorrespondenceBetween(userExecution.getElement1(affectedEObject, newRecipients_rRoot), userExecution.getElement2(affectedEObject, newRecipients_rRoot), userExecution.getTag1(affectedEObject, newRecipients_rRoot));
    
    postprocessElements();
    
    return true;
  }
}

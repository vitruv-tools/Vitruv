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
public class OnAdRootXReRootRecipientsInsertedAsRootRepairRoutine extends AbstractRepairRoutineRealization {
  private OnAdRootXReRootRecipientsInsertedAsRootRepairRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Recipients affectedEObject, final Addresses newAddresses_aRoot) {
      return newAddresses_aRoot;
    }
    
    public EObject getCorrepondenceSource1(final Recipients affectedEObject) {
      return affectedEObject;
    }
    
    public String getRetrieveTag1(final Recipients affectedEObject) {
      return "test_map_Addresses_and_Recipients_correspondence_Addresses:aRoot_with_Recipients:rRoot";
    }
    
    public EObject getElement2(final Recipients affectedEObject, final Addresses newAddresses_aRoot) {
      return affectedEObject;
    }
    
    public String getTag1(final Recipients affectedEObject, final Addresses newAddresses_aRoot) {
      return "test_map_Addresses_and_Recipients_correspondence_Addresses:aRoot_with_Recipients:rRoot";
    }
  }
  
  public OnAdRootXReRootRecipientsInsertedAsRootRepairRoutine(final RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Recipients affectedEObject) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.test_R2LSegment.OnAdRootXReRootRecipientsInsertedAsRootRepairRoutine.ActionUserExecution(getExecutionState(), this);
    this.affectedEObject = affectedEObject;
  }
  
  private Recipients affectedEObject;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine OnAdRootXReRootRecipientsInsertedAsRootRepairRoutine with input:");
    getLogger().debug("   affectedEObject: " + this.affectedEObject);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(affectedEObject), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.addresses.Addresses.class,
    	(edu.kit.ipd.sdq.metamodels.addresses.Addresses _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(affectedEObject)
    ).isEmpty()) {
    	return false;
    }
    edu.kit.ipd.sdq.metamodels.addresses.Addresses newAddresses_aRoot = edu.kit.ipd.sdq.metamodels.addresses.impl.AddressesFactoryImpl.eINSTANCE.createAddresses();
    notifyObjectCreated(newAddresses_aRoot);
    
    addCorrespondenceBetween(userExecution.getElement1(affectedEObject, newAddresses_aRoot), userExecution.getElement2(affectedEObject, newAddresses_aRoot), userExecution.getTag1(affectedEObject, newAddresses_aRoot));
    
    postprocessElements();
    
    return true;
  }
}

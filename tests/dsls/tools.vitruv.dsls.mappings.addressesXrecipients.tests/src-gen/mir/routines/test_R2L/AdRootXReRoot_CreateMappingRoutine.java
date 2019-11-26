package mir.routines.test_R2L;

import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AdRootXReRoot_CreateMappingRoutine extends AbstractRepairRoutineRealization {
  private AdRootXReRoot_CreateMappingRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Recipients rRoot_, final Addresses aRoot_) {
      return aRoot_;
    }
    
    public EObject getCorrepondenceSource1(final Recipients rRoot_) {
      return rRoot_;
    }
    
    public String getRetrieveTag1(final Recipients rRoot_) {
      return "test_map_Addresses_and_Recipients_correspondence_Addresses:aRoot_with_Recipients:rRoot";
    }
    
    public EObject getElement2(final Recipients rRoot_, final Addresses aRoot_) {
      return rRoot_;
    }
    
    public String getTag1(final Recipients rRoot_, final Addresses aRoot_) {
      return "test_map_Addresses_and_Recipients_correspondence_Addresses:aRoot_with_Recipients:rRoot";
    }
    
    public void callRoutine1(final Recipients rRoot_, final Addresses aRoot_, @Extension final mir.routines.test_R2L.RoutinesFacade _routinesFacade) {
      _routinesFacade.adRootXReRoot_BidirectionalUpdate(rRoot_);
    }
  }
  
  public AdRootXReRoot_CreateMappingRoutine(final mir.routines.test_R2L.RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Recipients rRoot_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.test_R2L.AdRootXReRoot_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.rRoot_ = rRoot_;
  }
  
  private Recipients rRoot_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AdRootXReRoot_CreateMappingRoutine with input:");
    getLogger().debug("   rRoot_: " + this.rRoot_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(rRoot_), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.addresses.Addresses.class,
    	(edu.kit.ipd.sdq.metamodels.addresses.Addresses _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(rRoot_)
    ).isEmpty()) {
    	return false;
    }
    edu.kit.ipd.sdq.metamodels.addresses.Addresses aRoot_ = edu.kit.ipd.sdq.metamodels.addresses.impl.AddressesFactoryImpl.eINSTANCE.createAddresses();
    notifyObjectCreated(aRoot_);
    
    addCorrespondenceBetween(userExecution.getElement1(rRoot_, aRoot_), userExecution.getElement2(rRoot_, aRoot_), userExecution.getTag1(rRoot_, aRoot_));
    
    userExecution.callRoutine1(rRoot_, aRoot_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}

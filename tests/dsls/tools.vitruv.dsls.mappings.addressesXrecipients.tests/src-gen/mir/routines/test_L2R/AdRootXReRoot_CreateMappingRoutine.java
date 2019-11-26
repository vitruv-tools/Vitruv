package mir.routines.test_L2R;

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
    
    public EObject getElement1(final Addresses aRoot_, final Recipients rRoot_) {
      return rRoot_;
    }
    
    public EObject getCorrepondenceSource1(final Addresses aRoot_) {
      return aRoot_;
    }
    
    public String getRetrieveTag1(final Addresses aRoot_) {
      return "test_map_Addresses_and_Recipients_correspondence_Addresses:aRoot_with_Recipients:rRoot";
    }
    
    public EObject getElement2(final Addresses aRoot_, final Recipients rRoot_) {
      return aRoot_;
    }
    
    public String getTag1(final Addresses aRoot_, final Recipients rRoot_) {
      return "test_map_Addresses_and_Recipients_correspondence_Addresses:aRoot_with_Recipients:rRoot";
    }
    
    public void callRoutine1(final Addresses aRoot_, final Recipients rRoot_, @Extension final mir.routines.test_L2R.RoutinesFacade _routinesFacade) {
      _routinesFacade.adRootXReRoot_BidirectionalUpdate(aRoot_);
    }
  }
  
  public AdRootXReRoot_CreateMappingRoutine(final mir.routines.test_L2R.RoutinesFacade routinesFacade, final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Addresses aRoot_) {
    super(routinesFacade, reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.test_L2R.AdRootXReRoot_CreateMappingRoutine.ActionUserExecution(getExecutionState(), this);
    this.aRoot_ = aRoot_;
  }
  
  private Addresses aRoot_;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine AdRootXReRoot_CreateMappingRoutine with input:");
    getLogger().debug("   aRoot_: " + this.aRoot_);
    
    if (!getCorrespondingElements(
    	userExecution.getCorrepondenceSource1(aRoot_), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.recipients.Recipients.class,
    	(edu.kit.ipd.sdq.metamodels.recipients.Recipients _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(aRoot_)
    ).isEmpty()) {
    	return false;
    }
    edu.kit.ipd.sdq.metamodels.recipients.Recipients rRoot_ = edu.kit.ipd.sdq.metamodels.recipients.impl.RecipientsFactoryImpl.eINSTANCE.createRecipients();
    notifyObjectCreated(rRoot_);
    
    addCorrespondenceBetween(userExecution.getElement1(aRoot_, rRoot_), userExecution.getElement2(aRoot_, rRoot_), userExecution.getTag1(aRoot_, rRoot_));
    
    userExecution.callRoutine1(aRoot_, rRoot_, this.getRoutinesFacade());
    
    postprocessElements();
    
    return true;
  }
}

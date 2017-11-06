package mir.routines.adXre_R2L;

import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import mir.routines.adXre_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.conditions.AdRootXReRootConditions;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class UpdateAdRootXReRootMappingInstanceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private UpdateAdRootXReRootMappingInstanceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceARoot(final Recipients rRoot) {
      return rRoot;
    }
    
    public String getRetrieveTag1(final Recipients rRoot) {
      return "AdRootXReRootMapping";
    }
    
    public boolean checkMatcherPrecondition1(final Recipients rRoot) {
      return AdRootXReRootConditions.adRootXReRootConditions().checkRightConditions(rRoot);
    }
    
    public void callRoutine1(final Recipients rRoot, final Addresses aRoot, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.enforceAdRootXReRootMappingConditionsFromRightToLeft(aRoot, rRoot);
    }
  }
  
  public UpdateAdRootXReRootMappingInstanceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Recipients rRoot) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_R2L.UpdateAdRootXReRootMappingInstanceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_R2L.RoutinesFacade(getExecutionState(), this);
    this.rRoot = rRoot;
  }
  
  private Recipients rRoot;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine UpdateAdRootXReRootMappingInstanceRoutine with input:");
    getLogger().debug("   rRoot: " + this.rRoot);
    
    if (!userExecution.checkMatcherPrecondition1(rRoot)) {
    	return false;
    }
    edu.kit.ipd.sdq.metamodels.addresses.Addresses aRoot = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceARoot(rRoot), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.addresses.Addresses.class,
    	(edu.kit.ipd.sdq.metamodels.addresses.Addresses _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(rRoot), 
    	false // asserted
    	);
    if (aRoot == null) {
    	return false;
    }
    registerObjectUnderModification(aRoot);
    userExecution.callRoutine1(rRoot, aRoot, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}

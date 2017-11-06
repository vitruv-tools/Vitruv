package mir.routines.adXre_R2L;

import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import mir.routines.adXre_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.AdRootXReRootMapping;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.conditions.AdRootXReRootConditions;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteAdRootXReRootMappingInstanceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private DeleteAdRootXReRootMappingInstanceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Recipients rRoot, final Addresses aRoot) {
      return aRoot;
    }
    
    public EObject getCorrepondenceSourceARoot(final Recipients rRoot) {
      return rRoot;
    }
    
    public void executeAction1(final Recipients rRoot, final Addresses aRoot, @Extension final RoutinesFacade _routinesFacade) {
      AdRootXReRootMapping.adRootXReRootMapping().removeInvalidatedInstanceHalves(aRoot, rRoot);
    }
    
    public String getRetrieveTag1(final Recipients rRoot) {
      return "AdRootXReRootMapping";
    }
    
    public EObject getElement2(final Recipients rRoot, final Addresses aRoot) {
      return rRoot;
    }
    
    public EObject getElement3(final Recipients rRoot, final Addresses aRoot) {
      return aRoot;
    }
    
    public String getTag1(final Recipients rRoot, final Addresses aRoot) {
      return "AdRootXReRoot";
    }
    
    public boolean checkMatcherPrecondition1(final Recipients rRoot) {
      boolean _checkRightConditions = AdRootXReRootConditions.adRootXReRootConditions().checkRightConditions(rRoot);
      return (!_checkRightConditions);
    }
  }
  
  public DeleteAdRootXReRootMappingInstanceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Recipients rRoot) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_R2L.DeleteAdRootXReRootMappingInstanceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_R2L.RoutinesFacade(getExecutionState(), this);
    this.rRoot = rRoot;
  }
  
  private Recipients rRoot;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteAdRootXReRootMappingInstanceRoutine with input:");
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
    removeCorrespondenceBetween(userExecution.getElement1(rRoot, aRoot), userExecution.getElement2(rRoot, aRoot), userExecution.getTag1(rRoot, aRoot));
    
    deleteObject(userExecution.getElement3(rRoot, aRoot));
    
    userExecution.executeAction1(rRoot, aRoot, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}

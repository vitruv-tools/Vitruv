package mir.routines.adXre_L2R;

import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import mir.routines.adXre_L2R.RoutinesFacade;
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
    
    public EObject getCorrepondenceSourceRRoot(final Addresses aRoot) {
      return aRoot;
    }
    
    public EObject getElement1(final Addresses aRoot, final Recipients rRoot) {
      return aRoot;
    }
    
    public void executeAction1(final Addresses aRoot, final Recipients rRoot, @Extension final RoutinesFacade _routinesFacade) {
      AdRootXReRootMapping.adRootXReRootMapping().removeInvalidatedInstanceHalves(aRoot, rRoot);
    }
    
    public String getRetrieveTag1(final Addresses aRoot) {
      return "AdRootXReRootMapping";
    }
    
    public EObject getElement2(final Addresses aRoot, final Recipients rRoot) {
      return rRoot;
    }
    
    public EObject getElement3(final Addresses aRoot, final Recipients rRoot) {
      return rRoot;
    }
    
    public String getTag1(final Addresses aRoot, final Recipients rRoot) {
      return "AdRootXReRoot";
    }
    
    public boolean checkMatcherPrecondition1(final Addresses aRoot) {
      boolean _checkLeftConditions = AdRootXReRootConditions.adRootXReRootConditions().checkLeftConditions(aRoot);
      return (!_checkLeftConditions);
    }
  }
  
  public DeleteAdRootXReRootMappingInstanceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Addresses aRoot) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_L2R.DeleteAdRootXReRootMappingInstanceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_L2R.RoutinesFacade(getExecutionState(), this);
    this.aRoot = aRoot;
  }
  
  private Addresses aRoot;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteAdRootXReRootMappingInstanceRoutine with input:");
    getLogger().debug("   aRoot: " + this.aRoot);
    
    if (!userExecution.checkMatcherPrecondition1(aRoot)) {
    	return false;
    }
    edu.kit.ipd.sdq.metamodels.recipients.Recipients rRoot = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceRRoot(aRoot), // correspondence source supplier
    	edu.kit.ipd.sdq.metamodels.recipients.Recipients.class,
    	(edu.kit.ipd.sdq.metamodels.recipients.Recipients _element) -> true, // correspondence precondition checker
    	userExecution.getRetrieveTag1(aRoot), 
    	false // asserted
    	);
    if (rRoot == null) {
    	return false;
    }
    registerObjectUnderModification(rRoot);
    removeCorrespondenceBetween(userExecution.getElement1(aRoot, rRoot), userExecution.getElement2(aRoot, rRoot), userExecution.getTag1(aRoot, rRoot));
    
    deleteObject(userExecution.getElement3(aRoot, rRoot));
    
    userExecution.executeAction1(aRoot, rRoot, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}

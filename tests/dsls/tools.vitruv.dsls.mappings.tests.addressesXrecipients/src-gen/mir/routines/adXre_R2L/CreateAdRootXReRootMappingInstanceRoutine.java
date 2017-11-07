package mir.routines.adXre_R2L;

import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import mir.routines.adXre_R2L.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.demo.domains.addresses.AddressesNamespace;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.AdRootXReRootMapping;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.conditions.AdRootXReRootConditions;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateAdRootXReRootMappingInstanceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateAdRootXReRootMappingInstanceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Recipients rRoot, final Addresses aRoot) {
      return aRoot;
    }
    
    public void executeAction1(final Recipients rRoot, final Addresses aRoot, @Extension final RoutinesFacade _routinesFacade) {
      AdRootXReRootMapping.adRootXReRootMapping().registerLeftElementsAndPromoteCandidates(aRoot, rRoot);
      String _lastSegment = rRoot.eResource().getURI().trimFileExtension().lastSegment();
      String _plus = (_lastSegment + ".");
      String _plus_1 = (_plus + AddressesNamespace.FILE_EXTENSION);
      this.persistProjectRelative(rRoot, aRoot, _plus_1);
    }
    
    public EObject getElement2(final Recipients rRoot, final Addresses aRoot) {
      return rRoot;
    }
    
    public String getTag1(final Recipients rRoot, final Addresses aRoot) {
      return "AdRootXReRootMapping";
    }
    
    public boolean checkMatcherPrecondition1(final Recipients rRoot) {
      return AdRootXReRootConditions.adRootXReRootConditions().checkRightConditions(rRoot);
    }
    
    public void callRoutine1(final Recipients rRoot, final Addresses aRoot, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.enforceAdRootXReRootMappingConditionsFromRightToLeft(aRoot, rRoot);
    }
  }
  
  public CreateAdRootXReRootMappingInstanceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Recipients rRoot) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_R2L.CreateAdRootXReRootMappingInstanceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_R2L.RoutinesFacade(getExecutionState(), this);
    this.rRoot = rRoot;
  }
  
  private Recipients rRoot;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateAdRootXReRootMappingInstanceRoutine with input:");
    getLogger().debug("   rRoot: " + this.rRoot);
    
    if (!userExecution.checkMatcherPrecondition1(rRoot)) {
    	return false;
    }
    edu.kit.ipd.sdq.metamodels.addresses.Addresses aRoot = edu.kit.ipd.sdq.metamodels.addresses.impl.AddressesFactoryImpl.eINSTANCE.createAddresses();
    notifyObjectCreated(aRoot);
    
    addCorrespondenceBetween(userExecution.getElement1(rRoot, aRoot), userExecution.getElement2(rRoot, aRoot), userExecution.getTag1(rRoot, aRoot));
    
    userExecution.callRoutine1(rRoot, aRoot, actionsFacade);
    
    userExecution.executeAction1(rRoot, aRoot, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}

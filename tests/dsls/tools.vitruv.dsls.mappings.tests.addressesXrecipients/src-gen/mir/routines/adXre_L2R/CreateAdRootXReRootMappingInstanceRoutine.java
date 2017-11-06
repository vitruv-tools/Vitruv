package mir.routines.adXre_L2R;

import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import java.io.IOException;
import mir.routines.adXre_L2R.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.demo.domains.recipients.RecipientsNamespace;
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
    
    public EObject getElement1(final Addresses aRoot, final Recipients rRoot) {
      return aRoot;
    }
    
    public void executeAction1(final Addresses aRoot, final Recipients rRoot, @Extension final RoutinesFacade _routinesFacade) {
      AdRootXReRootMapping.adRootXReRootMapping().registerRightElementsAndPromoteCandidates(aRoot, rRoot);
      String _lastSegment = aRoot.eResource().getURI().trimFileExtension().lastSegment();
      String _plus = (_lastSegment + ".");
      String _plus_1 = (_plus + RecipientsNamespace.FILE_EXTENSION);
      this.persistProjectRelative(aRoot, rRoot, _plus_1);
    }
    
    public EObject getElement2(final Addresses aRoot, final Recipients rRoot) {
      return rRoot;
    }
    
    public String getTag1(final Addresses aRoot, final Recipients rRoot) {
      return "AdRootXReRootMapping";
    }
    
    public boolean checkMatcherPrecondition1(final Addresses aRoot) {
      return AdRootXReRootConditions.adRootXReRootConditions().checkLeftConditions(aRoot);
    }
    
    public void callRoutine1(final Addresses aRoot, final Recipients rRoot, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.enforceAdRootXReRootMappingConditionsFromLeftToRight(aRoot, rRoot);
    }
  }
  
  public CreateAdRootXReRootMappingInstanceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Addresses aRoot) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.adXre_L2R.CreateAdRootXReRootMappingInstanceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.adXre_L2R.RoutinesFacade(getExecutionState(), this);
    this.aRoot = aRoot;
  }
  
  private Addresses aRoot;
  
  protected boolean executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateAdRootXReRootMappingInstanceRoutine with input:");
    getLogger().debug("   aRoot: " + this.aRoot);
    
    if (!userExecution.checkMatcherPrecondition1(aRoot)) {
    	return false;
    }
    edu.kit.ipd.sdq.metamodels.recipients.Recipients rRoot = edu.kit.ipd.sdq.metamodels.recipients.impl.RecipientsFactoryImpl.eINSTANCE.createRecipients();
    notifyObjectCreated(rRoot);
    
    addCorrespondenceBetween(userExecution.getElement1(aRoot, rRoot), userExecution.getElement2(aRoot, rRoot), userExecution.getTag1(aRoot, rRoot));
    
    userExecution.callRoutine1(aRoot, rRoot, actionsFacade);
    
    userExecution.executeAction1(aRoot, rRoot, actionsFacade);
    
    postprocessElements();
    
    return true;
  }
}

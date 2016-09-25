package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.Root;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class RemovedEAttributeValueResponse extends AbstractResponseRealization {
  public RemovedEAttributeValueResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEAttributeValue.class;
  }
  
  private boolean checkChangeProperties(final RemoveEAttributeValue<Root, Integer> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof Root)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("multiValuedEAttribute")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveEAttributeValue<?, ?>)) {
    	return false;
    }
    RemoveEAttributeValue typedChange = (RemoveEAttributeValue)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    RemoveEAttributeValue<Root, Integer> typedChange = (RemoveEAttributeValue<Root, Integer>)change;
    mir.routines.simpleChangesTests.RoutinesFacade routinesFacade = new mir.routines.simpleChangesTests.RoutinesFacade(this.executionState, this);
    mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.RemovedEAttributeValueResponse.EffectUserExecution userExecution = new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.RemovedEAttributeValueResponse.EffectUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public void callRoutine1(final RemoveEAttributeValue<Root, Integer> change, @Extension final RoutinesFacade _routinesFacade) {
      Root _affectedEObject = change.getAffectedEObject();
      Integer _oldValue = change.getOldValue();
      _routinesFacade.removeEAttribute(_affectedEObject, _oldValue);
    }
  }
}

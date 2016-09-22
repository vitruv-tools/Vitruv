package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.Root;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class InsertedEAttributeValueResponse extends AbstractResponseRealization {
  public InsertedEAttributeValueResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEAttributeValue.class;
  }
  
  private boolean checkChangeProperties(final InsertEAttributeValue<Root, Integer> change) {
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
    if (!(change instanceof InsertEAttributeValue<?, ?>)) {
    	return false;
    }
    InsertEAttributeValue typedChange = (InsertEAttributeValue)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    InsertEAttributeValue<Root, Integer> typedChange = (InsertEAttributeValue<Root, Integer>)change;
    new mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests.InsertedEAttributeValueResponse.CallRoutinesUserExecution(this.executionState, this).executeUserOperations(typedChange);
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEAttributeValue<Root, Integer> change) {
      Root _affectedEObject = change.getAffectedEObject();
      Integer _newValue = change.getNewValue();
      this.effectFacade.insertEAttribute(_affectedEObject, _newValue);
    }
  }
}

package mir.responses.responsesJavaTo5_1.parserIntegrationResponse;

import mir.routines.parserIntegrationResponse.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class RemoveFieldModifierEventParserResponse extends AbstractResponseRealization {
  public RemoveFieldModifierEventParserResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final RemoveEReference<Field, AnnotationInstanceOrModifier> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof Field)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("annotationsAndModifiers")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveEReference<?, ?>)) {
    	return false;
    }
    RemoveEReference typedChange = (RemoveEReference)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    RemoveEReference<Field, AnnotationInstanceOrModifier> typedChange = (RemoveEReference<Field, AnnotationInstanceOrModifier>)change;
    mir.routines.parserIntegrationResponse.RoutinesFacade routinesFacade = new mir.routines.parserIntegrationResponse.RoutinesFacade(this.executionState, this);
    mir.responses.responsesJavaTo5_1.parserIntegrationResponse.RemoveFieldModifierEventParserResponse.EffectUserExecution userExecution = new mir.responses.responsesJavaTo5_1.parserIntegrationResponse.RemoveFieldModifierEventParserResponse.EffectUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public void callRoutine1(final RemoveEReference<Field, AnnotationInstanceOrModifier> change, @Extension final RoutinesFacade _routinesFacade) {
    }
  }
}

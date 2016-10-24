package mir.reactions.reactionsJavaTo5_1.parserIntegrationResponse;

import mir.routines.parserIntegrationResponse.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class AddMethodEventParserReaction extends AbstractResponseRealization {
  public AddMethodEventParserReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final InsertEReference<ConcreteClassifier, Member> change) {
    Member _newValue = change.getNewValue();
    return (_newValue instanceof Method);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<ConcreteClassifier, Member> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof ConcreteClassifier)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("members")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertEReference<?, ?>)) {
    	return false;
    }
    InsertEReference typedChange = (InsertEReference)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    if (!checkTriggerPrecondition(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    InsertEReference<ConcreteClassifier, Member> typedChange = (InsertEReference<ConcreteClassifier, Member>)change;
    mir.routines.parserIntegrationResponse.RoutinesFacade routinesFacade = new mir.routines.parserIntegrationResponse.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaTo5_1.parserIntegrationResponse.AddMethodEventParserReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaTo5_1.parserIntegrationResponse.AddMethodEventParserReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public void callRoutine1(final InsertEReference<ConcreteClassifier, Member> change, @Extension final RoutinesFacade _routinesFacade) {
      ConcreteClassifier _affectedEObject = change.getAffectedEObject();
      Member _newValue = change.getNewValue();
      _routinesFacade.addedMethodEventParser(_affectedEObject, ((Method) _newValue));
    }
  }
}

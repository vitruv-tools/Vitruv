package mir.routines.packageMappingIntegration;

import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;

@SuppressWarnings("all")
public class AddFieldEventEffect extends AbstractEffectRealization {
  public AddFieldEventEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<ConcreteClassifier, Member> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<ConcreteClassifier, Member> change;
  
  private EObject getCorrepondenceSourceBasicComponent(final InsertEReference<ConcreteClassifier, Member> change) {
    ConcreteClassifier _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddFieldEventEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    BasicComponent basicComponent = getCorrespondingElement(
    	getCorrepondenceSourceBasicComponent(change), // correspondence source supplier
    	BasicComponent.class,
    	(BasicComponent _element) -> true, // correspondence precondition checker
    	null);
    if (basicComponent == null) {
    	return;
    }
    initializeRetrieveElementState(basicComponent);
    OperationInterface opInterface = getCorrespondingElement(
    	getCorrepondenceSourceOpInterface(change), // correspondence source supplier
    	OperationInterface.class,
    	(OperationInterface _element) -> true, // correspondence precondition checker
    	null);
    if (opInterface == null) {
    	return;
    }
    initializeRetrieveElementState(opInterface);
    
    preprocessElementStates();
    new mir.routines.packageMappingIntegration.AddFieldEventEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, basicComponent, opInterface);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceOpInterface(final InsertEReference<ConcreteClassifier, Member> change) {
    Member _newValue = change.getNewValue();
    TypeReference _typeReference = ((Field) _newValue).getTypeReference();
    return _typeReference;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<ConcreteClassifier, Member> change, final BasicComponent basicComponent, final OperationInterface opInterface) {
      Member _newValue = change.getNewValue();
      this.effectFacade.callCreateRequiredRole(basicComponent, opInterface, ((Field) _newValue));
    }
  }
}

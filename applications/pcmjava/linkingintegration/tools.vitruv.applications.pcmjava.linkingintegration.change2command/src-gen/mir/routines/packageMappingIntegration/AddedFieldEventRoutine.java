package mir.routines.packageMappingIntegration;

import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddedFieldEventRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private AddedFieldEventRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceBasicComponent(final ConcreteClassifier clazz, final Field field) {
      return clazz;
    }
    
    public EObject getCorrepondenceSourceOpInterface(final ConcreteClassifier clazz, final Field field, final BasicComponent basicComponent) {
      TypeReference _typeReference = field.getTypeReference();
      return _typeReference;
    }
    
    public void callRoutine1(final ConcreteClassifier clazz, final Field field, final BasicComponent basicComponent, final OperationInterface opInterface, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createRequiredRole(basicComponent, opInterface, field);
    }
  }
  
  public AddedFieldEventRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ConcreteClassifier clazz, final Field field) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.packageMappingIntegration.AddedFieldEventRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(getExecutionState(), this);
    this.clazz = clazz;this.field = field;
  }
  
  private ConcreteClassifier clazz;
  
  private Field field;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddedFieldEventRoutine with input:");
    getLogger().debug("   ConcreteClassifier: " + this.clazz);
    getLogger().debug("   Field: " + this.field);
    
    BasicComponent basicComponent = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceBasicComponent(clazz, field), // correspondence source supplier
    	BasicComponent.class,
    	(BasicComponent _element) -> true, // correspondence precondition checker
    	null);
    if (basicComponent == null) {
    	return;
    }
    initializeRetrieveElementState(basicComponent);
    OperationInterface opInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOpInterface(clazz, field, basicComponent), // correspondence source supplier
    	OperationInterface.class,
    	(OperationInterface _element) -> true, // correspondence precondition checker
    	null);
    if (opInterface == null) {
    	return;
    }
    initializeRetrieveElementState(opInterface);
    userExecution.callRoutine1(clazz, field, basicComponent, opInterface, effectFacade);
    
    postprocessElementStates();
  }
}

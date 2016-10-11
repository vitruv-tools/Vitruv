package mir.routines.packageMappingIntegration;

import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  public void renamedMethod(final Method method, final String newMethodName) {
    mir.routines.packageMappingIntegration.RenamedMethodRoutine effect = new mir.routines.packageMappingIntegration.RenamedMethodRoutine(this.executionState, calledBy,
    	method, newMethodName);
    effect.applyRoutine();
  }
  
  public void removedMethodEvent(final Method method) {
    mir.routines.packageMappingIntegration.RemovedMethodEventRoutine effect = new mir.routines.packageMappingIntegration.RemovedMethodEventRoutine(this.executionState, calledBy,
    	method);
    effect.applyRoutine();
  }
  
  public void addedMethodEvent(final ConcreteClassifier clazz, final Method method) {
    mir.routines.packageMappingIntegration.AddedMethodEventRoutine effect = new mir.routines.packageMappingIntegration.AddedMethodEventRoutine(this.executionState, calledBy,
    	clazz, method);
    effect.applyRoutine();
  }
  
  public void createOperationSignature(final OperationInterface opInterface, final Method newMethod) {
    mir.routines.packageMappingIntegration.CreateOperationSignatureRoutine effect = new mir.routines.packageMappingIntegration.CreateOperationSignatureRoutine(this.executionState, calledBy,
    	opInterface, newMethod);
    effect.applyRoutine();
  }
  
  public void createdMethodParameterEvent(final Method method, final Parameter parameter) {
    mir.routines.packageMappingIntegration.CreatedMethodParameterEventRoutine effect = new mir.routines.packageMappingIntegration.CreatedMethodParameterEventRoutine(this.executionState, calledBy,
    	method, parameter);
    effect.applyRoutine();
  }
  
  public void methodParameterNameChangedEvent(final Parameter parameter, final String oldParameterName, final String newParameterName) {
    mir.routines.packageMappingIntegration.MethodParameterNameChangedEventRoutine effect = new mir.routines.packageMappingIntegration.MethodParameterNameChangedEventRoutine(this.executionState, calledBy,
    	parameter, oldParameterName, newParameterName);
    effect.applyRoutine();
  }
  
  public void changedMethodType(final Method method, final TypeReference newType) {
    mir.routines.packageMappingIntegration.ChangedMethodTypeRoutine effect = new mir.routines.packageMappingIntegration.ChangedMethodTypeRoutine(this.executionState, calledBy,
    	method, newType);
    effect.applyRoutine();
  }
  
  public void removedFieldEvent(final Field field) {
    mir.routines.packageMappingIntegration.RemovedFieldEventRoutine effect = new mir.routines.packageMappingIntegration.RemovedFieldEventRoutine(this.executionState, calledBy,
    	field);
    effect.applyRoutine();
  }
  
  public void addedFieldEvent(final ConcreteClassifier clazz, final Field field) {
    mir.routines.packageMappingIntegration.AddedFieldEventRoutine effect = new mir.routines.packageMappingIntegration.AddedFieldEventRoutine(this.executionState, calledBy,
    	clazz, field);
    effect.applyRoutine();
  }
  
  public void createRequiredRole(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field) {
    mir.routines.packageMappingIntegration.CreateRequiredRoleRoutine effect = new mir.routines.packageMappingIntegration.CreateRequiredRoleRoutine(this.executionState, calledBy,
    	basicComponent, opInterface, field);
    effect.applyRoutine();
  }
  
  public void changedFieldTypeEvent(final Field field, final TypeReference oldType, final TypeReference newType) {
    mir.routines.packageMappingIntegration.ChangedFieldTypeEventRoutine effect = new mir.routines.packageMappingIntegration.ChangedFieldTypeEventRoutine(this.executionState, calledBy,
    	field, oldType, newType);
    effect.applyRoutine();
  }
  
  public void removeRequiredRoleAndCorrespondence(final OperationRequiredRole orr, final Field field) {
    mir.routines.packageMappingIntegration.RemoveRequiredRoleAndCorrespondenceRoutine effect = new mir.routines.packageMappingIntegration.RemoveRequiredRoleAndCorrespondenceRoutine(this.executionState, calledBy,
    	orr, field);
    effect.applyRoutine();
  }
  
  public void changedMethodModifierEvent(final Method method, final AnnotationInstanceOrModifier annotationOrModifier) {
    mir.routines.packageMappingIntegration.ChangedMethodModifierEventRoutine effect = new mir.routines.packageMappingIntegration.ChangedMethodModifierEventRoutine(this.executionState, calledBy,
    	method, annotationOrModifier);
    effect.applyRoutine();
  }
}

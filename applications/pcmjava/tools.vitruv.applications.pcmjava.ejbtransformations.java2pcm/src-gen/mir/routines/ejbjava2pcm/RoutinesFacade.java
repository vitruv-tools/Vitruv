package mir.routines.ejbjava2pcm;

import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  public void createRepositoryForFirstPackage(final org.emftext.language.java.containers.Package javaPackage) {
    mir.routines.ejbjava2pcm.CreateRepositoryForFirstPackageRoutine effect = new mir.routines.ejbjava2pcm.CreateRepositoryForFirstPackageRoutine(this.executionState, calledBy,
    	javaPackage);
    effect.applyRoutine();
  }
  
  public void createBasicComponent(final Repository repo, final NamedElement namedElement) {
    mir.routines.ejbjava2pcm.CreateBasicComponentRoutine effect = new mir.routines.ejbjava2pcm.CreateBasicComponentRoutine(this.executionState, calledBy,
    	repo, namedElement);
    effect.applyRoutine();
  }
  
  public void createOperationInterface(final Repository repo, final NamedElement namedElement) {
    mir.routines.ejbjava2pcm.CreateOperationInterfaceRoutine effect = new mir.routines.ejbjava2pcm.CreateOperationInterfaceRoutine(this.executionState, calledBy,
    	repo, namedElement);
    effect.applyRoutine();
  }
  
  public void createdAnnotationForField(final Field annotatedField) {
    mir.routines.ejbjava2pcm.CreatedAnnotationForFieldRoutine effect = new mir.routines.ejbjava2pcm.CreatedAnnotationForFieldRoutine(this.executionState, calledBy,
    	annotatedField);
    effect.applyRoutine();
  }
  
  public void createdField(final Field field) {
    mir.routines.ejbjava2pcm.CreatedFieldRoutine effect = new mir.routines.ejbjava2pcm.CreatedFieldRoutine(this.executionState, calledBy,
    	field);
    effect.applyRoutine();
  }
  
  public void createOperationRequiredRole(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field) {
    mir.routines.ejbjava2pcm.CreateOperationRequiredRoleRoutine effect = new mir.routines.ejbjava2pcm.CreateOperationRequiredRoleRoutine(this.executionState, calledBy,
    	basicComponent, opInterface, field);
    effect.applyRoutine();
  }
  
  public void createdImplements(final org.emftext.language.java.classifiers.Class clazz, final TypeReference implementz) {
    mir.routines.ejbjava2pcm.CreatedImplementsRoutine effect = new mir.routines.ejbjava2pcm.CreatedImplementsRoutine(this.executionState, calledBy,
    	clazz, implementz);
    effect.applyRoutine();
  }
  
  public void createdInterfaceMethod(final Interface interf, final InterfaceMethod method) {
    mir.routines.ejbjava2pcm.CreatedInterfaceMethodRoutine effect = new mir.routines.ejbjava2pcm.CreatedInterfaceMethodRoutine(this.executionState, calledBy,
    	interf, method);
    effect.applyRoutine();
  }
  
  public void createOperationSignature(final InterfaceMethod interfaceMethod, final OperationInterface opInterface) {
    mir.routines.ejbjava2pcm.CreateOperationSignatureRoutine effect = new mir.routines.ejbjava2pcm.CreateOperationSignatureRoutine(this.executionState, calledBy,
    	interfaceMethod, opInterface);
    effect.applyRoutine();
  }
  
  public void createdParameterInInterfaceMethod(final InterfaceMethod method, final Parameter parameter) {
    mir.routines.ejbjava2pcm.CreatedParameterInInterfaceMethodRoutine effect = new mir.routines.ejbjava2pcm.CreatedParameterInInterfaceMethodRoutine(this.executionState, calledBy,
    	method, parameter);
    effect.applyRoutine();
  }
  
  public void createPCMParameter(final Parameter jaMoPPParam, final OperationSignature opSignature) {
    mir.routines.ejbjava2pcm.CreatePCMParameterRoutine effect = new mir.routines.ejbjava2pcm.CreatePCMParameterRoutine(this.executionState, calledBy,
    	jaMoPPParam, opSignature);
    effect.applyRoutine();
  }
  
  public void createdReturnType(final InterfaceMethod method, final TypeReference type) {
    mir.routines.ejbjava2pcm.CreatedReturnTypeRoutine effect = new mir.routines.ejbjava2pcm.CreatedReturnTypeRoutine(this.executionState, calledBy,
    	method, type);
    effect.applyRoutine();
  }
  
  public void createPCMReturnType(final TypeReference returnType, final OperationSignature opSignature, final Method javaMethod) {
    mir.routines.ejbjava2pcm.CreatePCMReturnTypeRoutine effect = new mir.routines.ejbjava2pcm.CreatePCMReturnTypeRoutine(this.executionState, calledBy,
    	returnType, opSignature, javaMethod);
    effect.applyRoutine();
  }
  
  public void createdFieldInDatatypeClass(final org.emftext.language.java.classifiers.Class clazz, final Field field) {
    mir.routines.ejbjava2pcm.CreatedFieldInDatatypeClassRoutine effect = new mir.routines.ejbjava2pcm.CreatedFieldInDatatypeClassRoutine(this.executionState, calledBy,
    	clazz, field);
    effect.applyRoutine();
  }
  
  public void createdClassMethodInEJBClass(final org.emftext.language.java.classifiers.Class clazz, final ClassMethod classMethod) {
    mir.routines.ejbjava2pcm.CreatedClassMethodInEJBClassRoutine effect = new mir.routines.ejbjava2pcm.CreatedClassMethodInEJBClassRoutine(this.executionState, calledBy,
    	clazz, classMethod);
    effect.applyRoutine();
  }
  
  public void createSEFFForClassMethod(final BasicComponent basicComponent, final OperationSignature opSignature, final ClassMethod classMethod) {
    mir.routines.ejbjava2pcm.CreateSEFFForClassMethodRoutine effect = new mir.routines.ejbjava2pcm.CreateSEFFForClassMethodRoutine(this.executionState, calledBy,
    	basicComponent, opSignature, classMethod);
    effect.applyRoutine();
  }
}

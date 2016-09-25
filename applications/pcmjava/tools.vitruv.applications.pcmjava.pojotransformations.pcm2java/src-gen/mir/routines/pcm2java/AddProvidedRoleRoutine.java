package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.imports.impl.ImportsFactoryImpl;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.impl.TypesFactoryImpl;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddProvidedRoleRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private AddProvidedRoleRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport) {
      return interfaceImport;
    }
    
    public void update0Element(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
      EList<TypeReference> _implements = javaClass.getImplements();
      _implements.add(namespaceClassifierReference);
    }
    
    public EObject getCorrepondenceSourceJavaClass(final OperationProvidedRole providedRole, final Interface operationProvidingInterface) {
      InterfaceProvidingEntity _providingEntity_ProvidedRole = providedRole.getProvidingEntity_ProvidedRole();
      return _providingEntity_ProvidedRole;
    }
    
    public EObject getElement4(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
      return providedRole;
    }
    
    public EObject getElement5(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
      return javaClass;
    }
    
    public EObject getElement2(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport) {
      return providedRole;
    }
    
    public EObject getElement3(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
      return namespaceClassifierReference;
    }
    
    public void updateNamespaceClassifierReferenceElement(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
      Pcm2JavaHelper.createNamespaceClassifierReference(namespaceClassifierReference, operationProvidingInterface);
    }
    
    public EObject getCorrepondenceSourceOperationProvidingInterface(final OperationProvidedRole providedRole) {
      OperationInterface _providedInterface__OperationProvidedRole = providedRole.getProvidedInterface__OperationProvidedRole();
      return _providedInterface__OperationProvidedRole;
    }
    
    public void updateInterfaceImportElement(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport) {
      Pcm2JavaHelper.addImportToCompilationUnitOfClassifier(interfaceImport, javaClass, operationProvidingInterface);
    }
  }
  
  public AddProvidedRoleRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole providedRole) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2java.AddProvidedRoleRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    this.providedRole = providedRole;
  }
  
  private OperationProvidedRole providedRole;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddProvidedRoleRoutine with input:");
    getLogger().debug("   OperationProvidedRole: " + this.providedRole);
    
    Interface operationProvidingInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperationProvidingInterface(providedRole), // correspondence source supplier
    	Interface.class,
    	(Interface _element) -> true, // correspondence precondition checker
    	null);
    if (operationProvidingInterface == null) {
    	return;
    }
    initializeRetrieveElementState(operationProvidingInterface);
    org.emftext.language.java.classifiers.Class javaClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaClass(providedRole, operationProvidingInterface), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (javaClass == null) {
    	return;
    }
    initializeRetrieveElementState(javaClass);
    ClassifierImport interfaceImport = ImportsFactoryImpl.eINSTANCE.createClassifierImport();
    initializeCreateElementState(interfaceImport);
    userExecution.updateInterfaceImportElement(providedRole, operationProvidingInterface, javaClass, interfaceImport);
    
    addCorrespondenceBetween(userExecution.getElement1(providedRole, operationProvidingInterface, javaClass, interfaceImport), userExecution.getElement2(providedRole, operationProvidingInterface, javaClass, interfaceImport), "");
    
    NamespaceClassifierReference namespaceClassifierReference = TypesFactoryImpl.eINSTANCE.createNamespaceClassifierReference();
    initializeCreateElementState(namespaceClassifierReference);
    userExecution.updateNamespaceClassifierReferenceElement(providedRole, operationProvidingInterface, javaClass, interfaceImport, namespaceClassifierReference);
    
    addCorrespondenceBetween(userExecution.getElement3(providedRole, operationProvidingInterface, javaClass, interfaceImport, namespaceClassifierReference), userExecution.getElement4(providedRole, operationProvidingInterface, javaClass, interfaceImport, namespaceClassifierReference), "");
    
    // val updatedElement userExecution.getElement5(providedRole, operationProvidingInterface, javaClass, interfaceImport, namespaceClassifierReference);
    userExecution.update0Element(providedRole, operationProvidingInterface, javaClass, interfaceImport, namespaceClassifierReference);
    
    postprocessElementStates();
  }
}

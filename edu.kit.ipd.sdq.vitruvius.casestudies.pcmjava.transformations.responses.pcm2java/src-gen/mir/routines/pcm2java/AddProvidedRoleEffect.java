package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.imports.ClassifierImport;
import org.emftext.language.java.imports.impl.ImportsFactoryImpl;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.impl.TypesFactoryImpl;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

@SuppressWarnings("all")
public class AddProvidedRoleEffect extends AbstractEffectRealization {
  public AddProvidedRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final OperationProvidedRole providedRole) {
    super(responseExecutionState, calledBy);
    				this.providedRole = providedRole;
  }
  
  private OperationProvidedRole providedRole;
  
  private EObject getElement0(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
    return interfaceImport;
  }
  
  private EObject getElement1(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
    return namespaceClassifierReference;
  }
  
  private EObject getCorrepondenceSourceJavaClass(final OperationProvidedRole providedRole) {
    InterfaceProvidingEntity _providingEntity_ProvidedRole = providedRole.getProvidingEntity_ProvidedRole();
    return _providingEntity_ProvidedRole;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddProvidedRoleEffect with input:");
    getLogger().debug("   OperationProvidedRole: " + this.providedRole);
    
    Interface operationProvidingInterface = getCorrespondingElement(
    	getCorrepondenceSourceOperationProvidingInterface(providedRole), // correspondence source supplier
    	Interface.class,
    	(Interface _element) -> true, // correspondence precondition checker
    	null);
    if (operationProvidingInterface == null) {
    	return;
    }
    initializeRetrieveElementState(operationProvidingInterface);
    org.emftext.language.java.classifiers.Class javaClass = getCorrespondingElement(
    	getCorrepondenceSourceJavaClass(providedRole), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    if (javaClass == null) {
    	return;
    }
    initializeRetrieveElementState(javaClass);
    ClassifierImport interfaceImport = ImportsFactoryImpl.eINSTANCE.createClassifierImport();
    initializeCreateElementState(interfaceImport);
    NamespaceClassifierReference namespaceClassifierReference = TypesFactoryImpl.eINSTANCE.createNamespaceClassifierReference();
    initializeCreateElementState(namespaceClassifierReference);
    
    addCorrespondenceBetween(getElement0(providedRole, operationProvidingInterface, javaClass, interfaceImport, namespaceClassifierReference), getElement2(providedRole, operationProvidingInterface, javaClass, interfaceImport, namespaceClassifierReference), "");
    addCorrespondenceBetween(getElement1(providedRole, operationProvidingInterface, javaClass, interfaceImport, namespaceClassifierReference), getElement3(providedRole, operationProvidingInterface, javaClass, interfaceImport, namespaceClassifierReference), "");
    preprocessElementStates();
    new mir.routines.pcm2java.AddProvidedRoleEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	providedRole, operationProvidingInterface, javaClass, interfaceImport, namespaceClassifierReference);
    postprocessElementStates();
  }
  
  private EObject getElement2(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
    return providedRole;
  }
  
  private EObject getElement3(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
    return providedRole;
  }
  
  private EObject getCorrepondenceSourceOperationProvidingInterface(final OperationProvidedRole providedRole) {
    OperationInterface _providedInterface__OperationProvidedRole = providedRole.getProvidedInterface__OperationProvidedRole();
    return _providedInterface__OperationProvidedRole;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
      Pcm2JavaHelper.createNamespaceClassifierReference(namespaceClassifierReference, operationProvidingInterface);
      EList<TypeReference> _implements = javaClass.getImplements();
      _implements.add(namespaceClassifierReference);
      Pcm2JavaHelper.addImportToCompilationUnitOfClassifier(interfaceImport, javaClass, operationProvidingInterface);
    }
  }
}

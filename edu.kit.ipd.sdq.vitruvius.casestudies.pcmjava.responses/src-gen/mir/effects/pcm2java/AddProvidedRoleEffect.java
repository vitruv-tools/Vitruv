package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.responses.pcm2java.Pcm2JavaHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandlerFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
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
  public AddProvidedRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private OperationProvidedRole providedRole;
  
  private boolean isProvidedRoleSet;
  
  public void setProvidedRole(final OperationProvidedRole providedRole) {
    this.providedRole = providedRole;
    this.isProvidedRoleSet = true;
  }
  
  public boolean allParametersSet() {
    return isProvidedRoleSet;
  }
  
  private EObject getCorrepondenceSourceJavaClass(final OperationProvidedRole providedRole) {
    InterfaceProvidingEntity _providingEntity_ProvidedRole = providedRole.getProvidingEntity_ProvidedRole();
    return _providingEntity_ProvidedRole;
  }
  
  private EObject getCorrepondenceSourceOperationProvidingInterface(final OperationProvidedRole providedRole) {
    OperationInterface _providedInterface__OperationProvidedRole = providedRole.getProvidedInterface__OperationProvidedRole();
    return _providedInterface__OperationProvidedRole;
  }
  
  private EObject getCorrepondenceSourceInterfaceImport(final OperationProvidedRole providedRole) {
    return providedRole;
  }
  
  private EObject getCorrepondenceSourceNamespaceClassifierReference(final OperationProvidedRole providedRole) {
    return providedRole;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect AddProvidedRoleEffect with input:");
    getLogger().debug("   OperationProvidedRole: " + this.providedRole);
    
    Interface operationProvidingInterface = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceOperationProvidingInterface(providedRole), // correspondence source supplier
    	(Interface _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	Interface.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    if (isAborted()) return;
    org.emftext.language.java.classifiers.Class javaClass = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceJavaClass(providedRole), // correspondence source supplier
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	org.emftext.language.java.classifiers.Class.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    if (isAborted()) return;
    ClassifierImport interfaceImport = initializeCreateElementState(
    	() -> getCorrepondenceSourceInterfaceImport(providedRole), // correspondence source supplier
    	() -> ImportsFactoryImpl.eINSTANCE.createClassifierImport(), // element creation supplier
    	() -> null, // tag supplier
    	ClassifierImport.class);
    if (isAborted()) return;
    NamespaceClassifierReference namespaceClassifierReference = initializeCreateElementState(
    	() -> getCorrepondenceSourceNamespaceClassifierReference(providedRole), // correspondence source supplier
    	() -> TypesFactoryImpl.eINSTANCE.createNamespaceClassifierReference(), // element creation supplier
    	() -> null, // tag supplier
    	NamespaceClassifierReference.class);
    if (isAborted()) return;
    preProcessElements();
    new mir.effects.pcm2java.AddProvidedRoleEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	providedRole, operationProvidingInterface, javaClass, interfaceImport, namespaceClassifierReference);
    postProcessElements();
  }
  
  private static class EffectUserExecution {
    private Blackboard blackboard;
    
    private UserInteracting userInteracting;
    
    private TransformationResult transformationResult;
    
    @Extension
    private EffectsFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      this.blackboard = responseExecutionState.getBlackboard();
      this.userInteracting = responseExecutionState.getUserInteracting();
      this.transformationResult = responseExecutionState.getTransformationResult();
      this.effectFacade = new EffectsFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final OperationProvidedRole providedRole, final Interface operationProvidingInterface, final org.emftext.language.java.classifiers.Class javaClass, final ClassifierImport interfaceImport, final NamespaceClassifierReference namespaceClassifierReference) {
      Pcm2JavaHelper.createNamespaceClassifierReference(namespaceClassifierReference, operationProvidingInterface);
      EList<TypeReference> _implements = javaClass.getImplements();
      _implements.add(namespaceClassifierReference);
      Pcm2JavaHelper.addImportToCompilationUnitOfClassifier(interfaceImport, javaClass, operationProvidingInterface);
    }
  }
}

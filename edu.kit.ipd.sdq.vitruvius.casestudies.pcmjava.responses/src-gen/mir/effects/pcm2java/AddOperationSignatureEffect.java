package mir.effects.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.CorrespondenceFailHandlerFactory;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import java.io.IOException;
import mir.effects.pcm2java.EffectsFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.impl.MembersFactoryImpl;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
public class AddOperationSignatureEffect extends AbstractEffectRealization {
  public AddOperationSignatureEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private CreateNonRootEObjectInList<OperationSignature> change;
  
  private boolean isChangeSet;
  
  public void setChange(final CreateNonRootEObjectInList<OperationSignature> change) {
    this.change = change;
    this.isChangeSet = true;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private EObject getCorrepondenceSourceJavaInterface(final CreateNonRootEObjectInList<OperationSignature> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  private EObject getCorrepondenceSourceInterfaceMethod(final CreateNonRootEObjectInList<OperationSignature> change) {
    OperationSignature _newValue = change.getNewValue();
    return _newValue;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called effect AddOperationSignatureEffect with input:");
    getLogger().debug("   CreateNonRootEObjectInList: " + this.change);
    
    InterfaceMethod interfaceMethod = initializeCreateElementState(
    	() -> getCorrepondenceSourceInterfaceMethod(change), // correspondence source supplier
    	() -> MembersFactoryImpl.eINSTANCE.createInterfaceMethod(), // element creation supplier
    	() -> null, // tag supplier
    	InterfaceMethod.class);
    Interface javaInterface = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceJavaInterface(change), // correspondence source supplier
    	(Interface _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	Interface.class,
    	CorrespondenceFailHandlerFactory.createExceptionHandler());
    preProcessElements();
    new mir.effects.pcm2java.AddOperationSignatureEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, interfaceMethod, javaInterface);
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
    
    private void executeUserOperations(final CreateNonRootEObjectInList<OperationSignature> change, final InterfaceMethod interfaceMethod, final Interface javaInterface) {
      OperationSignature _newValue = change.getNewValue();
      String _entityName = _newValue.getEntityName();
      interfaceMethod.setName(_entityName);
      OperationSignature _newValue_1 = change.getNewValue();
      DataType _returnType__OperationSignature = _newValue_1.getReturnType__OperationSignature();
      this.effectFacade.callChangeInterfaceMethodReturnType(interfaceMethod, _returnType__OperationSignature);
      EList<Member> _members = javaInterface.getMembers();
      _members.add(interfaceMethod);
    }
  }
}

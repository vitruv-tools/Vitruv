package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
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
  
  private EObject getElement0(final CreateNonRootEObjectInList<OperationSignature> change, final Interface javaInterface, final InterfaceMethod interfaceMethod) {
    return interfaceMethod;
  }
  
  private EObject getElement1(final CreateNonRootEObjectInList<OperationSignature> change, final Interface javaInterface, final InterfaceMethod interfaceMethod) {
    OperationSignature _newValue = change.getNewValue();
    return _newValue;
  }
  
  public boolean allParametersSet() {
    return isChangeSet;
  }
  
  private EObject getCorrepondenceSourceJavaInterface(final CreateNonRootEObjectInList<OperationSignature> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine AddOperationSignatureEffect with input:");
    getLogger().debug("   CreateNonRootEObjectInList: " + this.change);
    
    Interface javaInterface = initializeRetrieveElementState(
    	() -> getCorrepondenceSourceJavaInterface(change), // correspondence source supplier
    	(Interface _element) -> true, // correspondence precondition checker
    	() -> null, // tag supplier
    	Interface.class,
    	false, true, false);
    if (isAborted()) {
    	return;
    }
    InterfaceMethod interfaceMethod = MembersFactoryImpl.eINSTANCE.createInterfaceMethod();
    initializeCreateElementState(interfaceMethod);
    
    addCorrespondenceBetween(getElement0(change, javaInterface, interfaceMethod), getElement1(change, javaInterface, interfaceMethod), "");
    preProcessElements();
    new mir.routines.pcm2java.AddOperationSignatureEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, javaInterface, interfaceMethod);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final CreateNonRootEObjectInList<OperationSignature> change, final Interface javaInterface, final InterfaceMethod interfaceMethod) {
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

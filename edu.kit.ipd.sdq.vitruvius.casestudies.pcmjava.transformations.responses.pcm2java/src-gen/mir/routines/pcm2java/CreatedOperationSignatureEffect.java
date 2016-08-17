package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.InsertEReference;
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
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
public class CreatedOperationSignatureEffect extends AbstractEffectRealization {
  public CreatedOperationSignatureEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<OperationInterface, OperationSignature> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<OperationInterface, OperationSignature> change;
  
  private EObject getElement0(final InsertEReference<OperationInterface, OperationSignature> change, final Interface javaInterface, final InterfaceMethod interfaceMethod) {
    return interfaceMethod;
  }
  
  private EObject getElement1(final InsertEReference<OperationInterface, OperationSignature> change, final Interface javaInterface, final InterfaceMethod interfaceMethod) {
    OperationSignature _newValue = change.getNewValue();
    return _newValue;
  }
  
  private EObject getCorrepondenceSourceJavaInterface(final InsertEReference<OperationInterface, OperationSignature> change) {
    OperationInterface _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedOperationSignatureEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    Interface javaInterface = getCorrespondingElement(
    	getCorrepondenceSourceJavaInterface(change), // correspondence source supplier
    	Interface.class,
    	(Interface _element) -> true, // correspondence precondition checker
    	null);
    if (javaInterface == null) {
    	return;
    }
    initializeRetrieveElementState(javaInterface);
    InterfaceMethod interfaceMethod = MembersFactoryImpl.eINSTANCE.createInterfaceMethod();
    initializeCreateElementState(interfaceMethod);
    
    addCorrespondenceBetween(getElement0(change, javaInterface, interfaceMethod), getElement1(change, javaInterface, interfaceMethod), "");
    preprocessElementStates();
    new mir.routines.pcm2java.CreatedOperationSignatureEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, javaInterface, interfaceMethod);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<OperationInterface, OperationSignature> change, final Interface javaInterface, final InterfaceMethod interfaceMethod) {
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

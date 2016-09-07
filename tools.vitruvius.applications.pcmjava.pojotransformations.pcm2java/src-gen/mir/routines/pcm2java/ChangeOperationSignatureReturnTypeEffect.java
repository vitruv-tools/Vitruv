package mir.routines.pcm2java;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.InterfaceMethod;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
public class ChangeOperationSignatureReturnTypeEffect extends AbstractEffectRealization {
  public ChangeOperationSignatureReturnTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEReference<OperationSignature, DataType> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEReference<OperationSignature, DataType> change;
  
  private EObject getCorrepondenceSourceInterfaceMethod(final ReplaceSingleValuedEReference<OperationSignature, DataType> change) {
    OperationSignature _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeOperationSignatureReturnTypeEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEReference: " + this.change);
    
    InterfaceMethod interfaceMethod = getCorrespondingElement(
    	getCorrepondenceSourceInterfaceMethod(change), // correspondence source supplier
    	InterfaceMethod.class,
    	(InterfaceMethod _element) -> true, // correspondence precondition checker
    	null);
    if (interfaceMethod == null) {
    	return;
    }
    initializeRetrieveElementState(interfaceMethod);
    
    preprocessElementStates();
    new mir.routines.pcm2java.ChangeOperationSignatureReturnTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, interfaceMethod);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final ReplaceSingleValuedEReference<OperationSignature, DataType> change, final InterfaceMethod interfaceMethod) {
      OperationSignature _affectedEObject = change.getAffectedEObject();
      DataType _returnType__OperationSignature = ((OperationSignature) _affectedEObject).getReturnType__OperationSignature();
      this.effectFacade.callChangeInterfaceMethodReturnType(interfaceMethod, _returnType__OperationSignature);
    }
  }
}

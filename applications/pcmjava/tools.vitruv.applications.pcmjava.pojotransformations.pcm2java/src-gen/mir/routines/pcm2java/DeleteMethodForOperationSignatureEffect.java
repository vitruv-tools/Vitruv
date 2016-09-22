package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.InterfaceMethod;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteMethodForOperationSignatureEffect extends AbstractEffectRealization {
  public DeleteMethodForOperationSignatureEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final OperationSignature operationSignature) {
    super(responseExecutionState, calledBy);
    				this.operationSignature = operationSignature;
  }
  
  private OperationSignature operationSignature;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
  }
  
  private EObject getElement0(final OperationSignature operationSignature, final InterfaceMethod interfaceMethod) {
    return interfaceMethod;
  }
  
  private EObject getCorrepondenceSourceInterfaceMethod(final OperationSignature operationSignature) {
    return operationSignature;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteMethodForOperationSignatureEffect with input:");
    getLogger().debug("   OperationSignature: " + this.operationSignature);
    
    InterfaceMethod interfaceMethod = getCorrespondingElement(
    	getCorrepondenceSourceInterfaceMethod(operationSignature), // correspondence source supplier
    	InterfaceMethod.class,
    	(InterfaceMethod _element) -> true, // correspondence precondition checker
    	null);
    if (interfaceMethod == null) {
    	return;
    }
    initializeRetrieveElementState(interfaceMethod);
    deleteObject(getElement0(operationSignature, interfaceMethod));
    
    preprocessElementStates();
    postprocessElementStates();
  }
}

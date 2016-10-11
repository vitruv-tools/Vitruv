package mir.routines.packageMappingIntegration;

import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemovedMethodEventRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private RemovedMethodEventRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final Method method, final OperationSignature opSig) {
      return opSig;
    }
    
    public void update0Element(final Method method, final OperationSignature opSig) {
      EcoreUtil.remove(opSig);
    }
    
    public EObject getElement2(final Method method, final OperationSignature opSig) {
      return method;
    }
    
    public EObject getElement3(final Method method, final OperationSignature opSig) {
      return opSig;
    }
    
    public EObject getCorrepondenceSourceOpSig(final Method method) {
      return method;
    }
  }
  
  public RemovedMethodEventRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Method method) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.packageMappingIntegration.RemovedMethodEventRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(getExecutionState(), this);
    this.method = method;
  }
  
  private Method method;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemovedMethodEventRoutine with input:");
    getLogger().debug("   Method: " + this.method);
    
    OperationSignature opSig = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOpSig(method), // correspondence source supplier
    	OperationSignature.class,
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    if (opSig == null) {
    	return;
    }
    initializeRetrieveElementState(opSig);
    removeCorrespondenceBetween(userExecution.getElement1(method, opSig), userExecution.getElement2(method, opSig));
    
    // val updatedElement userExecution.getElement3(method, opSig);
    userExecution.update0Element(method, opSig);
    
    postprocessElementStates();
  }
}

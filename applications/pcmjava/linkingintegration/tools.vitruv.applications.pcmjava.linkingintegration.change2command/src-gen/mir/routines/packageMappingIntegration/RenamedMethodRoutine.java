package mir.routines.packageMappingIntegration;

import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenamedMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private RenamedMethodRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final Method method, final String newMethodName, final OperationSignature operationSignature) {
      return operationSignature;
    }
    
    public void update0Element(final Method method, final String newMethodName, final OperationSignature operationSignature) {
      operationSignature.setEntityName(newMethodName);
    }
    
    public EObject getCorrepondenceSourceOperationSignature(final Method method, final String newMethodName) {
      return method;
    }
  }
  
  public RenamedMethodRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Method method, final String newMethodName) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.packageMappingIntegration.RenamedMethodRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(getExecutionState(), this);
    this.method = method;this.newMethodName = newMethodName;
  }
  
  private Method method;
  
  private String newMethodName;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenamedMethodRoutine with input:");
    getLogger().debug("   Method: " + this.method);
    getLogger().debug("   String: " + this.newMethodName);
    
    OperationSignature operationSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOperationSignature(method, newMethodName), // correspondence source supplier
    	OperationSignature.class,
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    if (operationSignature == null) {
    	return;
    }
    initializeRetrieveElementState(operationSignature);
    // val updatedElement userExecution.getElement1(method, newMethodName, operationSignature);
    userExecution.update0Element(method, newMethodName, operationSignature);
    
    postprocessElementStates();
  }
}

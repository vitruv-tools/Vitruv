package mir.routines.packageMappingIntegration;

import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Method;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenamedMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenamedMethodRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
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
  
  public RenamedMethodRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Method method, final String newMethodName) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.packageMappingIntegration.RenamedMethodRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(getExecutionState(), this);
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

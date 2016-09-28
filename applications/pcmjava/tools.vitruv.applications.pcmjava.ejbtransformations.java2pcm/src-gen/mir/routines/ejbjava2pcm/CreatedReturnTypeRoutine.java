package mir.routines.ejbjava2pcm;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatedReturnTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private CreatedReturnTypeRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceOpSignature(final InterfaceMethod method, final TypeReference type) {
      return method;
    }
    
    public void callRoutine1(final InterfaceMethod method, final TypeReference type, final OperationSignature opSignature, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createPCMReturnType(type, opSignature, method);
    }
  }
  
  public CreatedReturnTypeRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InterfaceMethod method, final TypeReference type) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreatedReturnTypeRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.method = method;this.type = type;
  }
  
  private InterfaceMethod method;
  
  private TypeReference type;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedReturnTypeRoutine with input:");
    getLogger().debug("   InterfaceMethod: " + this.method);
    getLogger().debug("   TypeReference: " + this.type);
    
    OperationSignature opSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOpSignature(method, type), // correspondence source supplier
    	OperationSignature.class,
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    if (opSignature == null) {
    	return;
    }
    initializeRetrieveElementState(opSignature);
    userExecution.callRoutine1(method, type, opSignature, effectFacade);
    
    postprocessElementStates();
  }
}

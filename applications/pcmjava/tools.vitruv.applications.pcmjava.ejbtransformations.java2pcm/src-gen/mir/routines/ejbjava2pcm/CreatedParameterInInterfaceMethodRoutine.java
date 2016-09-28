package mir.routines.ejbjava2pcm;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.parameters.Parameter;
import org.palladiosimulator.pcm.repository.OperationSignature;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatedParameterInInterfaceMethodRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private CreatedParameterInInterfaceMethodRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceOpSignature(final InterfaceMethod method, final Parameter parameter) {
      return method;
    }
    
    public void callRoutine1(final InterfaceMethod method, final Parameter parameter, final OperationSignature opSignature, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createPCMParameter(parameter, opSignature);
    }
  }
  
  public CreatedParameterInInterfaceMethodRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InterfaceMethod method, final Parameter parameter) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreatedParameterInInterfaceMethodRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.method = method;this.parameter = parameter;
  }
  
  private InterfaceMethod method;
  
  private Parameter parameter;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedParameterInInterfaceMethodRoutine with input:");
    getLogger().debug("   InterfaceMethod: " + this.method);
    getLogger().debug("   Parameter: " + this.parameter);
    
    OperationSignature opSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOpSignature(method, parameter), // correspondence source supplier
    	OperationSignature.class,
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    if (opSignature == null) {
    	return;
    }
    initializeRetrieveElementState(opSignature);
    userExecution.callRoutine1(method, parameter, opSignature, effectFacade);
    
    postprocessElementStates();
  }
}

package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class DeleteParameterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private DeleteParameterRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final OperationSignature signature, final Parameter parameter, final InterfaceMethod interfaceMethod, final OrdinaryParameter javaParameter) {
      return javaParameter;
    }
    
    public EObject getCorrepondenceSourceInterfaceMethod(final OperationSignature signature, final Parameter parameter) {
      return signature;
    }
    
    public EObject getCorrepondenceSourceJavaParameter(final OperationSignature signature, final Parameter parameter, final InterfaceMethod interfaceMethod) {
      return parameter;
    }
  }
  
  public DeleteParameterRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final OperationSignature signature, final Parameter parameter) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2java.DeleteParameterRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    this.signature = signature;this.parameter = parameter;
  }
  
  private OperationSignature signature;
  
  private Parameter parameter;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeleteParameterRoutine with input:");
    getLogger().debug("   OperationSignature: " + this.signature);
    getLogger().debug("   Parameter: " + this.parameter);
    
    InterfaceMethod interfaceMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInterfaceMethod(signature, parameter), // correspondence source supplier
    	InterfaceMethod.class,
    	(InterfaceMethod _element) -> true, // correspondence precondition checker
    	null);
    if (interfaceMethod == null) {
    	return;
    }
    initializeRetrieveElementState(interfaceMethod);
    OrdinaryParameter javaParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaParameter(signature, parameter, interfaceMethod), // correspondence source supplier
    	OrdinaryParameter.class,
    	(OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null);
    if (javaParameter == null) {
    	return;
    }
    initializeRetrieveElementState(javaParameter);
    deleteObject(userExecution.getElement1(signature, parameter, interfaceMethod, javaParameter));
    
    postprocessElementStates();
  }
}

package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeParameterTypeEffect extends AbstractEffectRealization {
  public ChangeParameterTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Parameter parameter) {
    super(responseExecutionState, calledBy);
    				this.parameter = parameter;
  }
  
  private Parameter parameter;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    private void executeUserOperations(final Parameter parameter, final InterfaceMethod interfaceMethod, final OrdinaryParameter javaParameter, final org.emftext.language.java.classifiers.Class javaParameterTypeClass) {
      DataType _dataType__Parameter = parameter.getDataType__Parameter();
      final TypeReference parameterTypeReference = Pcm2JavaHelper.createTypeReference(_dataType__Parameter, javaParameterTypeClass);
      javaParameter.setTypeReference(parameterTypeReference);
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
  
  private EObject getCorrepondenceSourceInterfaceMethod(final Parameter parameter) {
    OperationSignature _operationSignature__Parameter = parameter.getOperationSignature__Parameter();
    return _operationSignature__Parameter;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeParameterTypeEffect with input:");
    getLogger().debug("   Parameter: " + this.parameter);
    
    InterfaceMethod interfaceMethod = getCorrespondingElement(
    	getCorrepondenceSourceInterfaceMethod(parameter), // correspondence source supplier
    	InterfaceMethod.class,
    	(InterfaceMethod _element) -> true, // correspondence precondition checker
    	null);
    if (interfaceMethod == null) {
    	return;
    }
    initializeRetrieveElementState(interfaceMethod);
    OrdinaryParameter javaParameter = getCorrespondingElement(
    	getCorrepondenceSourceJavaParameter(parameter), // correspondence source supplier
    	OrdinaryParameter.class,
    	(OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null);
    if (javaParameter == null) {
    	return;
    }
    initializeRetrieveElementState(javaParameter);
    org.emftext.language.java.classifiers.Class javaParameterTypeClass = getCorrespondingElement(
    	getCorrepondenceSourceJavaParameterTypeClass(parameter), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(javaParameterTypeClass);
    
    preprocessElementStates();
    new mir.routines.pcm2java.ChangeParameterTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	parameter, interfaceMethod, javaParameter, javaParameterTypeClass);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceJavaParameterTypeClass(final Parameter parameter) {
    DataType _dataType__Parameter = parameter.getDataType__Parameter();
    return _dataType__Parameter;
  }
  
  private EObject getCorrepondenceSourceJavaParameter(final Parameter parameter) {
    return parameter;
  }
}

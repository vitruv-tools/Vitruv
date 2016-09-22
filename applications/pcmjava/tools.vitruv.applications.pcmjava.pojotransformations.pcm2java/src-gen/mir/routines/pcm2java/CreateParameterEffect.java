package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.parameters.impl.ParametersFactoryImpl;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateParameterEffect extends AbstractEffectRealization {
  public CreateParameterEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Parameter parameter) {
    super(responseExecutionState, calledBy);
    				this.parameter = parameter;
  }
  
  private Parameter parameter;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    private void executeUserOperations(final Parameter parameter, final InterfaceMethod interfaceMethod, final org.emftext.language.java.classifiers.Class javaParameterTypeClass, final OrdinaryParameter javaParameter) {
      String _parameterName = parameter.getParameterName();
      javaParameter.setName(_parameterName);
      EList<org.emftext.language.java.parameters.Parameter> _parameters = interfaceMethod.getParameters();
      _parameters.add(javaParameter);
      DataType _dataType__Parameter = parameter.getDataType__Parameter();
      final TypeReference parameterTypeReference = Pcm2JavaHelper.createTypeReference(_dataType__Parameter, javaParameterTypeClass);
      javaParameter.setTypeReference(parameterTypeReference);
    }
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    @Extension
    private RoutinesFacade effectFacade;
  }
  
  private EObject getElement0(final Parameter parameter, final InterfaceMethod interfaceMethod, final org.emftext.language.java.classifiers.Class javaParameterTypeClass, final OrdinaryParameter javaParameter) {
    return javaParameter;
  }
  
  private EObject getElement1(final Parameter parameter, final InterfaceMethod interfaceMethod, final org.emftext.language.java.classifiers.Class javaParameterTypeClass, final OrdinaryParameter javaParameter) {
    return parameter;
  }
  
  private EObject getCorrepondenceSourceInterfaceMethod(final Parameter parameter) {
    OperationSignature _operationSignature__Parameter = parameter.getOperationSignature__Parameter();
    return _operationSignature__Parameter;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateParameterEffect with input:");
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
    org.emftext.language.java.classifiers.Class javaParameterTypeClass = getCorrespondingElement(
    	getCorrepondenceSourceJavaParameterTypeClass(parameter), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(javaParameterTypeClass);
    OrdinaryParameter javaParameter = ParametersFactoryImpl.eINSTANCE.createOrdinaryParameter();
    initializeCreateElementState(javaParameter);
    
    addCorrespondenceBetween(getElement0(parameter, interfaceMethod, javaParameterTypeClass, javaParameter), getElement1(parameter, interfaceMethod, javaParameterTypeClass, javaParameter), "");
    preprocessElementStates();
    new mir.routines.pcm2java.CreateParameterEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	parameter, interfaceMethod, javaParameterTypeClass, javaParameter);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceJavaParameterTypeClass(final Parameter parameter) {
    DataType _dataType__Parameter = parameter.getDataType__Parameter();
    return _dataType__Parameter;
  }
}

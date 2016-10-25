package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.parameters.OrdinaryParameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import tools.vitruv.applications.pcmjava.pojotransformations.pcm2java.Pcm2JavaHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ChangeParameterTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangeParameterTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Parameter parameter, final InterfaceMethod interfaceMethod, final OrdinaryParameter javaParameter, final org.emftext.language.java.classifiers.Class javaParameterTypeClass) {
      return parameter;
    }
    
    public void update0Element(final Parameter parameter, final InterfaceMethod interfaceMethod, final OrdinaryParameter javaParameter, final org.emftext.language.java.classifiers.Class javaParameterTypeClass) {
      DataType _dataType__Parameter = parameter.getDataType__Parameter();
      final TypeReference parameterTypeReference = Pcm2JavaHelper.createTypeReference(_dataType__Parameter, javaParameterTypeClass);
      javaParameter.setTypeReference(parameterTypeReference);
    }
    
    public EObject getCorrepondenceSourceInterfaceMethod(final Parameter parameter) {
      OperationSignature _operationSignature__Parameter = parameter.getOperationSignature__Parameter();
      return _operationSignature__Parameter;
    }
    
    public EObject getCorrepondenceSourceJavaParameterTypeClass(final Parameter parameter, final InterfaceMethod interfaceMethod, final OrdinaryParameter javaParameter) {
      DataType _dataType__Parameter = parameter.getDataType__Parameter();
      return _dataType__Parameter;
    }
    
    public EObject getCorrepondenceSourceJavaParameter(final Parameter parameter, final InterfaceMethod interfaceMethod) {
      return parameter;
    }
  }
  
  public ChangeParameterTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Parameter parameter) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcm2java.ChangeParameterTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcm2java.RoutinesFacade(getExecutionState(), this);
    this.parameter = parameter;
  }
  
  private Parameter parameter;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeParameterTypeRoutine with input:");
    getLogger().debug("   Parameter: " + this.parameter);
    
    InterfaceMethod interfaceMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceInterfaceMethod(parameter), // correspondence source supplier
    	InterfaceMethod.class,
    	(InterfaceMethod _element) -> true, // correspondence precondition checker
    	null);
    if (interfaceMethod == null) {
    	return;
    }
    initializeRetrieveElementState(interfaceMethod);
    OrdinaryParameter javaParameter = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaParameter(parameter, interfaceMethod), // correspondence source supplier
    	OrdinaryParameter.class,
    	(OrdinaryParameter _element) -> true, // correspondence precondition checker
    	null);
    if (javaParameter == null) {
    	return;
    }
    initializeRetrieveElementState(javaParameter);
    org.emftext.language.java.classifiers.Class javaParameterTypeClass = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJavaParameterTypeClass(parameter, interfaceMethod, javaParameter), // correspondence source supplier
    	org.emftext.language.java.classifiers.Class.class,
    	(org.emftext.language.java.classifiers.Class _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(javaParameterTypeClass);
    // val updatedElement userExecution.getElement1(parameter, interfaceMethod, javaParameter, javaParameterTypeClass);
    userExecution.update0Element(parameter, interfaceMethod, javaParameter, javaParameterTypeClass);
    
    postprocessElementStates();
  }
}

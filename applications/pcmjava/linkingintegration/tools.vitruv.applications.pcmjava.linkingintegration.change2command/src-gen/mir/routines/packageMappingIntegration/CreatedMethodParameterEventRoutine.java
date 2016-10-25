package mir.routines.packageMappingIntegration;

import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;
import tools.vitruv.applications.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class CreatedMethodParameterEventRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatedMethodParameterEventRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Method method, final Parameter parameter, final OperationSignature opSignature) {
      return opSignature;
    }
    
    public void update0Element(final Method method, final Parameter parameter, final OperationSignature opSignature) {
      this.userInteracting.showMessage(UserInteractionType.MODAL, ("Created new parameter for OperationSiganture" + opSignature));
      final org.palladiosimulator.pcm.repository.Parameter pcmParameter = RepositoryFactory.eINSTANCE.createParameter();
      TypeReference _typeReference = parameter.getTypeReference();
      OperationInterface _interface__OperationSignature = opSignature.getInterface__OperationSignature();
      Repository _repository__Interface = _interface__OperationSignature.getRepository__Interface();
      long _arrayDimension = parameter.getArrayDimension();
      DataType _correspondingPCMDataTypeForTypeReference = TypeReferenceCorrespondenceHelper.getCorrespondingPCMDataTypeForTypeReference(_typeReference, this.correspondenceModel, 
        this.userInteracting, _repository__Interface, _arrayDimension);
      pcmParameter.setDataType__Parameter(_correspondingPCMDataTypeForTypeReference);
      String _name = parameter.getName();
      pcmParameter.setEntityName(_name);
    }
    
    public EObject getElement2(final Method method, final Parameter parameter, final OperationSignature opSignature) {
      return parameter;
    }
    
    public EObject getElement3(final Method method, final Parameter parameter, final OperationSignature opSignature) {
      return opSignature;
    }
    
    public EObject getCorrepondenceSourceOpSignature(final Method method, final Parameter parameter) {
      return method;
    }
  }
  
  public CreatedMethodParameterEventRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Method method, final Parameter parameter) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.packageMappingIntegration.CreatedMethodParameterEventRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(getExecutionState(), this);
    this.method = method;this.parameter = parameter;
  }
  
  private Method method;
  
  private Parameter parameter;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedMethodParameterEventRoutine with input:");
    getLogger().debug("   Method: " + this.method);
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
    addCorrespondenceBetween(userExecution.getElement1(method, parameter, opSignature), userExecution.getElement2(method, parameter, opSignature), "");
    
    // val updatedElement userExecution.getElement3(method, parameter, opSignature);
    userExecution.update0Element(method, parameter, opSignature);
    
    postprocessElementStates();
  }
}

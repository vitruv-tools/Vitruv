package mir.routines.packageMappingIntegration;

import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.userinteraction.UserInteractionType;

@SuppressWarnings("all")
public class ChangedMethodTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ChangedMethodTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final Method method, final TypeReference newType, final OperationSignature opSignature) {
      return opSignature;
    }
    
    public void update0Element(final Method method, final TypeReference newType, final OperationSignature opSignature) {
      OperationInterface _interface__OperationSignature = opSignature.getInterface__OperationSignature();
      final Repository repo = _interface__OperationSignature.getRepository__Interface();
      long _arrayDimension = method.getArrayDimension();
      final DataType newReturnValue = TypeReferenceCorrespondenceHelper.getCorrespondingPCMDataTypeForTypeReference(newType, 
        this.correspondenceModel, this.userInteracting, repo, _arrayDimension);
      opSignature.setReturnType__OperationSignature(newReturnValue);
      this.userInteracting.showMessage(UserInteractionType.MODAL, ("Changed return type of opSig to " + newReturnValue));
    }
    
    public EObject getCorrepondenceSourceOpSignature(final Method method, final TypeReference newType) {
      return method;
    }
  }
  
  public ChangedMethodTypeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Method method, final TypeReference newType) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.packageMappingIntegration.ChangedMethodTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(getExecutionState(), this);
    this.method = method;this.newType = newType;
  }
  
  private Method method;
  
  private TypeReference newType;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangedMethodTypeRoutine with input:");
    getLogger().debug("   Method: " + this.method);
    getLogger().debug("   TypeReference: " + this.newType);
    
    OperationSignature opSignature = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceOpSignature(method, newType), // correspondence source supplier
    	OperationSignature.class,
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    if (opSignature == null) {
    	return;
    }
    initializeRetrieveElementState(opSignature);
    // val updatedElement userExecution.getElement1(method, newType, opSignature);
    userExecution.update0Element(method, newType, opSignature);
    
    postprocessElementStates();
  }
}

package mir.routines.ejbjava2pcm;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatePCMReturnTypeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatePCMReturnTypeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final TypeReference returnType, final OperationSignature opSignature, final Method javaMethod) {
      return opSignature;
    }
    
    public void update0Element(final TypeReference returnType, final OperationSignature opSignature, final Method javaMethod) {
      OperationInterface _interface__OperationSignature = opSignature.getInterface__OperationSignature();
      Repository _repository__Interface = _interface__OperationSignature.getRepository__Interface();
      long _arrayDimension = javaMethod.getArrayDimension();
      final DataType pcmDataType = TypeReferenceCorrespondenceHelper.getCorrespondingPCMDataTypeForTypeReference(returnType, this.correspondenceModel, this.userInteracting, _repository__Interface, _arrayDimension);
      opSignature.setReturnType__OperationSignature(pcmDataType);
    }
  }
  
  public CreatePCMReturnTypeRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final TypeReference returnType, final OperationSignature opSignature, final Method javaMethod) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreatePCMReturnTypeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.returnType = returnType;this.opSignature = opSignature;this.javaMethod = javaMethod;
  }
  
  private TypeReference returnType;
  
  private OperationSignature opSignature;
  
  private Method javaMethod;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePCMReturnTypeRoutine with input:");
    getLogger().debug("   TypeReference: " + this.returnType);
    getLogger().debug("   OperationSignature: " + this.opSignature);
    getLogger().debug("   Method: " + this.javaMethod);
    
    // val updatedElement userExecution.getElement1(returnType, opSignature, javaMethod);
    userExecution.update0Element(returnType, opSignature, javaMethod);
    
    postprocessElementStates();
  }
}

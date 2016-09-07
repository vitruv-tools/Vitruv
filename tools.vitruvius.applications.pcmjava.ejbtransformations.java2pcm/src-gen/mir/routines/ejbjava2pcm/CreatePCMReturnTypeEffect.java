package mir.routines.ejbjava2pcm;

import tools.vitruvius.applications.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
public class CreatePCMReturnTypeEffect extends AbstractEffectRealization {
  public CreatePCMReturnTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final TypeReference returnType, final OperationSignature opSignature, final Method javaMethod) {
    super(responseExecutionState, calledBy);
    				this.returnType = returnType;this.opSignature = opSignature;this.javaMethod = javaMethod;
  }
  
  private TypeReference returnType;
  
  private OperationSignature opSignature;
  
  private Method javaMethod;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePCMReturnTypeEffect with input:");
    getLogger().debug("   TypeReference: " + this.returnType);
    getLogger().debug("   OperationSignature: " + this.opSignature);
    getLogger().debug("   Method: " + this.javaMethod);
    
    
    preprocessElementStates();
    new mir.routines.ejbjava2pcm.CreatePCMReturnTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	returnType, opSignature, javaMethod);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final TypeReference returnType, final OperationSignature opSignature, final Method javaMethod) {
      OperationInterface _interface__OperationSignature = opSignature.getInterface__OperationSignature();
      Repository _repository__Interface = _interface__OperationSignature.getRepository__Interface();
      long _arrayDimension = javaMethod.getArrayDimension();
      final DataType pcmDataType = TypeReferenceCorrespondenceHelper.getCorrespondingPCMDataTypeForTypeReference(returnType, this.correspondenceModel, this.userInteracting, _repository__Interface, _arrayDimension);
      opSignature.setReturnType__OperationSignature(pcmDataType);
    }
  }
}

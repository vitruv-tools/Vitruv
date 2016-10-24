package mir.routines.ejbjava2pcm;

import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.applications.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatePCMParameterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatePCMParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getElement1(final Parameter jaMoPPParam, final OperationSignature opSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return opSignature;
    }
    
    public void update0Element(final Parameter jaMoPPParam, final OperationSignature opSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      EList<org.palladiosimulator.pcm.repository.Parameter> _parameters__OperationSignature = opSignature.getParameters__OperationSignature();
      _parameters__OperationSignature.add(pcmParameter);
    }
    
    public EObject getElement2(final Parameter jaMoPPParam, final OperationSignature opSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return pcmParameter;
    }
    
    public EObject getElement3(final Parameter jaMoPPParam, final OperationSignature opSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      return jaMoPPParam;
    }
    
    public void updatePcmParameterElement(final Parameter jaMoPPParam, final OperationSignature opSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
      String _name = jaMoPPParam.getName();
      pcmParameter.setEntityName(_name);
      String _name_1 = jaMoPPParam.getName();
      pcmParameter.setParameterName(_name_1);
      TypeReference _typeReference = jaMoPPParam.getTypeReference();
      OperationInterface _interface__OperationSignature = opSignature.getInterface__OperationSignature();
      Repository _repository__Interface = _interface__OperationSignature.getRepository__Interface();
      long _arrayDimension = jaMoPPParam.getArrayDimension();
      DataType _correspondingPCMDataTypeForTypeReference = TypeReferenceCorrespondenceHelper.getCorrespondingPCMDataTypeForTypeReference(_typeReference, 
        this.correspondenceModel, this.userInteracting, _repository__Interface, _arrayDimension);
      pcmParameter.setDataType__Parameter(_correspondingPCMDataTypeForTypeReference);
    }
  }
  
  public CreatePCMParameterRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Parameter jaMoPPParam, final OperationSignature opSignature) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.ejbjava2pcm.CreatePCMParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(getExecutionState(), this);
    this.jaMoPPParam = jaMoPPParam;this.opSignature = opSignature;
  }
  
  private Parameter jaMoPPParam;
  
  private OperationSignature opSignature;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePCMParameterRoutine with input:");
    getLogger().debug("   Parameter: " + this.jaMoPPParam);
    getLogger().debug("   OperationSignature: " + this.opSignature);
    
    org.palladiosimulator.pcm.repository.Parameter pcmParameter = RepositoryFactoryImpl.eINSTANCE.createParameter();
    initializeCreateElementState(pcmParameter);
    userExecution.updatePcmParameterElement(jaMoPPParam, opSignature, pcmParameter);
    
    // val updatedElement userExecution.getElement1(jaMoPPParam, opSignature, pcmParameter);
    userExecution.update0Element(jaMoPPParam, opSignature, pcmParameter);
    
    addCorrespondenceBetween(userExecution.getElement2(jaMoPPParam, opSignature, pcmParameter), userExecution.getElement3(jaMoPPParam, opSignature, pcmParameter), "");
    
    postprocessElementStates();
  }
}

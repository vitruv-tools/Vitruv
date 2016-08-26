package mir.routines.ejbjava2pcm;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;

@SuppressWarnings("all")
public class CreatePCMParameterEffect extends AbstractEffectRealization {
  public CreatePCMParameterEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Parameter jaMoPPParam, final OperationSignature opSignature) {
    super(responseExecutionState, calledBy);
    				this.jaMoPPParam = jaMoPPParam;this.opSignature = opSignature;
  }
  
  private Parameter jaMoPPParam;
  
  private OperationSignature opSignature;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePCMParameterEffect with input:");
    getLogger().debug("   Parameter: " + this.jaMoPPParam);
    getLogger().debug("   OperationSignature: " + this.opSignature);
    
    org.palladiosimulator.pcm.repository.Parameter pcmParameter = RepositoryFactoryImpl.eINSTANCE.createParameter();
    initializeCreateElementState(pcmParameter);
    
    preprocessElementStates();
    new mir.routines.ejbjava2pcm.CreatePCMParameterEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	jaMoPPParam, opSignature, pcmParameter);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final Parameter jaMoPPParam, final OperationSignature opSignature, final org.palladiosimulator.pcm.repository.Parameter pcmParameter) {
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
      EList<org.palladiosimulator.pcm.repository.Parameter> _parameters__OperationSignature = opSignature.getParameters__OperationSignature();
      _parameters__OperationSignature.add(pcmParameter);
      List<EObject> _singletonList = Collections.<EObject>singletonList(pcmParameter);
      List<EObject> _singletonList_1 = Collections.<EObject>singletonList(jaMoPPParam);
      this.correspondenceModel.createAndAddCorrespondence(_singletonList, _singletonList_1);
    }
  }
}

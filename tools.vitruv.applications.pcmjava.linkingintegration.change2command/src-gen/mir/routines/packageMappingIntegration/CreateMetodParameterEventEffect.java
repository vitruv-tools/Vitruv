package mir.routines.packageMappingIntegration;

import tools.vitruv.applications.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteractionType;
import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

@SuppressWarnings("all")
public class CreateMetodParameterEventEffect extends AbstractEffectRealization {
  public CreateMetodParameterEventEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<Method, Parameter> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<Method, Parameter> change;
  
  private EObject getElement0(final InsertEReference<Method, Parameter> change, final OperationSignature opSignature) {
    return opSignature;
  }
  
  private EObject getElement1(final InsertEReference<Method, Parameter> change, final OperationSignature opSignature) {
    Parameter _newValue = change.getNewValue();
    return _newValue;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateMetodParameterEventEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    OperationSignature opSignature = getCorrespondingElement(
    	getCorrepondenceSourceOpSignature(change), // correspondence source supplier
    	OperationSignature.class,
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    if (opSignature == null) {
    	return;
    }
    initializeRetrieveElementState(opSignature);
    
    addCorrespondenceBetween(getElement0(change, opSignature), getElement1(change, opSignature), "");
    preprocessElementStates();
    new mir.routines.packageMappingIntegration.CreateMetodParameterEventEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, opSignature);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceOpSignature(final InsertEReference<Method, Parameter> change) {
    Method _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.packageMappingIntegration.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final InsertEReference<Method, Parameter> change, final OperationSignature opSignature) {
      this.userInteracting.showMessage(UserInteractionType.MODAL, ("Created new parameter for OperationSiganture" + opSignature));
      final org.palladiosimulator.pcm.repository.Parameter pcmParameter = RepositoryFactory.eINSTANCE.createParameter();
      Parameter _newValue = change.getNewValue();
      TypeReference _typeReference = _newValue.getTypeReference();
      OperationInterface _interface__OperationSignature = opSignature.getInterface__OperationSignature();
      Repository _repository__Interface = _interface__OperationSignature.getRepository__Interface();
      Parameter _newValue_1 = change.getNewValue();
      long _arrayDimension = _newValue_1.getArrayDimension();
      DataType _correspondingPCMDataTypeForTypeReference = TypeReferenceCorrespondenceHelper.getCorrespondingPCMDataTypeForTypeReference(_typeReference, this.correspondenceModel, 
        this.userInteracting, _repository__Interface, _arrayDimension);
      pcmParameter.setDataType__Parameter(_correspondingPCMDataTypeForTypeReference);
      Parameter _newValue_2 = change.getNewValue();
      String _name = _newValue_2.getName();
      pcmParameter.setEntityName(_name);
    }
  }
}

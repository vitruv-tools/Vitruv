package mir.routines.packageMappingIntegration;

import tools.vitruvius.applications.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruvius.framework.userinteraction.UserInteractionType;
import java.io.IOException;
import mir.routines.packageMappingIntegration.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
public class ChangeMethodTypeEventEffect extends AbstractEffectRealization {
  public ChangeMethodTypeEventEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEReference<Method, TypeReference> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEReference<Method, TypeReference> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeMethodTypeEventEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEReference: " + this.change);
    
    OperationSignature opSignature = getCorrespondingElement(
    	getCorrepondenceSourceOpSignature(change), // correspondence source supplier
    	OperationSignature.class,
    	(OperationSignature _element) -> true, // correspondence precondition checker
    	null);
    if (opSignature == null) {
    	return;
    }
    initializeRetrieveElementState(opSignature);
    
    preprocessElementStates();
    new mir.routines.packageMappingIntegration.ChangeMethodTypeEventEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, opSignature);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceOpSignature(final ReplaceSingleValuedEReference<Method, TypeReference> change) {
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
    
    private void executeUserOperations(final ReplaceSingleValuedEReference<Method, TypeReference> change, final OperationSignature opSignature) {
      OperationInterface _interface__OperationSignature = opSignature.getInterface__OperationSignature();
      final Repository repo = _interface__OperationSignature.getRepository__Interface();
      TypeReference _newValue = change.getNewValue();
      Method _affectedEObject = change.getAffectedEObject();
      long _arrayDimension = _affectedEObject.getArrayDimension();
      final DataType newReturnValue = TypeReferenceCorrespondenceHelper.getCorrespondingPCMDataTypeForTypeReference(_newValue, 
        this.correspondenceModel, this.userInteracting, repo, _arrayDimension);
      opSignature.setReturnType__OperationSignature(newReturnValue);
      this.userInteracting.showMessage(UserInteractionType.MODAL, ("Changed return type of opSig to " + newReturnValue));
    }
  }
}

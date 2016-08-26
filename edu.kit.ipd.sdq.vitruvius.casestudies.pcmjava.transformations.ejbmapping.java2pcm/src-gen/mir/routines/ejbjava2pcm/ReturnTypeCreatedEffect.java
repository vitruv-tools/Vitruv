package mir.routines.ejbjava2pcm;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.ReplaceSingleValuedEReference;
import java.io.IOException;
import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
public class ReturnTypeCreatedEffect extends AbstractEffectRealization {
  public ReturnTypeCreatedEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEReference<InterfaceMethod, TypeReference> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEReference<InterfaceMethod, TypeReference> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ReturnTypeCreatedEffect with input:");
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
    new mir.routines.ejbjava2pcm.ReturnTypeCreatedEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, opSignature);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceOpSignature(final ReplaceSingleValuedEReference<InterfaceMethod, TypeReference> change) {
    InterfaceMethod _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final ReplaceSingleValuedEReference<InterfaceMethod, TypeReference> change, final OperationSignature opSignature) {
      TypeReference _newValue = change.getNewValue();
      InterfaceMethod _affectedEObject = change.getAffectedEObject();
      this.effectFacade.callCreatePCMReturnType(_newValue, opSignature, ((Method) _affectedEObject));
    }
  }
}

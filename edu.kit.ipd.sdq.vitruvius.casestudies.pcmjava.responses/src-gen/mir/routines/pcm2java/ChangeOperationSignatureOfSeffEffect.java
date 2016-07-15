package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;

@SuppressWarnings("all")
public class ChangeOperationSignatureOfSeffEffect extends AbstractEffectRealization {
  public ChangeOperationSignatureOfSeffEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final UpdateSingleValuedNonContainmentEReference<Signature> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private UpdateSingleValuedNonContainmentEReference<Signature> change;
  
  private EObject getElement0(final UpdateSingleValuedNonContainmentEReference<Signature> change, final ClassMethod oldClassMethod) {
    return oldClassMethod;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeOperationSignatureOfSeffEffect with input:");
    getLogger().debug("   UpdateSingleValuedNonContainmentEReference: " + this.change);
    
    ClassMethod oldClassMethod = getCorrespondingElement(
    	getCorrepondenceSourceOldClassMethod(change), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	null);
    if (oldClassMethod == null) {
    	return;
    }
    initializeRetrieveElementState(oldClassMethod);
    deleteObject(getElement0(change, oldClassMethod));
    
    preprocessElementStates();
    new mir.routines.pcm2java.ChangeOperationSignatureOfSeffEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, oldClassMethod);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceOldClassMethod(final UpdateSingleValuedNonContainmentEReference<Signature> change) {
    EObject _newAffectedEObject = change.getNewAffectedEObject();
    return _newAffectedEObject;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final UpdateSingleValuedNonContainmentEReference<Signature> change, final ClassMethod oldClassMethod) {
      EObject _newAffectedEObject = change.getNewAffectedEObject();
      this.effectFacade.callCreateSEFF(((ResourceDemandingSEFF) _newAffectedEObject));
    }
  }
}

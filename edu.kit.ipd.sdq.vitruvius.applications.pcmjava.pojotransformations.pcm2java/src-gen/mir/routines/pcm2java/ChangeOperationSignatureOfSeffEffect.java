package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.ClassMethod;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;

@SuppressWarnings("all")
public class ChangeOperationSignatureOfSeffEffect extends AbstractEffectRealization {
  public ChangeOperationSignatureOfSeffEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEReference<ResourceDemandingSEFF, Signature> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEReference<ResourceDemandingSEFF, Signature> change;
  
  private EObject getElement0(final ReplaceSingleValuedEReference<ResourceDemandingSEFF, Signature> change, final ClassMethod oldClassMethod) {
    return oldClassMethod;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeOperationSignatureOfSeffEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEReference: " + this.change);
    
    ClassMethod oldClassMethod = getCorrespondingElement(
    	getCorrepondenceSourceOldClassMethod(change), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	null);
    initializeRetrieveElementState(oldClassMethod);
    deleteObject(getElement0(change, oldClassMethod));
    
    preprocessElementStates();
    new mir.routines.pcm2java.ChangeOperationSignatureOfSeffEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, oldClassMethod);
    postprocessElementStates();
  }
  
  private EObject getCorrepondenceSourceOldClassMethod(final ReplaceSingleValuedEReference<ResourceDemandingSEFF, Signature> change) {
    ResourceDemandingSEFF _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final ReplaceSingleValuedEReference<ResourceDemandingSEFF, Signature> change, final ClassMethod oldClassMethod) {
      ResourceDemandingSEFF _affectedEObject = change.getAffectedEObject();
      this.effectFacade.callCreateSEFF(((ResourceDemandingSEFF) _affectedEObject));
    }
  }
}

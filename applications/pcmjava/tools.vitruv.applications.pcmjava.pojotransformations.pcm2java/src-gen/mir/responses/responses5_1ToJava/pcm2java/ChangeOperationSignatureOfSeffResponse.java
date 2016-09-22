package mir.responses.responses5_1ToJava.pcm2java;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class ChangeOperationSignatureOfSeffResponse extends AbstractResponseRealization {
  public ChangeOperationSignatureOfSeffResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final ReplaceSingleValuedEReference<ResourceDemandingSEFF, Signature> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof ResourceDemandingSEFF)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("describedService__SEFF")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEReference<?, ?>)) {
    	return false;
    }
    ReplaceSingleValuedEReference typedChange = (ReplaceSingleValuedEReference)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    ReplaceSingleValuedEReference<ResourceDemandingSEFF, Signature> typedChange = (ReplaceSingleValuedEReference<ResourceDemandingSEFF, Signature>)change;
    mir.routines.pcm2java.ChangeOperationSignatureOfSeffEffect effect = new mir.routines.pcm2java.ChangeOperationSignatureOfSeffEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

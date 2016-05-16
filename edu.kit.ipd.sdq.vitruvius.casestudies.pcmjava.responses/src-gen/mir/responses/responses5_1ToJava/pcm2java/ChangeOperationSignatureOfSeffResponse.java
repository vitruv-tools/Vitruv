package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.Signature;
import org.palladiosimulator.pcm.seff.ResourceDemandingSEFF;

@SuppressWarnings("all")
class ChangeOperationSignatureOfSeffResponse extends AbstractResponseRealization {
  public ChangeOperationSignatureOfSeffResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return UpdateSingleValuedNonContainmentEReference.class;
  }
  
  private boolean checkChangeProperties(final UpdateSingleValuedNonContainmentEReference<Signature> change) {
    EObject changedElement = change.getOldAffectedEObject();
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
    if (!(change instanceof UpdateSingleValuedNonContainmentEReference<?>)) {
    	return false;
    }
    UpdateSingleValuedNonContainmentEReference typedChange = (UpdateSingleValuedNonContainmentEReference)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    UpdateSingleValuedNonContainmentEReference<Signature> typedChange = (UpdateSingleValuedNonContainmentEReference<Signature>)change;
    final org.palladiosimulator.pcm.repository.Signature oldValue = typedChange.getOldValue();
    if (oldValue != null) {
    	typedChange.setOldValue(new mir.responses.mocks.org.palladiosimulator.pcm.repository.SignatureContainerMock(oldValue, typedChange.getOldAffectedEObject()));
    }
    mir.routines.pcm2java.ChangeOperationSignatureOfSeffEffect effect = new mir.routines.pcm2java.ChangeOperationSignatureOfSeffEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
}

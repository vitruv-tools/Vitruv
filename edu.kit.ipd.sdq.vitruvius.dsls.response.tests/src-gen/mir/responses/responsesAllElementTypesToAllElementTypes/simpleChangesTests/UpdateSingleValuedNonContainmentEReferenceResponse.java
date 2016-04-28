package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class UpdateSingleValuedNonContainmentEReferenceResponse extends AbstractResponseRealization {
  public UpdateSingleValuedNonContainmentEReferenceResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getTrigger() {
    return UpdateSingleValuedNonContainmentEReference.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!checkChangeType(change)) {
    	return false;
    }
    if (!checkChangedObject(change)) {
    	return false;
    }
    UpdateSingleValuedNonContainmentEReference typedChange = (UpdateSingleValuedNonContainmentEReference)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  private boolean checkChangeType(final EChange change) {
    return change instanceof UpdateSingleValuedNonContainmentEReference<?>;
  }
  
  public void executeResponse(final EChange change) {
    UpdateSingleValuedNonContainmentEReference<NonRoot> typedChange = (UpdateSingleValuedNonContainmentEReference<NonRoot>)change;
    final allElementTypes.NonRoot oldValue = typedChange.getOldValue();
    if (oldValue != null) {
    	typedChange.setOldValue(new mir.responses.mocks.allElementTypes.NonRootContainerMock(oldValue, typedChange.getOldAffectedEObject()));
    }
    mir.routines.simpleChangesTests.UpdateSingleValuedNonContainmentEReferenceEffect effect = new mir.routines.simpleChangesTests.UpdateSingleValuedNonContainmentEReferenceEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
  
  private boolean checkChangedObject(final EChange change) {
    UpdateSingleValuedNonContainmentEReference<?> typedChange = (UpdateSingleValuedNonContainmentEReference<?>)change;
    EObject changedElement = typedChange.getOldAffectedEObject();
    if (!typedChange.getAffectedFeature().getName().equals("singleValuedNonContainmentEReference")) {
    	return false;
    }
    return changedElement instanceof Root;
  }
}

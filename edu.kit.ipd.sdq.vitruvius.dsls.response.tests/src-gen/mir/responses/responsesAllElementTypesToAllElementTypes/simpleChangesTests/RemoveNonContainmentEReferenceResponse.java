package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRoot;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.RemoveNonContainmentEReference;

@SuppressWarnings("all")
class RemoveNonContainmentEReferenceResponse extends AbstractResponseRealization {
  public RemoveNonContainmentEReferenceResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveNonContainmentEReference.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveNonContainmentEReference<?>)) {
    	return false;
    }
    RemoveNonContainmentEReference typedChange = (RemoveNonContainmentEReference)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    RemoveNonContainmentEReference<NonRoot> typedChange = (RemoveNonContainmentEReference<NonRoot>)change;
    final allElementTypes.NonRoot oldValue = typedChange.getOldValue();
    if (oldValue != null) {
    	typedChange.setOldValue(new mir.responses.mocks.allElementTypes.NonRootContainerMock(oldValue, typedChange.getOldAffectedEObject()));
    }
    mir.routines.simpleChangesTests.RemoveNonContainmentEReferenceEffect effect = new mir.routines.simpleChangesTests.RemoveNonContainmentEReferenceEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
}

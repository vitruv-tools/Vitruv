package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRoot;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.InsertNonContainmentEReference;

@SuppressWarnings("all")
class InsertNonContainmentEReferenceResponse extends AbstractResponseRealization {
  public InsertNonContainmentEReferenceResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertNonContainmentEReference.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertNonContainmentEReference<?>)) {
    	return false;
    }
    InsertNonContainmentEReference typedChange = (InsertNonContainmentEReference)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    InsertNonContainmentEReference<NonRoot> typedChange = (InsertNonContainmentEReference<NonRoot>)change;
    mir.routines.simpleChangesTests.InsertNonContainmentEReferenceEffect effect = new mir.routines.simpleChangesTests.InsertNonContainmentEReferenceEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
}

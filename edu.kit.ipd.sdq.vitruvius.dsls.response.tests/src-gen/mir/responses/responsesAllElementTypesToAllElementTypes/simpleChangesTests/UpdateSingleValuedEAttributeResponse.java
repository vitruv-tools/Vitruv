package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;

@SuppressWarnings("all")
class UpdateSingleValuedEAttributeResponse extends AbstractResponseRealization {
  public UpdateSingleValuedEAttributeResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return UpdateSingleValuedEAttribute.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof UpdateSingleValuedEAttribute<?>)) {
    	return false;
    }
    UpdateSingleValuedEAttribute typedChange = (UpdateSingleValuedEAttribute)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    UpdateSingleValuedEAttribute<Integer> typedChange = (UpdateSingleValuedEAttribute<Integer>)change;
    mir.routines.simpleChangesTests.UpdateSingleValuedEAttributeEffect effect = new mir.routines.simpleChangesTests.UpdateSingleValuedEAttributeEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
}

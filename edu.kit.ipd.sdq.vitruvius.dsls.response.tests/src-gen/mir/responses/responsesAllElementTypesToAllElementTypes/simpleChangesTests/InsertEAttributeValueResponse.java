package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.InsertEAttributeValue;

@SuppressWarnings("all")
class InsertEAttributeValueResponse extends AbstractResponseRealization {
  public InsertEAttributeValueResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEAttributeValue.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertEAttributeValue<?>)) {
    	return false;
    }
    InsertEAttributeValue typedChange = (InsertEAttributeValue)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    InsertEAttributeValue<Integer> typedChange = (InsertEAttributeValue<Integer>)change;
    mir.routines.simpleChangesTests.InsertEAttributeValueEffect effect = new mir.routines.simpleChangesTests.InsertEAttributeValueEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
}

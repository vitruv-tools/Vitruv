package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRoot;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle;

@SuppressWarnings("all")
class CreateNonRootEObjectSingleResponseResponse extends AbstractResponseRealization {
  public CreateNonRootEObjectSingleResponseResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateNonRootEObjectSingle.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateNonRootEObjectSingle<?>)) {
    	return false;
    }
    CreateNonRootEObjectSingle typedChange = (CreateNonRootEObjectSingle)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    CreateNonRootEObjectSingle<NonRoot> typedChange = (CreateNonRootEObjectSingle<NonRoot>)change;
    mir.routines.simpleChangesTests.CreateNonRootEObjectSingleResponseEffect effect = new mir.routines.simpleChangesTests.CreateNonRootEObjectSingleResponseEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
}

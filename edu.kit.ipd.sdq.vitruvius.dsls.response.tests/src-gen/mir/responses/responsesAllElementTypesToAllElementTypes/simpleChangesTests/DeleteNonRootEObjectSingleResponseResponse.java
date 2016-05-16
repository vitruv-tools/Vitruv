package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRoot;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectSingle;

@SuppressWarnings("all")
class DeleteNonRootEObjectSingleResponseResponse extends AbstractResponseRealization {
  public DeleteNonRootEObjectSingleResponseResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return DeleteNonRootEObjectSingle.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof DeleteNonRootEObjectSingle<?>)) {
    	return false;
    }
    DeleteNonRootEObjectSingle typedChange = (DeleteNonRootEObjectSingle)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    DeleteNonRootEObjectSingle<NonRoot> typedChange = (DeleteNonRootEObjectSingle<NonRoot>)change;
    final allElementTypes.NonRoot oldValue = typedChange.getOldValue();
    if (oldValue != null) {
    	typedChange.setOldValue(new mir.responses.mocks.allElementTypes.NonRootContainerMock(oldValue, typedChange.getOldAffectedEObject()));
    }
    mir.routines.simpleChangesTests.DeleteNonRootEObjectSingleResponseEffect effect = new mir.routines.simpleChangesTests.DeleteNonRootEObjectSingleResponseEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
}

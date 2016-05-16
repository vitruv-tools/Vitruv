package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRoot;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;

@SuppressWarnings("all")
class HelperResponseForNonRootObjectContainerContentsInitializationResponse extends AbstractResponseRealization {
  public HelperResponseForNonRootObjectContainerContentsInitializationResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateNonRootEObjectInList.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateNonRootEObjectInList<?>)) {
    	return false;
    }
    CreateNonRootEObjectInList typedChange = (CreateNonRootEObjectInList)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    CreateNonRootEObjectInList<NonRoot> typedChange = (CreateNonRootEObjectInList<NonRoot>)change;
    mir.routines.simpleChangesTests.HelperResponseForNonRootObjectContainerContentsInitializationEffect effect = new mir.routines.simpleChangesTests.HelperResponseForNonRootObjectContainerContentsInitializationEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
}

package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.Root;
import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class HelperResponseForDeleteSecondTestModelResponse extends AbstractResponseRealization {
  public HelperResponseForDeleteSecondTestModelResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final DeleteRootEObject<Root> change) {
    Root _oldValue = change.getOldValue();
    String _id = _oldValue.getId();
    boolean _equals = Objects.equal(_id, "EachTestModelSource");
    return _equals;
  }
  
  public static Class<? extends EChange> getTrigger() {
    return DeleteRootEObject.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!checkChangeType(change)) {
    	return false;
    }
    if (!checkChangedObject(change)) {
    	return false;
    }
    DeleteRootEObject typedChange = (DeleteRootEObject)change;
    if (!checkTriggerPrecondition(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  private boolean checkChangeType(final EChange change) {
    return change instanceof DeleteRootEObject<?>;
  }
  
  public void executeResponse(final EChange change) {
    DeleteRootEObject<Root> typedChange = (DeleteRootEObject<Root>)change;
    mir.routines.simpleChangesTests.HelperResponseForDeleteSecondTestModelEffect effect = new mir.routines.simpleChangesTests.HelperResponseForDeleteSecondTestModelEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
  
  private boolean checkChangedObject(final EChange change) {
    DeleteRootEObject<?> typedChange = (DeleteRootEObject<?>)change;
    EObject changedElement = typedChange.getOldValue();
    return changedElement instanceof Root;
  }
}

package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.Root;
import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class HelperResponseForCreateSecondTestModelResponse extends AbstractResponseRealization {
  public HelperResponseForCreateSecondTestModelResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final CreateRootEObject<Root> change) {
    Root _newValue = change.getNewValue();
    String _id = _newValue.getId();
    boolean _equals = Objects.equal(_id, "EachTestModelSource");
    return _equals;
  }
  
  public static Class<? extends EChange> getTrigger() {
    return CreateRootEObject.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!checkChangeType(change)) {
    	return false;
    }
    if (!checkChangedObject(change)) {
    	return false;
    }
    CreateRootEObject typedChange = (CreateRootEObject)change;
    if (!checkTriggerPrecondition(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  private boolean checkChangeType(final EChange change) {
    return change instanceof CreateRootEObject<?>;
  }
  
  public void executeResponse(final EChange change) {
    CreateRootEObject<Root> typedChange = (CreateRootEObject<Root>)change;
    mir.effects.simpleChangesTests.HelperResponseForCreateSecondTestModelEffect effect = new mir.effects.simpleChangesTests.HelperResponseForCreateSecondTestModelEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
  
  private boolean checkChangedObject(final EChange change) {
    CreateRootEObject<?> typedChange = (CreateRootEObject<?>)change;
    EObject changedElement = typedChange.getNewValue();
    return changedElement instanceof Root;
  }
}

package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.Root;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.RemoveEAttributeValue;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class RemoveEAttributeValueResponse extends AbstractResponseRealization {
  public RemoveEAttributeValueResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getTrigger() {
    return RemoveEAttributeValue.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!checkChangeType(change)) {
    	return false;
    }
    if (!checkChangedObject(change)) {
    	return false;
    }
    RemoveEAttributeValue typedChange = (RemoveEAttributeValue)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  private boolean checkChangeType(final EChange change) {
    return change instanceof RemoveEAttributeValue<?>;
  }
  
  public void executeResponse(final EChange change) {
    RemoveEAttributeValue<Integer> typedChange = (RemoveEAttributeValue<Integer>)change;
    mir.effects.simpleChangesTests.RemoveEAttributeValueEffect effect = new mir.effects.simpleChangesTests.RemoveEAttributeValueEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
  
  private boolean checkChangedObject(final EChange change) {
    RemoveEAttributeValue<?> typedChange = (RemoveEAttributeValue<?>)change;
    EObject changedElement = typedChange.getOldAffectedEObject();
    if (!typedChange.getAffectedFeature().getName().equals("multiValuedEAttribute")) {
    	return false;
    }
    return changedElement instanceof Root;
  }
}

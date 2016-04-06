package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.Root;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.InsertEAttributeValue;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class InsertEAttributeValueResponse extends AbstractResponseRealization {
  public InsertEAttributeValueResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getTrigger() {
    return InsertEAttributeValue.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!checkChangeType(change)) {
    	return false;
    }
    if (!checkChangedObject(change)) {
    	return false;
    }
    InsertEAttributeValue typedChange = (InsertEAttributeValue)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  private boolean checkChangeType(final EChange change) {
    return change instanceof InsertEAttributeValue<?>;
  }
  
  public void executeResponse(final EChange change) {
    InsertEAttributeValue<Integer> typedChange = (InsertEAttributeValue<Integer>)change;
    mir.effects.simpleChangesTests.InsertEAttributeValueEffect effect = new mir.effects.simpleChangesTests.InsertEAttributeValueEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
  
  private boolean checkChangedObject(final EChange change) {
    InsertEAttributeValue<?> typedChange = (InsertEAttributeValue<?>)change;
    EObject changedElement = typedChange.getOldAffectedEObject();
    if (!typedChange.getAffectedFeature().getName().equals("multiValuedEAttribute")) {
    	return false;
    }
    return changedElement instanceof Root;
  }
}

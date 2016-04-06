package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.InsertNonContainmentEReference;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class InsertNonContainmentEReferenceResponse extends AbstractResponseRealization {
  public InsertNonContainmentEReferenceResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getTrigger() {
    return InsertNonContainmentEReference.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!checkChangeType(change)) {
    	return false;
    }
    if (!checkChangedObject(change)) {
    	return false;
    }
    InsertNonContainmentEReference typedChange = (InsertNonContainmentEReference)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  private boolean checkChangeType(final EChange change) {
    return change instanceof InsertNonContainmentEReference<?>;
  }
  
  public void executeResponse(final EChange change) {
    InsertNonContainmentEReference<NonRoot> typedChange = (InsertNonContainmentEReference<NonRoot>)change;
    mir.effects.simpleChangesTests.InsertNonContainmentEReferenceEffect effect = new mir.effects.simpleChangesTests.InsertNonContainmentEReferenceEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
  
  private boolean checkChangedObject(final EChange change) {
    InsertNonContainmentEReference<?> typedChange = (InsertNonContainmentEReference<?>)change;
    EObject changedElement = typedChange.getOldAffectedEObject();
    if (!typedChange.getAffectedFeature().getName().equals("multiValuedNonContainmentEReference")) {
    	return false;
    }
    return changedElement instanceof Root;
  }
}

package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class CreateNonRootEObjectSingleResponseResponse extends AbstractResponseRealization {
  public CreateNonRootEObjectSingleResponseResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getTrigger() {
    return CreateNonRootEObjectSingle.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!checkChangeType(change)) {
    	return false;
    }
    if (!checkChangedObject(change)) {
    	return false;
    }
    CreateNonRootEObjectSingle typedChange = (CreateNonRootEObjectSingle)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  private boolean checkChangeType(final EChange change) {
    return change instanceof CreateNonRootEObjectSingle<?>;
  }
  
  public void executeResponse(final EChange change) {
    CreateNonRootEObjectSingle<NonRoot> typedChange = (CreateNonRootEObjectSingle<NonRoot>)change;
    mir.effects.simpleChangesTests.CreateNonRootEObjectSingleResponseEffect effect = new mir.effects.simpleChangesTests.CreateNonRootEObjectSingleResponseEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
  
  private boolean checkChangedObject(final EChange change) {
    CreateNonRootEObjectSingle<?> typedChange = (CreateNonRootEObjectSingle<?>)change;
    EObject changedElement = typedChange.getOldAffectedEObject();
    if (!typedChange.getAffectedFeature().getName().equals("singleValuedContainmentEReference")) {
    	return false;
    }
    return changedElement instanceof Root;
  }
}

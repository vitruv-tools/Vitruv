package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectSingle;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class DeleteNonRootEObjectSingleResponseResponse extends AbstractResponseRealization {
  public DeleteNonRootEObjectSingleResponseResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getTrigger() {
    return DeleteNonRootEObjectSingle.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!checkChangeType(change)) {
    	return false;
    }
    if (!checkChangedObject(change)) {
    	return false;
    }
    DeleteNonRootEObjectSingle typedChange = (DeleteNonRootEObjectSingle)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  private boolean checkChangeType(final EChange change) {
    return change instanceof DeleteNonRootEObjectSingle<?>;
  }
  
  public void executeResponse(final EChange change) {
    DeleteNonRootEObjectSingle<NonRoot> typedChange = (DeleteNonRootEObjectSingle<NonRoot>)change;
    final allElementTypes.NonRoot oldValue = typedChange.getOldValue();
    if (oldValue != null) {
    	typedChange.setOldValue(new mir.responses.mocks.allElementTypes.NonRootContainerMock(oldValue, typedChange.getOldAffectedEObject()));
    }
    mir.effects.simpleChangesTests.DeleteNonRootEObjectSingleResponseEffect effect = new mir.effects.simpleChangesTests.DeleteNonRootEObjectSingleResponseEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
  
  private boolean checkChangedObject(final EChange change) {
    DeleteNonRootEObjectSingle<?> typedChange = (DeleteNonRootEObjectSingle<?>)change;
    EObject changedElement = typedChange.getOldAffectedEObject();
    if (!typedChange.getAffectedFeature().getName().equals("singleValuedContainmentEReference")) {
    	return false;
    }
    return changedElement instanceof Root;
  }
}

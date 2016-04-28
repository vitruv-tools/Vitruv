package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class DeleteNonRootEObjectInListResponse extends AbstractResponseRealization {
  public DeleteNonRootEObjectInListResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getTrigger() {
    return DeleteNonRootEObjectInList.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!checkChangeType(change)) {
    	return false;
    }
    if (!checkChangedObject(change)) {
    	return false;
    }
    DeleteNonRootEObjectInList typedChange = (DeleteNonRootEObjectInList)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  private boolean checkChangeType(final EChange change) {
    return change instanceof DeleteNonRootEObjectInList<?>;
  }
  
  public void executeResponse(final EChange change) {
    DeleteNonRootEObjectInList<NonRoot> typedChange = (DeleteNonRootEObjectInList<NonRoot>)change;
    final allElementTypes.NonRoot oldValue = typedChange.getOldValue();
    if (oldValue != null) {
    	typedChange.setOldValue(new mir.responses.mocks.allElementTypes.NonRootContainerMock(oldValue, typedChange.getOldAffectedEObject()));
    }
    mir.routines.simpleChangesTests.DeleteNonRootEObjectInListEffect effect = new mir.routines.simpleChangesTests.DeleteNonRootEObjectInListEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
  
  private boolean checkChangedObject(final EChange change) {
    DeleteNonRootEObjectInList<?> typedChange = (DeleteNonRootEObjectInList<?>)change;
    EObject changedElement = typedChange.getOldAffectedEObject();
    if (!typedChange.getAffectedFeature().getName().equals("multiValuedContainmentEReference")) {
    	return false;
    }
    return changedElement instanceof Root;
  }
}

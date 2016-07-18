package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class UpdateSingleValuedNonContainmentEReferenceResponse extends AbstractResponseRealization {
  public UpdateSingleValuedNonContainmentEReferenceResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return UpdateSingleValuedNonContainmentEReference.class;
  }
  
  private boolean checkChangeProperties(final UpdateSingleValuedNonContainmentEReference<NonRoot> change) {
    EObject changedElement = change.getOldAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof Root)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("singleValuedNonContainmentEReference")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof UpdateSingleValuedNonContainmentEReference<?>)) {
    	return false;
    }
    UpdateSingleValuedNonContainmentEReference typedChange = (UpdateSingleValuedNonContainmentEReference)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    UpdateSingleValuedNonContainmentEReference<NonRoot> typedChange = (UpdateSingleValuedNonContainmentEReference<NonRoot>)change;
    final allElementTypes.NonRoot oldValue = typedChange.getOldValue();
    if (oldValue != null) {
    	typedChange.setOldValue(new mir.responses.mocks.allElementTypes.NonRootContainerMock(oldValue, typedChange.getOldAffectedEObject()));
    }
    mir.routines.simpleChangesTests.UpdateSingleValuedNonContainmentEReferenceEffect effect = new mir.routines.simpleChangesTests.UpdateSingleValuedNonContainmentEReferenceEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

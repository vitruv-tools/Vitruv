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
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return DeleteNonRootEObjectSingle.class;
  }
  
  private boolean checkChangeProperties(final DeleteNonRootEObjectSingle<NonRoot> change) {
    EObject changedElement = change.getOldAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof Root)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("singleValuedContainmentEReference")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof DeleteNonRootEObjectSingle<?>)) {
    	return false;
    }
    DeleteNonRootEObjectSingle typedChange = (DeleteNonRootEObjectSingle)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    DeleteNonRootEObjectSingle<NonRoot> typedChange = (DeleteNonRootEObjectSingle<NonRoot>)change;
    final allElementTypes.NonRoot oldValue = typedChange.getOldValue();
    if (oldValue != null) {
    	typedChange.setOldValue(new mir.responses.mocks.allElementTypes.NonRootContainerMock(oldValue, typedChange.getOldAffectedEObject()));
    }
    mir.routines.simpleChangesTests.DeleteNonRootEObjectSingleResponseEffect effect = new mir.routines.simpleChangesTests.DeleteNonRootEObjectSingleResponseEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

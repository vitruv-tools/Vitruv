package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruvius.framework.userinteraction.UserInteracting;

import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class DeleteNonRootEObjectInListResponse extends AbstractResponseRealization {
  public DeleteNonRootEObjectInListResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final RemoveEReference<Root, NonRoot> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof Root)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("multiValuedContainmentEReference")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveEReference<?, ?>)) {
    	return false;
    }
    RemoveEReference typedChange = (RemoveEReference)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    RemoveEReference<Root, NonRoot> typedChange = (RemoveEReference<Root, NonRoot>)change;
    mir.routines.simpleChangesTests.DeleteNonRootEObjectInListEffect effect = new mir.routines.simpleChangesTests.DeleteNonRootEObjectInListEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

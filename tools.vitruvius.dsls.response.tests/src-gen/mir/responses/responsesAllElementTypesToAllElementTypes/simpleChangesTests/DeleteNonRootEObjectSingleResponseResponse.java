package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import com.google.common.base.Objects;
import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruvius.framework.userinteraction.UserInteracting;

import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class DeleteNonRootEObjectSingleResponseResponse extends AbstractResponseRealization {
  public DeleteNonRootEObjectSingleResponseResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final ReplaceSingleValuedEReference<Root, NonRoot> change) {
    NonRoot _newValue = change.getNewValue();
    boolean _equals = Objects.equal(_newValue, null);
    return _equals;
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final ReplaceSingleValuedEReference<Root, NonRoot> change) {
    EObject changedElement = change.getAffectedEObject();
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
    if (!(change instanceof ReplaceSingleValuedEReference<?, ?>)) {
    	return false;
    }
    ReplaceSingleValuedEReference typedChange = (ReplaceSingleValuedEReference)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    if (!checkTriggerPrecondition(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    ReplaceSingleValuedEReference<Root, NonRoot> typedChange = (ReplaceSingleValuedEReference<Root, NonRoot>)change;
    mir.routines.simpleChangesTests.DeleteNonRootEObjectSingleResponseEffect effect = new mir.routines.simpleChangesTests.DeleteNonRootEObjectSingleResponseEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRootObjectContainerHelper;
import allElementTypes.Root;
import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;

import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class HelperResponseForNonRootObjectContainerInitializationResponse extends AbstractResponseRealization {
  public HelperResponseForNonRootObjectContainerInitializationResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final ReplaceSingleValuedEReference<Root, NonRootObjectContainerHelper> change) {
    NonRootObjectContainerHelper _newValue = change.getNewValue();
    return (!Objects.equal(_newValue, null));
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final ReplaceSingleValuedEReference<Root, NonRootObjectContainerHelper> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof Root)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("nonRootObjectContainerHelper")) {
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
    ReplaceSingleValuedEReference<Root, NonRootObjectContainerHelper> typedChange = (ReplaceSingleValuedEReference<Root, NonRootObjectContainerHelper>)change;
    mir.routines.simpleChangesTests.HelperResponseForNonRootObjectContainerInitializationEffect effect = new mir.routines.simpleChangesTests.HelperResponseForNonRootObjectContainerInitializationEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

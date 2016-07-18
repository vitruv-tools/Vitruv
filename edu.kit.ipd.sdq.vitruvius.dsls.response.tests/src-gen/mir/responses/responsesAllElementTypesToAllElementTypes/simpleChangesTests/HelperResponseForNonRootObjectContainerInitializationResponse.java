package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRootObjectContainerHelper;
import allElementTypes.Root;
import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectSingle;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class HelperResponseForNonRootObjectContainerInitializationResponse extends AbstractResponseRealization {
  public HelperResponseForNonRootObjectContainerInitializationResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final CreateNonRootEObjectSingle<NonRootObjectContainerHelper> change) {
    NonRootObjectContainerHelper _newValue = change.getNewValue();
    return (!Objects.equal(_newValue, null));
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateNonRootEObjectSingle.class;
  }
  
  private boolean checkChangeProperties(final CreateNonRootEObjectSingle<NonRootObjectContainerHelper> change) {
    EObject changedElement = change.getOldAffectedEObject();
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
    if (!(change instanceof CreateNonRootEObjectSingle<?>)) {
    	return false;
    }
    CreateNonRootEObjectSingle typedChange = (CreateNonRootEObjectSingle)change;
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
    CreateNonRootEObjectSingle<NonRootObjectContainerHelper> typedChange = (CreateNonRootEObjectSingle<NonRootObjectContainerHelper>)change;
    mir.routines.simpleChangesTests.HelperResponseForNonRootObjectContainerInitializationEffect effect = new mir.routines.simpleChangesTests.HelperResponseForNonRootObjectContainerInitializationEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

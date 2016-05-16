package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.Root;
import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class CreateRootTestResponse extends AbstractResponseRealization {
  public CreateRootTestResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final CreateRootEObject<Root> change) {
    Root _newValue = change.getNewValue();
    String _id = _newValue.getId();
    boolean _equals = Objects.equal(_id, "Further_Source_Test_Model");
    return _equals;
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateRootEObject.class;
  }
  
  private boolean checkChangeProperties(final CreateRootEObject<Root> change) {
    EObject changedElement = change.getNewValue();
    // Check model element type
    if (!(changedElement instanceof Root)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateRootEObject<?>)) {
    	return false;
    }
    CreateRootEObject typedChange = (CreateRootEObject)change;
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
    CreateRootEObject<Root> typedChange = (CreateRootEObject<Root>)change;
    mir.routines.simpleChangesTests.CreateRootTestEffect effect = new mir.routines.simpleChangesTests.CreateRootTestEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
}

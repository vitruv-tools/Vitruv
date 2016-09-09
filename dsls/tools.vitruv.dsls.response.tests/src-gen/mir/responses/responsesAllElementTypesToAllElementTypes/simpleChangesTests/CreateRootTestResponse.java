package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.Root;
import com.google.common.base.Objects;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.root.InsertRootEObject;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreateRootTestResponse extends AbstractResponseRealization {
  public CreateRootTestResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final InsertRootEObject<Root> change) {
    Root _newValue = change.getNewValue();
    String _id = _newValue.getId();
    boolean _equals = Objects.equal(_id, "Further_Source_Test_Model");
    return _equals;
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertRootEObject.class;
  }
  
  private boolean checkChangeProperties(final InsertRootEObject<Root> change) {
    EObject changedElement = change.getNewValue();
    // Check model element type
    if (!(changedElement instanceof Root)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertRootEObject<?>)) {
    	return false;
    }
    InsertRootEObject typedChange = (InsertRootEObject)change;
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
    InsertRootEObject<Root> typedChange = (InsertRootEObject<Root>)change;
    mir.routines.simpleChangesTests.CreateRootTestEffect effect = new mir.routines.simpleChangesTests.CreateRootTestEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

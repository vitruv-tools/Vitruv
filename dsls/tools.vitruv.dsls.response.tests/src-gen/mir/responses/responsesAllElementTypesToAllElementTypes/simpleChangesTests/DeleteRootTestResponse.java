package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.Root;
import com.google.common.base.Objects;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.root.RemoveRootEObject;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class DeleteRootTestResponse extends AbstractResponseRealization {
  public DeleteRootTestResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final RemoveRootEObject<Root> change) {
    Root _oldValue = change.getOldValue();
    String _id = _oldValue.getId();
    boolean _equals = Objects.equal(_id, "Further_Source_Test_Model");
    return _equals;
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveRootEObject.class;
  }
  
  private boolean checkChangeProperties(final RemoveRootEObject<Root> change) {
    EObject changedElement = change.getOldValue();
    // Check model element type
    if (!(changedElement instanceof Root)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveRootEObject<?>)) {
    	return false;
    }
    RemoveRootEObject typedChange = (RemoveRootEObject)change;
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
    RemoveRootEObject<Root> typedChange = (RemoveRootEObject<Root>)change;
    mir.routines.simpleChangesTests.DeleteRootTestEffect effect = new mir.routines.simpleChangesTests.DeleteRootTestEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

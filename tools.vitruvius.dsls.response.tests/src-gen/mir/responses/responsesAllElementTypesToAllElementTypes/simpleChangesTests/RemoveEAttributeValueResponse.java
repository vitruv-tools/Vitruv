package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.Root;
import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.attribute.RemoveEAttributeValue;
import tools.vitruvius.framework.userinteraction.UserInteracting;

import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class RemoveEAttributeValueResponse extends AbstractResponseRealization {
  public RemoveEAttributeValueResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEAttributeValue.class;
  }
  
  private boolean checkChangeProperties(final RemoveEAttributeValue<Root, Integer> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof Root)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("multiValuedEAttribute")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveEAttributeValue<?, ?>)) {
    	return false;
    }
    RemoveEAttributeValue typedChange = (RemoveEAttributeValue)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    RemoveEAttributeValue<Root, Integer> typedChange = (RemoveEAttributeValue<Root, Integer>)change;
    mir.routines.simpleChangesTests.RemoveEAttributeValueEffect effect = new mir.routines.simpleChangesTests.RemoveEAttributeValueEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

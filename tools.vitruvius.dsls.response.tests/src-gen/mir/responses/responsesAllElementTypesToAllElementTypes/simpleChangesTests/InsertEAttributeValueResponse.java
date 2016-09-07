package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.Root;
import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.attribute.InsertEAttributeValue;
import tools.vitruvius.framework.userinteraction.UserInteracting;

import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class InsertEAttributeValueResponse extends AbstractResponseRealization {
  public InsertEAttributeValueResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEAttributeValue.class;
  }
  
  private boolean checkChangeProperties(final InsertEAttributeValue<Root, Integer> change) {
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
    if (!(change instanceof InsertEAttributeValue<?, ?>)) {
    	return false;
    }
    InsertEAttributeValue typedChange = (InsertEAttributeValue)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    InsertEAttributeValue<Root, Integer> typedChange = (InsertEAttributeValue<Root, Integer>)change;
    mir.routines.simpleChangesTests.InsertEAttributeValueEffect effect = new mir.routines.simpleChangesTests.InsertEAttributeValueEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

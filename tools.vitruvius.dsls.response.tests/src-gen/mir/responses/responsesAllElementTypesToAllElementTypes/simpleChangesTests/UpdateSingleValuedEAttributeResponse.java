package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.Root;
import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruvius.framework.userinteraction.UserInteracting;

import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class UpdateSingleValuedEAttributeResponse extends AbstractResponseRealization {
  public UpdateSingleValuedEAttributeResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final ReplaceSingleValuedEAttribute<Root, Integer> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof Root)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("singleValuedEAttribute")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEAttribute<?, ?>)) {
    	return false;
    }
    ReplaceSingleValuedEAttribute typedChange = (ReplaceSingleValuedEAttribute)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    ReplaceSingleValuedEAttribute<Root, Integer> typedChange = (ReplaceSingleValuedEAttribute<Root, Integer>)change;
    mir.routines.simpleChangesTests.UpdateSingleValuedEAttributeEffect effect = new mir.routines.simpleChangesTests.UpdateSingleValuedEAttributeEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

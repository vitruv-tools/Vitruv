package mir.responses.responsesAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRoot;
import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruvius.framework.userinteraction.UserInteracting;

import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class ReplaceNonRootIdResponse extends AbstractResponseRealization {
  public ReplaceNonRootIdResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final ReplaceSingleValuedEAttribute<NonRoot, String> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof NonRoot)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("id")) {
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
    ReplaceSingleValuedEAttribute<NonRoot, String> typedChange = (ReplaceSingleValuedEAttribute<NonRoot, String>)change;
    mir.routines.simpleChangesTests.ReplaceNonRootIdEffect effect = new mir.routines.simpleChangesTests.ReplaceNonRootIdEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

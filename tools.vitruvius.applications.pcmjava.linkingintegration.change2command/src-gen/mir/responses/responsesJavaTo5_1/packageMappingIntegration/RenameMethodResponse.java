package mir.responses.responsesJavaTo5_1.packageMappingIntegration;

import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruvius.framework.userinteraction.UserInteracting;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Method;

@SuppressWarnings("all")
class RenameMethodResponse extends AbstractResponseRealization {
  public RenameMethodResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final ReplaceSingleValuedEAttribute<Method, String> change) {
    return true;
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final ReplaceSingleValuedEAttribute<Method, String> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof Method)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("name")) {
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
    if (!checkTriggerPrecondition(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    ReplaceSingleValuedEAttribute<Method, String> typedChange = (ReplaceSingleValuedEAttribute<Method, String>)change;
    mir.routines.packageMappingIntegration.RenameMethodEffect effect = new mir.routines.packageMappingIntegration.RenameMethodEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

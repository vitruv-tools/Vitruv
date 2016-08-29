package mir.responses.responsesJavaTo5_1.rename;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Method;

@SuppressWarnings("all")
class RenameMethodResponse extends AbstractResponseRealization {
  public RenameMethodResponse(final UserInteracting userInteracting) {
    super(userInteracting);
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
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    ReplaceSingleValuedEAttribute<Method, String> typedChange = (ReplaceSingleValuedEAttribute<Method, String>)change;
    mir.routines.rename.RenameMethodEffect effect = new mir.routines.rename.RenameMethodEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

package mir.responses.responsesJavaTo5_1.ejbjava2pcm;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.types.TypeReference;

@SuppressWarnings("all")
class ReturnTypeCreatedResponse extends AbstractResponseRealization {
  public ReturnTypeCreatedResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final ReplaceSingleValuedEReference<InterfaceMethod, TypeReference> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof InterfaceMethod)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("typeReference")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEReference<?, ?>)) {
    	return false;
    }
    ReplaceSingleValuedEReference typedChange = (ReplaceSingleValuedEReference)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    ReplaceSingleValuedEReference<InterfaceMethod, TypeReference> typedChange = (ReplaceSingleValuedEReference<InterfaceMethod, TypeReference>)change;
    mir.routines.ejbjava2pcm.ReturnTypeCreatedEffect effect = new mir.routines.ejbjava2pcm.ReturnTypeCreatedEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

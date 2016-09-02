package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
class RenameOperationSignatureResponse extends AbstractResponseRealization {
  public RenameOperationSignatureResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final ReplaceSingleValuedEAttribute<OperationSignature, String> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof OperationSignature)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("entityName")) {
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
    ReplaceSingleValuedEAttribute<OperationSignature, String> typedChange = (ReplaceSingleValuedEAttribute<OperationSignature, String>)change;
    mir.routines.pcm2java.RenameOperationSignatureEffect effect = new mir.routines.pcm2java.RenameOperationSignatureEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

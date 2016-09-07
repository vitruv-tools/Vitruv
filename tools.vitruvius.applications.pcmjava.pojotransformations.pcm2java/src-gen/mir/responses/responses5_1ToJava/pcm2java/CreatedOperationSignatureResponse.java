package mir.responses.responses5_1ToJava.pcm2java;

import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruvius.framework.userinteraction.UserInteracting;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
class CreatedOperationSignatureResponse extends AbstractResponseRealization {
  public CreatedOperationSignatureResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<OperationInterface, OperationSignature> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof OperationInterface)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("signatures__OperationInterface")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertEReference<?, ?>)) {
    	return false;
    }
    InsertEReference typedChange = (InsertEReference)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    InsertEReference<OperationInterface, OperationSignature> typedChange = (InsertEReference<OperationInterface, OperationSignature>)change;
    mir.routines.pcm2java.CreatedOperationSignatureEffect effect = new mir.routines.pcm2java.CreatedOperationSignatureEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

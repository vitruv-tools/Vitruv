package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

@SuppressWarnings("all")
class ChangedProvidedInterfaceOfProvidedRoleResponse extends AbstractResponseRealization {
  public ChangedProvidedInterfaceOfProvidedRoleResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final ReplaceSingleValuedEReference<OperationProvidedRole, OperationInterface> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof OperationProvidedRole)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("providedInterface__OperationProvidedRole")) {
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
    ReplaceSingleValuedEReference<OperationProvidedRole, OperationInterface> typedChange = (ReplaceSingleValuedEReference<OperationProvidedRole, OperationInterface>)change;
    mir.routines.pcm2java.ChangedProvidedInterfaceOfProvidedRoleEffect effect = new mir.routines.pcm2java.ChangedProvidedInterfaceOfProvidedRoleEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

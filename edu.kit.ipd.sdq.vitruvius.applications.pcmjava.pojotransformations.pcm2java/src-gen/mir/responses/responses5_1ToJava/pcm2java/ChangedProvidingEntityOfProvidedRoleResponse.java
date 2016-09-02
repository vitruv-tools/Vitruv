package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

@SuppressWarnings("all")
class ChangedProvidingEntityOfProvidedRoleResponse extends AbstractResponseRealization {
  public ChangedProvidingEntityOfProvidedRoleResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final ReplaceSingleValuedEReference<OperationProvidedRole, InterfaceProvidingEntity> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof OperationProvidedRole)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("providingEntity_ProvidedRole")) {
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
    ReplaceSingleValuedEReference<OperationProvidedRole, InterfaceProvidingEntity> typedChange = (ReplaceSingleValuedEReference<OperationProvidedRole, InterfaceProvidingEntity>)change;
    mir.routines.pcm2java.ChangedProvidingEntityOfProvidedRoleEffect effect = new mir.routines.pcm2java.ChangedProvidingEntityOfProvidedRoleEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

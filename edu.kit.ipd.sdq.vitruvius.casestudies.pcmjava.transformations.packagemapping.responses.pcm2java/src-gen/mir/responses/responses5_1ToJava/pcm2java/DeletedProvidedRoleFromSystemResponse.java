package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.reference.RemoveEReference;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.ProvidedRole;

@SuppressWarnings("all")
class DeletedProvidedRoleFromSystemResponse extends AbstractResponseRealization {
  public DeletedProvidedRoleFromSystemResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final RemoveEReference<org.palladiosimulator.pcm.system.System, ProvidedRole> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof org.palladiosimulator.pcm.system.System)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("providedRoles_InterfaceProvidingEntity")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveEReference<?, ?>)) {
    	return false;
    }
    RemoveEReference typedChange = (RemoveEReference)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    RemoveEReference<org.palladiosimulator.pcm.system.System, ProvidedRole> typedChange = (RemoveEReference<org.palladiosimulator.pcm.system.System, ProvidedRole>)change;
    mir.routines.pcm2java.DeletedProvidedRoleFromSystemEffect effect = new mir.routines.pcm2java.DeletedProvidedRoleFromSystemEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

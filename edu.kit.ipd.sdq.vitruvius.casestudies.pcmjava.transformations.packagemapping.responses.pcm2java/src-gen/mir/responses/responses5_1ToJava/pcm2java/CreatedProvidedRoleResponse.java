package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.reference.InsertEReference;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;

@SuppressWarnings("all")
class CreatedProvidedRoleResponse extends AbstractResponseRealization {
  public CreatedProvidedRoleResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final InsertEReference<InterfaceProvidingEntity, ProvidedRole> change) {
    ProvidedRole _newValue = change.getNewValue();
    return (_newValue instanceof OperationProvidedRole);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<InterfaceProvidingEntity, ProvidedRole> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof InterfaceProvidingEntity)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("providedRoles_InterfaceProvidingEntity")) {
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
    if (!checkTriggerPrecondition(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    InsertEReference<InterfaceProvidingEntity, ProvidedRole> typedChange = (InsertEReference<InterfaceProvidingEntity, ProvidedRole>)change;
    mir.routines.pcm2java.CreatedProvidedRoleEffect effect = new mir.routines.pcm2java.CreatedProvidedRoleEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

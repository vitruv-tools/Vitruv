package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;

@SuppressWarnings("all")
class AddedProvidedRoleInterfaceProvidingEntityResponse extends AbstractResponseRealization {
  public AddedProvidedRoleInterfaceProvidingEntityResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final CreateNonRootEObjectInList<ProvidedRole> change) {
    ProvidedRole _newValue = change.getNewValue();
    return (_newValue instanceof OperationProvidedRole);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateNonRootEObjectInList.class;
  }
  
  private boolean checkChangeProperties(final CreateNonRootEObjectInList<ProvidedRole> change) {
    EObject changedElement = change.getOldAffectedEObject();
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
    if (!(change instanceof CreateNonRootEObjectInList<?>)) {
    	return false;
    }
    CreateNonRootEObjectInList typedChange = (CreateNonRootEObjectInList)change;
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
    CreateNonRootEObjectInList<ProvidedRole> typedChange = (CreateNonRootEObjectInList<ProvidedRole>)change;
    mir.routines.pcm2java.AddedProvidedRoleInterfaceProvidingEntityEffect effect = new mir.routines.pcm2java.AddedProvidedRoleInterfaceProvidingEntityEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
}

package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RequiredRole;

@SuppressWarnings("all")
class AddRequiredRoleToInterfaceRequiringEntityResponse extends AbstractResponseRealization {
  public AddRequiredRoleToInterfaceRequiringEntityResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final CreateNonRootEObjectInList<RequiredRole> change) {
    RequiredRole _newValue = change.getNewValue();
    return (_newValue instanceof OperationRequiredRole);
  }
  
  public static Class<? extends EChange> getTrigger() {
    return CreateNonRootEObjectInList.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!checkChangeType(change)) {
    	return false;
    }
    if (!checkChangedObject(change)) {
    	return false;
    }
    CreateNonRootEObjectInList typedChange = (CreateNonRootEObjectInList)change;
    if (!checkTriggerPrecondition(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  private boolean checkChangeType(final EChange change) {
    return change instanceof CreateNonRootEObjectInList<?>;
  }
  
  public void executeResponse(final EChange change) {
    CreateNonRootEObjectInList<RequiredRole> typedChange = (CreateNonRootEObjectInList<RequiredRole>)change;
    mir.routines.pcm2java.AddRequiredRoleToInterfaceRequiringEntityEffect effect = new mir.routines.pcm2java.AddRequiredRoleToInterfaceRequiringEntityEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
  
  private boolean checkChangedObject(final EChange change) {
    CreateNonRootEObjectInList<?> typedChange = (CreateNonRootEObjectInList<?>)change;
    EObject changedElement = typedChange.getOldAffectedEObject();
    if (!typedChange.getAffectedFeature().getName().equals("requiredRoles_InterfaceRequiringEntity")) {
    	return false;
    }
    return changedElement instanceof InterfaceRequiringEntity;
  }
}

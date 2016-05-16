package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RequiredRole;

@SuppressWarnings("all")
class RemoveRequiredRoleFromInterfaceRequiringEntityResponse extends AbstractResponseRealization {
  public RemoveRequiredRoleFromInterfaceRequiringEntityResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final DeleteNonRootEObjectInList<RequiredRole> change) {
    RequiredRole _oldValue = change.getOldValue();
    return (_oldValue instanceof OperationRequiredRole);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return DeleteNonRootEObjectInList.class;
  }
  
  private boolean checkChangeProperties(final DeleteNonRootEObjectInList<RequiredRole> change) {
    EObject changedElement = change.getOldAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof InterfaceRequiringEntity)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("requiredRoles_InterfaceRequiringEntity")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof DeleteNonRootEObjectInList<?>)) {
    	return false;
    }
    DeleteNonRootEObjectInList typedChange = (DeleteNonRootEObjectInList)change;
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
    DeleteNonRootEObjectInList<RequiredRole> typedChange = (DeleteNonRootEObjectInList<RequiredRole>)change;
    final org.palladiosimulator.pcm.repository.RequiredRole oldValue = typedChange.getOldValue();
    if (oldValue != null) {
    	typedChange.setOldValue(new mir.responses.mocks.org.palladiosimulator.pcm.repository.RequiredRoleContainerMock(oldValue, typedChange.getOldAffectedEObject()));
    }
    mir.routines.pcm2java.RemoveRequiredRoleFromInterfaceRequiringEntityEffect effect = new mir.routines.pcm2java.RemoveRequiredRoleFromInterfaceRequiringEntityEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
}

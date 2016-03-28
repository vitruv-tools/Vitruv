package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

@SuppressWarnings("all")
class RemovedProvidedRoleFromComponentResponse extends AbstractResponseRealization {
  public RemovedProvidedRoleFromComponentResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getTrigger() {
    return DeleteNonRootEObjectInList.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!checkChangeType(change)) {
    	return false;
    }
    if (!checkChangedObject(change)) {
    	return false;
    }
    DeleteNonRootEObjectInList typedChange = (DeleteNonRootEObjectInList)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  private boolean checkChangeType(final EChange change) {
    return change instanceof DeleteNonRootEObjectInList<?>;
  }
  
  public void executeResponse(final EChange change) {
    DeleteNonRootEObjectInList<ProvidedRole> typedChange = (DeleteNonRootEObjectInList<ProvidedRole>)change;
    final org.palladiosimulator.pcm.repository.ProvidedRole oldValue = typedChange.getOldValue();
    if (oldValue != null) {
    	typedChange.setOldValue(new mir.responses.mocks.org.palladiosimulator.pcm.repository.ProvidedRoleContainerMock(oldValue, typedChange.getOldAffectedEObject()));
    }
    mir.effects.pcm2java.RemovedProvidedRoleFromComponentEffect effect = new mir.effects.pcm2java.RemovedProvidedRoleFromComponentEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
  
  private boolean checkChangedObject(final EChange change) {
    DeleteNonRootEObjectInList<?> typedChange = (DeleteNonRootEObjectInList<?>)change;
    EObject changedElement = typedChange.getOldAffectedEObject();
    if (!typedChange.getAffectedFeature().getName().equals("providedRoles_InterfaceProvidingEntity")) {
    	return false;
    }
    return changedElement instanceof RepositoryComponent;
  }
}

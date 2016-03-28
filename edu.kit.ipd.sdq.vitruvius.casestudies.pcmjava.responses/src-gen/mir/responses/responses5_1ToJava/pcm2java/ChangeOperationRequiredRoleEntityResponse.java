package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

@SuppressWarnings("all")
class ChangeOperationRequiredRoleEntityResponse extends AbstractResponseRealization {
  public ChangeOperationRequiredRoleEntityResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getTrigger() {
    return UpdateSingleValuedNonContainmentEReference.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!checkChangeType(change)) {
    	return false;
    }
    if (!checkChangedObject(change)) {
    	return false;
    }
    UpdateSingleValuedNonContainmentEReference typedChange = (UpdateSingleValuedNonContainmentEReference)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  private boolean checkChangeType(final EChange change) {
    return change instanceof UpdateSingleValuedNonContainmentEReference<?>;
  }
  
  public void executeResponse(final EChange change) {
    UpdateSingleValuedNonContainmentEReference<InterfaceRequiringEntity> typedChange = (UpdateSingleValuedNonContainmentEReference<InterfaceRequiringEntity>)change;
    final org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity oldValue = typedChange.getOldValue();
    if (oldValue != null) {
    	typedChange.setOldValue(new mir.responses.mocks.org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntityContainerMock(oldValue, typedChange.getOldAffectedEObject()));
    }
    mir.effects.pcm2java.ChangeOperationRequiredRoleEntityEffect effect = new mir.effects.pcm2java.ChangeOperationRequiredRoleEntityEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
  
  private boolean checkChangedObject(final EChange change) {
    UpdateSingleValuedNonContainmentEReference<?> typedChange = (UpdateSingleValuedNonContainmentEReference<?>)change;
    EObject changedElement = typedChange.getOldAffectedEObject();
    if (!typedChange.getAffectedFeature().getName().equals("requiringEntity_RequiredRole")) {
    	return false;
    }
    return changedElement instanceof OperationRequiredRole;
  }
}

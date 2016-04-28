package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.OperationSignature;

@SuppressWarnings("all")
class ChangeOperationSignatureReturnTypeResponse extends AbstractResponseRealization {
  public ChangeOperationSignatureReturnTypeResponse(final UserInteracting userInteracting) {
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
    UpdateSingleValuedNonContainmentEReference<DataType> typedChange = (UpdateSingleValuedNonContainmentEReference<DataType>)change;
    final org.palladiosimulator.pcm.repository.DataType oldValue = typedChange.getOldValue();
    if (oldValue != null) {
    	typedChange.setOldValue(new mir.responses.mocks.org.palladiosimulator.pcm.repository.DataTypeContainerMock(oldValue, typedChange.getOldAffectedEObject()));
    }
    mir.routines.pcm2java.ChangeOperationSignatureReturnTypeEffect effect = new mir.routines.pcm2java.ChangeOperationSignatureReturnTypeEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
  
  private boolean checkChangedObject(final EChange change) {
    UpdateSingleValuedNonContainmentEReference<?> typedChange = (UpdateSingleValuedNonContainmentEReference<?>)change;
    EObject changedElement = typedChange.getOldAffectedEObject();
    if (!typedChange.getAffectedFeature().getName().equals("returnType__OperationSignature")) {
    	return false;
    }
    return changedElement instanceof OperationSignature;
  }
}

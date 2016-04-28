package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.DataType;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
class RemovedCollectionDataTypeResponse extends AbstractResponseRealization {
  public RemovedCollectionDataTypeResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final DeleteNonRootEObjectInList<DataType> change) {
    DataType _oldValue = change.getOldValue();
    return (_oldValue instanceof CollectionDataType);
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
    if (!checkTriggerPrecondition(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  private boolean checkChangeType(final EChange change) {
    return change instanceof DeleteNonRootEObjectInList<?>;
  }
  
  public void executeResponse(final EChange change) {
    DeleteNonRootEObjectInList<DataType> typedChange = (DeleteNonRootEObjectInList<DataType>)change;
    final org.palladiosimulator.pcm.repository.DataType oldValue = typedChange.getOldValue();
    if (oldValue != null) {
    	typedChange.setOldValue(new mir.responses.mocks.org.palladiosimulator.pcm.repository.DataTypeContainerMock(oldValue, typedChange.getOldAffectedEObject()));
    }
    mir.routines.pcm2java.RemovedCollectionDataTypeEffect effect = new mir.routines.pcm2java.RemovedCollectionDataTypeEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
  
  private boolean checkChangedObject(final EChange change) {
    DeleteNonRootEObjectInList<?> typedChange = (DeleteNonRootEObjectInList<?>)change;
    EObject changedElement = typedChange.getOldAffectedEObject();
    if (!typedChange.getAffectedFeature().getName().equals("dataTypes__Repository")) {
    	return false;
    }
    return changedElement instanceof Repository;
  }
}

package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.CollectionDataType;

@SuppressWarnings("all")
class RenamedCollectionDataTypeResponse extends AbstractResponseRealization {
  public RenamedCollectionDataTypeResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getTrigger() {
    return UpdateSingleValuedEAttribute.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!checkChangeType(change)) {
    	return false;
    }
    if (!checkChangedObject(change)) {
    	return false;
    }
    UpdateSingleValuedEAttribute typedChange = (UpdateSingleValuedEAttribute)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  private boolean checkChangeType(final EChange change) {
    return change instanceof UpdateSingleValuedEAttribute<?>;
  }
  
  public void executeResponse(final EChange change) {
    UpdateSingleValuedEAttribute<String> typedChange = (UpdateSingleValuedEAttribute<String>)change;
    mir.routines.pcm2java.RenamedCollectionDataTypeEffect effect = new mir.routines.pcm2java.RenamedCollectionDataTypeEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
  
  private boolean checkChangedObject(final EChange change) {
    UpdateSingleValuedEAttribute<?> typedChange = (UpdateSingleValuedEAttribute<?>)change;
    EObject changedElement = typedChange.getOldAffectedEObject();
    if (!typedChange.getAffectedFeature().getName().equals("entityName")) {
    	return false;
    }
    return changedElement instanceof CollectionDataType;
  }
}

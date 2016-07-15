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
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return UpdateSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final UpdateSingleValuedEAttribute<String> change) {
    EObject changedElement = change.getOldAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof CollectionDataType)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("entityName")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof UpdateSingleValuedEAttribute<?>)) {
    	return false;
    }
    UpdateSingleValuedEAttribute typedChange = (UpdateSingleValuedEAttribute)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    UpdateSingleValuedEAttribute<String> typedChange = (UpdateSingleValuedEAttribute<String>)change;
    mir.routines.pcm2java.RenamedCollectionDataTypeEffect effect = new mir.routines.pcm2java.RenamedCollectionDataTypeEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

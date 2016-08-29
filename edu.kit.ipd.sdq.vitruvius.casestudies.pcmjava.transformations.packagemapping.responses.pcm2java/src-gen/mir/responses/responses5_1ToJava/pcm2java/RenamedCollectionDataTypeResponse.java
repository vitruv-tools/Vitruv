package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.CollectionDataType;

@SuppressWarnings("all")
class RenamedCollectionDataTypeResponse extends AbstractResponseRealization {
  public RenamedCollectionDataTypeResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final ReplaceSingleValuedEAttribute<CollectionDataType, String> change) {
    EObject changedElement = change.getAffectedEObject();
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
    if (!(change instanceof ReplaceSingleValuedEAttribute<?, ?>)) {
    	return false;
    }
    ReplaceSingleValuedEAttribute typedChange = (ReplaceSingleValuedEAttribute)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    ReplaceSingleValuedEAttribute<CollectionDataType, String> typedChange = (ReplaceSingleValuedEAttribute<CollectionDataType, String>)change;
    mir.routines.pcm2java.RenamedCollectionDataTypeEffect effect = new mir.routines.pcm2java.RenamedCollectionDataTypeEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

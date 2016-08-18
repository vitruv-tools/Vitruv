package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.ReplaceSingleValuedEAttribute;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.CompositeDataType;

@SuppressWarnings("all")
class RenamedCompositeDataTypeResponse extends AbstractResponseRealization {
  public RenamedCompositeDataTypeResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final ReplaceSingleValuedEAttribute<CompositeDataType, String> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof CompositeDataType)) {
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
    ReplaceSingleValuedEAttribute<CompositeDataType, String> typedChange = (ReplaceSingleValuedEAttribute<CompositeDataType, String>)change;
    mir.routines.pcm2java.RenamedCompositeDataTypeEffect effect = new mir.routines.pcm2java.RenamedCompositeDataTypeEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

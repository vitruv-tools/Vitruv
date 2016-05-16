package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;

@SuppressWarnings("all")
class CreatedParameterResponse extends AbstractResponseRealization {
  public CreatedParameterResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateNonRootEObjectInList.class;
  }
  
  private boolean checkChangeProperties(final CreateNonRootEObjectInList<Parameter> change) {
    EObject changedElement = change.getOldAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof OperationSignature)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("parameters__OperationSignature")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateNonRootEObjectInList<?>)) {
    	return false;
    }
    CreateNonRootEObjectInList typedChange = (CreateNonRootEObjectInList)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    CreateNonRootEObjectInList<Parameter> typedChange = (CreateNonRootEObjectInList<Parameter>)change;
    mir.routines.pcm2java.CreatedParameterEffect effect = new mir.routines.pcm2java.CreatedParameterEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
}

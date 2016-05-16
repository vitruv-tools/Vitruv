package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class CreatedSystemResponse extends AbstractResponseRealization {
  public CreatedSystemResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateRootEObject.class;
  }
  
  private boolean checkChangeProperties(final CreateRootEObject<org.palladiosimulator.pcm.system.System> change) {
    EObject changedElement = change.getNewValue();
    // Check model element type
    if (!(changedElement instanceof org.palladiosimulator.pcm.system.System)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateRootEObject<?>)) {
    	return false;
    }
    CreateRootEObject typedChange = (CreateRootEObject)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    CreateRootEObject<org.palladiosimulator.pcm.system.System> typedChange = (CreateRootEObject<org.palladiosimulator.pcm.system.System>)change;
    mir.routines.pcm2java.CreatedSystemEffect effect = new mir.routines.pcm2java.CreatedSystemEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
}

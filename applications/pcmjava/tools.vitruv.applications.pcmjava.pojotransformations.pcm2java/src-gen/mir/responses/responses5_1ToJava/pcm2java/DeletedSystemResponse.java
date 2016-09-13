package mir.responses.responses5_1ToJava.pcm2java;

import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.root.RemoveRootEObject;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class DeletedSystemResponse extends AbstractResponseRealization {
  public DeletedSystemResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveRootEObject.class;
  }
  
  private boolean checkChangeProperties(final RemoveRootEObject<org.palladiosimulator.pcm.system.System> change) {
    EObject changedElement = change.getOldValue();
    // Check model element type
    if (!(changedElement instanceof org.palladiosimulator.pcm.system.System)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveRootEObject<?>)) {
    	return false;
    }
    RemoveRootEObject typedChange = (RemoveRootEObject)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    RemoveRootEObject<org.palladiosimulator.pcm.system.System> typedChange = (RemoveRootEObject<org.palladiosimulator.pcm.system.System>)change;
    mir.routines.pcm2java.DeletedSystemEffect effect = new mir.routines.pcm2java.DeletedSystemEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

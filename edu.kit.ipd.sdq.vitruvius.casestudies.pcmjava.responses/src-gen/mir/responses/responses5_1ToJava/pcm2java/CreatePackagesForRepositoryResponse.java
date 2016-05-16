package mir.responses.responses5_1ToJava.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject;
import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.Repository;

@SuppressWarnings("all")
class CreatePackagesForRepositoryResponse extends AbstractResponseRealization {
  public CreatePackagesForRepositoryResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateRootEObject.class;
  }
  
  private boolean checkChangeProperties(final CreateRootEObject<Repository> change) {
    EObject changedElement = change.getNewValue();
    // Check model element type
    if (!(changedElement instanceof Repository)) {
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
    CreateRootEObject<Repository> typedChange = (CreateRootEObject<Repository>)change;
    mir.routines.pcm2java.CreatePackagesForRepositoryEffect effect = new mir.routines.pcm2java.CreatePackagesForRepositoryEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
}

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
  
  public static Class<? extends EChange> getTrigger() {
    return CreateRootEObject.class;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!checkChangeType(change)) {
    	return false;
    }
    if (!checkChangedObject(change)) {
    	return false;
    }
    CreateRootEObject typedChange = (CreateRootEObject)change;
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  private boolean checkChangeType(final EChange change) {
    return change instanceof CreateRootEObject<?>;
  }
  
  public void executeResponse(final EChange change) {
    CreateRootEObject<Repository> typedChange = (CreateRootEObject<Repository>)change;
    mir.routines.pcm2java.CreatePackagesForRepositoryEffect effect = new mir.routines.pcm2java.CreatePackagesForRepositoryEffect(this.executionState, this);
    effect.setChange(typedChange);
    effect.applyEffect();
  }
  
  private boolean checkChangedObject(final EChange change) {
    CreateRootEObject<?> typedChange = (CreateRootEObject<?>)change;
    EObject changedElement = typedChange.getNewValue();
    return changedElement instanceof Repository;
  }
}

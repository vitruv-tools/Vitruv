package mir.responses.responsesJavaTo5_1.ejbjava2pcm;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.root.InsertRootEObject;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("all")
class CreatedFirstPackageResponse extends AbstractResponseRealization {
  public CreatedFirstPackageResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertRootEObject.class;
  }
  
  private boolean checkChangeProperties(final InsertRootEObject<org.emftext.language.java.containers.Package> change) {
    EObject changedElement = change.getNewValue();
    // Check model element type
    if (!(changedElement instanceof org.emftext.language.java.containers.Package)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertRootEObject<?>)) {
    	return false;
    }
    InsertRootEObject typedChange = (InsertRootEObject)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    InsertRootEObject<org.emftext.language.java.containers.Package> typedChange = (InsertRootEObject<org.emftext.language.java.containers.Package>)change;
    mir.routines.ejbjava2pcm.CreatedFirstPackageEffect effect = new mir.routines.ejbjava2pcm.CreatedFirstPackageEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

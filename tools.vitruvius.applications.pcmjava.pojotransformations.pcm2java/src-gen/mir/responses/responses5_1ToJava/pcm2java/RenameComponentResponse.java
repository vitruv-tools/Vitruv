package mir.responses.responses5_1ToJava.pcm2java;

import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruvius.framework.userinteraction.UserInteracting;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.RepositoryComponent;

@SuppressWarnings("all")
class RenameComponentResponse extends AbstractResponseRealization {
  public RenameComponentResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final ReplaceSingleValuedEAttribute<RepositoryComponent, String> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof RepositoryComponent)) {
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
    ReplaceSingleValuedEAttribute<RepositoryComponent, String> typedChange = (ReplaceSingleValuedEAttribute<RepositoryComponent, String>)change;
    mir.routines.pcm2java.RenameComponentEffect effect = new mir.routines.pcm2java.RenameComponentEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

package mir.responses.responses5_1ToJava.pcm2java;

import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruvius.framework.userinteraction.UserInteracting;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

@SuppressWarnings("all")
class DeletedSeffResponse extends AbstractResponseRealization {
  public DeletedSeffResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final RemoveEReference<BasicComponent, ServiceEffectSpecification> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof BasicComponent)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("serviceEffectSpecifications__BasicComponent")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveEReference<?, ?>)) {
    	return false;
    }
    RemoveEReference typedChange = (RemoveEReference)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    RemoveEReference<BasicComponent, ServiceEffectSpecification> typedChange = (RemoveEReference<BasicComponent, ServiceEffectSpecification>)change;
    mir.routines.pcm2java.DeletedSeffEffect effect = new mir.routines.pcm2java.DeletedSeffEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

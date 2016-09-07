package mir.responses.responses5_1ToJava.pcm2java;

import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruvius.framework.userinteraction.UserInteracting;

import org.eclipse.emf.ecore.EObject;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.seff.ServiceEffectSpecification;

@SuppressWarnings("all")
class CreatedSEFFResponse extends AbstractResponseRealization {
  public CreatedSEFFResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<BasicComponent, ServiceEffectSpecification> change) {
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
    if (!(change instanceof InsertEReference<?, ?>)) {
    	return false;
    }
    InsertEReference typedChange = (InsertEReference)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    InsertEReference<BasicComponent, ServiceEffectSpecification> typedChange = (InsertEReference<BasicComponent, ServiceEffectSpecification>)change;
    mir.routines.pcm2java.CreatedSEFFEffect effect = new mir.routines.pcm2java.CreatedSEFFEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

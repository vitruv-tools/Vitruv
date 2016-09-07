package mir.responses.responsesJavaTo5_1.packageMappingIntegration;

import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruvius.framework.userinteraction.UserInteracting;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.Parameter;

@SuppressWarnings("all")
class CreateMetodParameterEventResponse extends AbstractResponseRealization {
  public CreateMetodParameterEventResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<Method, Parameter> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof Method)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("parameters")) {
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
    InsertEReference<Method, Parameter> typedChange = (InsertEReference<Method, Parameter>)change;
    mir.routines.packageMappingIntegration.CreateMetodParameterEventEffect effect = new mir.routines.packageMappingIntegration.CreateMetodParameterEventEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

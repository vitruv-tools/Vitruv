package mir.responses.responsesJavaTo5_1.packageMappingIntegration;

import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruvius.framework.userinteraction.UserInteracting;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;

@SuppressWarnings("all")
class ChangeFieldModifierEventResponse extends AbstractResponseRealization {
  public ChangeFieldModifierEventResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<Field, AnnotationInstanceOrModifier> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof Field)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("annotationsAndModifiers")) {
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
    InsertEReference<Field, AnnotationInstanceOrModifier> typedChange = (InsertEReference<Field, AnnotationInstanceOrModifier>)change;
    mir.routines.packageMappingIntegration.ChangeFieldModifierEventEffect effect = new mir.routines.packageMappingIntegration.ChangeFieldModifierEventEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

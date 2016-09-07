package mir.responses.responsesJavaTo5_1.parserIntegrationResponse;

import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruvius.framework.userinteraction.UserInteracting;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;

@SuppressWarnings("all")
class RemoveFieldModifierEventParserResponse extends AbstractResponseRealization {
  public RemoveFieldModifierEventParserResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final RemoveEReference<Field, AnnotationInstanceOrModifier> change) {
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
    RemoveEReference<Field, AnnotationInstanceOrModifier> typedChange = (RemoveEReference<Field, AnnotationInstanceOrModifier>)change;
    mir.routines.parserIntegrationResponse.RemoveFieldModifierEventParserEffect effect = new mir.routines.parserIntegrationResponse.RemoveFieldModifierEventParserEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

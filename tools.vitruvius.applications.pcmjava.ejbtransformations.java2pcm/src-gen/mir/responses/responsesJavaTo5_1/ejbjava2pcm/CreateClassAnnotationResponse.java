package mir.responses.responsesJavaTo5_1.ejbjava2pcm;

import tools.vitruvius.applications.pcmjava.ejbtransformations.java2pcm.EJBAnnotationHelper;
import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruvius.framework.userinteraction.UserInteracting;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;

@SuppressWarnings("all")
class CreateClassAnnotationResponse extends AbstractResponseRealization {
  public CreateClassAnnotationResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final InsertEReference<org.emftext.language.java.classifiers.Class, AnnotationInstanceOrModifier> change) {
    org.emftext.language.java.classifiers.Class _affectedEObject = change.getAffectedEObject();
    boolean _isEJBClass = EJBAnnotationHelper.isEJBClass(_affectedEObject);
    return _isEJBClass;
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<org.emftext.language.java.classifiers.Class, AnnotationInstanceOrModifier> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof org.emftext.language.java.classifiers.Class)) {
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
    if (!checkTriggerPrecondition(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    InsertEReference<org.emftext.language.java.classifiers.Class, AnnotationInstanceOrModifier> typedChange = (InsertEReference<org.emftext.language.java.classifiers.Class, AnnotationInstanceOrModifier>)change;
    mir.routines.ejbjava2pcm.CreateClassAnnotationEffect effect = new mir.routines.ejbjava2pcm.CreateClassAnnotationEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

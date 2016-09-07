package mir.responses.responsesJavaTo5_1.ejbjava2pcm;

import tools.vitruvius.applications.pcmjava.ejbtransformations.java2pcm.EJBAnnotationHelper;
import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruvius.framework.userinteraction.UserInteracting;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.Member;

@SuppressWarnings("all")
class CreateFieldResponse extends AbstractResponseRealization {
  public CreateFieldResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final InsertEReference<org.emftext.language.java.classifiers.Class, Member> change) {
    Member _newValue = change.getNewValue();
    boolean _hasEJBAnnotation = EJBAnnotationHelper.hasEJBAnnotation(_newValue);
    return _hasEJBAnnotation;
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<org.emftext.language.java.classifiers.Class, Member> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof org.emftext.language.java.classifiers.Class)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("members")) {
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
    InsertEReference<org.emftext.language.java.classifiers.Class, Member> typedChange = (InsertEReference<org.emftext.language.java.classifiers.Class, Member>)change;
    mir.routines.ejbjava2pcm.CreateFieldEffect effect = new mir.routines.ejbjava2pcm.CreateFieldEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

package mir.responses.responsesJavaTo5_1.ejbjava2pcm;

import tools.vitruvius.applications.pcmjava.ejbtransformations.java2pcm.EJBAnnotationHelper;
import tools.vitruvius.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruvius.framework.userinteraction.UserInteracting;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;

@SuppressWarnings("all")
class CreateInterfaceAnnotationResponse extends AbstractResponseRealization {
  public CreateInterfaceAnnotationResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final InsertEReference<Interface, AnnotationInstanceOrModifier> change) {
    Interface _affectedEObject = change.getAffectedEObject();
    boolean _isEJBBuisnessInterface = EJBAnnotationHelper.isEJBBuisnessInterface(_affectedEObject);
    return _isEJBBuisnessInterface;
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<Interface, AnnotationInstanceOrModifier> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof Interface)) {
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
    InsertEReference<Interface, AnnotationInstanceOrModifier> typedChange = (InsertEReference<Interface, AnnotationInstanceOrModifier>)change;
    mir.routines.ejbjava2pcm.CreateInterfaceAnnotationEffect effect = new mir.routines.ejbjava2pcm.CreateInterfaceAnnotationEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

package mir.responses.responsesJavaTo5_1.ejbjava2pcm;

import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.ejbmapping.java2pcm.EJBAnnotationHelper;
import edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations.ejbmapping.java2pcm.EJBJava2PcmHelper;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractResponseRealization;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.InsertEReference;
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting;

import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Member;

@SuppressWarnings("all")
class CreateClassMethodInEJBClassResponse extends AbstractResponseRealization {
  public CreateClassMethodInEJBClassResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final InsertEReference<org.emftext.language.java.classifiers.Class, Member> change) {
    return (((change.getNewValue() instanceof ClassMethod) && EJBAnnotationHelper.isEJBClass(change.getAffectedEObject())) && 
      EJBJava2PcmHelper.overridesInterfaceMethod(((ClassMethod) change.getNewValue()), ((org.emftext.language.java.classifiers.Class) change.getAffectedEObject())));
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
    mir.routines.ejbjava2pcm.CreateClassMethodInEJBClassEffect effect = new mir.routines.ejbjava2pcm.CreateClassMethodInEJBClassEffect(this.executionState, this, typedChange);
    effect.applyRoutine();
  }
}

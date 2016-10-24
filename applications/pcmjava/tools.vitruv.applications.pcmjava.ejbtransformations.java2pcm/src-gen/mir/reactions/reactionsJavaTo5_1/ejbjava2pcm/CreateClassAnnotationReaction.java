package mir.reactions.reactionsJavaTo5_1.ejbjava2pcm;

import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import org.palladiosimulator.pcm.repository.Repository;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EJBAnnotationHelper;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EJBJava2PcmHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreateClassAnnotationReaction extends AbstractResponseRealization {
  public CreateClassAnnotationReaction(final UserInteracting userInteracting) {
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
    mir.routines.ejbjava2pcm.RoutinesFacade routinesFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateClassAnnotationReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaTo5_1.ejbjava2pcm.CreateClassAnnotationReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public void callRoutine1(final InsertEReference<org.emftext.language.java.classifiers.Class, AnnotationInstanceOrModifier> change, @Extension final RoutinesFacade _routinesFacade) {
      final Repository repo = EJBJava2PcmHelper.findRepository(this.correspondenceModel);
      org.emftext.language.java.classifiers.Class _affectedEObject = change.getAffectedEObject();
      _routinesFacade.createBasicComponent(repo, ((NamedElement) _affectedEObject));
    }
  }
}

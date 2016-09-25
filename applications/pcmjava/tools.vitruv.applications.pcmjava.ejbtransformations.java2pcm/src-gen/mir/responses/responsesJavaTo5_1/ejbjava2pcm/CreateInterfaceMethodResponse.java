package mir.responses.responsesJavaTo5_1.ejbjava2pcm;

import mir.routines.ejbjava2pcm.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.members.InterfaceMethod;
import org.emftext.language.java.members.Member;
import tools.vitruv.applications.pcmjava.ejbtransformations.java2pcm.EJBAnnotationHelper;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class CreateInterfaceMethodResponse extends AbstractResponseRealization {
  public CreateInterfaceMethodResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  private boolean checkTriggerPrecondition(final InsertEReference<Interface, Member> change) {
    return ((change.getNewValue() instanceof InterfaceMethod) && EJBAnnotationHelper.isEJBBuisnessInterface(change.getAffectedEObject()));
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<Interface, Member> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof Interface)) {
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
    InsertEReference<Interface, Member> typedChange = (InsertEReference<Interface, Member>)change;
    mir.routines.ejbjava2pcm.RoutinesFacade routinesFacade = new mir.routines.ejbjava2pcm.RoutinesFacade(this.executionState, this);
    mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateInterfaceMethodResponse.EffectUserExecution userExecution = new mir.responses.responsesJavaTo5_1.ejbjava2pcm.CreateInterfaceMethodResponse.EffectUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public void callRoutine1(final InsertEReference<Interface, Member> change, @Extension final RoutinesFacade _routinesFacade) {
      Interface _affectedEObject = change.getAffectedEObject();
      Member _newValue = change.getNewValue();
      _routinesFacade.createdInterfaceMethod(_affectedEObject, ((InterfaceMethod) _newValue));
    }
  }
}

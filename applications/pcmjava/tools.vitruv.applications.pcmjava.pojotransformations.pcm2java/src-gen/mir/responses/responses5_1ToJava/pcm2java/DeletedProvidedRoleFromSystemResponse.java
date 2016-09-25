package mir.responses.responses5_1ToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class DeletedProvidedRoleFromSystemResponse extends AbstractResponseRealization {
  public DeletedProvidedRoleFromSystemResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final RemoveEReference<org.palladiosimulator.pcm.system.System, ProvidedRole> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof org.palladiosimulator.pcm.system.System)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("providedRoles_InterfaceProvidingEntity")) {
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
    RemoveEReference<org.palladiosimulator.pcm.system.System, ProvidedRole> typedChange = (RemoveEReference<org.palladiosimulator.pcm.system.System, ProvidedRole>)change;
    mir.routines.pcm2java.RoutinesFacade routinesFacade = new mir.routines.pcm2java.RoutinesFacade(this.executionState, this);
    mir.responses.responses5_1ToJava.pcm2java.DeletedProvidedRoleFromSystemResponse.CallRoutinesUserExecution userExecution = new mir.responses.responses5_1ToJava.pcm2java.DeletedProvidedRoleFromSystemResponse.CallRoutinesUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public void callRoutine1(final RemoveEReference<org.palladiosimulator.pcm.system.System, ProvidedRole> change, @Extension final RoutinesFacade _routinesFacade) {
      ProvidedRole _oldValue = change.getOldValue();
      _routinesFacade.removeProvidedRole(_oldValue);
    }
  }
}

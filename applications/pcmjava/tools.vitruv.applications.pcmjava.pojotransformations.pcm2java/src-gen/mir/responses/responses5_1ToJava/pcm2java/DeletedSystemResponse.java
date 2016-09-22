package mir.responses.responses5_1ToJava.pcm2java;

import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.AbstractResponseRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.root.RemoveRootEObject;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class DeletedSystemResponse extends AbstractResponseRealization {
  public DeletedSystemResponse(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveRootEObject.class;
  }
  
  private boolean checkChangeProperties(final RemoveRootEObject<org.palladiosimulator.pcm.system.System> change) {
    EObject changedElement = change.getOldValue();
    // Check model element type
    if (!(changedElement instanceof org.palladiosimulator.pcm.system.System)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveRootEObject<?>)) {
    	return false;
    }
    RemoveRootEObject typedChange = (RemoveRootEObject)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of response " + this.getClass().getName());
    return true;
  }
  
  public void executeResponse(final EChange change) {
    RemoveRootEObject<org.palladiosimulator.pcm.system.System> typedChange = (RemoveRootEObject<org.palladiosimulator.pcm.system.System>)change;
    new mir.responses.responses5_1ToJava.pcm2java.DeletedSystemResponse.CallRoutinesUserExecution(this.executionState, this).executeUserOperations(typedChange);
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final RemoveRootEObject<org.palladiosimulator.pcm.system.System> change) {
      final org.palladiosimulator.pcm.system.System system = change.getOldValue();
      String _entityName = system.getEntityName();
      this.effectFacade.deleteJavaPackage(system, _entityName, "root_system");
      this.effectFacade.deleteJavaClassifier(system);
    }
  }
}

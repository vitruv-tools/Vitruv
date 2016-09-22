package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ReinitializeOperationRequiredRoleEffect extends AbstractEffectRealization {
  public ReinitializeOperationRequiredRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole requiredRole) {
    super(responseExecutionState, calledBy);
    				this.requiredRole = requiredRole;
  }
  
  private OperationRequiredRole requiredRole;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final OperationRequiredRole requiredRole) {
      InterfaceRequiringEntity _requiringEntity_RequiredRole = requiredRole.getRequiringEntity_RequiredRole();
      this.effectFacade.callRemoveRequiredRole(requiredRole, _requiringEntity_RequiredRole);
      this.effectFacade.callAddRequiredRole(requiredRole);
    }
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ReinitializeOperationRequiredRoleEffect with input:");
    getLogger().debug("   OperationRequiredRole: " + this.requiredRole);
    
    
    preprocessElementStates();
    new mir.routines.pcm2java.ReinitializeOperationRequiredRoleEffect.CallRoutinesUserExecution(getExecutionState(), this).executeUserOperations(
    	requiredRole);
    postprocessElementStates();
  }
}

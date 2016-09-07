package mir.routines.pcm2java;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

@SuppressWarnings("all")
public class ReinitializeOperationRequiredRoleEffect extends AbstractEffectRealization {
  public ReinitializeOperationRequiredRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final OperationRequiredRole requiredRole) {
    super(responseExecutionState, calledBy);
    				this.requiredRole = requiredRole;
  }
  
  private OperationRequiredRole requiredRole;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ReinitializeOperationRequiredRoleEffect with input:");
    getLogger().debug("   OperationRequiredRole: " + this.requiredRole);
    
    
    preprocessElementStates();
    new mir.routines.pcm2java.ReinitializeOperationRequiredRoleEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	requiredRole);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final OperationRequiredRole requiredRole) {
      InterfaceRequiringEntity _requiringEntity_RequiredRole = requiredRole.getRequiringEntity_RequiredRole();
      this.effectFacade.callRemoveRequiredRole(requiredRole, _requiringEntity_RequiredRole);
      this.effectFacade.callAddRequiredRole(requiredRole);
    }
  }
}

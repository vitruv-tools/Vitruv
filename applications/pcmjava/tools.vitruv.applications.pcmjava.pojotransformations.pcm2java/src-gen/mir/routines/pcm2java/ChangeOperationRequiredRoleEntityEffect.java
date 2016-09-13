package mir.routines.pcm2java;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

@SuppressWarnings("all")
public class ChangeOperationRequiredRoleEntityEffect extends AbstractEffectRealization {
  public ChangeOperationRequiredRoleEntityEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEReference<OperationRequiredRole, InterfaceRequiringEntity> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEReference<OperationRequiredRole, InterfaceRequiringEntity> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangeOperationRequiredRoleEntityEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEReference: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.pcm2java.ChangeOperationRequiredRoleEntityEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.pcm2java.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final ReplaceSingleValuedEReference<OperationRequiredRole, InterfaceRequiringEntity> change) {
      final OperationRequiredRole requiredRole = change.getAffectedEObject();
      InterfaceRequiringEntity _oldValue = change.getOldValue();
      this.effectFacade.callRemoveRequiredRole(requiredRole, _oldValue);
      this.effectFacade.callAddRequiredRole(requiredRole);
    }
  }
}

package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.feature.reference.ReplaceSingleValuedEReference;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

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
      OperationRequiredRole _affectedEObject = change.getAffectedEObject();
      final OperationRequiredRole requiredRole = ((OperationRequiredRole) _affectedEObject);
      InterfaceRequiringEntity _oldValue = change.getOldValue();
      this.effectFacade.callRemoveRequiredRole(requiredRole, _oldValue);
      this.effectFacade.callAddRequiredRole(requiredRole);
    }
  }
}

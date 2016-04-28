package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

@SuppressWarnings("all")
public class ReinitializeOperationRequiredRoleEffect extends AbstractEffectRealization {
  public ReinitializeOperationRequiredRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
    super(responseExecutionState, calledBy);
  }
  
  private OperationRequiredRole requiredRole;
  
  private boolean isRequiredRoleSet;
  
  public void setRequiredRole(final OperationRequiredRole requiredRole) {
    this.requiredRole = requiredRole;
    this.isRequiredRoleSet = true;
  }
  
  public boolean allParametersSet() {
    return isRequiredRoleSet;
  }
  
  protected void executeEffect() throws IOException {
    getLogger().debug("Called routine ReinitializeOperationRequiredRoleEffect with input:");
    getLogger().debug("   OperationRequiredRole: " + this.requiredRole);
    
    if (isAborted()) {
    	return;
    }
    
    preProcessElements();
    new mir.routines.pcm2java.ReinitializeOperationRequiredRoleEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	requiredRole);
    postProcessElements();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final OperationRequiredRole requiredRole) {
      InterfaceRequiringEntity _requiringEntity_RequiredRole = requiredRole.getRequiringEntity_RequiredRole();
      this.effectFacade.callRemoveRequiredRole(requiredRole, _requiringEntity_RequiredRole);
      this.effectFacade.callAddRequiredRole(requiredRole);
    }
  }
}

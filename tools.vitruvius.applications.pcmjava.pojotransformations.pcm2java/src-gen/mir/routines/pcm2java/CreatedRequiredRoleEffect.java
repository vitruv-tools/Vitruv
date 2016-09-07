package mir.routines.pcm2java;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.reference.InsertEReference;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import org.palladiosimulator.pcm.repository.RequiredRole;

@SuppressWarnings("all")
public class CreatedRequiredRoleEffect extends AbstractEffectRealization {
  public CreatedRequiredRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<InterfaceRequiringEntity, RequiredRole> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<InterfaceRequiringEntity, RequiredRole> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedRequiredRoleEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.pcm2java.CreatedRequiredRoleEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final InsertEReference<InterfaceRequiringEntity, RequiredRole> change) {
      RequiredRole _newValue = change.getNewValue();
      this.effectFacade.callAddRequiredRole(((OperationRequiredRole) _newValue));
    }
  }
}

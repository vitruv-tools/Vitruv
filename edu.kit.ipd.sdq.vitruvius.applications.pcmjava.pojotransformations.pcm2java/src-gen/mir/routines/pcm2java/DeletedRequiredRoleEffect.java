package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.RemoveEReference;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceRequiringEntity;
import org.palladiosimulator.pcm.repository.RepositoryComponent;
import org.palladiosimulator.pcm.repository.RequiredRole;

@SuppressWarnings("all")
public class DeletedRequiredRoleEffect extends AbstractEffectRealization {
  public DeletedRequiredRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RemoveEReference<InterfaceRequiringEntity, RequiredRole> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private RemoveEReference<InterfaceRequiringEntity, RequiredRole> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeletedRequiredRoleEffect with input:");
    getLogger().debug("   RemoveEReference: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.pcm2java.DeletedRequiredRoleEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final RemoveEReference<InterfaceRequiringEntity, RequiredRole> change) {
      RequiredRole _oldValue = change.getOldValue();
      InterfaceRequiringEntity _affectedEObject = change.getAffectedEObject();
      this.effectFacade.callRemoveRequiredRole(_oldValue, ((RepositoryComponent) _affectedEObject));
    }
  }
}

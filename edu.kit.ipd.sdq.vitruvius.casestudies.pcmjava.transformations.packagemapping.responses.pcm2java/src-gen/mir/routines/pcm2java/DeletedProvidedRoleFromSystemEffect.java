package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.reference.RemoveEReference;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.ProvidedRole;

@SuppressWarnings("all")
public class DeletedProvidedRoleFromSystemEffect extends AbstractEffectRealization {
  public DeletedProvidedRoleFromSystemEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final RemoveEReference<org.palladiosimulator.pcm.system.System, ProvidedRole> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private RemoveEReference<org.palladiosimulator.pcm.system.System, ProvidedRole> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine DeletedProvidedRoleFromSystemEffect with input:");
    getLogger().debug("   RemoveEReference: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.pcm2java.DeletedProvidedRoleFromSystemEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final RemoveEReference<org.palladiosimulator.pcm.system.System, ProvidedRole> change) {
      ProvidedRole _oldValue = change.getOldValue();
      this.effectFacade.callRemoveProvidedRole(_oldValue);
    }
  }
}

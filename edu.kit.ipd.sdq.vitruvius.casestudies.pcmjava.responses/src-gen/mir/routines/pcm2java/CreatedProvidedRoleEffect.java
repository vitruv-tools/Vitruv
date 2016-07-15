package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;

@SuppressWarnings("all")
public class CreatedProvidedRoleEffect extends AbstractEffectRealization {
  public CreatedProvidedRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final CreateNonRootEObjectInList<ProvidedRole> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private CreateNonRootEObjectInList<ProvidedRole> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedProvidedRoleEffect with input:");
    getLogger().debug("   CreateNonRootEObjectInList: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.pcm2java.CreatedProvidedRoleEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final CreateNonRootEObjectInList<ProvidedRole> change) {
      ProvidedRole _newValue = change.getNewValue();
      this.effectFacade.callAddProvidedRole(((OperationProvidedRole) _newValue));
    }
  }
}

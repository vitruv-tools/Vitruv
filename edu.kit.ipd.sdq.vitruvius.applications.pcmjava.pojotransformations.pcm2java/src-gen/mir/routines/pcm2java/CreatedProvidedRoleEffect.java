package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.feature.reference.InsertEReference;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.core.entity.InterfaceProvidingEntity;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;
import org.palladiosimulator.pcm.repository.ProvidedRole;

@SuppressWarnings("all")
public class CreatedProvidedRoleEffect extends AbstractEffectRealization {
  public CreatedProvidedRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final InsertEReference<InterfaceProvidingEntity, ProvidedRole> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private InsertEReference<InterfaceProvidingEntity, ProvidedRole> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatedProvidedRoleEffect with input:");
    getLogger().debug("   InsertEReference: " + this.change);
    
    
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
    
    private void executeUserOperations(final InsertEReference<InterfaceProvidingEntity, ProvidedRole> change) {
      ProvidedRole _newValue = change.getNewValue();
      this.effectFacade.callAddProvidedRole(((OperationProvidedRole) _newValue));
    }
  }
}

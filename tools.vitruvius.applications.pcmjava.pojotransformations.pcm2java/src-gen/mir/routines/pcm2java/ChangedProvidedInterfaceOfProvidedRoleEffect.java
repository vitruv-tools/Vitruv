package mir.routines.pcm2java;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationProvidedRole;

@SuppressWarnings("all")
public class ChangedProvidedInterfaceOfProvidedRoleEffect extends AbstractEffectRealization {
  public ChangedProvidedInterfaceOfProvidedRoleEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEReference<OperationProvidedRole, OperationInterface> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEReference<OperationProvidedRole, OperationInterface> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ChangedProvidedInterfaceOfProvidedRoleEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEReference: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.pcm2java.ChangedProvidedInterfaceOfProvidedRoleEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final ReplaceSingleValuedEReference<OperationProvidedRole, OperationInterface> change) {
      OperationProvidedRole _affectedEObject = change.getAffectedEObject();
      final OperationProvidedRole operationProvidedRole = ((OperationProvidedRole) _affectedEObject);
      this.effectFacade.callRemoveProvidedRole(operationProvidedRole);
      this.effectFacade.callAddProvidedRole(operationProvidedRole);
    }
  }
}

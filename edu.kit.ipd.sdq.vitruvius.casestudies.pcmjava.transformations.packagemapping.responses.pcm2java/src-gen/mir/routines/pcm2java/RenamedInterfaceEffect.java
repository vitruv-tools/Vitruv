package mir.routines.pcm2java;

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.AbstractEffectRealization;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.ResponseExecutionState;
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.ReplaceSingleValuedEAttribute;
import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.Interface;
import org.palladiosimulator.pcm.repository.OperationInterface;

@SuppressWarnings("all")
public class RenamedInterfaceEffect extends AbstractEffectRealization {
  public RenamedInterfaceEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEAttribute<Interface, String> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEAttribute<Interface, String> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenamedInterfaceEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEAttribute: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.pcm2java.RenamedInterfaceEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final ReplaceSingleValuedEAttribute<Interface, String> change) {
      Interface _affectedEObject = change.getAffectedEObject();
      this.effectFacade.callRenameInterface(((OperationInterface) _affectedEObject));
    }
  }
}

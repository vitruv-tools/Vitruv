package mir.routines.pcm2java;

import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;

import java.io.IOException;
import mir.routines.pcm2java.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.palladiosimulator.pcm.repository.CompositeDataType;

@SuppressWarnings("all")
public class RenamedCompositeDataTypeEffect extends AbstractEffectRealization {
  public RenamedCompositeDataTypeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEAttribute<CompositeDataType, String> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEAttribute<CompositeDataType, String> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenamedCompositeDataTypeEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEAttribute: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.pcm2java.RenamedCompositeDataTypeEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
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
    
    private void executeUserOperations(final ReplaceSingleValuedEAttribute<CompositeDataType, String> change) {
      CompositeDataType _affectedEObject = change.getAffectedEObject();
      this.effectFacade.callRenameCompositeDataType(((CompositeDataType) _affectedEObject));
    }
  }
}

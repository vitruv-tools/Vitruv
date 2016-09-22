package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

@SuppressWarnings("all")
public class ReplacedNonRootEObjectSingleResponseEffect extends AbstractEffectRealization {
  public ReplacedNonRootEObjectSingleResponseEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEReference<Root, NonRoot> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEReference<Root, NonRoot> change;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ReplacedNonRootEObjectSingleResponseEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEReference: " + this.change);
    
    
    preprocessElementStates();
    new mir.routines.simpleChangesTests.ReplacedNonRootEObjectSingleResponseEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final ReplaceSingleValuedEReference<Root, NonRoot> change) {
      boolean _isFromNonDefaultValue = change.isFromNonDefaultValue();
      if (_isFromNonDefaultValue) {
        NonRoot _oldValue = change.getOldValue();
        this.effectFacade.callDeleteNonRootEObjectSingle(_oldValue);
      }
      boolean _isToNonDefaultValue = change.isToNonDefaultValue();
      if (_isToNonDefaultValue) {
        Root _affectedEObject = change.getAffectedEObject();
        NonRoot _newValue = change.getNewValue();
        this.effectFacade.callCreateNonRootEObjectSingle(_affectedEObject, _newValue);
      }
    }
  }
}

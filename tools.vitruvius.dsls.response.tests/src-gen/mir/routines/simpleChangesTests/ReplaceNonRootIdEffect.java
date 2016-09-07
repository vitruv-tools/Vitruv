package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import tools.vitruvius.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruvius.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving;
import tools.vitruvius.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;

import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class ReplaceNonRootIdEffect extends AbstractEffectRealization {
  public ReplaceNonRootIdEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final ReplaceSingleValuedEAttribute<NonRoot, String> change) {
    super(responseExecutionState, calledBy);
    				this.change = change;
  }
  
  private ReplaceSingleValuedEAttribute<NonRoot, String> change;
  
  private EObject getCorrepondenceSourceTargetElement(final ReplaceSingleValuedEAttribute<NonRoot, String> change) {
    NonRoot _affectedEObject = change.getAffectedEObject();
    return _affectedEObject;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ReplaceNonRootIdEffect with input:");
    getLogger().debug("   ReplaceSingleValuedEAttribute: " + this.change);
    
    NonRoot targetElement = getCorrespondingElement(
    	getCorrepondenceSourceTargetElement(change), // correspondence source supplier
    	NonRoot.class,
    	(NonRoot _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    
    preprocessElementStates();
    new mir.routines.simpleChangesTests.ReplaceNonRootIdEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	change, targetElement);
    postprocessElementStates();
  }
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    private void executeUserOperations(final ReplaceSingleValuedEAttribute<NonRoot, String> change, final NonRoot targetElement) {
      String _newValue = change.getNewValue();
      targetElement.setId(_newValue);
    }
  }
}

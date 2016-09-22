package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ReplaceNonRootIdEffect extends AbstractEffectRealization {
  public ReplaceNonRootIdEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NonRoot nonRoot, final String value) {
    super(responseExecutionState, calledBy);
    				this.nonRoot = nonRoot;this.value = value;
  }
  
  private NonRoot nonRoot;
  
  private String value;
  
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    private void executeUserOperations(final NonRoot nonRoot, final String value, final NonRoot targetElement) {
      targetElement.setId(value);
    }
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
  }
  
  private EObject getCorrepondenceSourceTargetElement(final NonRoot nonRoot, final String value) {
    return nonRoot;
  }
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ReplaceNonRootIdEffect with input:");
    getLogger().debug("   NonRoot: " + this.nonRoot);
    getLogger().debug("   String: " + this.value);
    
    NonRoot targetElement = getCorrespondingElement(
    	getCorrepondenceSourceTargetElement(nonRoot, value), // correspondence source supplier
    	NonRoot.class,
    	(NonRoot _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    
    preprocessElementStates();
    new mir.routines.simpleChangesTests.ReplaceNonRootIdEffect.EffectUserExecution(getExecutionState(), this).executeUserOperations(
    	nonRoot, value, targetElement);
    postprocessElementStates();
  }
}

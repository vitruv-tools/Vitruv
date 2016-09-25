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
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final NonRoot nonRoot, final String value) {
      return nonRoot;
    }
    
    public EObject getElement1(final NonRoot nonRoot, final String value, final NonRoot targetElement) {
      return targetElement;
    }
    
    public void update0Element(final NonRoot nonRoot, final String value, final NonRoot targetElement) {
      targetElement.setId(value);
    }
  }
  
  private ReplaceNonRootIdEffect.EffectUserExecution userExecution;
  
  public ReplaceNonRootIdEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final NonRoot nonRoot, final String value) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.simpleChangesTests.ReplaceNonRootIdEffect.EffectUserExecution(getExecutionState(), this);
    				this.nonRoot = nonRoot;this.value = value;
  }
  
  private NonRoot nonRoot;
  
  private String value;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ReplaceNonRootIdEffect with input:");
    getLogger().debug("   NonRoot: " + this.nonRoot);
    getLogger().debug("   String: " + this.value);
    
    NonRoot targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(nonRoot, value), // correspondence source supplier
    	NonRoot.class,
    	(NonRoot _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    // val updatedElement userExecution.getElement1(nonRoot, value, targetElement);
    userExecution.update0Element(nonRoot, value, targetElement);
    
    preprocessElementStates();
    postprocessElementStates();
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
  }
}

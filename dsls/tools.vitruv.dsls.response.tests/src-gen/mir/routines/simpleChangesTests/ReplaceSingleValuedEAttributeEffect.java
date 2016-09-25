package mir.routines.simpleChangesTests;

import allElementTypes.Root;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ReplaceSingleValuedEAttributeEffect extends AbstractEffectRealization {
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final Root root, final Integer value) {
      return root;
    }
    
    public EObject getElement1(final Root root, final Integer value, final Root targetElement) {
      return targetElement;
    }
    
    public void update0Element(final Root root, final Integer value, final Root targetElement) {
      targetElement.setSingleValuedEAttribute(value);
    }
  }
  
  private ReplaceSingleValuedEAttributeEffect.EffectUserExecution userExecution;
  
  public ReplaceSingleValuedEAttributeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Root root, final Integer value) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.simpleChangesTests.ReplaceSingleValuedEAttributeEffect.EffectUserExecution(getExecutionState(), this);
    				this.root = root;this.value = value;
  }
  
  private Root root;
  
  private Integer value;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ReplaceSingleValuedEAttributeEffect with input:");
    getLogger().debug("   Root: " + this.root);
    getLogger().debug("   Integer: " + this.value);
    
    Root targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(root, value), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    // val updatedElement userExecution.getElement1(root, value, targetElement);
    userExecution.update0Element(root, value, targetElement);
    
    preprocessElementStates();
    new mir.routines.simpleChangesTests.ReplaceSingleValuedEAttributeEffect.CallRoutinesUserExecution(getExecutionState(), this).executeUserOperations(
    	root, value, targetElement);
    postprocessElementStates();
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    public void executeUserOperations(final Root root, final Integer value, final Root targetElement) {
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.UpdateSingleValuedEAttribute);
    }
  }
}

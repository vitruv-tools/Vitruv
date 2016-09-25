package mir.routines.simpleChangesTests;

import allElementTypes.Root;
import java.io.IOException;
import java.util.function.Predicate;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.response.AbstractEffectRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveEAttributeEffect extends AbstractEffectRealization {
  private static class EffectUserExecution extends AbstractEffectRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final Root root, final Integer removedAttributeValue) {
      return root;
    }
    
    public EObject getElement1(final Root root, final Integer removedAttributeValue, final Root targetElement) {
      return targetElement;
    }
    
    public void update0Element(final Root root, final Integer removedAttributeValue, final Root targetElement) {
      final EList<Integer> sourceValueList = root.getMultiValuedEAttribute();
      EList<Integer> _multiValuedEAttribute = targetElement.getMultiValuedEAttribute();
      final Predicate<Integer> _function = (Integer it) -> {
        int _intValue = it.intValue();
        boolean _contains = sourceValueList.contains(Integer.valueOf(_intValue));
        return (!_contains);
      };
      _multiValuedEAttribute.removeIf(_function);
    }
  }
  
  private RemoveEAttributeEffect.EffectUserExecution userExecution;
  
  public RemoveEAttributeEffect(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Root root, final Integer removedAttributeValue) {
    super(responseExecutionState, calledBy);
    				this.userExecution = new mir.routines.simpleChangesTests.RemoveEAttributeEffect.EffectUserExecution(getExecutionState(), this);
    				this.root = root;this.removedAttributeValue = removedAttributeValue;
  }
  
  private Root root;
  
  private Integer removedAttributeValue;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveEAttributeEffect with input:");
    getLogger().debug("   Root: " + this.root);
    getLogger().debug("   Integer: " + this.removedAttributeValue);
    
    Root targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(root, removedAttributeValue), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    // val updatedElement userExecution.getElement1(root, removedAttributeValue, targetElement);
    userExecution.update0Element(root, removedAttributeValue, targetElement);
    
    preprocessElementStates();
    new mir.routines.simpleChangesTests.RemoveEAttributeEffect.CallRoutinesUserExecution(getExecutionState(), this).executeUserOperations(
    	root, removedAttributeValue, targetElement);
    postprocessElementStates();
  }
  
  private static class CallRoutinesUserExecution extends AbstractEffectRealization.UserExecution {
    @Extension
    private RoutinesFacade effectFacade;
    
    public CallRoutinesUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
      this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(responseExecutionState, calledBy);
    }
    
    public void executeUserOperations(final Root root, final Integer removedAttributeValue, final Root targetElement) {
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.RemoveEAttributeValue);
    }
  }
}

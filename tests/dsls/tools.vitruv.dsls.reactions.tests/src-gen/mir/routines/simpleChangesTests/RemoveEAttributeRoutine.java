package mir.routines.simpleChangesTests;

import allElementTypes.Root;
import java.io.IOException;
import java.util.function.Predicate;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.reactions.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveEAttributeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemoveEAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
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
    
    public void callRoutine1(final Root root, final Integer removedAttributeValue, final Root targetElement, @Extension final RoutinesFacade _routinesFacade) {
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.RemoveEAttributeValue);
    }
  }
  
  public RemoveEAttributeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Root root, final Integer removedAttributeValue) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.RemoveEAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.root = root;this.removedAttributeValue = removedAttributeValue;
  }
  
  private Root root;
  
  private Integer removedAttributeValue;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveEAttributeRoutine with input:");
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
    
    userExecution.callRoutine1(root, removedAttributeValue, targetElement, actionsFacade);
    
    postprocessElementStates();
  }
}

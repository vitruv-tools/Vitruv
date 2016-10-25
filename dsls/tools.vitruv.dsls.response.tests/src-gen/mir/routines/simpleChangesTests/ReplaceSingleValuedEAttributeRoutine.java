package mir.routines.simpleChangesTests;

import allElementTypes.Root;
import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ReplaceSingleValuedEAttributeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ReplaceSingleValuedEAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final Root root, final Integer value) {
      return root;
    }
    
    public EObject getElement1(final Root root, final Integer value, final Root targetElement) {
      return targetElement;
    }
    
    public boolean checkMatcherPrecondition2(final Root root, final Integer value, final Root targetElement) {
      return (!Objects.equal(value, null));
    }
    
    public void update0Element(final Root root, final Integer value, final Root targetElement) {
      targetElement.setSingleValuedEAttribute(value);
    }
    
    public boolean checkMatcherPrecondition1(final Root root, final Integer value) {
      return (!Objects.equal(root, null));
    }
    
    public void callRoutine1(final Root root, final Integer value, final Root targetElement, @Extension final RoutinesFacade _routinesFacade) {
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.UpdateSingleValuedEAttribute);
    }
  }
  
  public ReplaceSingleValuedEAttributeRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Root root, final Integer value) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.ReplaceSingleValuedEAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.root = root;this.value = value;
  }
  
  private Root root;
  
  private Integer value;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ReplaceSingleValuedEAttributeRoutine with input:");
    getLogger().debug("   Root: " + this.root);
    getLogger().debug("   Integer: " + this.value);
    
    if (!userExecution.checkMatcherPrecondition1(root, value)) {
    	return;
    }
    Root targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(root, value), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    if (!userExecution.checkMatcherPrecondition2(root, value, targetElement)) {
    	return;
    }
    // val updatedElement userExecution.getElement1(root, value, targetElement);
    userExecution.update0Element(root, value, targetElement);
    
    userExecution.callRoutine1(root, value, targetElement, actionsFacade);
    
    postprocessElementStates();
  }
}

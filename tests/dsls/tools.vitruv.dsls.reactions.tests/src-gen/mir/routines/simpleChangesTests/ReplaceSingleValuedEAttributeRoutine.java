package mir.routines.simpleChangesTests;

import allElementTypes.Root;
import com.google.common.base.Objects;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.reactions.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ReplaceSingleValuedEAttributeRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ReplaceSingleValuedEAttributeRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final Root rootElement, final Integer value) {
      return rootElement;
    }
    
    public EObject getElement1(final Root rootElement, final Integer value, final Root targetElement) {
      return targetElement;
    }
    
    public boolean checkMatcherPrecondition2(final Root rootElement, final Integer value, final Root targetElement) {
      return (!Objects.equal(value, null));
    }
    
    public void update0Element(final Root rootElement, final Integer value, final Root targetElement) {
      targetElement.setSingleValuedEAttribute(value);
    }
    
    public boolean checkMatcherPrecondition1(final Root rootElement, final Integer value) {
      return (!Objects.equal(rootElement, null));
    }
    
    public void callRoutine1(final Root rootElement, final Integer value, final Root targetElement, @Extension final RoutinesFacade _routinesFacade) {
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.UpdateSingleValuedEAttribute);
    }
  }
  
  public ReplaceSingleValuedEAttributeRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Root rootElement, final Integer value) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.ReplaceSingleValuedEAttributeRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.rootElement = rootElement;this.value = value;
  }
  
  private Root rootElement;
  
  private Integer value;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ReplaceSingleValuedEAttributeRoutine with input:");
    getLogger().debug("   Root: " + this.rootElement);
    getLogger().debug("   Integer: " + this.value);
    
    if (!userExecution.checkMatcherPrecondition1(rootElement, value)) {
    	return;
    }
    Root targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(rootElement, value), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    if (!userExecution.checkMatcherPrecondition2(rootElement, value, targetElement)) {
    	return;
    }
    // val updatedElement userExecution.getElement1(rootElement, value, targetElement);
    userExecution.update0Element(rootElement, value, targetElement);
    
    userExecution.callRoutine1(rootElement, value, targetElement, actionsFacade);
    
    postprocessElementStates();
  }
}

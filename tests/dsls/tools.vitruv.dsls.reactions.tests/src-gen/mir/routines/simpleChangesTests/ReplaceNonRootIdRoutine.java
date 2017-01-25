package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ReplaceNonRootIdRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ReplaceNonRootIdRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
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
  
  public ReplaceNonRootIdRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NonRoot nonRoot, final String value) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.ReplaceNonRootIdRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.nonRoot = nonRoot;this.value = value;
  }
  
  private NonRoot nonRoot;
  
  private String value;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ReplaceNonRootIdRoutine with input:");
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
    
    postprocessElementStates();
  }
}

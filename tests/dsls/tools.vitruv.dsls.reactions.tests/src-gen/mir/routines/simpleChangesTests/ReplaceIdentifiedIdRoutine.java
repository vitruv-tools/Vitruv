package mir.routines.simpleChangesTests;

import allElementTypes.Identified;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ReplaceIdentifiedIdRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ReplaceIdentifiedIdRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final Identified identified, final String value) {
      return identified;
    }
    
    public EObject getElement1(final Identified identified, final String value, final Identified targetElement) {
      return targetElement;
    }
    
    public void update0Element(final Identified identified, final String value, final Identified targetElement) {
      targetElement.setId(value);
    }
  }
  
  public ReplaceIdentifiedIdRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Identified identified, final String value) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.ReplaceIdentifiedIdRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.identified = identified;this.value = value;
  }
  
  private Identified identified;
  
  private String value;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ReplaceIdentifiedIdRoutine with input:");
    getLogger().debug("   Identified: " + this.identified);
    getLogger().debug("   String: " + this.value);
    
    Identified targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(identified, value), // correspondence source supplier
    	Identified.class,
    	(Identified _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    registerObjectUnderModification(targetElement);
    // val updatedElement userExecution.getElement1(identified, value, targetElement);
    userExecution.update0Element(identified, value, targetElement);
    
    postprocessElements();
  }
}

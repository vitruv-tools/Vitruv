package mir.routines.simpleChangesTests;

import allElementTypes.NonRootObjectContainerHelper;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ReplaceNonRootObjectContainerHelperIdRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ReplaceNonRootObjectContainerHelperIdRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final NonRootObjectContainerHelper nonRootObjectContainerHelper, final String value) {
      return nonRootObjectContainerHelper;
    }
    
    public EObject getElement1(final NonRootObjectContainerHelper nonRootObjectContainerHelper, final String value, final NonRootObjectContainerHelper targetElement) {
      return targetElement;
    }
    
    public void update0Element(final NonRootObjectContainerHelper nonRootObjectContainerHelper, final String value, final NonRootObjectContainerHelper targetElement) {
      targetElement.setId(value);
    }
  }
  
  public ReplaceNonRootObjectContainerHelperIdRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NonRootObjectContainerHelper nonRootObjectContainerHelper, final String value) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.ReplaceNonRootObjectContainerHelperIdRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.nonRootObjectContainerHelper = nonRootObjectContainerHelper;this.value = value;
  }
  
  private NonRootObjectContainerHelper nonRootObjectContainerHelper;
  
  private String value;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ReplaceNonRootObjectContainerHelperIdRoutine with input:");
    getLogger().debug("   NonRootObjectContainerHelper: " + this.nonRootObjectContainerHelper);
    getLogger().debug("   String: " + this.value);
    
    NonRootObjectContainerHelper targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(nonRootObjectContainerHelper, value), // correspondence source supplier
    	NonRootObjectContainerHelper.class,
    	(NonRootObjectContainerHelper _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    registerObjectUnderModification(targetElement);
    // val updatedElement userExecution.getElement1(nonRootObjectContainerHelper, value, targetElement);
    userExecution.update0Element(nonRootObjectContainerHelper, value, targetElement);
    
    postprocessElements();
  }
}

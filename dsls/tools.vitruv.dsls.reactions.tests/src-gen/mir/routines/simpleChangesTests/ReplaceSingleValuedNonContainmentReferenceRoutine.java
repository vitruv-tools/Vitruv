package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.reactions.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ReplaceSingleValuedNonContainmentReferenceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private ReplaceSingleValuedNonContainmentReferenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetElement(final Root root, final NonRoot newReferencedElement, final Root targetContainer) {
      return newReferencedElement;
    }
    
    public EObject getElement1(final Root root, final NonRoot newReferencedElement, final Root targetContainer, final NonRoot targetElement) {
      return targetContainer;
    }
    
    public void update0Element(final Root root, final NonRoot newReferencedElement, final Root targetContainer, final NonRoot targetElement) {
      targetContainer.setSingleValuedNonContainmentEReference(targetElement);
    }
    
    public EObject getCorrepondenceSourceTargetContainer(final Root root, final NonRoot newReferencedElement) {
      return root;
    }
    
    public void callRoutine1(final Root root, final NonRoot newReferencedElement, final Root targetContainer, final NonRoot targetElement, @Extension final RoutinesFacade _routinesFacade) {
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.UpdateSingleValuedNonContainmentEReference);
    }
  }
  
  public ReplaceSingleValuedNonContainmentReferenceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Root root, final NonRoot newReferencedElement) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.ReplaceSingleValuedNonContainmentReferenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.root = root;this.newReferencedElement = newReferencedElement;
  }
  
  private Root root;
  
  private NonRoot newReferencedElement;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine ReplaceSingleValuedNonContainmentReferenceRoutine with input:");
    getLogger().debug("   Root: " + this.root);
    getLogger().debug("   NonRoot: " + this.newReferencedElement);
    
    Root targetContainer = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetContainer(root, newReferencedElement), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetContainer == null) {
    	return;
    }
    initializeRetrieveElementState(targetContainer);
    NonRoot targetElement = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetElement(root, newReferencedElement, targetContainer), // correspondence source supplier
    	NonRoot.class,
    	(NonRoot _element) -> true, // correspondence precondition checker
    	null);
    if (targetElement == null) {
    	return;
    }
    initializeRetrieveElementState(targetElement);
    // val updatedElement userExecution.getElement1(root, newReferencedElement, targetContainer, targetElement);
    userExecution.update0Element(root, newReferencedElement, targetContainer, targetElement);
    
    userExecution.callRoutine1(root, newReferencedElement, targetContainer, targetElement, actionsFacade);
    
    postprocessElementStates();
  }
}

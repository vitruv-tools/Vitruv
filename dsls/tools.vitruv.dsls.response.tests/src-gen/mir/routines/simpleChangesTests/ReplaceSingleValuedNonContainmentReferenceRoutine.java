package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import java.io.IOException;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.response.tests.simpleChangesTests.SimpleChangesTestsExecutionMonitor;
import tools.vitruv.extensions.dslsruntime.response.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.response.ResponseExecutionState;
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class ReplaceSingleValuedNonContainmentReferenceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade effectFacade;
  
  private ReplaceSingleValuedNonContainmentReferenceRoutine.EffectUserExecution userExecution;
  
  private static class EffectUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public EffectUserExecution(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy) {
      super(responseExecutionState);
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
  
  public ReplaceSingleValuedNonContainmentReferenceRoutine(final ResponseExecutionState responseExecutionState, final CallHierarchyHaving calledBy, final Root root, final NonRoot newReferencedElement) {
    super(responseExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.ReplaceSingleValuedNonContainmentReferenceRoutine.EffectUserExecution(getExecutionState(), this);
    this.effectFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
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
    
    userExecution.callRoutine1(root, newReferencedElement, targetContainer, targetElement, effectFacade);
    
    postprocessElementStates();
  }
}

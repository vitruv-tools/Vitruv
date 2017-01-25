package mir.routines.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import com.google.common.base.Objects;
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
public class RemoveNonContainmentReferenceRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemoveNonContainmentReferenceRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceTargetRoot(final Root rootElement, final NonRoot removedNonRoot) {
      return rootElement;
    }
    
    public EObject getElement1(final Root rootElement, final NonRoot removedNonRoot, final Root targetRoot) {
      return targetRoot;
    }
    
    public void update0Element(final Root rootElement, final NonRoot removedNonRoot, final Root targetRoot) {
      EList<NonRoot> _multiValuedNonContainmentEReference = targetRoot.getMultiValuedNonContainmentEReference();
      final Predicate<NonRoot> _function = (NonRoot it) -> {
        String _id = it.getId();
        String _id_1 = removedNonRoot.getId();
        return Objects.equal(_id, _id_1);
      };
      _multiValuedNonContainmentEReference.removeIf(_function);
    }
    
    public void callRoutine1(final Root rootElement, final NonRoot removedNonRoot, final Root targetRoot, @Extension final RoutinesFacade _routinesFacade) {
      SimpleChangesTestsExecutionMonitor _instance = SimpleChangesTestsExecutionMonitor.getInstance();
      _instance.set(SimpleChangesTestsExecutionMonitor.ChangeType.RemoveNonContainmentEReference);
    }
  }
  
  public RemoveNonContainmentReferenceRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Root rootElement, final NonRoot removedNonRoot) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.simpleChangesTests.RemoveNonContainmentReferenceRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.simpleChangesTests.RoutinesFacade(getExecutionState(), this);
    this.rootElement = rootElement;this.removedNonRoot = removedNonRoot;
  }
  
  private Root rootElement;
  
  private NonRoot removedNonRoot;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveNonContainmentReferenceRoutine with input:");
    getLogger().debug("   Root: " + this.rootElement);
    getLogger().debug("   NonRoot: " + this.removedNonRoot);
    
    Root targetRoot = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceTargetRoot(rootElement, removedNonRoot), // correspondence source supplier
    	Root.class,
    	(Root _element) -> true, // correspondence precondition checker
    	null);
    if (targetRoot == null) {
    	return;
    }
    initializeRetrieveElementState(targetRoot);
    // val updatedElement userExecution.getElement1(rootElement, removedNonRoot, targetRoot);
    userExecution.update0Element(rootElement, removedNonRoot, targetRoot);
    
    userExecution.callRoutine1(rootElement, removedNonRoot, targetRoot, actionsFacade);
    
    postprocessElementStates();
  }
}

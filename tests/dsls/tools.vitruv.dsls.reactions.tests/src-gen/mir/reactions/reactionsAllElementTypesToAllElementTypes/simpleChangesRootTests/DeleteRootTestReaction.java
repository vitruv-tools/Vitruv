package mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesRootTests;

import allElementTypes.Root;
import mir.routines.simpleChangesRootTests.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot;
import tools.vitruv.framework.change.echange.root.RemoveRootEObject;

@SuppressWarnings("all")
class DeleteRootTestReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    RemoveRootEObject<Root> typedChange = ((RemoveAndDeleteRoot<Root>)change).getRemoveChange();
    Root oldValue = typedChange.getOldValue();
    mir.routines.simpleChangesRootTests.RoutinesFacade routinesFacade = new mir.routines.simpleChangesRootTests.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesRootTests.DeleteRootTestReaction.ActionUserExecution userExecution = new mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesRootTests.DeleteRootTestReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(oldValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveAndDeleteRoot.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    RemoveRootEObject<Root> relevantChange = ((RemoveAndDeleteRoot<Root>)change).getRemoveChange();
    if (!(relevantChange.getOldValue() instanceof Root)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveAndDeleteRoot)) {
    	return false;
    }
    getLogger().debug("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().debug("Passed change properties check of reaction " + this.getClass().getName());
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Root oldValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteRoot(oldValue);
    }
  }
}

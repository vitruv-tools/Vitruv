package mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests;

import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.AtomicEChange;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CompoundEChange;

@SuppressWarnings("all")
class AnyChangeReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    EChange typedChange = (EChange)change;
    mir.routines.simpleChangesTests.RoutinesFacade routinesFacade = new mir.routines.simpleChangesTests.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests.AnyChangeReaction.ActionUserExecution userExecution = new mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests.AnyChangeReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return EChange.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    EChange relevantChange = (EChange)change;
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof EChange)) {
    	return false;
    }
    getLogger().debug("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().debug("Passed change properties check of reaction " + this.getClass().getName());
    EChange typedChange = (EChange)change;
    if (!checkUserDefinedPrecondition(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private boolean checkUserDefinedPrecondition(final EChange change) {
    return ((change instanceof AtomicEChange) || (change instanceof CompoundEChange));
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final EChange change, @Extension final RoutinesFacade _routinesFacade) {
      change.eClass();
    }
  }
}

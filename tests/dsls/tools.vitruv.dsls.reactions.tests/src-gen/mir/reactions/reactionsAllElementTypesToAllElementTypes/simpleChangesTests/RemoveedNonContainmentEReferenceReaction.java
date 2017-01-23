package mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.Root;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class RemoveedNonContainmentEReferenceReaction extends AbstractReactionRealization {
  public RemoveedNonContainmentEReferenceReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    RemoveEReference<Root, NonRoot> typedChange = (RemoveEReference<Root, NonRoot>)change;
    mir.routines.simpleChangesTests.RoutinesFacade routinesFacade = new mir.routines.simpleChangesTests.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests.RemoveedNonContainmentEReferenceReaction.ActionUserExecution userExecution = new mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests.RemoveedNonContainmentEReferenceReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEReference.class;
  }
  
  private boolean checkChangeProperties(final RemoveEReference<Root, NonRoot> change) {
    // Check affected object
    if (!(change.getAffectedEObject() instanceof Root)) {
    	return false;
    }
    // Check feature
    if (!change.getAffectedFeature().getName().equals("multiValuedNonContainmentEReference")) {
    	return false;
    }
    if (!(change.getOldValue() instanceof NonRoot)
    ) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveEReference<?, ?>)) {
    	return false;
    }
    RemoveEReference<Root, NonRoot> typedChange = (RemoveEReference<Root, NonRoot>)change;
    if (!checkChangeProperties(typedChange)) {
    	return false;
    }
    getLogger().debug("Passed precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final RemoveEReference<Root, NonRoot> change, @Extension final RoutinesFacade _routinesFacade) {
      Root _affectedEObject = change.getAffectedEObject();
      NonRoot _oldValue = change.getOldValue();
      _routinesFacade.removeNonContainmentReference(_affectedEObject, _oldValue);
    }
  }
}

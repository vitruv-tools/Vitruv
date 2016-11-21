package mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.Root;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class RemovedEAttributeValueReaction extends AbstractReactionRealization {
  public RemovedEAttributeValueReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    RemoveEAttributeValue<allElementTypes.Root, java.lang.Integer> typedChange = (RemoveEAttributeValue<allElementTypes.Root, java.lang.Integer>)change;
    mir.routines.simpleChangesTests.RoutinesFacade routinesFacade = new mir.routines.simpleChangesTests.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests.RemovedEAttributeValueReaction.ActionUserExecution userExecution = new mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests.RemovedEAttributeValueReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return RemoveEAttributeValue.class;
  }
  
  private boolean checkChangeProperties(final RemoveEAttributeValue<Root, Integer> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof Root)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("multiValuedEAttribute")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof RemoveEAttributeValue<?, ?>)) {
    	return false;
    }
    RemoveEAttributeValue typedChange = (RemoveEAttributeValue)change;
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
    
    public void callRoutine1(final RemoveEAttributeValue<Root, Integer> change, @Extension final RoutinesFacade _routinesFacade) {
      Root _affectedEObject = change.getAffectedEObject();
      Integer _oldValue = change.getOldValue();
      _routinesFacade.removeEAttribute(_affectedEObject, _oldValue);
    }
  }
}

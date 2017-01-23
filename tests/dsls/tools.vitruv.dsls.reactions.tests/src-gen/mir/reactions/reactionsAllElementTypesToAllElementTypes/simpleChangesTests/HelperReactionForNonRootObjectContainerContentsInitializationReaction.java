package mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.NonRootObjectContainerHelper;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class HelperReactionForNonRootObjectContainerContentsInitializationReaction extends AbstractReactionRealization {
  public HelperReactionForNonRootObjectContainerContentsInitializationReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    CreateAndInsertNonRoot<NonRootObjectContainerHelper, NonRoot> typedChange = (CreateAndInsertNonRoot<NonRootObjectContainerHelper, NonRoot>)change;
    mir.routines.simpleChangesTests.RoutinesFacade routinesFacade = new mir.routines.simpleChangesTests.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests.HelperReactionForNonRootObjectContainerContentsInitializationReaction.ActionUserExecution userExecution = new mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests.HelperReactionForNonRootObjectContainerContentsInitializationReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return CreateAndInsertNonRoot.class;
  }
  
  private boolean checkChangeProperties(final CreateAndInsertNonRoot<NonRootObjectContainerHelper, NonRoot> change) {
    if (!(change.getCreateChange().getAffectedEObject() instanceof NonRoot)) {
    	return false;
    }
    // Check affected object
    if (!(change.getInsertChange().getAffectedEObject() instanceof NonRootObjectContainerHelper)) {
    	return false;
    }
    // Check feature
    if (!change.getInsertChange().getAffectedFeature().getName().equals("nonRootObjectsContainment")) {
    	return false;
    }
    if (!(change.getInsertChange().getNewValue() instanceof NonRoot)) {
    	return false;
    }
    
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof CreateAndInsertNonRoot)) {
    	return false;
    }
    CreateAndInsertNonRoot<NonRootObjectContainerHelper, NonRoot> typedChange = (CreateAndInsertNonRoot<NonRootObjectContainerHelper, NonRoot>)change;
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
    
    public void callRoutine1(final CreateAndInsertNonRoot<NonRootObjectContainerHelper, NonRoot> change, @Extension final RoutinesFacade _routinesFacade) {
      InsertEReference<NonRootObjectContainerHelper, NonRoot> _insertChange = change.getInsertChange();
      NonRootObjectContainerHelper _affectedEObject = _insertChange.getAffectedEObject();
      InsertEReference<NonRootObjectContainerHelper, NonRoot> _insertChange_1 = change.getInsertChange();
      NonRoot _newValue = _insertChange_1.getNewValue();
      _routinesFacade.createNonRootInContainer(_affectedEObject, _newValue);
    }
  }
}

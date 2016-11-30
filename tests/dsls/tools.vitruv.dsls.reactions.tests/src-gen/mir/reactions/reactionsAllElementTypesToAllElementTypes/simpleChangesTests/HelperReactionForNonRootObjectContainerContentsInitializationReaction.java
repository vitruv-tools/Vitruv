package mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRoot;
import allElementTypes.NonRootObjectContainerHelper;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class HelperReactionForNonRootObjectContainerContentsInitializationReaction extends AbstractReactionRealization {
  public HelperReactionForNonRootObjectContainerContentsInitializationReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    InsertEReference<allElementTypes.NonRootObjectContainerHelper, allElementTypes.NonRoot> typedChange = (InsertEReference<allElementTypes.NonRootObjectContainerHelper, allElementTypes.NonRoot>)change;
    mir.routines.simpleChangesTests.RoutinesFacade routinesFacade = new mir.routines.simpleChangesTests.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests.HelperReactionForNonRootObjectContainerContentsInitializationReaction.ActionUserExecution userExecution = new mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests.HelperReactionForNonRootObjectContainerContentsInitializationReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(typedChange, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return InsertEReference.class;
  }
  
  private boolean checkChangeProperties(final InsertEReference<NonRootObjectContainerHelper, NonRoot> change) {
    EObject changedElement = change.getAffectedEObject();
    // Check model element type
    if (!(changedElement instanceof NonRootObjectContainerHelper)) {
    	return false;
    }
    
    // Check feature
    if (!change.getAffectedFeature().getName().equals("nonRootObjectsContainment")) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof InsertEReference<?, ?>)) {
    	return false;
    }
    InsertEReference typedChange = (InsertEReference)change;
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
    
    public void callRoutine1(final InsertEReference<NonRootObjectContainerHelper, NonRoot> change, @Extension final RoutinesFacade _routinesFacade) {
      NonRootObjectContainerHelper _affectedEObject = change.getAffectedEObject();
      NonRoot _newValue = change.getNewValue();
      _routinesFacade.createNonRootInContainer(_affectedEObject, _newValue);
    }
  }
}

package mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests;

import allElementTypes.NonRootObjectContainerHelper;
import mir.routines.simpleChangesTests.RoutinesFacade;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class ReplaceNonRootObjectContainerHelperIdReaction extends AbstractReactionRealization {
  public ReplaceNonRootObjectContainerHelperIdReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEAttribute<NonRootObjectContainerHelper, String> typedChange = (ReplaceSingleValuedEAttribute<NonRootObjectContainerHelper, String>)change;
    NonRootObjectContainerHelper affectedEObject = typedChange.getAffectedEObject();
    EAttribute affectedFeature = typedChange.getAffectedFeature();
    String oldValue = typedChange.getOldValue();
    String newValue = typedChange.getNewValue();
    mir.routines.simpleChangesTests.RoutinesFacade routinesFacade = new mir.routines.simpleChangesTests.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests.ReplaceNonRootObjectContainerHelperIdReaction.ActionUserExecution userExecution = new mir.reactions.reactionsAllElementTypesToAllElementTypes.simpleChangesTests.ReplaceNonRootObjectContainerHelperIdReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEAttribute.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEAttribute<NonRootObjectContainerHelper, String> relevantChange = (ReplaceSingleValuedEAttribute<NonRootObjectContainerHelper, String>)change;
    if (!(relevantChange.getAffectedEObject() instanceof NonRootObjectContainerHelper)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("id")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof String)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof String)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEAttribute)) {
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
    
    public void callRoutine1(final NonRootObjectContainerHelper affectedEObject, final EAttribute affectedFeature, final String oldValue, final String newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.replaceNonRootObjectContainerHelperId(affectedEObject, newValue);
    }
  }
}

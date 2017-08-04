package mir.reactions.reactionsFamiliesToPersons.familiesToPersons;

import edu.kit.ipd.sdq.metamodels.families.Family;
import edu.kit.ipd.sdq.metamodels.families.Member;
import mir.routines.familiesToPersons.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

@SuppressWarnings("all")
class CreatedFatherReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEReference<Family, Member> typedChange = (ReplaceSingleValuedEReference<Family, Member>)change;
    Family affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    Member oldValue = typedChange.getOldValue();
    Member newValue = typedChange.getNewValue();
    mir.routines.familiesToPersons.RoutinesFacade routinesFacade = new mir.routines.familiesToPersons.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedFatherReaction.ActionUserExecution userExecution = new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedFatherReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEReference<Family, Member> relevantChange = (ReplaceSingleValuedEReference<Family, Member>)change;
    if (!(relevantChange.getAffectedEObject() instanceof Family)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("father")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof Member)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof Member)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEReference)) {
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
    
    public void callRoutine1(final Family affectedEObject, final EReference affectedFeature, final Member oldValue, final Member newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createFather(newValue);
    }
  }
}

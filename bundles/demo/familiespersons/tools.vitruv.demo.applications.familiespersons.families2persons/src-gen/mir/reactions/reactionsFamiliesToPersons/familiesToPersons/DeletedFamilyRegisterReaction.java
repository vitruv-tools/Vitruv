package mir.reactions.reactionsFamiliesToPersons.familiesToPersons;

import edu.kit.ipd.sdq.metamodels.families.FamilyRegister;
import mir.routines.familiesToPersons.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;

@SuppressWarnings("all")
class DeletedFamilyRegisterReaction extends AbstractReactionRealization {
  public void executeReaction(final EChange change) {
    DeleteEObject<FamilyRegister> typedChange = (DeleteEObject<FamilyRegister>)change;
    FamilyRegister affectedEObject = typedChange.getAffectedEObject();
    mir.routines.familiesToPersons.RoutinesFacade routinesFacade = new mir.routines.familiesToPersons.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsFamiliesToPersons.familiesToPersons.DeletedFamilyRegisterReaction.ActionUserExecution userExecution = new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.DeletedFamilyRegisterReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return DeleteEObject.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    DeleteEObject<FamilyRegister> relevantChange = (DeleteEObject<FamilyRegister>)change;
    if (!(relevantChange.getAffectedEObject() instanceof FamilyRegister)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof DeleteEObject)) {
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
    
    public void callRoutine1(final FamilyRegister affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deletePersonRegister(affectedEObject);
    }
  }
}

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
  private DeleteEObject<FamilyRegister> deleteChange;
  
  private int currentlyMatchedChange;
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    edu.kit.ipd.sdq.metamodels.families.FamilyRegister affectedEObject = deleteChange.getAffectedEObject();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.routines.familiesToPersons.RoutinesFacade routinesFacade = new mir.routines.familiesToPersons.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsFamiliesToPersons.familiesToPersons.DeletedFamilyRegisterReaction.ActionUserExecution userExecution = new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.DeletedFamilyRegisterReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, routinesFacade);
    
    resetChanges();
  }
  
  private boolean matchDeleteChange(final EChange change) {
    if (change instanceof DeleteEObject<?>) {
    	DeleteEObject<edu.kit.ipd.sdq.metamodels.families.FamilyRegister> _localTypedChange = (DeleteEObject<edu.kit.ipd.sdq.metamodels.families.FamilyRegister>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof edu.kit.ipd.sdq.metamodels.families.FamilyRegister)) {
    		return false;
    	}
    	this.deleteChange = (DeleteEObject<edu.kit.ipd.sdq.metamodels.families.FamilyRegister>) change;
    	return true;
    }
    
    return false;
  }
  
  private void resetChanges() {
    deleteChange = null;
    currentlyMatchedChange = 0;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchDeleteChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
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

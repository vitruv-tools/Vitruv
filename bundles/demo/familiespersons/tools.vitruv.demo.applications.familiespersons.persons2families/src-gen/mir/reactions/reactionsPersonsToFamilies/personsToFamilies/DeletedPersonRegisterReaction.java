package mir.reactions.reactionsPersonsToFamilies.personsToFamilies;

import edu.kit.ipd.sdq.metamodels.persons.PersonRegister;
import mir.routines.personsToFamilies.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;

@SuppressWarnings("all")
class DeletedPersonRegisterReaction extends AbstractReactionRealization {
  private DeleteEObject<PersonRegister> deleteChange;
  
  private int currentlyMatchedChange;
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    edu.kit.ipd.sdq.metamodels.persons.PersonRegister affectedEObject = deleteChange.getAffectedEObject();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.routines.personsToFamilies.RoutinesFacade routinesFacade = new mir.routines.personsToFamilies.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsPersonsToFamilies.personsToFamilies.DeletedPersonRegisterReaction.ActionUserExecution userExecution = new mir.reactions.reactionsPersonsToFamilies.personsToFamilies.DeletedPersonRegisterReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, routinesFacade);
    
    resetChanges();
  }
  
  private boolean matchDeleteChange(final EChange change) {
    if (change instanceof DeleteEObject<?>) {
    	DeleteEObject<edu.kit.ipd.sdq.metamodels.persons.PersonRegister> _localTypedChange = (DeleteEObject<edu.kit.ipd.sdq.metamodels.persons.PersonRegister>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof edu.kit.ipd.sdq.metamodels.persons.PersonRegister)) {
    		return false;
    	}
    	this.deleteChange = (DeleteEObject<edu.kit.ipd.sdq.metamodels.persons.PersonRegister>) change;
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
    
    public void callRoutine1(final PersonRegister affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.deleteFamilyRegister(affectedEObject);
    }
  }
}

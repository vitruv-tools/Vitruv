package mir.reactions.reactionsRecipientsToAddresses.adXre_R2L;

import edu.kit.ipd.sdq.metamodels.recipients.Location;
import mir.routines.adXre_R2L.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.AddressXRecipientLocationCityMapping;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;

@SuppressWarnings("all")
class LocationDeletedReaction extends AbstractReactionRealization {
  private DeleteEObject<Location> deleteChange;
  
  private int currentlyMatchedChange;
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    edu.kit.ipd.sdq.metamodels.recipients.Location affectedEObject = deleteChange.getAffectedEObject();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.routines.adXre_R2L.RoutinesFacade routinesFacade = new mir.routines.adXre_R2L.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.LocationDeletedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.LocationDeletedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, routinesFacade);
    
    resetChanges();
  }
  
  private boolean matchDeleteChange(final EChange change) {
    if (change instanceof DeleteEObject<?>) {
    	DeleteEObject<edu.kit.ipd.sdq.metamodels.recipients.Location> _localTypedChange = (DeleteEObject<edu.kit.ipd.sdq.metamodels.recipients.Location>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof edu.kit.ipd.sdq.metamodels.recipients.Location)) {
    		return false;
    	}
    	this.deleteChange = (DeleteEObject<edu.kit.ipd.sdq.metamodels.recipients.Location>) change;
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
    
    public void callRoutine1(final Location affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      AddressXRecipientLocationCityMapping.addressXRecipientLocationCityMapping().removeLocation(affectedEObject);
    }
  }
}

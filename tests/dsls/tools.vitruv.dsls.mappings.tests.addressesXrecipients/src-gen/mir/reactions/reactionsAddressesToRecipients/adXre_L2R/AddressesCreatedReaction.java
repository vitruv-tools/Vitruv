package mir.reactions.reactionsAddressesToRecipients.adXre_L2R;

import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import mir.routines.adXre_L2R.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.AdRootXReRootMapping;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.CreateEObject;

/**
 * *****************************************************
 *  ********** BEGIN CREATE REACTIONS **********
 *  *****************************************************
 */
@SuppressWarnings("all")
class AddressesCreatedReaction extends AbstractReactionRealization {
  private CreateEObject<Addresses> createChange;
  
  private int currentlyMatchedChange;
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    edu.kit.ipd.sdq.metamodels.addresses.Addresses affectedEObject = createChange.getAffectedEObject();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.routines.adXre_L2R.RoutinesFacade routinesFacade = new mir.routines.adXre_L2R.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressesCreatedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressesCreatedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, routinesFacade);
    
    resetChanges();
  }
  
  private void resetChanges() {
    createChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchCreateChange(final EChange change) {
    if (change instanceof CreateEObject<?>) {
    	CreateEObject<edu.kit.ipd.sdq.metamodels.addresses.Addresses> _localTypedChange = (CreateEObject<edu.kit.ipd.sdq.metamodels.addresses.Addresses>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof edu.kit.ipd.sdq.metamodels.addresses.Addresses)) {
    		return false;
    	}
    	this.createChange = (CreateEObject<edu.kit.ipd.sdq.metamodels.addresses.Addresses>) change;
    	return true;
    }
    
    return false;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchCreateChange(change)) {
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
    
    public void callRoutine1(final Addresses affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      AdRootXReRootMapping.adRootXReRootMapping().addAddresses(affectedEObject);
    }
  }
}

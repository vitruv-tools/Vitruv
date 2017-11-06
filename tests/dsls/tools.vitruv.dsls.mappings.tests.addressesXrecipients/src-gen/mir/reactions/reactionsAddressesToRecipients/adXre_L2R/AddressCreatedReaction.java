package mir.reactions.reactionsAddressesToRecipients.adXre_L2R;

import edu.kit.ipd.sdq.metamodels.addresses.Address;
import mir.routines.adXre_L2R.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.aXr_all_or_nothing.AddressXRecipientLocationCityMapping;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.CreateEObject;

@SuppressWarnings("all")
class AddressCreatedReaction extends AbstractReactionRealization {
  private CreateEObject<Address> createChange;
  
  private int currentlyMatchedChange;
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    edu.kit.ipd.sdq.metamodels.addresses.Address affectedEObject = createChange.getAffectedEObject();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.routines.adXre_L2R.RoutinesFacade routinesFacade = new mir.routines.adXre_L2R.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressCreatedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsAddressesToRecipients.adXre_L2R.AddressCreatedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, routinesFacade);
    
    resetChanges();
  }
  
  private void resetChanges() {
    createChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchCreateChange(final EChange change) {
    if (change instanceof CreateEObject<?>) {
    	CreateEObject<edu.kit.ipd.sdq.metamodels.addresses.Address> _localTypedChange = (CreateEObject<edu.kit.ipd.sdq.metamodels.addresses.Address>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof edu.kit.ipd.sdq.metamodels.addresses.Address)) {
    		return false;
    	}
    	this.createChange = (CreateEObject<edu.kit.ipd.sdq.metamodels.addresses.Address>) change;
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
    
    public void callRoutine1(final Address affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      AddressXRecipientLocationCityMapping.addressXRecipientLocationCityMapping().addAddress(affectedEObject);
    }
  }
}

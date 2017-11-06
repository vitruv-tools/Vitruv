package mir.reactions.reactionsRecipientsToAddresses.adXre_R2L;

import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import mir.routines.adXre_R2L.RoutinesFacade;
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
class RecipientsCreatedReaction extends AbstractReactionRealization {
  private CreateEObject<Recipients> createChange;
  
  private int currentlyMatchedChange;
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    edu.kit.ipd.sdq.metamodels.recipients.Recipients affectedEObject = createChange.getAffectedEObject();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.routines.adXre_R2L.RoutinesFacade routinesFacade = new mir.routines.adXre_R2L.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.RecipientsCreatedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsRecipientsToAddresses.adXre_R2L.RecipientsCreatedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, routinesFacade);
    
    resetChanges();
  }
  
  private void resetChanges() {
    createChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchCreateChange(final EChange change) {
    if (change instanceof CreateEObject<?>) {
    	CreateEObject<edu.kit.ipd.sdq.metamodels.recipients.Recipients> _localTypedChange = (CreateEObject<edu.kit.ipd.sdq.metamodels.recipients.Recipients>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof edu.kit.ipd.sdq.metamodels.recipients.Recipients)) {
    		return false;
    	}
    	this.createChange = (CreateEObject<edu.kit.ipd.sdq.metamodels.recipients.Recipients>) change;
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
    
    public void callRoutine1(final Recipients affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      AdRootXReRootMapping.adRootXReRootMapping().addRecipients(affectedEObject);
    }
  }
}

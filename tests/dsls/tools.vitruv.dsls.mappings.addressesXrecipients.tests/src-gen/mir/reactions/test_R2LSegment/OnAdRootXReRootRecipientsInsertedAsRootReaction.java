package mir.reactions.test_R2LSegment;

import edu.kit.ipd.sdq.metamodels.recipients.Recipients;
import mir.routines.test_R2LSegment.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.CreateEObject;

@SuppressWarnings("all")
public class OnAdRootXReRootRecipientsInsertedAsRootReaction extends AbstractReactionRealization {
  private CreateEObject<Recipients> createChange;
  
  private int currentlyMatchedChange;
  
  public OnAdRootXReRootRecipientsInsertedAsRootReaction(final RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    edu.kit.ipd.sdq.metamodels.recipients.Recipients affectedEObject = createChange.getAffectedEObject();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.test_R2LSegment.OnAdRootXReRootRecipientsInsertedAsRootReaction.ActionUserExecution userExecution = new mir.reactions.test_R2LSegment.OnAdRootXReRootRecipientsInsertedAsRootReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(createChange, affectedEObject, this.getRoutinesFacade());
    
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
    
    public void callRoutine1(final CreateEObject createChange, final Recipients affectedEObject, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.onAdRootXReRootRecipientsInsertedAsRootRepair(affectedEObject);
    }
  }
}

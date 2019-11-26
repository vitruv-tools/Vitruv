package mir.reactions.test_L2R;

import edu.kit.ipd.sdq.metamodels.addresses.Addresses;
import org.eclipse.xtext.xbase.lib.Extension;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.CreateEObject;

@SuppressWarnings("all")
public class OnAdRootXReRootAddressesInsertedAsRootReaction extends AbstractReactionRealization {
  private CreateEObject<Addresses> createChange;
  
  private int currentlyMatchedChange;
  
  public OnAdRootXReRootAddressesInsertedAsRootReaction(final mir.routines.test_L2R.RoutinesFacade routinesFacade) {
    super(routinesFacade);
  }
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    edu.kit.ipd.sdq.metamodels.addresses.Addresses affectedEObject = createChange.getAffectedEObject();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.reactions.test_L2R.OnAdRootXReRootAddressesInsertedAsRootReaction.ActionUserExecution userExecution = new mir.reactions.test_L2R.OnAdRootXReRootAddressesInsertedAsRootReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(createChange, affectedEObject, this.getRoutinesFacade());
    
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
    
    public void callRoutine1(final CreateEObject createChange, final Addresses affectedEObject, @Extension final mir.routines.test_L2R.RoutinesFacade _routinesFacade) {
      _routinesFacade.adRootXReRoot_ElementCreatedCheck(affectedEObject);
    }
  }
}

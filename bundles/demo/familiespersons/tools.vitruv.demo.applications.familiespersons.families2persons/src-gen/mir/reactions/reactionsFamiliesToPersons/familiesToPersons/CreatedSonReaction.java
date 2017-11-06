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
import tools.vitruv.framework.change.echange.eobject.CreateEObject;
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference;

@SuppressWarnings("all")
class CreatedSonReaction extends AbstractReactionRealization {
  private CreateEObject<Member> createChange;
  
  private InsertEReference<Family, Member> insertChange;
  
  private int currentlyMatchedChange;
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    edu.kit.ipd.sdq.metamodels.families.Family affectedEObject = insertChange.getAffectedEObject();
    EReference affectedFeature = insertChange.getAffectedFeature();
    edu.kit.ipd.sdq.metamodels.families.Member newValue = insertChange.getNewValue();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.routines.familiesToPersons.RoutinesFacade routinesFacade = new mir.routines.familiesToPersons.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedSonReaction.ActionUserExecution userExecution = new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedSonReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, newValue, routinesFacade);
    
    resetChanges();
  }
  
  private void resetChanges() {
    createChange = null;
    insertChange = null;
    currentlyMatchedChange = 0;
  }
  
  private boolean matchCreateChange(final EChange change) {
    if (change instanceof CreateEObject<?>) {
    	CreateEObject<edu.kit.ipd.sdq.metamodels.families.Member> _localTypedChange = (CreateEObject<edu.kit.ipd.sdq.metamodels.families.Member>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof edu.kit.ipd.sdq.metamodels.families.Member)) {
    		return false;
    	}
    	this.createChange = (CreateEObject<edu.kit.ipd.sdq.metamodels.families.Member>) change;
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
    	return false; // Only proceed on the last of the expected changes
    }
    if (currentlyMatchedChange == 1) {
    	if (!matchInsertChange(change)) {
    		resetChanges();
    		checkPrecondition(change); // Reexecute to potentially register this as first change
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private boolean matchInsertChange(final EChange change) {
    if (change instanceof InsertEReference<?, ?>) {
    	InsertEReference<edu.kit.ipd.sdq.metamodels.families.Family, edu.kit.ipd.sdq.metamodels.families.Member> _localTypedChange = (InsertEReference<edu.kit.ipd.sdq.metamodels.families.Family, edu.kit.ipd.sdq.metamodels.families.Member>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof edu.kit.ipd.sdq.metamodels.families.Family)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("sons")) {
    		return false;
    	}
    	if (!(_localTypedChange.getNewValue() instanceof edu.kit.ipd.sdq.metamodels.families.Member)) {
    		return false;
    	}
    	this.insertChange = (InsertEReference<edu.kit.ipd.sdq.metamodels.families.Family, edu.kit.ipd.sdq.metamodels.families.Member>) change;
    	return true;
    }
    
    return false;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Family affectedEObject, final EReference affectedFeature, final Member newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createSon(newValue);
    }
  }
}

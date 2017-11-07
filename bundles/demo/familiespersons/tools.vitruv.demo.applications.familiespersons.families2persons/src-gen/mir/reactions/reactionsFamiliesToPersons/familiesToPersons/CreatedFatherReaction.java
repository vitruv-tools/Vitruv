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
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;

@SuppressWarnings("all")
class CreatedFatherReaction extends AbstractReactionRealization {
  private ReplaceSingleValuedEReference<Family, Member> replaceChange;
  
  private int currentlyMatchedChange;
  
  public void executeReaction(final EChange change) {
    if (!checkPrecondition(change)) {
    	return;
    }
    edu.kit.ipd.sdq.metamodels.families.Family affectedEObject = replaceChange.getAffectedEObject();
    EReference affectedFeature = replaceChange.getAffectedFeature();
    edu.kit.ipd.sdq.metamodels.families.Member oldValue = replaceChange.getOldValue();
    edu.kit.ipd.sdq.metamodels.families.Member newValue = replaceChange.getNewValue();
    				
    getLogger().trace("Passed complete precondition check of Reaction " + this.getClass().getName());
    				
    mir.routines.familiesToPersons.RoutinesFacade routinesFacade = new mir.routines.familiesToPersons.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedFatherReaction.ActionUserExecution userExecution = new mir.reactions.reactionsFamiliesToPersons.familiesToPersons.CreatedFatherReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
    
    resetChanges();
  }
  
  private void resetChanges() {
    replaceChange = null;
    currentlyMatchedChange = 0;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (currentlyMatchedChange == 0) {
    	if (!matchReplaceChange(change)) {
    		resetChanges();
    		return false;
    	} else {
    		currentlyMatchedChange++;
    	}
    }
    
    return true;
  }
  
  private boolean matchReplaceChange(final EChange change) {
    if (change instanceof ReplaceSingleValuedEReference<?, ?>) {
    	ReplaceSingleValuedEReference<edu.kit.ipd.sdq.metamodels.families.Family, edu.kit.ipd.sdq.metamodels.families.Member> _localTypedChange = (ReplaceSingleValuedEReference<edu.kit.ipd.sdq.metamodels.families.Family, edu.kit.ipd.sdq.metamodels.families.Member>) change;
    	if (!(_localTypedChange.getAffectedEObject() instanceof edu.kit.ipd.sdq.metamodels.families.Family)) {
    		return false;
    	}
    	if (!_localTypedChange.getAffectedFeature().getName().equals("father")) {
    		return false;
    	}
    	if (_localTypedChange.isFromNonDefaultValue() && !(_localTypedChange.getOldValue() instanceof edu.kit.ipd.sdq.metamodels.families.Member)) {
    		return false;
    	}
    	if (_localTypedChange.isToNonDefaultValue() && !(_localTypedChange.getNewValue() instanceof edu.kit.ipd.sdq.metamodels.families.Member)) {
    		return false;
    	}
    	this.replaceChange = (ReplaceSingleValuedEReference<edu.kit.ipd.sdq.metamodels.families.Family, edu.kit.ipd.sdq.metamodels.families.Member>) change;
    	return true;
    }
    
    return false;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Family affectedEObject, final EReference affectedFeature, final Member oldValue, final Member newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.createFather(newValue);
    }
  }
}

package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.extensions.dslsruntime.reactions.IReactionRealization
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.userinteraction.UserInteracting

abstract class AbstractReactionRealization extends CallHierarchyHaving implements IReactionRealization {
	protected UserInteracting userInteracting;
	protected ReactionExecutionState executionState;
	
	override applyEvent(EChange change, ReactionExecutionState reactionExecutionState) {
    	this.executionState = reactionExecutionState;
    	this.userInteracting = reactionExecutionState.userInteracting;
    	
    	if (checkPrecondition(change)) {
    		try {	
				executeReaction(change);
			} finally {
				// The reactions was completely executed, so remove all objects registered for modification 
				// by the effects as they are no longer under modification
				// even if there was an exception!
				TuidManager.instance.flushRegisteredObjectsUnderModification();	
			}
		}
	}
	
	protected def void executeReaction(EChange change);
	
}
package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.extensions.dslsruntime.reactions.IReactionRealization
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.tuid.TuidManager

abstract class AbstractReactionRealization extends CallHierarchyHaving implements IReactionRealization {
	protected ReactionExecutionState executionState;
	
	override applyEvent(EChange change, ReactionExecutionState reactionExecutionState) {
		getLogger().debug("Called reactions " + this.getClass().getSimpleName() + " with event: " + change);
    	
    	this.executionState = reactionExecutionState;
    	
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
		
		return executionState.transformationResult;
	}
	
	protected def void executeReaction(EChange change);
	
}
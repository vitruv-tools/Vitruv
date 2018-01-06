package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.extensions.dslsruntime.reactions.IReactionRealization
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.userinteraction.UserInteracting
import org.eclipse.xtend.lib.annotations.Accessors

abstract class AbstractReactionRealization extends CallHierarchyHaving implements IReactionRealization {
	protected val AbstractReactionsExecutor executor;
	protected UserInteracting userInteracting;
	protected ReactionExecutionState executionState;
	
	public new(AbstractReactionsExecutor executor) {
		this.executor = executor;
	}
	
	override applyEvent(EChange change, ReactionExecutionState reactionExecutionState) {
    	this.executionState = reactionExecutionState;
    	this.userInteracting = reactionExecutionState.userInteracting;
    	
    		try {	
				executeReaction(change);
			} finally {
				// The reactions was completely executed, so remove all objects registered for modification 
				// by the effects as they are no longer under modification
				// even if there was an exception!
				TuidManager.instance.flushRegisteredObjectsUnderModification();	
			}
	}
	
	protected def void executeReaction(EChange change);
	
	
	static abstract class ChangeMatcher<T extends EChange> {
		@Accessors(PUBLIC_GETTER)
		T change

		def boolean check(EChange change);
	}
}
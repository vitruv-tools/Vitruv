package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.extensions.dslsruntime.reactions.IReactionRealization
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.userinteraction.UserInteracting
import org.eclipse.xtend.lib.annotations.Accessors

abstract class AbstractReactionRealization extends CallHierarchyHaving implements IReactionRealization {
	private val AbstractRepairRoutinesFacade routinesFacade;
	protected UserInteracting userInteracting;
	protected ReactionExecutionState executionState;
	
	public new(AbstractRepairRoutinesFacade routinesFacade) {
		this.routinesFacade = routinesFacade;
	}
	
	// generic return type for convenience; the requested type has to match the type of the facade provided during construction:
	protected def <T extends AbstractRepairRoutinesFacade> T getRoutinesFacade() {
		return routinesFacade as T;
	}
	
	override applyEvent(EChange change, ReactionExecutionState reactionExecutionState) {
    	this.executionState = reactionExecutionState;
    	this.userInteracting = reactionExecutionState.userInteracting;

		// capture the current execution state of the facade:
		val facadeExecutionState = routinesFacade._captureExecutionState();
		// set the reaction execution state and caller to use for all following routine calls:
		routinesFacade._setExecutionState(executionState, this);
    	
    		try {	
				executeReaction(change);
			} finally {
				// restore the previously captured execution state of the facade:
				routinesFacade._restoreExecutionState(facadeExecutionState);

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
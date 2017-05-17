package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.extensions.dslsruntime.reactions.IReactionRealization
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.util.command.ChangePropagationResult

abstract class AbstractReactionRealization extends CallHierarchyHaving implements IReactionRealization {
	protected val UserInteracting userInteracting
	protected ReactionExecutionState executionState

	new(UserInteracting userInteracting) {
		this.userInteracting = userInteracting
	}

	override applyEvent(EChange change, CorrespondenceModel correspondenceModel) {
		logger.debug('''Called reactions «class.simpleName» with event: «change»''')

		executionState = new ReactionExecutionState(userInteracting, correspondenceModel, new ChangePropagationResult)

		if (checkPrecondition(change)) {
			try {
				executeReaction(change)
			} finally {
				// The reactions was completely executed, so remove all objects registered for modification 
				// by the effects as they are no longer under modification
				// even if there was an exception!
				TuidManager::instance.flushRegisteredObjectsUnderModification
			}

		}

		executionState.transformationResult
	}

	protected def void executeReaction(EChange change)

}

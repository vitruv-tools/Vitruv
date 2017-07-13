package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.command.ChangePropagationResult

interface IReactionRealization {
	def ChangePropagationResult applyEvent(EChange change, ReactionExecutionState executionState)
	def boolean checkPrecondition(EChange change)
}

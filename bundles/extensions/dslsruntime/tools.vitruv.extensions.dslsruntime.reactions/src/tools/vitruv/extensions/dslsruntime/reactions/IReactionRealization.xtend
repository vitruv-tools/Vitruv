package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.framework.change.echange.EChange

interface IReactionRealization {
	def void applyEvent(EChange change, ReactionExecutionState executionState);
}

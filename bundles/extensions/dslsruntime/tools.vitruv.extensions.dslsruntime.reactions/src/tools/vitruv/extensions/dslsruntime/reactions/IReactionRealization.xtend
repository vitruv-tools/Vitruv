package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.framework.change.echange.EChange

public interface IReactionRealization {
	public def void applyEvent(EChange change, ReactionExecutionState executionState);
	public def boolean checkPrecondition(EChange change);
}

package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.command.ChangePropagationResult

public interface IReactionRealization {
	public def ChangePropagationResult applyEvent(EChange change, ReactionExecutionState executionState);
	public def boolean checkPrecondition(EChange change);
}

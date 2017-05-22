package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.command.ChangePropagationResult

public interface IReactionRealization {
	public def ChangePropagationResult applyEvent(EChange change, CorrespondenceModel correspondenceModel);
	public def boolean checkPrecondition(EChange change);
}

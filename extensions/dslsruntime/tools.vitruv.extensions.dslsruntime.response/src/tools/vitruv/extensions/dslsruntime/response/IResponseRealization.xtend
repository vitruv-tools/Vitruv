package tools.vitruv.extensions.dslsruntime.response

import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.command.ChangePropagationResult

public interface IResponseRealization {
	public def ChangePropagationResult applyEvent(EChange change, CorrespondenceModel correspondenceModel);
	public def boolean checkPrecondition(EChange change);
}

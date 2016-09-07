package tools.vitruv.extensions.dslsruntime.response

import tools.vitruv.framework.util.command.TransformationResult
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.correspondence.CorrespondenceModel

public interface IResponseRealization {
	public def TransformationResult applyEvent(EChange change, CorrespondenceModel correspondenceModel);
	public def boolean checkPrecondition(EChange change);
}

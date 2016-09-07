package tools.vitruvius.extensions.dslsruntime.response

import tools.vitruvius.framework.util.command.TransformationResult
import tools.vitruvius.framework.change.echange.EChange
import tools.vitruvius.framework.correspondence.CorrespondenceModel

public interface IResponseRealization {
	public def TransformationResult applyEvent(EChange change, CorrespondenceModel correspondenceModel);
	public def boolean checkPrecondition(EChange change);
}

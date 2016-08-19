package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel

public interface IResponseRealization {
	public def TransformationResult applyEvent(EChange change, CorrespondenceModel correspondenceInstance);
	public def boolean checkPrecondition(EChange change);
}

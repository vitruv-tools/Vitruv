package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.changes.echange.EChange
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel

public interface IResponseRealization {
	public def TransformationResult applyEvent(EChange change, CorrespondenceModel correspondenceModel);
	public def boolean checkPrecondition(EChange change);
}

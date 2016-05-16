package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence

public interface IResponseRealization {
	public def TransformationResult applyEvent(EChange change, CorrespondenceInstance<Correspondence> correspondenceInstance);
	public def boolean checkPrecondition(EChange change);
}

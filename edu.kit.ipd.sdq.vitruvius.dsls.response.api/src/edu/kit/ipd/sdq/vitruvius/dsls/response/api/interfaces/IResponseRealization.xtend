package edu.kit.ipd.sdq.vitruvius.dsls.response.api.interfaces

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard

public interface IResponseRealization {//<T extends Event> {
	public def TransformationResult applyEvent(EChange event, Blackboard blackboard);
	public def boolean checkPrecondition(EChange event);
}

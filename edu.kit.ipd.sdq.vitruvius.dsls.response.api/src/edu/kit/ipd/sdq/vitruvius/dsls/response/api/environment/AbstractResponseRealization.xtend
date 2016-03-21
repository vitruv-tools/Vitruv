package edu.kit.ipd.sdq.vitruvius.dsls.response.api.environment

import edu.kit.ipd.sdq.vitruvius.dsls.response.api.interfaces.IResponseRealization
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.dsls.response.api.runtime.ResponseExecutionState

abstract class AbstractResponseRealization extends Loggable implements IResponseRealization {
	protected val UserInteracting userInteracting;
	protected ResponseExecutionState executionState;
	
	public new(UserInteracting userInteracting) {
		this.userInteracting = userInteracting;
	}
	
	override applyEvent(EChange change, Blackboard blackboard) {
		getLogger().debug("Called response " + this.getClass().getSimpleName() + " with event: " + change);
    	
    	this.executionState = new ResponseExecutionState(userInteracting, blackboard, new TransformationResult());
    	
    	if (checkPrecondition(change)) {	
			executeResponse(change);
		}
		
		return executionState.transformationResult;
	}
	
	protected def void executeResponse(EChange change);
	
}
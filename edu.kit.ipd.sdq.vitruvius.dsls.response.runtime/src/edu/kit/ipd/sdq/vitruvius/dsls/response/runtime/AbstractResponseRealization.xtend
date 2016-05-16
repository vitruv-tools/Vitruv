package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.IResponseRealization
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.structure.CallHierarchyHaving
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance

abstract class AbstractResponseRealization extends CallHierarchyHaving implements IResponseRealization {
	protected val UserInteracting userInteracting;
	protected ResponseExecutionState executionState;
	
	public new(UserInteracting userInteracting) {
		this.userInteracting = userInteracting;
	}
	
	override applyEvent(EChange change, CorrespondenceInstance<Correspondence> correspondenceInstance) {
		getLogger().debug("Called response " + this.getClass().getSimpleName() + " with event: " + change);
    	
    	this.executionState = new ResponseExecutionState(userInteracting, correspondenceInstance, new TransformationResult());
    	
    	if (checkPrecondition(change)) {	
			executeResponse(change);
		}
		
		return executionState.transformationResult;
	}
	
	protected def void executeResponse(EChange change);
	
}
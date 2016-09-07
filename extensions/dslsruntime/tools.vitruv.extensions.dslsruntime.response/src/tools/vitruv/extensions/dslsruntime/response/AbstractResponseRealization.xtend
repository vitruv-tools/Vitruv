package tools.vitruv.extensions.dslsruntime.response

import tools.vitruv.extensions.dslsruntime.response.IResponseRealization
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.util.command.TransformationResult
import tools.vitruv.extensions.dslsruntime.response.structure.CallHierarchyHaving
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.correspondence.CorrespondenceModel

abstract class AbstractResponseRealization extends CallHierarchyHaving implements IResponseRealization {
	protected val UserInteracting userInteracting;
	protected ResponseExecutionState executionState;
	
	public new(UserInteracting userInteracting) {
		this.userInteracting = userInteracting;
	}
	
	override applyEvent(EChange change, CorrespondenceModel correspondenceModel) {
		getLogger().debug("Called response " + this.getClass().getSimpleName() + " with event: " + change);
    	
    	this.executionState = new ResponseExecutionState(userInteracting, correspondenceModel, new TransformationResult());
    	
    	if (checkPrecondition(change)) {	
			executeResponse(change);
		}
		
		return executionState.transformationResult;
	}
	
	protected def void executeResponse(EChange change);
	
}
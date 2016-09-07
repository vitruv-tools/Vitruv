package tools.vitruvius.extensions.dslsruntime.response

import tools.vitruvius.extensions.dslsruntime.response.IResponseRealization
import tools.vitruvius.framework.userinteraction.UserInteracting
import tools.vitruvius.framework.util.command.TransformationResult
import tools.vitruvius.extensions.dslsruntime.response.structure.CallHierarchyHaving
import tools.vitruvius.framework.change.echange.EChange
import tools.vitruvius.framework.correspondence.CorrespondenceModel

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
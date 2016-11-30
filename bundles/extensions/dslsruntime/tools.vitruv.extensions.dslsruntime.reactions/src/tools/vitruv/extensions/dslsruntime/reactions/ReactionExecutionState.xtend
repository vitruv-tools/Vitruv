package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.command.ChangePropagationResult

class ReactionExecutionState {
	private val UserInteracting userInteracting;
	private val CorrespondenceModel correspondenceModel;
	private val ChangePropagationResult transformationResult;
	
	public new(UserInteracting userInteracting, CorrespondenceModel correspondenceModel, ChangePropagationResult transformationResult) {
		this.userInteracting = userInteracting;
		this.correspondenceModel = correspondenceModel;
		this.transformationResult = transformationResult;
	}
	
	public def UserInteracting getUserInteracting() {
		return this.userInteracting;
	}
	
	public def CorrespondenceModel getCorrespondenceModel() {
		return this.correspondenceModel;
	}
	
	public def ChangePropagationResult getTransformationResult() {
		return transformationResult;
	}
}
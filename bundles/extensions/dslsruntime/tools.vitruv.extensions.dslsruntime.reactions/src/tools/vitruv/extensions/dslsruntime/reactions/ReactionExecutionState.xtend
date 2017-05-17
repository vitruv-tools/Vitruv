package tools.vitruv.extensions.dslsruntime.reactions

import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.correspondence.CorrespondenceModel
import tools.vitruv.framework.util.command.ChangePropagationResult

class ReactionExecutionState {
	val UserInteracting userInteracting
	val CorrespondenceModel correspondenceModel
	val ChangePropagationResult transformationResult

	new(UserInteracting userInteracting, CorrespondenceModel correspondenceModel,
		ChangePropagationResult transformationResult) {
		this.userInteracting = userInteracting
		this.correspondenceModel = correspondenceModel
		this.transformationResult = transformationResult
	}

	def UserInteracting getUserInteracting() {
		userInteracting
	}

	def CorrespondenceModel getCorrespondenceModel() {
		correspondenceModel
	}

	def ChangePropagationResult getTransformationResult() {
		transformationResult
	}
}

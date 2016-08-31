package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import edu.kit.ipd.sdq.vitruvius.framework.util.command.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.userinteraction.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.correspondence.CorrespondenceModel

class ResponseExecutionState {
	private val UserInteracting userInteracting;
	private val CorrespondenceModel correspondenceModel;
	private val TransformationResult transformationResult;
	
	public new(UserInteracting userInteracting, CorrespondenceModel correspondenceModel, TransformationResult transformationResult) {
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
	
	public def TransformationResult getTransformationResult() {
		return transformationResult;
	}
}
package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel

class ResponseExecutionState {
	private val UserInteracting userInteracting;
	private val CorrespondenceModel correspondenceInstance;
	private val TransformationResult transformationResult;
	
	public new(UserInteracting userInteracting, CorrespondenceModel correspondenceInstance, TransformationResult transformationResult) {
		this.userInteracting = userInteracting;
		this.correspondenceInstance = correspondenceInstance;
		this.transformationResult = transformationResult;
	}
	
	public def UserInteracting getUserInteracting() {
		return this.userInteracting;
	}
	
	public def CorrespondenceModel getCorrespondenceInstance() {
		return this.correspondenceInstance;
	}
	
	public def TransformationResult getTransformationResult() {
		return transformationResult;
	}
}
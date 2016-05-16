package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence

class ResponseExecutionState {
	private val UserInteracting userInteracting;
	private val CorrespondenceInstance<Correspondence> correspondenceInstance;
	private val TransformationResult transformationResult;
	
	public new(UserInteracting userInteracting, CorrespondenceInstance<Correspondence> correspondenceInstance, TransformationResult transformationResult) {
		this.userInteracting = userInteracting;
		this.correspondenceInstance = correspondenceInstance;
		this.transformationResult = transformationResult;
	}
	
	public def UserInteracting getUserInteracting() {
		return this.userInteracting;
	}
	
	public def CorrespondenceInstance<Correspondence> getCorrespondenceInstance() {
		return this.correspondenceInstance;
	}
	
	public def TransformationResult getTransformationResult() {
		return transformationResult;
	}
}
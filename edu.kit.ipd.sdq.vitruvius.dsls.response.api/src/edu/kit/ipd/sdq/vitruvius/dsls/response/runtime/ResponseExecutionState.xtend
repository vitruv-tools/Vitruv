package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TransformationResult
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Blackboard
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting

class ResponseExecutionState {
	private val UserInteracting userInteracting;
	private val Blackboard blackboard;
	private val TransformationResult transformationResult;
	
	public new(UserInteracting userInteracting, Blackboard blackboard, TransformationResult transformationResult) {
		this.userInteracting = userInteracting;
		this.blackboard = blackboard;
		this.transformationResult = transformationResult;
	}
	
	public def UserInteracting getUserInteracting() {
		return this.userInteracting;
	}
	
	public def Blackboard getBlackboard() {
		return this.blackboard;
	}
	
	public def TransformationResult getTransformationResult() {
		return transformationResult;
	}
}
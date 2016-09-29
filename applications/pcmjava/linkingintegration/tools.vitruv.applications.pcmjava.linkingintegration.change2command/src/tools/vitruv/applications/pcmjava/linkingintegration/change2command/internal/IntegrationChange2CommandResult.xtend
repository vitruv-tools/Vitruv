package tools.vitruv.applications.pcmjava.linkingintegration.change2command.internal

import tools.vitruv.framework.util.command.TransformationResult

class IntegrationChange2CommandResult {
	
	private TransformationResult propagationResult;
	
	new(TransformationResult propagationResult) {
		this.propagationResult = propagationResult;
	}
	
	def isIntegrationChange() {
		return propagationResult != null;
	}
	
	def getPropagationResult() {
		return propagationResult
	}
}

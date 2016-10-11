package tools.vitruv.applications.pcmjava.linkingintegration.change2command.internal

import tools.vitruv.framework.util.command.ChangePropagationResult

class IntegrationChange2CommandResult {
	
	private ChangePropagationResult propagationResult;
	
	new(ChangePropagationResult propagationResult) {
		this.propagationResult = propagationResult;
	}
	
	def isIntegrationChange() {
		return propagationResult != null;
	}
	
	def getPropagationResult() {
		return propagationResult
	}
}

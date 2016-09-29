package tools.vitruv.framework.change.processing

import java.util.List
import java.util.ArrayList
import tools.vitruv.framework.change.description.TransactionalChange
import tools.vitruv.framework.util.command.TransformationResult

class ChangeProcessorResult {
	val TransactionalChange resultingChange;
	val List<TransformationResult> propagationResults;
	
	new(TransactionalChange resultingChange, List<TransformationResult> propagationResults) {
		this.resultingChange = resultingChange;
		this.propagationResults = propagationResults;
	}
	
	def TransactionalChange getResultingChange() {
		return resultingChange;
	}
	
	def List<TransformationResult> getPropagationResults() {
		return new ArrayList<TransformationResult>(propagationResults);
	}
	
}
package tools.vitruv.framework.change.processing

import java.util.List
import java.util.ArrayList
import tools.vitruv.framework.util.command.VitruviusRecordingCommand
import tools.vitruv.framework.change.description.TransactionalChange

class ChangeProcessorResult {
	val TransactionalChange resultingChange;
	val List<VitruviusRecordingCommand> generatedCommands;
	
	new(TransactionalChange resultingChange, List<VitruviusRecordingCommand> generatedCommands) {
		this.resultingChange = resultingChange;
		this.generatedCommands = generatedCommands;
	}
	
	def TransactionalChange getResultingChange() {
		return resultingChange;
	}
	
	def List<VitruviusRecordingCommand> getGeneratedCommands() {
		return new ArrayList<VitruviusRecordingCommand>(generatedCommands);
	}
	
}
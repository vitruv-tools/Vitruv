package tools.vitruvius.framework.change.processing

import java.util.List
import java.util.ArrayList
import tools.vitruvius.framework.change.description.ConcreteChange
import tools.vitruvius.framework.util.command.VitruviusRecordingCommand

class ChangeProcessorResult {
	val ConcreteChange resultingChange;
	val List<VitruviusRecordingCommand> generatedCommands;
	
	new(ConcreteChange resultingChange, List<VitruviusRecordingCommand> generatedCommands) {
		this.resultingChange = resultingChange;
		this.generatedCommands = generatedCommands;
	}
	
	def ConcreteChange getResultingChange() {
		return resultingChange;
	}
	
	def List<VitruviusRecordingCommand> getGeneratedCommands() {
		return new ArrayList<VitruviusRecordingCommand>(generatedCommands);
	}
	
}
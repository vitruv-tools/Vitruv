package edu.kit.ipd.sdq.vitruvius.codeintegration.change2command.internal

import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.util.command.VitruviusRecordingCommand

class IntegrationChange2CommandResult {
	
	private List<? extends VitruviusRecordingCommand> commands
	
	new(List<? extends VitruviusRecordingCommand> commands) {
		this.commands = commands
	}
	
	def isIntegrationChange() {
		return commands.size > 0
	}
	
	def getCommands() {
		return commands
	}
}

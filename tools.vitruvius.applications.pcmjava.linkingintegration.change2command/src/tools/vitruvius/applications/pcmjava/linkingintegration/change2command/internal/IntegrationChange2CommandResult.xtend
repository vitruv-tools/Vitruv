package tools.vitruvius.applications.pcmjava.linkingintegration.change2command.internal

import java.util.List
import tools.vitruvius.framework.util.command.VitruviusRecordingCommand

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

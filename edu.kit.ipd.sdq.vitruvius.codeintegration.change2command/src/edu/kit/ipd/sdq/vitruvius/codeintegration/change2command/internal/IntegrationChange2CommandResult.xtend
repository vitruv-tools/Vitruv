package edu.kit.ipd.sdq.vitruvius.codeintegration.change2command.internal

import org.eclipse.emf.common.command.Command
import java.util.List

class IntegrationChange2CommandResult {
	
	private List<? extends Command> commands
	
	new(List<? extends Command> commands) {
		this.commands = commands
	}
	
	def isIntegrationChange() {
		return commands.size > 0
	}
	
	def getCommands() {
		return commands
	}
}

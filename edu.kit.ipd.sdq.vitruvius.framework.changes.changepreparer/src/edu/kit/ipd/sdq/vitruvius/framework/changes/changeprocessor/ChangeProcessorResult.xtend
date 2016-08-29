package edu.kit.ipd.sdq.vitruvius.framework.changes.changeprocessor

import org.eclipse.emf.common.command.Command
import java.util.List
import java.util.ArrayList
import edu.kit.ipd.sdq.vitruvius.framework.changes.changedescription.ConcreteChange

class ChangeProcessorResult {
	val ConcreteChange resultingChange;
	val List<Command> generatedCommands;
	
	new(ConcreteChange resultingChange, List<Command> generatedCommands) {
		this.resultingChange = resultingChange;
		this.generatedCommands = generatedCommands;
	}
	
	def ConcreteChange getResultingChange() {
		return resultingChange;
	}
	
	def List<Command> getGeneratedCommands() {
		return new ArrayList<Command>(generatedCommands);
	}
	
}
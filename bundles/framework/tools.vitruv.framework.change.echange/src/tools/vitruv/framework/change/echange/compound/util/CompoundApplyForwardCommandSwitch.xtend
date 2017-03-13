package tools.vitruv.framework.change.echange.compound.util

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.command.Command
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.util.ApplyForwardCommandSwitch

class CompoundApplyForwardCommandSwitch extends CompoundSwitch<List<Command>> {
	def public List<Command> caseCompoundEChange(CompoundEChange object) {
		val commands = new ArrayList<Command>
		for (AtomicEChange change : object.atomicChanges) {
			commands.addAll((new ApplyForwardCommandSwitch()).doSwitch(change))
		}
		return commands	
	}	
}
package tools.vitruv.framework.change.echange.compound.util

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.command.Command
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.util.ApplyBackwardCommandSwitch

/**
 * Switch to create commands for all EChange classes of the compound package.
 * The commands applies the EChanges backward.
 */
class CompoundApplyBackwardCommandSwitch extends CompoundSwitch<List<Command>> {
	/**
	 * Create commands to apply a compound change backward.
	 * @param object The change which commands should be created.
	 */
	def public List<Command> caseCompoundEChange(CompoundEChange object) {
		val commands = new ArrayList<Command>
		for (AtomicEChange change : object.atomicChanges.reverse) {
			commands.addAll((new ApplyBackwardCommandSwitch()).doSwitch(change))
		}
		return commands	
	}	
}
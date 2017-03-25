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
	private static CompoundApplyBackwardCommandSwitch instance;
	
	private new() {}
	
	/**
	 * Gets the singleton of the switch.
	 * @return The singleton instance.
	 */
	public static def CompoundApplyBackwardCommandSwitch getInstance() {
		if (instance == null) {
			instance = new CompoundApplyBackwardCommandSwitch();
		}
		return instance;
	}
	
	/**
	 * Create commands to apply a compound change backward.
	 * @param object The change which commands should be created.
	 */
	override public List<Command> caseCompoundEChange(CompoundEChange object) {
		val commands = new ArrayList<Command>
		for (AtomicEChange change : object.atomicChanges.reverseView) {
			commands.addAll(ApplyBackwardCommandSwitch.instance.doSwitch(change))
		}
		return commands	
	}	
}
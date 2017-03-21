package tools.vitruv.framework.change.echange.util

import java.util.List
import org.eclipse.emf.common.command.Command
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.util.CompoundApplyBackwardCommandSwitch
import tools.vitruv.framework.change.echange.eobject.util.EObjectApplyBackwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.util.FeatureApplyBackwardCommandSwitch
import tools.vitruv.framework.change.echange.root.util.RootApplyBackwardCommandSwitch

/**
 * Switch to create commands for all EChange classes of the echange package.
 * The commands applies the EChanges backward.
 */
public class ApplyBackwardCommandSwitch extends EChangeSwitch<List<Command>> {
	private static ApplyBackwardCommandSwitch instance;
	
	private new() {}
	
	/**
	 * Gets the singleton of the switch.
	 * @return The singleton instance.
	 */
	public static def ApplyBackwardCommandSwitch getInstance() {
		if (instance == null) {
			instance = new ApplyBackwardCommandSwitch();
		}
		return instance;
	}
	
	/**
	 * Create commands to apply a {@link EChange} change backward.
	 * @param object The change which commands should be created.
	 */	
	def public List<Command> caseEChange(EChange object) {
		return CompoundApplyBackwardCommandSwitch.instance.doSwitch(object)
	}
	
	/**
	 * Create commands to apply a {@link AtomicEChange} change backward.
	 * @param object The change which commands should be created.
	 */	
	def public List<Command> caseAtomicEChange(AtomicEChange object) {
		var result = FeatureApplyBackwardCommandSwitch.instance.doSwitch(object)
		if (result == null) {
			result = RootApplyBackwardCommandSwitch.instance.doSwitch(object)
		}
		if (result == null) {
			result = EObjectApplyBackwardCommandSwitch.instance.doSwitch(object)
		}
		return result
	}
}
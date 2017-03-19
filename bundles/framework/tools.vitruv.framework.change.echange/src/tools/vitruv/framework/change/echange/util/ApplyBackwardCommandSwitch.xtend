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
	/**
	 * Create commands to apply a {@link EChange} change backward.
	 * @param object The change which commands should be created.
	 */	
	def public List<Command> caseEChange(EChange object) {
		return (new CompoundApplyBackwardCommandSwitch()).doSwitch(object)
	}
	
	/**
	 * Create commands to apply a {@link AtomicEChange} change backward.
	 * @param object The change which commands should be created.
	 */	
	def public List<Command> caseAtomicEChange(AtomicEChange object) {
		var result = (new FeatureApplyBackwardCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new RootApplyBackwardCommandSwitch()).doSwitch(object)
		}
		if (result == null) {
			result = (new EObjectApplyBackwardCommandSwitch()).doSwitch(object)
		}
		return result
	}
}
package tools.vitruv.framework.change.echange.util;

import java.util.List
import org.eclipse.emf.common.command.Command
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.util.CompoundApplyForwardCommandSwitch
import tools.vitruv.framework.change.echange.eobject.util.EObjectApplyForwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.util.FeatureApplyForwardCommandSwitch
import tools.vitruv.framework.change.echange.root.util.RootApplyForwardCommandSwitch

/**
 * Switch to create commands for all EChange classes of the echange package.
 * The commands applies the EChanges forward.
 */
public class ApplyForwardCommandSwitch extends EChangeSwitch<List<Command>>{
	/**
	 * Create commands to apply a {@link EChange} change forward.
	 * @param object The change which commands should be created.
	 */
	def public List<Command> caseEChange(EChange object) {
		return (new CompoundApplyForwardCommandSwitch()).doSwitch(object)
	}
	
	/**
	 * Create commands to apply a {@link AtomicEChange} change forward.
	 * @param object The change which commands should be created.
	 */
	def public List<Command> caseAtomicEChange(AtomicEChange object) {
		var result = (new FeatureApplyForwardCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new RootApplyForwardCommandSwitch()).doSwitch(object)
		}
		if (result == null) {
			result = (new EObjectApplyForwardCommandSwitch()).doSwitch(object)
		}
		return result		
	}
}
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
	private static ApplyForwardCommandSwitch instance;
	
	private new() {}
	
	/**
	 * Gets the singleton of the switch.
	 * @return The singleton instance.
	 */
	public static def ApplyForwardCommandSwitch getInstance() {
		if (instance == null) {
			instance = new ApplyForwardCommandSwitch();
		}
		return instance;
	}
	
	/**
	 * Create commands to apply a {@link EChange} change forward.
	 * @param object The change which commands should be created.
	 */
	override public List<Command> caseEChange(EChange object) {
		return CompoundApplyForwardCommandSwitch.instance.doSwitch(object)
	}
	
	/**
	 * Create commands to apply a {@link AtomicEChange} change forward.
	 * @param object The change which commands should be created.
	 */
	override public List<Command> caseAtomicEChange(AtomicEChange object) {
		var result = FeatureApplyForwardCommandSwitch.instance.doSwitch(object)
		if (result == null) {
			result = RootApplyForwardCommandSwitch.instance.doSwitch(object)
		}
		if (result == null) {
			result = EObjectApplyForwardCommandSwitch.instance.doSwitch(object)
		}
		return result		
	}
}
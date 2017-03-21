package tools.vitruv.framework.change.echange.feature.util

import java.util.List
import org.eclipse.emf.common.command.Command
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.UpdateMultiValuedFeatureEChange
import tools.vitruv.framework.change.echange.feature.attribute.util.AttributeApplyBackwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.list.util.ListApplyBackwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.reference.util.ReferenceApplyBackwardCommandSwitch

/**
 * Switch to create commands for all EChange classes of the feature package.
 * The commands applies the EChanges backward.
 */
public class FeatureApplyBackwardCommandSwitch extends FeatureSwitch<List<Command>> {
	private static FeatureApplyBackwardCommandSwitch instance;
	
	private new() {}
	
	/**
	 * Gets the singleton of the switch.
	 * @return The singleton instance.
	 */
	public static def FeatureApplyBackwardCommandSwitch getInstance() {
		if (instance == null) {
			instance = new FeatureApplyBackwardCommandSwitch();
		}
		return instance;
	}
	
	/**
	 * Create commands to apply a {@link FeatureEChange} change backward.
	 * @param object The change which commands should be created.
	 */	
	def public List<Command> caseFeatureEChange(FeatureEChange object) {
		var result = AttributeApplyBackwardCommandSwitch.instance.doSwitch(object)
		if (result == null) {
			result = ReferenceApplyBackwardCommandSwitch.instance.doSwitch(object)
		}
		return result
	}
	
	/**
	 * Create commands to apply a {@link UpdateMultiValuedFeatureEChange} change backward.
	 * @param object The change which commands should be created.
	 */	
	def public List<Command> caseUpdateMultiValuedFeatureEChange(UpdateMultiValuedFeatureEChange object) {
		var result = ListApplyBackwardCommandSwitch.instance.doSwitch(object)
		return result	
	}
}	
package tools.vitruv.framework.change.echange.feature.util

import java.util.List
import org.eclipse.emf.common.command.Command
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.UpdateMultiValuedFeatureEChange
import tools.vitruv.framework.change.echange.feature.attribute.util.AttributeApplyForwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.list.util.ListApplyForwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.reference.util.ReferenceApplyForwardCommandSwitch

/**
 * Switch to create commands for all EChange classes of the feature package.
 * The commands applies the EChanges forward.
 */
public class FeatureApplyForwardCommandSwitch extends FeatureSwitch<List<Command>> {
	/**
	 * Create commands to apply a {@link FeatureEChange} change forward.
	 * @param object The change which commands should be created.
	 */
	def public List<Command> caseFeatureEChange(FeatureEChange object) {
		var result = (new AttributeApplyForwardCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new ReferenceApplyForwardCommandSwitch()).doSwitch(object)
		}
		return result
	}
	
	/**
	 * Create commands to apply a {@link UpdateMultiValuedFeatureEChange} change forward.
	 * @param object The change which commands should be created.
	 */
	def public List<Command> caseUpdateMultiValuedFeatureEChange(UpdateMultiValuedFeatureEChange object) {
		var result = (new ListApplyForwardCommandSwitch()).doSwitch(object)
		return result	
	}
}
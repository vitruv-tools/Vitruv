package tools.vitruv.framework.change.echange.feature.util

import java.util.List
import org.eclipse.emf.common.command.Command
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.UpdateMultiValuedFeatureEChange
import tools.vitruv.framework.change.echange.feature.attribute.util.AttributeApplyBackwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.list.util.ListApplyBackwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.reference.util.ReferenceApplyBackwardCommandSwitch

public class FeatureApplyBackwardCommandSwitch extends FeatureSwitch<List<Command>> {
	def public List<Command> caseFeatureEChange(FeatureEChange object) {
		var result = (new AttributeApplyBackwardCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new ReferenceApplyBackwardCommandSwitch()).doSwitch(object)
		}
		return result
	}
	def public List<Command> caseUpdateMultiValuedFeatureEChange(UpdateMultiValuedFeatureEChange object) {
		var result = (new ListApplyBackwardCommandSwitch()).doSwitch(object)
		return result	
	}
}	
package tools.vitruv.framework.change.echange.feature.util

import java.util.List
import org.eclipse.emf.common.command.Command
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.UpdateMultiValuedFeatureEChange
import tools.vitruv.framework.change.echange.feature.attribute.util.AttributeApplyForwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.list.util.ListApplyForwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.reference.util.ReferenceApplyForwardCommandSwitch

public class FeatureApplyForwardCommandSwitch extends FeatureSwitch<List<Command>> {
	def public List<Command> caseFeatureEChange(FeatureEChange object) {
		var result = (new AttributeApplyForwardCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new ReferenceApplyForwardCommandSwitch()).doSwitch(object)
		}
		return result
	}
	def public List<Command> caseUpdateMultiValuedFeatureEChange(UpdateMultiValuedFeatureEChange object) {
		var result = (new ListApplyForwardCommandSwitch()).doSwitch(object)
		return result	
	}
}
package tools.vitruv.framework.change.echange.feature.util

import java.util.List
import org.eclipse.emf.common.command.Command
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.UpdateMultiValuedFeatureEChange
import tools.vitruv.framework.change.echange.feature.attribute.util.AttributeRevertCommandSwitch
import tools.vitruv.framework.change.echange.feature.list.util.ListRevertCommandSwitch
import tools.vitruv.framework.change.echange.feature.reference.util.ReferenceRevertCommandSwitch

public class FeatureRevertCommandSwitch extends FeatureSwitch<List<Command>> {
	def public List<Command> caseFeatureEChange(FeatureEChange object) {
		var result = (new AttributeRevertCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new ReferenceRevertCommandSwitch()).doSwitch(object)
		}
		return result
	}
	def public List<Command> caseUpdateMultiValuedFeatureEChange(UpdateMultiValuedFeatureEChange object) {
		var result = (new ListRevertCommandSwitch()).doSwitch(object)
		return result	
	}
}	
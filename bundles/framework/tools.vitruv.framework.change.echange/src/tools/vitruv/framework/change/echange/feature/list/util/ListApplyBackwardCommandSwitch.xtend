package tools.vitruv.framework.change.echange.feature.list.util

import java.util.List
import org.eclipse.emf.common.command.Command
import tools.vitruv.framework.change.echange.feature.attribute.util.AttributeApplyBackwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange
import tools.vitruv.framework.change.echange.feature.reference.util.ReferenceApplyBackwardCommandSwitch

public class ListApplyBackwardCommandSwitch extends ListSwitch<List<Command>> {
	def public List<Command> caseRemoveFromListEChange(RemoveFromListEChange object) {
		var result = (new AttributeApplyBackwardCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new ReferenceApplyBackwardCommandSwitch()).doSwitch(object)
		}
		return result
	}
}
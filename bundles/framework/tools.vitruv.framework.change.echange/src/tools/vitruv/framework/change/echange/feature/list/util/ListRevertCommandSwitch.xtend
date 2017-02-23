package tools.vitruv.framework.change.echange.feature.list.util

import org.eclipse.emf.common.command.Command
import tools.vitruv.framework.change.echange.feature.attribute.util.AttributeRevertCommandSwitch
import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange
import tools.vitruv.framework.change.echange.feature.reference.util.ReferenceRevertCommandSwitch

public class ListRevertCommandSwitch extends ListSwitch<Command> {
	def public Command caseRemoveFromListEChange(RemoveFromListEChange object) {
		var result = (new AttributeRevertCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new ReferenceRevertCommandSwitch()).doSwitch(object)
		}
		return result
	}
}
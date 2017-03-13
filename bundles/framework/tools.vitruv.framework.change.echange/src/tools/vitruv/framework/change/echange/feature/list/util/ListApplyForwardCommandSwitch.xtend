package tools.vitruv.framework.change.echange.feature.list.util

import java.util.List
import org.eclipse.emf.common.command.Command
import tools.vitruv.framework.change.echange.feature.attribute.util.AttributeApplyForwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange
import tools.vitruv.framework.change.echange.feature.reference.util.ReferenceApplyForwardCommandSwitch

public class ListApplyForwardCommandSwitch extends ListSwitch<List<Command>> {
	def public List<Command> caseRemoveFromListEChange(RemoveFromListEChange object) {
		var result = (new AttributeApplyForwardCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new ReferenceApplyForwardCommandSwitch()).doSwitch(object)
		}
		return result
	}
}


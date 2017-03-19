package tools.vitruv.framework.change.echange.feature.list.util

import java.util.List
import org.eclipse.emf.common.command.Command
import tools.vitruv.framework.change.echange.feature.attribute.util.AttributeApplyBackwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange
import tools.vitruv.framework.change.echange.feature.reference.util.ReferenceApplyBackwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.list.InsertInListEChange

/**
 * Switch to create commands for all EChange classes of the list package.
 * The commands applies the EChanges backward.
 */
public class ListApplyBackwardCommandSwitch extends ListSwitch<List<Command>> {
	/**
	 * Create commands to apply a {@link RemoveFromListEChange} change backward.
	 * @param object The change which commands should be created.
	 */
	def public List<Command> caseRemoveFromListEChange(RemoveFromListEChange object) {
		var result = (new AttributeApplyBackwardCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new ReferenceApplyBackwardCommandSwitch()).doSwitch(object)
		}
		return result
	}
	
	/**
	 * Create commands to apply a {@link InsertInListEChange} change backward.
	 * @param object The change which commands should be created.
	 */	
	def public List<Command> caseInsertInListEChange(InsertInListEChange object) {
		var result = (new AttributeApplyBackwardCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new ReferenceApplyBackwardCommandSwitch()).doSwitch(object)
		}
		return result
	}
}
package tools.vitruv.framework.change.echange.feature.list.util

import java.util.List
import org.eclipse.emf.common.command.Command
import tools.vitruv.framework.change.echange.feature.attribute.util.AttributeApplyForwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.list.InsertInListEChange
import tools.vitruv.framework.change.echange.feature.list.RemoveFromListEChange
import tools.vitruv.framework.change.echange.feature.reference.util.ReferenceApplyForwardCommandSwitch

/**
 * Switch to create commands for all EChange classes of the list package.
 * The commands applies the EChanges forward.
 */
public class ListApplyForwardCommandSwitch extends ListSwitch<List<Command>> {
	/**
	 * Create commands to apply a {@link RemoveFromListEChange} change forward.
	 * @param object The change which commands should be created.
	 */
	def public List<Command> caseRemoveFromListEChange(RemoveFromListEChange object) {
		var result = (new AttributeApplyForwardCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new ReferenceApplyForwardCommandSwitch()).doSwitch(object)
		}
		return result
	}
	
	/**
	 * Create commands to apply a {@link InsertInListEChange} change forward.
	 * @param object The change which commands should be created.
	 */
	def public List<Command> caseInsertInListEChange(InsertInListEChange object) {
		var result = (new AttributeApplyForwardCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new ReferenceApplyForwardCommandSwitch()).doSwitch(object)
		}
		return result
	}
}


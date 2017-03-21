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
	private static ListApplyForwardCommandSwitch instance;
	
	private new() {}
	
	/**
	 * Gets the singleton of the switch.
	 * @return The singleton instance.
	 */
	public static def ListApplyForwardCommandSwitch getInstance() {
		if (instance == null) {
			instance = new ListApplyForwardCommandSwitch();
		}
		return instance;
	}
	
	/**
	 * Create commands to apply a {@link RemoveFromListEChange} change forward.
	 * @param object The change which commands should be created.
	 */
	def public List<Command> caseRemoveFromListEChange(RemoveFromListEChange object) {
		var result = AttributeApplyForwardCommandSwitch.instance.doSwitch(object)
		if (result == null) {
			result = ReferenceApplyForwardCommandSwitch.instance.doSwitch(object)
		}
		return result
	}
	
	/**
	 * Create commands to apply a {@link InsertInListEChange} change forward.
	 * @param object The change which commands should be created.
	 */
	def public List<Command> caseInsertInListEChange(InsertInListEChange object) {
		var result = AttributeApplyForwardCommandSwitch.instance.doSwitch(object)
		if (result == null) {
			result = ReferenceApplyForwardCommandSwitch.instance.doSwitch(object)
		}
		return result
	}
}


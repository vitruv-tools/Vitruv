package tools.vitruv.framework.change.echange.feature.attribute.util

import java.util.Collections
import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.edit.command.AddCommand
import org.eclipse.emf.edit.command.SetCommand
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.util.EChangeUtil
import tools.vitruv.framework.util.command.RemoveAtCommand

/**
 * Switch to create commands for all EChange classes of the attribute package.
 * The commands applies the EChanges forward.
 */
public class AttributeApplyForwardCommandSwitch extends AttributeSwitch<List<Command>> {
	/**
	 * Create commands to apply a {@link InsertEAttributeValue} change forward.
	 * @param object The change which commands should be created.
	 */
	 override public List<Command> caseInsertEAttributeValue(InsertEAttributeValue object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		return Collections.singletonList(AddCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.newValue, object.index))
	}	

	/**
	 * Create commands to apply a {@link RemoveEAttributeValue} change forward.
	 * @param object The change which commands should be created.
	 */
	override public List<Command> caseRemoveEAttributeValue(RemoveEAttributeValue object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		return Collections.singletonList(RemoveAtCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.oldValue, object.index))
	}
	
	/**
	 * Create commands to apply a {@link ReplaceSingleValuedEAttribute} change forward.
	 * @param object The change which commands should be created.
	 */
	override public List<Command> caseReplaceSingleValuedEAttribute(ReplaceSingleValuedEAttribute object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		return Collections.singletonList(SetCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.newValue))
	}	
}
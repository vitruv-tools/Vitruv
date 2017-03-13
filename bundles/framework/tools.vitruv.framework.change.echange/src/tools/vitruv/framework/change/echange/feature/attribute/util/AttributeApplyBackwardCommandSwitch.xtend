package tools.vitruv.framework.change.echange.feature.attribute.util

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.edit.command.AddCommand
import org.eclipse.emf.edit.command.SetCommand
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.util.EChangeUtil
import tools.vitruv.framework.util.command.RemoveAtCommand

public class AttributeApplyBackwardCommandSwitch extends AttributeSwitch<List<Command>> {
	override public List<Command> caseInsertEAttributeValue(InsertEAttributeValue object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		val commands = new ArrayList<Command>
		commands.add(RemoveAtCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.newValue, object.index))
		return commands
	}	
	override public List<Command> caseRemoveEAttributeValue(RemoveEAttributeValue object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		val commands = new ArrayList<Command>
		commands.add(AddCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.oldValue, object.index))
		return commands
	}
	override public List<Command> caseReplaceSingleValuedEAttribute(ReplaceSingleValuedEAttribute object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		val commands = new ArrayList<Command>
		commands.add(SetCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.oldValue))
		return commands
	}	
}
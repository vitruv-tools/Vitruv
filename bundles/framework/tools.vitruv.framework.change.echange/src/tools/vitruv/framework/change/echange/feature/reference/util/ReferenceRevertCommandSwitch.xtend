package tools.vitruv.framework.change.echange.feature.reference.util

import org.eclipse.emf.common.command.Command
import org.eclipse.emf.edit.command.AddCommand
import org.eclipse.emf.edit.command.SetCommand
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.util.EChangeUtil
import tools.vitruv.framework.util.command.RemoveAtCommand

public class ReferenceRevertCommandSwitch extends ReferenceSwitch<Command> {
	override public Command caseInsertEReference(InsertEReference object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		return RemoveAtCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.newValue, object.index);
	}	
	override public Command caseRemoveEReference(RemoveEReference object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		return AddCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.oldValue, object.index);
	}
	override public Command caseReplaceSingleValuedEReference(ReplaceSingleValuedEReference object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		return SetCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.oldValue)
	}	
}
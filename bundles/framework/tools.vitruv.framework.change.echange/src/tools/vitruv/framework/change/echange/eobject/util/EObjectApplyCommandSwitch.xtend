package tools.vitruv.framework.change.echange.eobject.util

import org.eclipse.emf.common.command.Command
import org.eclipse.emf.edit.command.AddCommand
import org.eclipse.emf.edit.command.RemoveCommand
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.util.EChangeUtil

class EObjectApplyCommandSwitch extends EobjectSwitch<Command> {
	override public Command caseCreateEObject(CreateEObject object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		return new AddCommand(editingDomain, object.stagingArea.contents, object.affectedEObject)		
	}
	override public Command caseDeleteEObject(DeleteEObject object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		return new RemoveCommand(editingDomain, object.stagingArea.contents, object.affectedEObject)
	}
}
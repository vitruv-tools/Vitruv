package tools.vitruv.framework.change.echange.eobject.util

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.edit.command.AddCommand
import org.eclipse.emf.edit.command.RemoveCommand
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.util.EChangeUtil

class EObjectApplyCommandSwitch extends EobjectSwitch<List<Command>> {
	override public List<Command> caseCreateEObject(CreateEObject object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		val commands = new ArrayList<Command>
		commands.add(new AddCommand(editingDomain, object.stagingArea.contents, object.affectedEObject))
		return commands
	}
	override public List<Command> caseDeleteEObject(DeleteEObject object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		val commands = new ArrayList<Command>
		commands.add(new RemoveCommand(editingDomain, object.stagingArea.contents, object.affectedEObject))
		return commands
	}
}
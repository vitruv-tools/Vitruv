package tools.vitruv.framework.change.echange.feature.reference.util

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.edit.command.AddCommand
import org.eclipse.emf.edit.command.RemoveCommand
import org.eclipse.emf.edit.command.SetCommand
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.util.EChangeUtil
import tools.vitruv.framework.change.echange.util.StagingArea
import tools.vitruv.framework.util.command.RemoveAtCommand

public class ReferenceApplyCommandSwitch extends ReferenceSwitch<List<Command>> {
	override public List<Command> caseInsertEReference(InsertEReference object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		val commands = new ArrayList<Command>
		
		if (object.containment) {
			// Remove from staging area first
			val stagingArea = StagingArea.getStagingArea(object.affectedEObject.eResource.resourceSet)
			commands.add(new RemoveCommand(editingDomain, stagingArea.contents, object.newValue))	
		}
		commands.add(AddCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.newValue, object.index))
		
		return commands
	}	
	override public List<Command> caseRemoveEReference(RemoveEReference object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		val commands = new ArrayList<Command>
		
		commands.add(RemoveAtCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.oldValue, object.index))
		if (object.containment) {
			// Insert in staging area after removing reference
			val stagingArea = StagingArea.getStagingArea(object.affectedEObject.eResource.resourceSet)
			commands.add(new AddCommand(editingDomain, stagingArea.contents, object.oldValue))
		}
		
		return commands
	}
	override public List<Command> caseReplaceSingleValuedEReference(ReplaceSingleValuedEReference object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		val stagingArea = StagingArea.getStagingArea(object.affectedEObject.eResource.resourceSet)
		val commands = new ArrayList<Command>

		if (object.containment) {
			commands.add(new RemoveCommand(editingDomain, stagingArea.contents, object.oldValue))
		}
		commands.add(SetCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.newValue))
		if (object.containment) {
			commands.add(new AddCommand(editingDomain, stagingArea.contents, object.newValue))
		}
		
		return commands
	}	
}
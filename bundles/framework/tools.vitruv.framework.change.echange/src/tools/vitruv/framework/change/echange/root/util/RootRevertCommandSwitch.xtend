package tools.vitruv.framework.change.echange.root.util

import org.eclipse.emf.common.command.Command
import org.eclipse.emf.edit.command.AddCommand
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.util.EChangeUtil
import tools.vitruv.framework.change.echange.util.StagingArea

public class RootRevertCommandSwitch extends RootSwitch<Command> {
	override public Command caseInsertRootEObject(InsertRootEObject object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.newValue)
		
		// Remove from resource and put in staging area
		// Will be automatically removed because object can only be in one resource.
		val stagingArea = StagingArea.getStagingArea(object.resource)
		return new AddCommand(editingDomain, stagingArea.contents, object.newValue)	
	}
	override public Command caseRemoveRootEObject(RemoveRootEObject object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.oldValue)
		
		// Remove from staging area and insert in resource
		// Will be automatically removed because object can only be in one resource.
		return new AddCommand(editingDomain, object.resource.getContents, object.oldValue, object.index)
	}	
}
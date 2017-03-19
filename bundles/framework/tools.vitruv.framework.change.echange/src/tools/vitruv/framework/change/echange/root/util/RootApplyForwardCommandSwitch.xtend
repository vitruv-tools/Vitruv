package tools.vitruv.framework.change.echange.root.util

import java.util.Collections
import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.edit.command.AddCommand
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.util.EChangeUtil
import tools.vitruv.framework.change.echange.util.StagingArea

/**
 * Switch to create commands for all EChange classes of the root package.
 * The commands applies the EChanges forward.
 */
public class RootApplyForwardCommandSwitch extends RootSwitch<List<Command>> {
	/**
	 * Create commands to apply a {@link InsertRootEObject} change forward.
	 * @param object The change which commands should be created.
	 */
	override public List<Command> caseInsertRootEObject(InsertRootEObject object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.newValue)
		// Remove from staging area and insert in resource.
		// Will be automatically removed because object can only be in one resource.	
		return Collections.singletonList(new AddCommand(editingDomain, object.resource.getContents, object.newValue, object.index))
	}
	
	/**
	 * Create commands to apply a {@link RemoveRootEObject} change forward.
	 * @param object The change which commands should be created.
	 */
	override public List<Command> caseRemoveRootEObject(RemoveRootEObject object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.oldValue)			
		// Remove from resource and insert in staging area
		// Will be automatically removed because object can only be in one resource.	
		val stagingArea = StagingArea.getStagingArea(object.resource)
		return Collections.singletonList(new AddCommand(editingDomain, stagingArea.contents, object.oldValue))
	}
}
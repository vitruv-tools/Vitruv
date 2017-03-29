package tools.vitruv.framework.change.echange.root.util

import java.util.Collections
import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.edit.command.AddCommand
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.change.echange.util.EChangeUtil
import tools.vitruv.framework.change.echange.resolve.StagingArea

/**
 * Switch to create commands for all EChange classes of the root package.
 * The commands applies the EChanges forward.
 */
public class RootApplyForwardCommandSwitch extends RootSwitch<List<Command>> {
	private static RootApplyForwardCommandSwitch instance;
	
	private new() {}
	
	/**
	 * Gets the singleton of the switch.
	 * @return The singleton instance.
	 */
	public static def RootApplyForwardCommandSwitch getInstance() {
		if (instance == null) {
			instance = new RootApplyForwardCommandSwitch();
		}
		return instance;
	}
		
	/**
	 * Create commands to apply a {@link InsertRootEObject} change forward.
	 * @param object The change which commands should be created.
	 */
	override public <A extends EObject> List<Command> caseInsertRootEObject(InsertRootEObject<A> object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.newValue)
		// Remove from staging area and insert in resource.
		// Will be automatically removed because object can only be in one resource.	
		return Collections.singletonList(new AddCommand(editingDomain, object.resource.getContents, object.newValue, object.index))
	}
	
	/**
	 * Create commands to apply a {@link RemoveRootEObject} change forward.
	 * @param object The change which commands should be created.
	 */
	override public <A extends EObject> List<Command> caseRemoveRootEObject(RemoveRootEObject<A> object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.oldValue)			
		// Remove from resource and insert in staging area
		// Will be automatically removed because object can only be in one resource.	
		val stagingArea = StagingArea.getStagingArea(object.resource)
		return Collections.singletonList(new AddCommand(editingDomain, stagingArea.contents, object.oldValue))
	}
}
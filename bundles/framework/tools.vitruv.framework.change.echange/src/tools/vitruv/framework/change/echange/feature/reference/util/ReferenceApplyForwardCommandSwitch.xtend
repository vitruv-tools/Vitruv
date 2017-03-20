package tools.vitruv.framework.change.echange.feature.reference.util

import java.util.Collections
import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.common.command.CompoundCommand
import org.eclipse.emf.edit.command.AddCommand
import org.eclipse.emf.edit.command.RemoveCommand
import org.eclipse.emf.edit.command.SetCommand
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.util.EChangeUtil
import tools.vitruv.framework.change.echange.util.StagingArea
import tools.vitruv.framework.change.echange.util.RemoveAtCommand

/**
 * Switch to create commands for all EChange classes of the reference package.
 * The commands applies the EChanges forward.
 */
public class ReferenceApplyForwardCommandSwitch extends ReferenceSwitch<List<Command>> {
	/**
	 * Create commands to apply a {@link InsertEReference} change forward.
	 * @param object The change which commands should be created.
	 */
	override public List<Command> caseInsertEReference(InsertEReference object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		val compoundCommand = new CompoundCommand()
		if (object.containment) {
			// Remove from staging area first
			val stagingArea = StagingArea.getStagingArea(object.affectedEObject.eResource.resourceSet)
			compoundCommand.append(new RemoveCommand(editingDomain, stagingArea.contents, object.newValue))	
		}
		compoundCommand.append(AddCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.newValue, object.index))
		return Collections.singletonList(compoundCommand)
	}	
	
	/**
	 * Create commands to apply a {@link RemoveEReference} change forward.
	 * @param object The change which commands should be created.
	 */
	override public List<Command> caseRemoveEReference(RemoveEReference object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		val compoundCommand = new CompoundCommand()
		
		compoundCommand.append(RemoveAtCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.oldValue, object.index))
		if (object.containment) {
			// Insert in staging area after removing reference
			val stagingArea = StagingArea.getStagingArea(object.affectedEObject.eResource.resourceSet)
			compoundCommand.append(new AddCommand(editingDomain, stagingArea.contents, object.oldValue))
		}
		
		return Collections.singletonList(compoundCommand)
	}
	
	/**
	 * Create commands to apply a {@link CreateEObject} change forward.
	 * @param object The change which commands should be created.
	 */
	override public List<Command> caseReplaceSingleValuedEReference(ReplaceSingleValuedEReference object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		val stagingArea = StagingArea.getStagingArea(object.affectedEObject.eResource.resourceSet)
		val compoundCommand = new CompoundCommand()

		if (object.containment && object.newValue != null) {
			compoundCommand.append(new RemoveCommand(editingDomain, stagingArea.contents, object.newValue))
		}
		compoundCommand.append(SetCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.newValue))
		if (object.containment && object.oldValue != null) {
			compoundCommand.append(new AddCommand(editingDomain, stagingArea.contents, object.oldValue))
		}
		
		return Collections.singletonList(compoundCommand)
	}	
}
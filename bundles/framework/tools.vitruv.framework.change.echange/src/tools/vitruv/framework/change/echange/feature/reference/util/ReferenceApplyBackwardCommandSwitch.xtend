package tools.vitruv.framework.change.echange.feature.reference.util

import java.util.Collections
import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.common.command.CompoundCommand
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.edit.command.AddCommand
import org.eclipse.emf.edit.command.RemoveCommand
import org.eclipse.emf.edit.command.SetCommand
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.util.EChangeUtil
import tools.vitruv.framework.change.echange.util.RemoveAtCommand
import tools.vitruv.framework.change.echange.util.StagingArea

/**
 * Switch to create commands for all EChange classes of the reference package.
 * The commands applies the EChanges backward.
 */
public class ReferenceApplyBackwardCommandSwitch extends ReferenceSwitch<List<Command>> {
	private static ReferenceApplyBackwardCommandSwitch instance;
	
	private new() {}
	
	/**
	 * Gets the singleton of the switch.
	 * @return The singleton instance.
	 */
	public static def ReferenceApplyBackwardCommandSwitch getInstance() {
		if (instance == null) {
			instance = new ReferenceApplyBackwardCommandSwitch();
		}
		return instance;
	}
	
	/**
	 * Create commands to apply a {@link InsertEReference} change backward.
	 * @param object The change which commands should be created.
	 */	
	override public <A extends EObject, T extends EObject> List<Command> caseInsertEReference(InsertEReference<A, T> object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		val compoundCommand = new CompoundCommand()

		compoundCommand.append(RemoveAtCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.newValue, object.index))
		if (object.containment) {
			val stagingArea = StagingArea.getStagingArea(object.affectedEObject.eResource.resourceSet)
			compoundCommand.append(new AddCommand(editingDomain, stagingArea.contents, object.newValue))
		}			
			
		return Collections.singletonList(compoundCommand)
	}	
	
	/**
	 * Create commands to apply a {@link RemoveEReference} change backward.
	 * @param object The change which commands should be created.
	 */	
	override public <A extends EObject, T extends EObject> List<Command> caseRemoveEReference(RemoveEReference<A, T> object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		val compoundCommand = new CompoundCommand()
		
		if (object.containment) {
			val stagingArea = StagingArea.getStagingArea(object.affectedEObject.eResource.resourceSet) 
			compoundCommand.append(new RemoveCommand(editingDomain, stagingArea.contents, object.oldValue))
		}		
		compoundCommand.append(AddCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.oldValue, object.index))			

		return Collections.singletonList(compoundCommand)
	}

	/**
	 * Create commands to apply a {@link ReplaceSingleValuedEReference} change backward.
	 * @param object The change which commands should be created.
	 */	
	override public <A extends EObject, T extends EObject> List<Command> caseReplaceSingleValuedEReference(ReplaceSingleValuedEReference<A, T> object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		val stagingArea = StagingArea.getStagingArea(object.affectedEObject.eResource.resourceSet) 
		val compoundCommand = new CompoundCommand()
		
		if (object.containment && object.oldValue != null) {
			compoundCommand.append(new RemoveCommand(editingDomain, stagingArea.contents, object.oldValue))
		}
		compoundCommand.append(SetCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.oldValue))
		if (object.containment && object.newValue != null) {
			compoundCommand.append(new AddCommand(editingDomain, stagingArea.contents, object.newValue))			
		}
		
		return Collections.singletonList(compoundCommand)
	}	
}
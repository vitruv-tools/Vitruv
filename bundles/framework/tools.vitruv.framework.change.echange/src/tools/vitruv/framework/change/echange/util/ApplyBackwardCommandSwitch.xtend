package tools.vitruv.framework.change.echange.util

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.common.command.CompoundCommand
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.edit.command.AddCommand
import org.eclipse.emf.edit.command.SetCommand
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.command.AddToStagingAreaCommand
import tools.vitruv.framework.change.echange.command.RemoveAtCommand
import tools.vitruv.framework.change.echange.command.RemoveFromStagingAreaCommand
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.framework.change.echange.resolve.StagingArea
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject

/**
 * Switch to create commands for all EChange classes.
 * The commands applies the EChanges backward.
 */
package class ApplyBackwardCommandSwitch {
	/**
	 * Dispatch method to create commands to apply a {@link InsertEAttributeValue} change backward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(InsertEAttributeValue<EObject, Object> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		return #[new RemoveAtCommand(editingDomain, change.affectedEObject, change.affectedFeature, change.newValue,
				change.index)]
	}

	/**
	 * Dispatch method to create commands to apply a {@link RemoveEAttributeValue} change backward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(RemoveEAttributeValue<EObject, Object> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		return #[new AddCommand(editingDomain, change.affectedEObject, change.affectedFeature, change.oldValue,
				change.index)]
	}

	/**
	 * Dispatch method to create commands to apply a {@link ReplaceSingleValuedEAttribute} change backward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(ReplaceSingleValuedEAttribute<EObject, Object> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		return #[new SetCommand(editingDomain, change.affectedEObject, change.affectedFeature, change.oldValue)]
	}

	/**
	 * Dispatch method to create commands to apply a {@link InsertEReference} change backward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(InsertEReference<EObject, EObject> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		val compoundCommand = new CompoundCommand()

		compoundCommand.append(new RemoveAtCommand(editingDomain, change.affectedEObject, change.affectedFeature, change.newValue, change.index))
		if (change.containment) {
			val stagingArea = StagingArea.getStagingArea(change.affectedEObject.eResource)
			compoundCommand.append(new AddToStagingAreaCommand(editingDomain, stagingArea, change.newValue))
		}

		return #[compoundCommand]
	}

	/**
	 * Dispatch method to create commands to apply a {@link RemoveEReference} change backward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(RemoveEReference<EObject, EObject> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		val compoundCommand = new CompoundCommand()

		if (change.containment) {
			val stagingArea = StagingArea.getStagingArea(change.affectedEObject.eResource)
			compoundCommand.append(new RemoveFromStagingAreaCommand(editingDomain, stagingArea, change.oldValue))
		}
		compoundCommand.append(new AddCommand(editingDomain, change.affectedEObject, change.affectedFeature, change.oldValue, change.index))
		return #[compoundCommand]
	}

	/**
	 * Dispatch method to create commands to apply a {@link ReplaceSingleValuedEReference} change backward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(ReplaceSingleValuedEReference<EObject, EObject> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		val stagingArea = StagingArea.getStagingArea(change.affectedEObject.eResource)
		val compoundCommand = new CompoundCommand()

		if (change.containment && change.oldValue != null) {
			compoundCommand.append(new RemoveFromStagingAreaCommand(editingDomain, stagingArea, change.oldValue))
		}
		compoundCommand.append(new SetCommand(editingDomain, change.affectedEObject, change.affectedFeature, change.oldValue))
		if (change.containment && change.newValue != null) {
			compoundCommand.append(new AddToStagingAreaCommand(editingDomain, stagingArea, change.newValue))
		}

		return #[compoundCommand]
	}

	/**
	 * Dispatch method to create commands to apply a {@link InsertRootEObject} change backward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(InsertRootEObject<EObject> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.newValue)
		// Will be automatically removed from resource because object can only be in one resource.
		val stagingArea = StagingArea.getStagingArea(change.resource)
		return #[new AddToStagingAreaCommand(editingDomain, stagingArea, change.newValue)]
	}

	/**
	 * Dispatch method to create commands to apply a {@link RemoveRootEObject} change backward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(RemoveRootEObject<EObject> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.oldValue)
		// Will be automatically removed from resource because object can only be in one resource.
		return #[new AddCommand(editingDomain, change.resource.getContents, change.oldValue, change.index)]
	}

	/**
	 * Dispatch method to create commands to apply a {@link CreateEObject} change backward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(CreateEObject<EObject> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		return #[new RemoveFromStagingAreaCommand(editingDomain, change.stagingArea, change.affectedEObject)]
	}

	/**
	 * Dispatch method to create commands to apply a {@link DeleteEObject} change backward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(DeleteEObject<EObject> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		return #[new AddToStagingAreaCommand(editingDomain, change.stagingArea, change.affectedEObject)]
	}

	/**
	 * Dispatch method to create commands to apply a compound change backward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(CompoundEChange change) {
		val commands = new ArrayList<Command>
		for (AtomicEChange c : change.atomicChanges.reverseView) {
			commands.addAll(getCommands(c))
		}
		return commands
	}
}

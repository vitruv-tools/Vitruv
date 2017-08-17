package tools.vitruv.framework.change.echange.util

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.edit.command.AddCommand
import org.eclipse.emf.edit.command.SetCommand
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.command.RemoveAtCommand
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
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
		return #[new RemoveAtCommand(editingDomain, change.affectedEObject, change.affectedFeature, change.newValue, change.index)]
	}

	/**
	 * Dispatch method to create commands to apply a {@link RemoveEReference} change backward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(RemoveEReference<EObject, EObject> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		return #[new AddCommand(editingDomain, change.affectedEObject, change.affectedFeature, change.oldValue, change.index)]
	}

	/**
	 * Dispatch method to create commands to apply a {@link ReplaceSingleValuedEReference} change backward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(ReplaceSingleValuedEReference<EObject, EObject> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		return #[new SetCommand(editingDomain, change.affectedEObject, change.affectedFeature, change.oldValue)]
	}

	/**
	 * Dispatch method to create commands to apply a {@link InsertRootEObject} change backward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(InsertRootEObject<EObject> change) {
		// Will be automatically removed from resource because object can only be in one resource.
		return #[]
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
		return #[]
	}

	/**
	 * Dispatch method to create commands to apply a {@link DeleteEObject} change backward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(DeleteEObject<EObject> change) {
		return #[]
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

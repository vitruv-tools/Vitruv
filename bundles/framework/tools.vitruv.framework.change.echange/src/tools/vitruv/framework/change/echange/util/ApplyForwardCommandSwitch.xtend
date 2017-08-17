package tools.vitruv.framework.change.echange.util;

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.edit.command.AddCommand
import org.eclipse.emf.edit.command.SetCommand
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.command.RemoveAtCommand
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
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
import org.eclipse.emf.edit.command.RemoveCommand

/**
 * Switch to create commands for all EChange classes.
 * The commands applies the EChanges forward.
 */
package class ApplyForwardCommandSwitch {
	/**
	 * Dispatch method to create commands to apply a {@link InsertEAttributeValue} change forward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(InsertEAttributeValue<EObject, Object> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		return #[new AddCommand(editingDomain, change.affectedEObject, change.affectedFeature, change.newValue,
				change.index)]
	}

	/**
	 * Dispatch method to create commands to apply a {@link RemoveEAttributeValue} change forward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(RemoveEAttributeValue<EObject, Object> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		return #[new RemoveAtCommand(editingDomain, change.affectedEObject, change.affectedFeature, change.oldValue,
				change.index)]
	}

	/**
	 * Dispatch method to create commands to apply a {@link ReplaceSingleValuedEAttribute} change forward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(ReplaceSingleValuedEAttribute<EObject, Object> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		return #[new SetCommand(editingDomain, change.affectedEObject, change.affectedFeature, change.newValue)]
	}

	/**
	 * Dispatch method to create commands to apply a {@link InsertEReference} change forward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(InsertEReference<EObject, EObject> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		return #[new AddCommand(editingDomain, change.affectedEObject, change.affectedFeature, change.newValue,
				change.index)];
	}

	/**
	 * Dispatch method to create commands to apply a {@link RemoveEReference} change forward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(RemoveEReference<EObject, EObject> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		return #[new RemoveAtCommand(editingDomain, change.affectedEObject, change.affectedFeature, change.oldValue,
				change.index)];
	}

	/**
	 * Dispatch method to create commands to apply a {@link ReplaceSingleValuedEReference} change forward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(ReplaceSingleValuedEReference<EObject, EObject> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		return #[new SetCommand(editingDomain, change.affectedEObject, change.affectedFeature, change.newValue)]
	}

	/**
	 * Dispatch method to create commands to apply a {@link InsertRootEObject} change forward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(InsertRootEObject<EObject> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.newValue)
		// Will be automatically removed from resource because object can only be in one resource.	
		return #[new AddCommand(editingDomain, change.resource.getContents, change.newValue, change.index)]
	}

	/**
	 * Dispatch method to create commands to apply a {@link RemoveRootEObject} change forward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(RemoveRootEObject<EObject> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.oldValue)
		return #[new RemoveCommand(editingDomain, change.resource.getContents, change.oldValue)];
	}

	/**
	 * Dispatch method to create commands to apply a {@link CreateEObject} change forward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(CreateEObject<EObject> change) {
		return #[]
	}

	/**
	 * Dispatch method to create commands to apply a {@link DeleteEObject} change forward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(DeleteEObject<EObject> change) {
		return #[]
	}

	/**
	 * Dispatch method to create commands to apply a compound change forward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(CompoundEChange change) {
		val commands = new ArrayList<Command>
		for (AtomicEChange c : change.atomicChanges) {
			commands.addAll(getCommands(c))
		}
		return commands
	}

	/**
	 * Dispatch method to create command to apply an unset change forward.
	 * @param object The change which command should be created.
	 */
	def package dispatch static List<Command> getCommands(ExplicitUnsetEFeature<EObject, EStructuralFeature> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		return #[new SetCommand(editingDomain, change.affectedEObject, change.affectedFeature, SetCommand.UNSET_VALUE)]
	}
}

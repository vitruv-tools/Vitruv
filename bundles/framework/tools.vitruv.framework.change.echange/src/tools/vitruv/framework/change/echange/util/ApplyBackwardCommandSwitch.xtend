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
import org.eclipse.emf.edit.command.RemoveCommand
import org.apache.log4j.Logger

/**
 * Switch to create commands for all EChange classes.
 * The commands applies the EChanges backward.
 */
package class ApplyBackwardCommandSwitch {
	static val Logger logger = Logger.getLogger(ApplyBackwardCommandSwitch)
	
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
		if(!EChangeUtil.alreadyContainsObject(change.affectedEObject, change.affectedFeature, change.newValue)) {
			if (change.affectedFeature.EOpposite === null) {
				logger.warn("Tried to remove value " + change.newValue + ", but although not opposite feature was not contained in " + change.affectedEObject);
			} 
			return #[];
		}
		return #[new RemoveAtCommand(editingDomain, change.affectedEObject, change.affectedFeature, change.newValue, change.index)]
	}

	/**
	 * Dispatch method to create commands to apply a {@link RemoveEReference} change backward.
	 * @param object The change which commands should be created.
	 */
	def package dispatch static List<Command> getCommands(RemoveEReference<EObject, EObject> change) {
		val editingDomain = EChangeUtil.getEditingDomain(change.affectedEObject)
		if(EChangeUtil.alreadyContainsObject(change.affectedEObject, change.affectedFeature, change.oldValue)) {
			if (change.affectedFeature.EOpposite === null) {
				logger.warn("Tried to add value " + change.oldValue + ", but although not opposite feature was not contained in " + change.affectedEObject);
			} 
			return #[];
		}
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
		val editingDomain = EChangeUtil.getEditingDomain(change.newValue)
		return #[new RemoveCommand(editingDomain, change.resource.contents, change.newValue)]
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
		return change.consequentialRemoveChanges.reverseView.map[commands].flatten.toList;
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

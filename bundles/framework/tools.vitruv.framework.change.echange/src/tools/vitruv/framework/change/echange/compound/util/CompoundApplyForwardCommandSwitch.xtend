package tools.vitruv.framework.change.echange.compound.util

import java.util.ArrayList
import java.util.List
import org.eclipse.emf.common.command.Command
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
import tools.vitruv.framework.change.echange.util.ApplyForwardCommandSwitch
import tools.vitruv.framework.change.echange.util.EChangeUtil
import tools.vitruv.framework.change.echange.feature.attribute.SubtractiveAttributeEChange
import org.eclipse.emf.ecore.EObject
import java.util.Collections
import org.eclipse.emf.edit.command.SetCommand

/**
 * Switch to create commands for all EChange classes of the compound package.
 * The commands applies the EChanges forward.
 */
class CompoundApplyForwardCommandSwitch extends CompoundSwitch<List<Command>> {
	/**
	 * Create commands to apply a compound change forward.
	 * @param object The change which commands should be created.
	 */
	def public List<Command> caseCompoundEChange(CompoundEChange object) {
		val commands = new ArrayList<Command>
		for (AtomicEChange change : object.atomicChanges) {
			commands.addAll((new ApplyForwardCommandSwitch()).doSwitch(change))
		}
		return commands	
	}	
	
	/**
	 * Create command to apply unset change forward.
	 * @param object The change which command should be created.
	 */
	override public List<Command> caseExplicitUnsetEFeature(ExplicitUnsetEFeature object) {
		var SubtractiveAttributeEChange<EObject, ?> firstChange = 
			object.atomicChanges.get(0) as SubtractiveAttributeEChange<EObject, ?>
		val editingDomain = EChangeUtil.getEditingDomain(firstChange.affectedEObject)
		return Collections.singletonList(SetCommand.create(editingDomain, 
			firstChange.affectedEObject, firstChange.affectedFeature, SetCommand.UNSET_VALUE))		
	}
}
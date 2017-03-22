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
import org.eclipse.emf.common.command.UnexecutableCommand

/**
 * Switch to create commands for all EChange classes of the compound package.
 * The commands applies the EChanges forward.
 */
class CompoundApplyForwardCommandSwitch extends CompoundSwitch<List<Command>> {
	private static CompoundApplyForwardCommandSwitch instance;
	
	private new() {}
	
	/**
	 * Gets the singleton of the switch.
	 * @return The singleton instance.
	 */
	public static def CompoundApplyForwardCommandSwitch getInstance() {
		if (instance == null) {
			instance = new CompoundApplyForwardCommandSwitch();
		}
		return instance;
	}
	
	/**
	 * Create commands to apply a compound change forward.
	 * @param object The change which commands should be created.
	 */
	def public List<Command> caseCompoundEChange(CompoundEChange object) {
		val commands = new ArrayList<Command>
		for (AtomicEChange change : object.atomicChanges) {
			commands.addAll(ApplyForwardCommandSwitch.instance.doSwitch(change))
		}
		return commands	
	}	
	
	/**
	 * Create command to apply unset change forward.
	 * @param object The change which command should be created.
	 */
	override public List<Command> caseExplicitUnsetEFeature(ExplicitUnsetEFeature object) {
		if (object.atomicChanges.size == 0) {
			return Collections.singletonList(UnexecutableCommand.INSTANCE)
		}
		var SubtractiveAttributeEChange<EObject, ?> firstChange = 
			object.atomicChanges.get(0) as SubtractiveAttributeEChange<EObject, ?>
		val editingDomain = EChangeUtil.getEditingDomain(firstChange.affectedEObject)
		return Collections.singletonList(SetCommand.create(editingDomain, 
			firstChange.affectedEObject, firstChange.affectedFeature, SetCommand.UNSET_VALUE))		
	}
}
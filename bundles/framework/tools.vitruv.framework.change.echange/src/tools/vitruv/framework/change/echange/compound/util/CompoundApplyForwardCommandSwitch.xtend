package tools.vitruv.framework.change.echange.compound.util

import java.util.ArrayList
import java.util.Collections
import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.edit.command.SetCommand
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.compound.CompoundEChange
import tools.vitruv.framework.change.echange.compound.ExplicitUnsetEFeature
import tools.vitruv.framework.change.echange.util.ApplyForwardCommandSwitch
import tools.vitruv.framework.change.echange.util.EChangeUtil

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
	override public List<Command> caseCompoundEChange(CompoundEChange object) {
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
	override public <A extends EObject, F extends EStructuralFeature> List<Command> caseExplicitUnsetEFeature(ExplicitUnsetEFeature<A, F> object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		return Collections.singletonList(SetCommand.create(editingDomain, 
			object.affectedEObject, object.affectedFeature, SetCommand.UNSET_VALUE))		
	}
}
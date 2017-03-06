package tools.vitruv.framework.change.echange.util

import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.util.CompoundRevertCommandSwitch
import tools.vitruv.framework.change.echange.eobject.util.EObjectRevertCommandSwitch
import tools.vitruv.framework.change.echange.feature.util.FeatureRevertCommandSwitch
import tools.vitruv.framework.change.echange.root.util.RootRevertCommandSwitch

public class RevertCommandSwitch extends EChangeSwitch<List<Command>> {
	override public List<Command> doSwitch(EObject eObject) {
		return super.doSwitch(eObject)
	}
	def public List<Command> caseEChange(EChange object) {
		return (new CompoundRevertCommandSwitch()).doSwitch(object)
	}
	def public List<Command> caseAtomicEChange(AtomicEChange object) {
		var result = (new FeatureRevertCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new RootRevertCommandSwitch()).doSwitch(object)
		}
		if (result == null) {
			result = (new EObjectRevertCommandSwitch()).doSwitch(object)
		}
		return result
	}
}
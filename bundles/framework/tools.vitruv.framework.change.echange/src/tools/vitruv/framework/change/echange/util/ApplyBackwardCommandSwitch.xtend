package tools.vitruv.framework.change.echange.util

import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.util.CompoundApplyBackwardCommandSwitch
import tools.vitruv.framework.change.echange.eobject.util.EObjectApplyBackwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.util.FeatureApplyBackwardCommandSwitch
import tools.vitruv.framework.change.echange.root.util.RootApplyBackwardCommandSwitch

public class ApplyBackwardCommandSwitch extends EChangeSwitch<List<Command>> {
	override public List<Command> doSwitch(EObject eObject) {
		return super.doSwitch(eObject)
	}
	def public List<Command> caseEChange(EChange object) {
		return (new CompoundApplyBackwardCommandSwitch()).doSwitch(object)
	}
	def public List<Command> caseAtomicEChange(AtomicEChange object) {
		var result = (new FeatureApplyBackwardCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new RootApplyBackwardCommandSwitch()).doSwitch(object)
		}
		if (result == null) {
			result = (new EObjectApplyBackwardCommandSwitch()).doSwitch(object)
		}
		return result
	}
}
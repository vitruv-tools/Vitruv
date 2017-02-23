package tools.vitruv.framework.change.echange.util

import org.eclipse.emf.common.command.Command
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.AdditiveEChange
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.eobject.util.EObjectRevertCommandSwitch
import tools.vitruv.framework.change.echange.feature.util.FeatureRevertCommandSwitch
import tools.vitruv.framework.change.echange.root.util.RootRevertCommandSwitch

public class RevertCommandSwitch extends EChangeSwitch<Command> {
	override public Command doSwitch(EObject eObject) {
		return super.doSwitch(eObject)
	}
	def public Command caseAtomicEChange(AtomicEChange object) {
		var result = (new FeatureRevertCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new RootRevertCommandSwitch()).doSwitch(object)
		}
		if (result == null) {
			result = (new EObjectRevertCommandSwitch()).doSwitch(object)
		}
		return result;
	}
	def public Command caseAdditiveEChange(AdditiveEChange object) {

	}
}
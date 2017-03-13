package tools.vitruv.framework.change.echange.util;

import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.util.CompoundApplyForwardCommandSwitch
import tools.vitruv.framework.change.echange.eobject.util.EObjectApplyForwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.util.FeatureApplyForwardCommandSwitch
import tools.vitruv.framework.change.echange.root.util.RootApplyForwardCommandSwitch

public class ApplyForwardCommandSwitch extends EChangeSwitch<List<Command>>{
	override public List<Command> doSwitch(EObject eObject) {
		return super.doSwitch(eObject)
	}
	def public List<Command> caseEChange(EChange object) {
		return (new CompoundApplyForwardCommandSwitch()).doSwitch(object)
	}
	def public List<Command> caseAtomicEChange(AtomicEChange object) {
		var result = (new FeatureApplyForwardCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new RootApplyForwardCommandSwitch()).doSwitch(object)
		}
		if (result == null) {
			result = (new EObjectApplyForwardCommandSwitch()).doSwitch(object)
		}
		return result		
	}
}
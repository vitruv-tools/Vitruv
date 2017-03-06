package tools.vitruv.framework.change.echange.util;

import org.eclipse.emf.common.command.Command
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.util.CompoundApplyCommandSwitch
import tools.vitruv.framework.change.echange.eobject.util.EObjectApplyCommandSwitch
import tools.vitruv.framework.change.echange.feature.util.FeatureApplyCommandSwitch
import tools.vitruv.framework.change.echange.root.util.RootApplyCommandSwitch
import java.util.List

public class ApplyCommandSwitch extends EChangeSwitch<List<Command>>{
	override public List<Command> doSwitch(EObject eObject) {
		return super.doSwitch(eObject)
	}
	def public List<Command> caseEChange(EChange object) {
		return (new CompoundApplyCommandSwitch()).doSwitch(object)
	}
	def public List<Command> caseAtomicEChange(AtomicEChange object) {
		var result = (new FeatureApplyCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new RootApplyCommandSwitch()).doSwitch(object)
		}
		if (result == null) {
			result = (new EObjectApplyCommandSwitch()).doSwitch(object)
		}
		return result		
	}
}
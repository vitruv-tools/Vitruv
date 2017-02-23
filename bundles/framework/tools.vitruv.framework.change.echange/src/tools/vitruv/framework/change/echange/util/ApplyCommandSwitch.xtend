package tools.vitruv.framework.change.echange.util;

import org.eclipse.emf.common.command.Command
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.echange.AdditiveEChange
import tools.vitruv.framework.change.echange.AtomicEChange
import tools.vitruv.framework.change.echange.eobject.util.EObjectApplyCommandSwitch
import tools.vitruv.framework.change.echange.feature.util.FeatureApplyCommandSwitch
import tools.vitruv.framework.change.echange.root.util.RootApplyCommandSwitch

public class ApplyCommandSwitch extends EChangeSwitch<Command>{
	override public Command doSwitch(EObject eObject) {
		return super.doSwitch(eObject)
	}
	def public Command caseAtomicEChange(AtomicEChange object) {
		var result = (new FeatureApplyCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new RootApplyCommandSwitch()).doSwitch(object)
		}
		if (result == null) {
			result = (new EObjectApplyCommandSwitch()).doSwitch(object)
		}
		return result		
	}
	def public Command caseAdditiveEChange(AdditiveEChange object) {

	}
}
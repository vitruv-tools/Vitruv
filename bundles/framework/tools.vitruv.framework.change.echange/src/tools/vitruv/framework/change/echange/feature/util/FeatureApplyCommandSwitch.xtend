package tools.vitruv.framework.change.echange.feature.util

import org.eclipse.emf.common.command.Command
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.UpdateMultiValuedFeatureEChange
import tools.vitruv.framework.change.echange.feature.attribute.util.AttributeApplyCommandSwitch
import tools.vitruv.framework.change.echange.feature.list.util.ListApplyCommandSwitch
import tools.vitruv.framework.change.echange.feature.reference.util.ReferenceApplyCommandSwitch

public class FeatureApplyCommandSwitch extends FeatureSwitch<Command> {
	def public Command caseFeatureEChange(FeatureEChange object) {
		var result = (new AttributeApplyCommandSwitch()).doSwitch(object)
		if (result == null) {
			result = (new ReferenceApplyCommandSwitch()).doSwitch(object)
		}
		return result
	}
	def public Command caseUpdateMultiValuedFeatureEChange(UpdateMultiValuedFeatureEChange object) {
		var result = (new ListApplyCommandSwitch()).doSwitch(object)
		return result	
	}
}
package tools.vitruv.framework.change.description

import org.eclipse.xtend.lib.annotations.Data
import tools.vitruv.framework.uuid.UuidResolver
import java.util.Collection
import tools.vitruv.framework.change.interaction.UserInteractionBase

@Data
class PropagatedChange {
	private val VitruviusChange originalChange;
	private val VitruviusChange consequentialChanges;
	private val Collection<UserInteractionBase> userInteractions;
	
	override toString() '''
	Original change:
		«originalChange»
	Consequential change: «consequentialChanges»
	'''// TODO DK: incorporate user input here in some way?
	
	def applyBackward(UuidResolver uuidResolver) {
		consequentialChanges.unresolveIfApplicable;
		originalChange.unresolveIfApplicable;
		consequentialChanges.resolveAfterAndApplyBackward(uuidResolver);
		originalChange.resolveAfterAndApplyBackward(uuidResolver);
	}
	
}
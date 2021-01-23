package tools.vitruv.dsls.commonalities.generator.reactions.condition

import com.google.inject.Inject
import org.eclipse.xtext.xbase.XExpression
import tools.vitruv.dsls.commonalities.generator.reactions.condition.ParticipationConditionOperatorHelper.ParticipationConditionOperatorContext
import tools.vitruv.dsls.commonalities.generator.reactions.helper.ReactionsGenerationHelper
import tools.vitruv.dsls.commonalities.language.ParticipationCondition
import tools.vitruv.dsls.commonalities.participation.ParticipationContext
import tools.vitruv.dsls.commonalities.participation.ParticipationContext.ContextClass
import tools.vitruv.dsls.reactions.builder.TypeProvider

import static extension tools.vitruv.dsls.commonalities.participation.ParticipationConditionHelper.*

class ParticipationConditionInitializationHelper extends ReactionsGenerationHelper {
	@Inject extension ParticipationConditionOperatorHelper participationConditionOperatorHelper

	package new() {
	}

	def getParticipationConditionsInitializers(ParticipationContext participationContext, ContextClass contextClass) {
		return participationContext.getEnforcedParticipationConditions(contextClass).map [
			participationConditionInitializer
		]
	}

	private def (TypeProvider)=>XExpression getParticipationConditionInitializer(
		ParticipationCondition participationCondition) {
		[participationCondition.enforce(new ParticipationConditionOperatorContext(it))]
	}
}

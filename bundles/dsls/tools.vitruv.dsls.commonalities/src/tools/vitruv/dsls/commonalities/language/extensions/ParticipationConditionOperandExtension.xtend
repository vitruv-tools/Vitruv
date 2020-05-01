package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationCondition
import tools.vitruv.dsls.commonalities.language.ParticipationConditionOperand

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*

@Utility
package class ParticipationConditionOperandExtension {

	def static ParticipationCondition getParticipationCondition(ParticipationConditionOperand operand) {
		return operand.getDirectContainer(ParticipationCondition)
	}

	def static Participation getParticipation(ParticipationConditionOperand operand) {
		return operand.getContainer(Participation)
	}
}

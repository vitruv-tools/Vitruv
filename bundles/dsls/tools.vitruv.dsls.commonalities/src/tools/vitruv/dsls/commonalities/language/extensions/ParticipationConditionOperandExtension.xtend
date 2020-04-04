package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationAttributeOperand
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationClassOperand
import tools.vitruv.dsls.commonalities.language.ParticipationCondition
import tools.vitruv.dsls.commonalities.language.ParticipationConditionLeftOperand
import tools.vitruv.dsls.commonalities.language.ParticipationConditionRightOperand

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*

@Utility
package class ParticipationConditionOperandExtension {

	def static ParticipationCondition getParticipationCondition(ParticipationConditionRightOperand operand) {
		return operand.getDirectContainer(ParticipationCondition)
	}

	def static ParticipationCondition getParticipationCondition(ParticipationConditionLeftOperand operand) {
		return operand.getDirectContainer(ParticipationCondition)
	}

	def static Participation getParticipation(ParticipationConditionRightOperand operand) {
		return operand.getContainer(Participation)
	}

	def static Participation getParticipation(ParticipationConditionLeftOperand operand) {
		return operand.getContainer(Participation)
	}

	def static dispatch ParticipationClass getParticipationClass(ParticipationClassOperand operand) {
		return operand.participationClass
	}

	def static dispatch ParticipationClass getParticipationClass(ParticipationAttributeOperand operand) {
		return operand.participationAttribute.participationClass
	}

	def static dispatch ParticipationClass getParticipationClass(ParticipationConditionRightOperand operand) {
		return null // catch any other
	}
}

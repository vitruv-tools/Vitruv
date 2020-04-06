package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.ParticipationAttributeOperand
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationClassOperand
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

	def static dispatch ParticipationClass getParticipationClass(ParticipationClassOperand operand) {
		return operand.participationClass
	}

	def static dispatch ParticipationClass getParticipationClass(ParticipationAttributeOperand operand) {
		return operand.participationAttribute.participationClass
	}

	def static dispatch ParticipationClass getParticipationClass(ParticipationConditionOperand operand) {
		return null // catch any other
	}

	def static dispatch ParticipationAttribute getParticipationAttribute(ParticipationAttributeOperand operand) {
		return operand.participationAttribute
	}

	def static dispatch ParticipationAttribute getParticipationAttribute(ParticipationConditionOperand operand) {
		return null // catch any other
	}
}

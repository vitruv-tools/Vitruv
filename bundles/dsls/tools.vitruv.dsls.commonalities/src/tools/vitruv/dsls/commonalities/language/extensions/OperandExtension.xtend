package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.Operand
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.ParticipationAttributeOperand
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationClassOperand
import tools.vitruv.dsls.commonalities.language.ReferencedParticipationAttributeOperand

@Utility
package class OperandExtension {

	def static dispatch ParticipationClass getParticipationClass(ParticipationClassOperand operand) {
		return operand.participationClass
	}

	def static dispatch ParticipationClass getParticipationClass(ParticipationAttributeOperand operand) {
		return operand.participationAttribute.participationClass
	}

	def static dispatch ParticipationClass getParticipationClass(ReferencedParticipationAttributeOperand operand) {
		return operand.participationAttribute.participationClass
	}

	def static dispatch ParticipationClass getParticipationClass(Operand operand) {
		return null // catch any other
	}

	def static dispatch ParticipationAttribute getParticipationAttribute(ParticipationAttributeOperand operand) {
		return operand.participationAttribute
	}

	def static dispatch ParticipationAttribute getParticipationAttribute(ReferencedParticipationAttributeOperand operand) {
		return operand.participationAttribute
	}

	def static dispatch ParticipationAttribute getParticipationAttribute(Operand operand) {
		return null // catch any other
	}
}

package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.Operand
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.ParticipationAttributeOperand
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationClassOperand
import tools.vitruv.dsls.commonalities.language.ReferencedParticipationAttributeOperand

import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationClassExtension.*

@Utility
package class OperandExtension {

	def static Participation getParticipation(Operand operand) {
		return operand.participationClass?.participation // can be null
	}

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

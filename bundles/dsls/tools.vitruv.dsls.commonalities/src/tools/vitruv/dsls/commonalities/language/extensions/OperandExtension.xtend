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

	static def Participation getParticipation(Operand operand) {
		return operand.participationClass?.participation // can be null
	}

	static def dispatch ParticipationClass getParticipationClass(ParticipationClassOperand operand) {
		return operand.participationClass
	}

	static def dispatch ParticipationClass getParticipationClass(ParticipationAttributeOperand operand) {
		return operand.participationAttribute.participationClass
	}

	static def dispatch ParticipationClass getParticipationClass(ReferencedParticipationAttributeOperand operand) {
		return operand.participationAttribute.participationClass
	}

	static def dispatch ParticipationClass getParticipationClass(Operand operand) {
		return null // catch any other
	}

	static def dispatch ParticipationAttribute getParticipationAttribute(ParticipationAttributeOperand operand) {
		return operand.participationAttribute
	}

	static def dispatch ParticipationAttribute getParticipationAttribute(ReferencedParticipationAttributeOperand operand) {
		return operand.participationAttribute
	}

	static def dispatch ParticipationAttribute getParticipationAttribute(Operand operand) {
		return null // catch any other
	}
}

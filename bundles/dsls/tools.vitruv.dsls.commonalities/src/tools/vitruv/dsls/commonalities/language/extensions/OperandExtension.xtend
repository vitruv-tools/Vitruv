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
		operand.participationClass?.participation // can be null
	}

	static def dispatch ParticipationClass getParticipationClass(ParticipationClassOperand operand) {
		operand.participationClass
	}

	static def dispatch ParticipationClass getParticipationClass(ParticipationAttributeOperand operand) {
		 operand.participationAttribute.participationClass
	}

	static def dispatch ParticipationClass getParticipationClass(ReferencedParticipationAttributeOperand operand) {
		operand.participationAttribute.participationClass
	}

	static def dispatch ParticipationClass getParticipationClass(Operand operand) {
		null // catch any other
	}

	static def dispatch ParticipationAttribute getParticipationAttribute(ParticipationAttributeOperand operand) {
		operand.participationAttribute
	}

	static def dispatch ParticipationAttribute getParticipationAttribute(ReferencedParticipationAttributeOperand operand) {
		operand.participationAttribute
	}

	static def dispatch ParticipationAttribute getParticipationAttribute(Operand operand) {
		null // catch any other
	}
}

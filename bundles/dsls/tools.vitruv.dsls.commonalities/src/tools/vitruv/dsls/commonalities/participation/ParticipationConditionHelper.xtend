package tools.vitruv.dsls.commonalities.participation

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collections
import tools.vitruv.dsls.commonalities.participation.ParticipationContext
import tools.vitruv.dsls.commonalities.participation.ParticipationContext.ContextClass

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

@Utility
class ParticipationConditionHelper {

	static def getCheckedParticipationConditions(ParticipationContext participationContext) {
		val participation = participationContext.participation
		return participation.conditions
			.filter[checked]
			.filter[!isContainment] // Containments are checked separately
			.filter [
				// Exclude conditions whose operands refer to participation classes that are not involved in the
				// current participation context:
				val operands = Collections.singleton(leftOperand) + rightOperands
				operands.map[it.participationClass].filterNull.forall [ operandParticipationClass |
					participationContext.classes.exists [
						it.participationClass == operandParticipationClass
					]
				]
			]
	}

	static def getEnforcedParticipationConditions(ParticipationContext participationContext,
		ContextClass involvedContextClass) {
		val participation = participationContext.participation
		val involvedParticipationClass = involvedContextClass.participationClass
		return participation.conditions
			.filter[!isContainment] // Containments are handled separately
			.filter[enforced]
			.filter[leftOperand.participationClass == involvedParticipationClass]
			.filter [
				// Exclude conditions whose operands refer to participation classes that are not involved in the
				// current participation context:
				rightOperands.map[it.participationClass].filterNull.forall [ operandParticipationClass |
					participationContext.classes.exists [
						it.participationClass == operandParticipationClass
					]
				]
			]
	}
}

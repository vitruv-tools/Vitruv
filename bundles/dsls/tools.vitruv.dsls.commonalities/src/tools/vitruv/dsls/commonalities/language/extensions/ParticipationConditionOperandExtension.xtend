package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationCondition
import tools.vitruv.dsls.commonalities.language.ParticipationConditionOperand

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*

@Utility
package class ParticipationConditionOperandExtension {

	static def boolean isInParticipationConditionContext(ParticipationConditionOperand operand) {
		return (operand.participationCondition !== null)
	}

	// Returns null if not in participation condition context:
	static def ParticipationCondition getParticipationCondition(ParticipationConditionOperand operand) {
		return operand.getOptionalDirectContainer(ParticipationCondition)
	}

	// Returns null if not in participation condition context:
	static def Participation getParticipation(ParticipationConditionOperand operand) {
		return operand.getOptionalContainer(Participation)
	}
}

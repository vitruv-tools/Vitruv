package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.BidirectionalParticipationCondition
import tools.vitruv.dsls.commonalities.language.CheckedParticipationCondition
import tools.vitruv.dsls.commonalities.language.EnforcedParticipationCondition
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationCondition

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*

@Utility
package class ParticipationConditionExtension {

	def static Participation getParticipation(ParticipationCondition participationCondition) {
		return participationCondition.getDirectContainer(Participation)
	}

	def static dispatch isEnforced(BidirectionalParticipationCondition condition) {
		true
	}

	def static dispatch isEnforced(EnforcedParticipationCondition condition) {
		true
	}

	def static dispatch isEnforced(CheckedParticipationCondition condition) {
		false
	}

	def static dispatch isChecked(BidirectionalParticipationCondition condition) {
		false
	}

	def static dispatch isChecked(EnforcedParticipationCondition condition) {
		false
	}

	def static dispatch isChecked(CheckedParticipationCondition condition) {
		true
	}
}

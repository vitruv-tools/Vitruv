package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.BidirectionalParticipationCondition
import tools.vitruv.dsls.commonalities.language.CheckedParticipationCondition
import tools.vitruv.dsls.commonalities.language.EnforcedParticipationCondition
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationCondition

@Utility
package class ParticipationConditionExtension {

	def static Participation getParticipation(ParticipationCondition participationCondition) {
		val container = participationCondition.eContainer
		if (container instanceof Participation) {
			return container
		}
		throw new IllegalStateException("Found a ParticipationCondition that is not contained inside of any Participation!")
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

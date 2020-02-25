package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.ParticipationCondition

@Utility
package class ParticipationAttributeExtension {

	// null if not contained in any ParticipationCondition
	def static ParticipationCondition getOptionalContainingParticipationCondition(ParticipationAttribute participationAttribute) {
		val container = participationAttribute.eContainer
		return (container instanceof ParticipationCondition) ? container : null
	}
}

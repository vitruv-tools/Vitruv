package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.ParticipationAttribute
import tools.vitruv.dsls.commonalities.language.ParticipationCondition

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*

@Utility
package class ParticipationAttributeExtension {

	def static ParticipationCondition getOptionalContainingParticipationCondition(
		ParticipationAttribute participationAttribute) {
		return participationAttribute.getOptionalDirectContainer(ParticipationCondition)
	}
}

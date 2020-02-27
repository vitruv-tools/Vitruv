package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.Commonality
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationClass
import tools.vitruv.dsls.commonalities.language.ParticipationRelation

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*

@Utility
package class ParticipationClassExtension {

	def static ParticipationRelation getOptionalParticipationRelation(ParticipationClass participationClass) {
		return participationClass.getOptionalDirectContainer(ParticipationRelation)
	}

	def static getDomain(ParticipationClass participationClass) {
		participationClass.superMetaclass?.domain
	}

	def static getParticipation(ParticipationClass participationClass) {
		if (participationClass.eIsProxy) return null
		return participationClass.getContainer(Participation)
	}

	def static Commonality getParticipatingCommonality(ParticipationClass participationClass) {
		val metaclass = participationClass.superMetaclass
		if (metaclass instanceof Commonality) {
			return metaclass
		}
		return null
	}
}

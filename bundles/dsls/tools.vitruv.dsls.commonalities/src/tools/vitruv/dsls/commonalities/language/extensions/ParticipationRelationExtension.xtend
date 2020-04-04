package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.ContainmentRelation

@Utility
class ParticipationRelationExtension {

	// TODO support other structural operators by having them provide the containment relationships?
	def static isContainment(ParticipationRelation relation) {
		return (relation.operator.qualifiedName == ContainmentRelation.name)
	}
}

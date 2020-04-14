package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collections
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.ContainmentOperator

@Utility
class ParticipationRelationExtension {

	// TODO support other structural operators by having them provide the containment relationships?
	def static isContainment(ParticipationRelation relation) {
		return (relation.operator.qualifiedName == ContainmentOperator.name)
	}

	def static getContainments(ParticipationRelation relation) {
		if (!relation.isContainment) {
			return Collections.emptyList()
		}
		val container = relation.rightClasses.head
		if (container === null) {
			return Collections.emptyList()
		}
		return relation.leftClasses.map [ contained |
			new Containment(contained, container, null)
		]
	}
}

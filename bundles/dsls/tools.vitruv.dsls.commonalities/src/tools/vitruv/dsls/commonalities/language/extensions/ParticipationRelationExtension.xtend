package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collections
import tools.vitruv.dsls.commonalities.language.ParticipationRelation

import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationRelationOperatorExtension.*

@Utility
package class ParticipationRelationExtension {

	def static isContainment(ParticipationRelation relation) {
		return relation.operator.isContainment
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

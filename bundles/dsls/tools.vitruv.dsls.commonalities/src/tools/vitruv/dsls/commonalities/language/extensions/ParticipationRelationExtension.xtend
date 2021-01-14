package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.commonalities.participation.Containment
import tools.vitruv.dsls.commonalities.participation.ReferenceContainment

import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationPartExtension.*
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.ContainmentOperator
import org.eclipse.xtext.common.types.JvmDeclaredType
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.ParticipationRelationOperator
import static extension tools.vitruv.dsls.commonalities.util.JvmAnnotationHelper.*

@Utility
package class ParticipationRelationExtension {
	static def isContainment(ParticipationRelation relation) {
		relation.operator.qualifiedName == ContainmentOperator.name
	}

	static def Iterable<Containment> getContainments(ParticipationRelation relation) {
		if (!relation.isContainment) {
			return emptyList()
		}
		val container = relation.container
		if (container === null) {
			return emptyList()
		}
		return relation.leftParts.map [ containedPart |
			new ReferenceContainment(container, containedPart.container, null)
		]
	}

	private static def getParticipationRelationOperatorAnnotation(JvmDeclaredType operatorType) {
		return operatorType.annotations.filter[annotation.qualifiedName == ParticipationRelationOperator.name].head
	}
	
	static def getParticipationRelationOperatorName(JvmDeclaredType operatorType) {
		return operatorType.participationRelationOperatorAnnotation?.getStringAnnotationValue('name')
	}

	static def getOperatorName(ParticipationRelation relation) {
		relation.operator.participationRelationOperatorName
	}
}

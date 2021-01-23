package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.xtext.common.types.JvmDeclaredType
import tools.vitruv.dsls.commonalities.language.ParticipationRelation
import tools.vitruv.dsls.commonalities.participation.Containment
import tools.vitruv.dsls.commonalities.participation.ReferenceContainment
import tools.vitruv.extensions.dslruntime.commonalities.operators.CommonalitiesOperatorConventions
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.ContainmentOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.ParticipationRelationOperator

import static extension tools.vitruv.dsls.commonalities.language.extensions.ParticipationPartExtension.*

@Utility
package class ParticipationRelationExtension {
	static val containmentOperatorTypeQualifiedName = CommonalitiesOperatorConventions.toOperatorTypeQualifiedName(
		ContainmentOperator.packageName,
		ContainmentOperator.annotations.filter(ParticipationRelationOperator).head.name 
	)
	
	// TODO support other structural operators
	static def isContainment(ParticipationRelation relation) {
		relation.operator.qualifiedName == containmentOperatorTypeQualifiedName
	}

	static def Iterable<Containment> getContainments(ParticipationRelation relation) {
		if (!relation.isContainment) {
			return emptyList()
		}
		val container = relation.declaredContainerClass
		if (container === null) {
			return emptyList()
		}
		return relation.leftParts.map [ containedPart |
			new ReferenceContainment(container, containedPart.declaredContainerClass, null)
		]
	}

	static def getParticipationRelationOperatorName(JvmDeclaredType operatorType) {
		CommonalitiesOperatorConventions.toOperatorLanguageName(operatorType.simpleName)
	}

	static def getOperatorName(ParticipationRelation relation) {
		relation.operator.participationRelationOperatorName
	}
}

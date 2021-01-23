package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.xtext.common.types.JvmDeclaredType
import tools.vitruv.dsls.commonalities.language.ParticipationRelationOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.ContainmentOperator

import tools.vitruv.extensions.dslruntime.commonalities.operators.CommonalitiesOperatorConventions

@Utility
package class ParticipationRelationOperatorExtension {
	static val containmentOperatorTypeQualifiedName = CommonalitiesOperatorConventions.toOperatorTypeQualifiedName(
		ContainmentOperator.packageName,
		ContainmentOperator.annotations
			.filter(tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.ParticipationRelationOperator)
			.head.name
	)
	
	static def getParticipationRelationOperatorName(JvmDeclaredType operatorType) {
		CommonalitiesOperatorConventions.toOperatorLanguageName(operatorType.simpleName)
	}

	static def getName(ParticipationRelationOperator operator) {
		operator.jvmType.participationRelationOperatorName
	}

	// TODO support other structural operators
	static def isContainment(ParticipationRelationOperator operator) {
		operator.jvmType.qualifiedName == containmentOperatorTypeQualifiedName
	}
}

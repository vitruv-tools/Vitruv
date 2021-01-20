package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.xtext.common.types.JvmDeclaredType
import tools.vitruv.dsls.commonalities.language.ParticipationConditionOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.ContainmentOperator

import tools.vitruv.extensions.dslruntime.commonalities.operators.CommonalitiesOperatorConventions

@Utility
package class ParticipationConditionOperatorExtension {
	static val containmentOperatorTypeQualifiedName = CommonalitiesOperatorConventions.toOperatorTypeQualifiedName(
		ContainmentOperator.packageName,
		ContainmentOperator.annotations
			.filter(tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.ParticipationConditionOperator)
			.head.name
	)

	static def getParticipationConditionOperatorName(JvmDeclaredType operatorType) {
		CommonalitiesOperatorConventions.toOperatorLanguageName(operatorType.simpleName)
	}

	static def getName(ParticipationConditionOperator operator) {
		return operator.jvmType.participationConditionOperatorName
	}

	// TODO support other structural operators
	static def isContainment(ParticipationConditionOperator operator) {
		operator.jvmType.qualifiedName == containmentOperatorTypeQualifiedName
	}
}

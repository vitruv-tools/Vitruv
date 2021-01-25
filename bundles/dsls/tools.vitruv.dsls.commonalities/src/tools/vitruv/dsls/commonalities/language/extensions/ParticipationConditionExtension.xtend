package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.xtext.common.types.JvmDeclaredType
import tools.vitruv.dsls.commonalities.language.BidirectionalParticipationCondition
import tools.vitruv.dsls.commonalities.language.CheckedParticipationCondition
import tools.vitruv.dsls.commonalities.language.EnforcedParticipationCondition
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationCondition
import tools.vitruv.dsls.commonalities.participation.Containment
import tools.vitruv.dsls.commonalities.participation.ReferenceContainment
import tools.vitruv.extensions.dslruntime.commonalities.operators.CommonalitiesOperatorConventions
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.ContainmentOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.ParticipationConditionOperator

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.OperandExtension.*

@Utility
package class ParticipationConditionExtension {
	static val containmentOperatorTypeQualifiedName = CommonalitiesOperatorConventions.toOperatorTypeQualifiedName(
		ContainmentOperator.packageName,
		ContainmentOperator.annotations.filter(ParticipationConditionOperator).head.name 
	)

	static def getParticipationConditionOperatorName(JvmDeclaredType operatorType) {
		CommonalitiesOperatorConventions.toOperatorLanguageName(operatorType.simpleName)
	}

	static def getName(ParticipationCondition condition) {
		condition.operator.participationConditionOperatorName
	}

	// TODO support other structural operators
	static def isContainment(ParticipationCondition condition) {
		condition.operator.qualifiedName == containmentOperatorTypeQualifiedName
	}

	static def Participation getParticipation(ParticipationCondition participationCondition) {
		participationCondition.getDirectEContainer(Participation)
	}

	static def dispatch isEnforced(BidirectionalParticipationCondition condition) {
		true
	}

	static def dispatch isEnforced(EnforcedParticipationCondition condition) {
		true
	}

	static def dispatch isEnforced(CheckedParticipationCondition condition) {
		false
	}

	static def dispatch isChecked(BidirectionalParticipationCondition condition) {
		true
	}

	static def dispatch isChecked(EnforcedParticipationCondition condition) {
		false
	}

	static def dispatch isChecked(CheckedParticipationCondition condition) {
		true
	}

	static def Containment getContainment(ParticipationCondition condition) {
		if (!condition.isContainment) {
			return null
		}
		val containerOperand = condition.rightOperands.head
		val container = containerOperand?.participationClass
		if (container === null) {
			return null
		}
		val contained = condition.leftOperand.participationClass
		return new ReferenceContainment(container, contained, containerOperand.participationAttribute)
	}
}

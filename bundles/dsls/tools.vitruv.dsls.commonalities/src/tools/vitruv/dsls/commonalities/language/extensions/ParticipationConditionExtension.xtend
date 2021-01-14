package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.dsls.commonalities.language.BidirectionalParticipationCondition
import tools.vitruv.dsls.commonalities.language.CheckedParticipationCondition
import tools.vitruv.dsls.commonalities.language.EnforcedParticipationCondition
import tools.vitruv.dsls.commonalities.language.Participation
import tools.vitruv.dsls.commonalities.language.ParticipationCondition
import tools.vitruv.dsls.commonalities.participation.Containment
import tools.vitruv.dsls.commonalities.participation.ReferenceContainment

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageElementExtension.*
import static extension tools.vitruv.dsls.commonalities.language.extensions.OperandExtension.*
import static extension tools.vitruv.dsls.commonalities.util.JvmAnnotationHelper.*
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.ParticipationConditionOperator
import org.eclipse.xtext.common.types.JvmDeclaredType
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.condition.ContainmentOperator

@Utility
package class ParticipationConditionExtension {

	static def Participation getParticipation(ParticipationCondition participationCondition) {
		return participationCondition.getDirectContainer(Participation)
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
	
	private static def getParticipationConditionOperatorAnnotation(JvmDeclaredType operatorType) {
		return operatorType.annotations
			.filter[annotation.qualifiedName == ParticipationConditionOperator.name]
			.head
	}

	static def getParticipationConditionOperatorName(JvmDeclaredType operatorType) {
		operatorType.participationConditionOperatorAnnotation?.getStringAnnotationValue('name')
	}

	static def getOperatorName(ParticipationCondition condition) {
		condition.operator.participationConditionOperatorName
	}

	// TODO support other structural operators
	static def isContainment(ParticipationCondition condition) {
		return condition.operator.qualifiedName == ContainmentOperator.name
	}
}

package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.xtext.common.types.JvmDeclaredType
import tools.vitruv.dsls.commonalities.language.ParticipationRelationOperator
import tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.ContainmentOperator

import static extension tools.vitruv.dsls.commonalities.util.JvmAnnotationHelper.*

@Utility
package class ParticipationRelationOperatorExtension {

	static val ANNOTATION = tools.vitruv.extensions.dslruntime.commonalities.operators.participation.relation.ParticipationRelationOperator
	static val ANNOTATION_NAME = ANNOTATION.name

	private static def getParticipationRelationOperatorAnnotation(JvmDeclaredType operatorType) {
		return operatorType.annotations
			.filter[annotation.qualifiedName == ANNOTATION_NAME]
			.head
	}

	static def getParticipationRelationOperatorName(JvmDeclaredType operatorType) {
		val annotation = operatorType.participationRelationOperatorAnnotation
		if (annotation === null) return null
		return annotation.getStringAnnotationValue('name')
	}

	static def getName(ParticipationRelationOperator operator) {
		return operator.jvmType.participationRelationOperatorName
	}

	// TODO support other structural operators
	static def isContainment(ParticipationRelationOperator operator) {
		return (operator.jvmType.qualifiedName == ContainmentOperator.name)
	}
}

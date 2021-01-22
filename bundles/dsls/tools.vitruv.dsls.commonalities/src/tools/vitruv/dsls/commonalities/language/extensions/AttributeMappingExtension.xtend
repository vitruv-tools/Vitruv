package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.xtext.common.types.JvmDeclaredType

import static extension tools.vitruv.dsls.commonalities.util.JvmAnnotationHelper.*
import tools.vitruv.extensions.dslruntime.commonalities.operators.CommonalitiesOperatorConventions
import tools.vitruv.dsls.commonalities.language.OperatorAttributeMapping

@Utility
package class AttributeMappingExtension {
	private static def getAttributeMappingOperatorAnnotation(JvmDeclaredType operatorType) {
		return operatorType.annotations
			.filter[annotation.qualifiedName == tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.attribute.AttributeMappingOperator.name]
			.head
	}

	static def getAttributeMappingOperatorName(JvmDeclaredType operatorType) {
		CommonalitiesOperatorConventions.toOperatorLanguageName(operatorType.simpleName)
	}

	static def getName(OperatorAttributeMapping mapping) {
		return mapping.operator.attributeMappingOperatorName
	}

	private static def AttributeTypeDescription getAttributeTypeDescription(
		JvmDeclaredType operator,
		String valueName
	) {
		val annotation = operator.attributeMappingOperatorAnnotation
		if (annotation === null) return null
		val typeAnnotation = annotation.getAnnotationAnnotationValue(valueName)
		val multiValued = typeAnnotation.getBooleanAnnotationValue('multiValued')
		val typeRef = typeAnnotation.getTypeAnnotationValue('type')
		return new AttributeTypeDescription(multiValued, typeRef.qualifiedName)
	}

	static def AttributeTypeDescription getCommonalityAttributeTypeDescription(OperatorAttributeMapping mapping) {
		return mapping.operator.getAttributeTypeDescription('commonalityAttributeType')
	}

	static def AttributeTypeDescription getParticipationAttributeTypeDescription(OperatorAttributeMapping mapping) {
		return mapping.operator.getAttributeTypeDescription('participationAttributeType')
	}
}

package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.xtext.common.types.JvmDeclaredType
import tools.vitruv.dsls.commonalities.language.ReferenceMappingOperator

import static extension tools.vitruv.dsls.commonalities.util.JvmAnnotationHelper.*

@Utility
package class ReferenceMappingOperatorExtension {

	static val ANNOTATION = tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.ReferenceMappingOperator
	static val ANNOTATION_NAME = ANNOTATION.name

	private static def getReferenceMappingOperatorAnnotation(JvmDeclaredType operatorType) {
		return operatorType.annotations
			.filter[annotation.qualifiedName == ANNOTATION_NAME]
			.head
	}

	static def getReferenceMappingOperatorName(JvmDeclaredType operatorType) {
		val annotation = operatorType.referenceMappingOperatorAnnotation
		if (annotation === null) return null
		return annotation.getStringAnnotationValue('name')
	}

	static def getName(ReferenceMappingOperator operator) {
		return operator.jvmType.referenceMappingOperatorName
	}

	static def boolean isMultiValued(ReferenceMappingOperator operator) {
		val annotation = operator.jvmType.referenceMappingOperatorAnnotation
		if (annotation === null) return false
		return annotation.getBooleanAnnotationValue('isMultiValued')
	}

	static def boolean isAttributeReference(ReferenceMappingOperator operator) {
		val annotation = operator.jvmType.referenceMappingOperatorAnnotation
		if (annotation === null) return false
		return annotation.getBooleanAnnotationValue('isAttributeReference')
	}
}

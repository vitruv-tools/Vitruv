package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.xtext.common.types.JvmDeclaredType
import tools.vitruv.dsls.commonalities.language.ReferenceMappingOperator

import static extension tools.vitruv.dsls.commonalities.util.JvmAnnotationHelper.*
import tools.vitruv.extensions.dslruntime.commonalities.operators.CommonalitiesOperatorConventions

@Utility
package class ReferenceMappingOperatorExtension {
	private static def getReferenceMappingOperatorAnnotation(JvmDeclaredType operatorType) {
		return operatorType.annotations
			.filter[annotation.qualifiedName == tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.ReferenceMappingOperator.name]
			.head
	}

	static def getReferenceMappingOperatorName(JvmDeclaredType operatorType) {
		CommonalitiesOperatorConventions.toOperatorLanguageName(operatorType.simpleName)
	}

	static def getName(ReferenceMappingOperator operator) {
		operator.jvmType.referenceMappingOperatorName
	}

	static def boolean isMultiValued(ReferenceMappingOperator operator) {
		val annotation = operator.jvmType.referenceMappingOperatorAnnotation
		if (annotation === null) return false
		annotation.getBooleanAnnotationValue('isMultiValued')
	}

	static def boolean isAttributeReference(ReferenceMappingOperator operator) {
		val annotation = operator.jvmType.referenceMappingOperatorAnnotation
		if (annotation === null) return false
		annotation.getBooleanAnnotationValue('isAttributeReference')
	}
}

package tools.vitruv.dsls.commonalities.language.extensions

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.xtext.common.types.JvmDeclaredType

import static extension tools.vitruv.dsls.commonalities.util.JvmAnnotationHelper.*
import tools.vitruv.extensions.dslruntime.commonalities.operators.CommonalitiesOperatorConventions
import tools.vitruv.dsls.commonalities.language.OperatorReferenceMapping

@Utility
package class ReferenceMappingExtension {
	private static def getReferenceMappingOperatorAnnotation(JvmDeclaredType operatorType) {
		return operatorType.annotations
			.filter[annotation.qualifiedName == tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference.ReferenceMappingOperator.name]
			.head
	}

	static def getReferenceMappingOperatorName(JvmDeclaredType operatorType) {
		CommonalitiesOperatorConventions.toOperatorLanguageName(operatorType.simpleName)
	}

	static def getName(OperatorReferenceMapping mapping) {
		mapping.operator.referenceMappingOperatorName
	}

	static def boolean isMultiValued(OperatorReferenceMapping mapping) {
		val annotation = mapping.operator.referenceMappingOperatorAnnotation
		if (annotation === null) return false
		annotation.getBooleanAnnotationValue('isMultiValued')
	}

	static def boolean isAttributeReference(OperatorReferenceMapping mapping) {
		val annotation =  mapping.operator.referenceMappingOperatorAnnotation
		if (annotation === null) return false
		annotation.getBooleanAnnotationValue('isAttributeReference')
	}
}

package tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference

import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class ReferenceMappingOperatorHelper {

	private def static ReferenceMappingOperator getAnnotation(IReferenceMappingOperator operator) {
		val annotation = operator.class.getAnnotation(ReferenceMappingOperator)
		if (annotation === null) {
			throw new IllegalStateException('''Missing operator annotation for reference mapping operator: «
				operator.class.name»''')
		}
		return annotation
	}

	def static getName(IReferenceMappingOperator operator) {
		return operator.annotation.name
	}

	def static isMultiValued(IReferenceMappingOperator operator) {
		return operator.annotation.isMultiValued
	}

	def static isAttributeReference(IReferenceMappingOperator operator) {
		return operator.annotation.isAttributeReference
	}
}

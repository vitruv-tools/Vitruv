package tools.vitruv.extensions.dslruntime.commonalities.operators.mapping.reference

import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class ReferenceMappingOperatorHelper {

	private static def ReferenceMappingOperator getAnnotation(IReferenceMappingOperator operator) {
		val annotation = operator.class.getAnnotation(ReferenceMappingOperator)
		if (annotation === null) {
			throw new IllegalStateException('''Missing operator annotation for reference mapping operator: «
				operator.class.name»''')
		}
		return annotation
	}

	static def getName(IReferenceMappingOperator operator) {
		return operator.annotation.name
	}

	static def isMultiValued(IReferenceMappingOperator operator) {
		return operator.annotation.isMultiValued
	}

	static def isAttributeReference(IReferenceMappingOperator operator) {
		return operator.annotation.isAttributeReference
	}
}

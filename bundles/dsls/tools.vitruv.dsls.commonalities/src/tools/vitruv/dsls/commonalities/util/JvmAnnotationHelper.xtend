package tools.vitruv.dsls.commonalities.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.xtext.common.types.JvmAnnotationAnnotationValue
import org.eclipse.xtext.common.types.JvmAnnotationReference
import org.eclipse.xtext.common.types.JvmBooleanAnnotationValue
import org.eclipse.xtext.common.types.JvmStringAnnotationValue
import org.eclipse.xtext.common.types.JvmTypeAnnotationValue
import org.eclipse.xtext.common.types.JvmTypeReference

@Utility
class JvmAnnotationHelper {

	static def String getStringAnnotationValue(JvmAnnotationReference annotation, String valueName) {
		return annotation.values
			.filter[it.valueName == valueName]
			.filter(JvmStringAnnotationValue).head
			?.values?.head
	}

	static def boolean getBooleanAnnotationValue(JvmAnnotationReference annotation, String valueName) {
		return annotation.values
			.filter[it.valueName == valueName]
			.filter(JvmBooleanAnnotationValue).head
			?.values?.head
	}

	static def JvmTypeReference getTypeAnnotationValue(JvmAnnotationReference annotation, String valueName) {
		return annotation.values
			.filter[it.valueName == valueName]
			.filter(JvmTypeAnnotationValue).head
			?.values?.head
	}

	static def JvmAnnotationReference getAnnotationAnnotationValue(JvmAnnotationReference annotation,
		String valueName) {
		return annotation.values
			.filter[it.valueName == valueName]
			.filter(JvmAnnotationAnnotationValue).head
			?.values?.head
	}
}

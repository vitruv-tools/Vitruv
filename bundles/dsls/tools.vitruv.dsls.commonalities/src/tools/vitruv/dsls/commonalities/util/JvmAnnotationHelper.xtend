package tools.vitruv.dsls.commonalities.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.xtext.common.types.JvmAnnotationReference
import org.eclipse.xtext.common.types.JvmBooleanAnnotationValue
import org.eclipse.xtext.common.types.JvmStringAnnotationValue

@Utility
class JvmAnnotationHelper {

	def static String getStringAnnotationValue(JvmAnnotationReference annotation, String valueName) {
		return annotation.explicitValues
			.filter[it.valueName == valueName]
			.filter(JvmStringAnnotationValue).head
			?.values?.head
	}

	def static boolean getBooleanAnnotationValue(JvmAnnotationReference annotation, String valueName) {
		return annotation.explicitValues
			.filter[it.valueName == valueName]
			.filter(JvmBooleanAnnotationValue).head
			?.values?.head
	}
}

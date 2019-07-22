package tools.vitruv.dsls.mappings.generator.action

import org.eclipse.xtext.xbase.XFeatureCall
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter

interface FeatureRoutineCall {

	def void connectRoutineCall(MappingParameter parameter,  XFeatureCall call)
}

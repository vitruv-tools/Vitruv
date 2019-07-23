package tools.vitruv.dsls.mappings.generator.action

import org.eclipse.xtext.xbase.XFeatureCall
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder

interface FeatureRoutineCall {

	def void connectRoutineCall(FluentRoutineBuilder callingRoutine, XFeatureCall call)
}

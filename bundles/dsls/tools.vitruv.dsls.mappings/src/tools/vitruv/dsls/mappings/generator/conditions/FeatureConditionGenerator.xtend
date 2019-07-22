package tools.vitruv.dsls.mappings.generator.conditions

import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider
import org.eclipse.xtext.xbase.XFeatureCall
import org.eclipse.xtext.xbase.XExpression

interface FeatureConditionGenerator {
	
	def boolean feasibleForParameter(MappingParameter parameter)
	
	def XExpression generateFeatureCondition(RoutineTypeProvider provider, boolean usesNewValue)
}
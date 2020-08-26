package tools.vitruv.dsls.mappings.generator.conditions

import org.eclipse.xtext.xbase.XExpression
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingParameter
import tools.vitruv.dsls.reactions.builder.TypeProvider

interface FeatureConditionGenerator {
	
	def boolean feasibleForParameter(MappingParameter parameter)
	
	def XExpression generateFeatureCondition(TypeProvider provider, XExpression variable)
	
	def boolean hasCorrespondenceInitialization()
	
	def XExpression generateCorrespondenceInitialization(TypeProvider provider)
}
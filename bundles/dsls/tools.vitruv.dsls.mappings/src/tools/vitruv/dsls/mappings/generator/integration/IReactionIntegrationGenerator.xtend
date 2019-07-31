package tools.vitruv.dsls.mappings.generator.integration

import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import org.eclipse.xtext.generator.IFileSystemAccess2
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import tools.vitruv.dsls.mappings.generator.MappingGeneratorContext

interface IReactionIntegrationGenerator {
	
	def void init(MappingGeneratorContext l2rContext, MappingGeneratorContext r2lContext);
	
	def void check(Mapping mapping);
		
	def void generate(IFileSystemAccess2 fsa, IReactionsGenerator reactionsGenerator);
	
}
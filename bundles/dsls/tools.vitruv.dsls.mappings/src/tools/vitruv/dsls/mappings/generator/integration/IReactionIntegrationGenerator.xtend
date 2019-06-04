package tools.vitruv.dsls.mappings.generator.integration

import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import org.eclipse.xtext.generator.IFileSystemAccess2
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator

interface IReactionIntegrationGenerator {
	
	def void init(ReactionGeneratorContext l2rContext, ReactionGeneratorContext r2lContext);
	
	def void check(Mapping mapping);
	
	def void generate(IFileSystemAccess2 fsa, IReactionsGenerator reactionsGenerator);
	
}
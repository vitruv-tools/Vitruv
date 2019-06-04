package tools.vitruv.dsls.mappings.generator.integration

import tools.vitruv.dsls.mappings.generator.ReactionGeneratorContext
import org.eclipse.xtext.generator.IFileSystemAccess2
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator

class EmbeddedReactionIntegrationGenerator extends AbstractReactionIntegrationGenerator {

	private ReactionIntegrationCache cache = new ReactionIntegrationCache;

	override init(ReactionGeneratorContext l2rContext, ReactionGeneratorContext r2lContext) {
		this.l2rIntegration = l2rContext;
		this.r2lIntegration = r2lContext
	}
	
	override generate(IFileSystemAccess2 fsa, IReactionsGenerator reactionsGenerator) {
		//nothing to do
	}

}

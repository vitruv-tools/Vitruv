package tools.vitruv.dsls.mappings.generator.integration

import org.eclipse.xtext.generator.IFileSystemAccess2
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator

class EmbeddedReactionIntegrationGenerator extends AbstractReactionIntegrationGenerator {


	override init() {
		this.l2rIntegration = l2rContext;
		this.r2lIntegration = r2lContext
	}
	
	override generate(IFileSystemAccess2 fsa, IReactionsGenerator reactionsGenerator) {
		//nothing to do
	}

}

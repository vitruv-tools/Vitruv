package tools.vitruv.dsls.mappings.generator.integration

import java.util.ArrayList
import java.util.List
import org.eclipse.xtext.generator.IFileSystemAccess2
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory

class StandaloneReactionIntegrationGenerator extends AbstractReactionIntegrationGenerator {

	private List<StandaloneReactionIntegration> integrations = new ArrayList<StandaloneReactionIntegration>

	override init() {
		l2rIntegration = new StandaloneReactionIntegration(l2rContext.segment, true);
		integrations.add(l2rIntegration as StandaloneReactionIntegration);
		r2lIntegration = new StandaloneReactionIntegration(r2lContext.segment, false);
		integrations.add(r2lIntegration as StandaloneReactionIntegration);
	}

	override generate(IFileSystemAccess2 fsa, IReactionsGenerator reactionsGenerator) {
		val file = ReactionsLanguageFactory.eINSTANCE.createReactionsFile
		integrations.filter[filled].forEach [
			file.reactionsSegments.add(it.reactionsSegment)
		]
		if (file.reactionsSegments.size > 0) {
			file.reactionsSegments.forEach [
				println(it)
				reactions.forEach[println(it)]
				routines.forEach[println(it)]
			]
			file.metamodelImports += l2rContext.mappingsFile.metamodelImports
			print('write integration file')
			reactionsGenerator.addReactionsFile("integration", file)
		}
	}
}

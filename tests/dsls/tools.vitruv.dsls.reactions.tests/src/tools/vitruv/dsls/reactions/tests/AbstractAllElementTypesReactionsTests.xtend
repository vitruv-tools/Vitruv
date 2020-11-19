package tools.vitruv.dsls.reactions.tests

import tools.vitruv.testutils.domains.AllElementTypesDomainProvider
import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import tools.vitruv.testutils.VitruvApplicationTest

@ExtendWith(InjectionExtension)
@InjectWith(ReactionsLanguageInjectorProvider)
abstract class AbstractAllElementTypesReactionsTests extends VitruvApplicationTest {
	protected static val MODEL_FILE_EXTENSION = new AllElementTypesDomainProvider().domain.fileExtensions.get(0)

	protected static def String getProjectModelPath(String modelName) {
		"model/" + modelName + "." + MODEL_FILE_EXTENSION;
	}

	@Inject AllElementTypesRedundancyReactionsCompiler reactionCompiler

	override protected createChangePropagationSpecifications() {
		newArrayList(reactionCompiler.getNewChangePropagationSpecifications())
	}

	protected override getVitruvDomains() {
		return #[new AllElementTypesDomainProvider().domain]
	}
}

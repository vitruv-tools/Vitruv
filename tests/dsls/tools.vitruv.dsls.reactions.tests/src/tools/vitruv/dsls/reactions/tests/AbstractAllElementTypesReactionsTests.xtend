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
	@Inject AllElementTypesRedundancyReactionsCompiler reactionCompiler

	override protected createChangePropagationSpecifications() {
		reactionCompiler.getNewChangePropagationSpecifications()
	}

	protected override getVitruvDomains() {
		return #[new AllElementTypesDomainProvider().domain]
	}
}

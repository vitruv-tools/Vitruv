package tools.vitruv.dsls.reactions.tests.importTests

import com.google.inject.Inject
import tools.vitruv.dsls.reactions.tests.AbstractAllElementTypesReactionsTests
import tools.vitruv.testutils.domains.AllElementTypesDomainProvider

abstract class AbstractReactionImportsTests extends AbstractAllElementTypesReactionsTests {
	@Inject ImportTestReactionsCompiler reactionCompiler

	override protected createChangePropagationSpecifications() {
		newArrayList(reactionCompiler.getNewChangePropagationSpecifications())
	}

	protected override getVitruvDomains() {
		return #[new AllElementTypesDomainProvider().domain]
	}
}

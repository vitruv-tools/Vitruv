package tools.vitruv.dsls.reactions.tests.importTests

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.runner.RunWith
import tools.vitruv.dsls.reactions.tests.AbstractAllElementTypesReactionsTests
import tools.vitruv.dsls.reactions.tests.ReactionsLanguageInjectorProvider
import tools.vitruv.testutils.domains.AllElementTypesDomainProvider

@RunWith(XtextRunner)
@InjectWith(ReactionsLanguageInjectorProvider)
abstract class AbstractReactionImportsTests extends AbstractAllElementTypesReactionsTests {

	@Inject ImportTestReactionsCompiler reactionCompiler

	override protected createChangePropagationSpecifications() {
		newArrayList(reactionCompiler.getNewChangePropagationSpecifications())
	}

	protected override getVitruvDomains() {
		return #[new AllElementTypesDomainProvider().domain];
	}
}
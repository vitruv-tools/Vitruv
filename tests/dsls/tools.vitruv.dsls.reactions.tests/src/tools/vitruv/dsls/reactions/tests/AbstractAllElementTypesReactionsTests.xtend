package tools.vitruv.dsls.reactions.tests

import tools.vitruv.framework.tests.VitruviusApplicationTest
import tools.vitruv.framework.testutils.domains.AllElementTypesDomainProvider
import com.google.inject.Inject
import org.junit.runner.RunWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.InjectWith

@RunWith(XtextRunner)
@InjectWith(ReactionsLanguageInjectorProvider)
abstract class AbstractAllElementTypesReactionsTests extends VitruviusApplicationTest {
	protected static val MODEL_FILE_EXTENSION = new AllElementTypesDomainProvider().domain.fileExtensions.get(0);
	
	@Inject SimpleChangeReactionsCompiler reactionCompiler

	override protected createChangePropagationSpecifications() {
		#[reactionCompiler.newConcreteChangePropagationSpecification()]
	}
	
	protected override getVitruvDomains() {
		return #[new AllElementTypesDomainProvider().domain];
	}
}
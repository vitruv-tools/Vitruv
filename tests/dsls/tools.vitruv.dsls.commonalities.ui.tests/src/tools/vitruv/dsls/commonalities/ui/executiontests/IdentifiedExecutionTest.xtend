package tools.vitruv.dsls.commonalities.ui.executiontests

import tools.vitruv.framework.tests.VitruviusApplicationTest
import tools.vitruv.framework.testutils.domains.UmlMockupDomainProvider
import tools.vitruv.framework.testutils.domains.AllElementTypesDomainProvider
import tools.vitruv.framework.testutils.domains.AllElementTypes2DomainProvider
import tools.vitruv.framework.testutils.domains.PcmMockupDomainProvider
import org.junit.runner.RunWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.InjectWith
import com.google.inject.Inject
import org.junit.Test
import tools.vitruv.dsls.commonalities.ui.tests.CommonalitiesLanguageUiInjectorProvider

@RunWith(XtextRunner)
@InjectWith(CommonalitiesLanguageUiInjectorProvider)
class IdentifiedExecutionTest extends VitruviusApplicationTest {
	
	@Inject ExecutionTestCompiler compiler

	override protected cleanup() {
	}

	override protected setup() {
	}

	override protected createChangePropagationSpecifications() {
		compiler.changePropagationDefinitions
	}

	override protected getVitruvDomains() {
		#[new AllElementTypesDomainProvider, new AllElementTypes2DomainProvider, new PcmMockupDomainProvider,
			new UmlMockupDomainProvider].map[domain]
	}
	
	@Test
	def void foo() {}

}

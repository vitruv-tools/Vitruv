package tools.vitruv.dsls.commonalities.ui.tests.aliases

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.runner.RunWith
import tools.vitruv.dsls.commonalities.testutils.CombinedUiInjectorProvider
import tools.vitruv.dsls.commonalities.ui.executiontests.IdentifiedExecutionTest

/**
 * Inherits the Identified execution tests, but uses the modified commonalities
 * files in this package, which make use of aliases.
 */
@RunWith(XtextRunner)
@InjectWith(CombinedUiInjectorProvider)
class AliasesTest extends IdentifiedExecutionTest {

	@Inject AliasesTestCompiler compiler

	override protected createChangePropagationSpecifications() {
		compiler.changePropagationDefinitions
	}
}

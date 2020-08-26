package tools.vitruv.dsls.commonalities.ui.tests.aliases

import com.google.inject.Inject
import tools.vitruv.dsls.commonalities.ui.executiontests.IdentifiedExecutionTest

/**
 * Inherits the Identified execution tests, but uses the modified commonalities
 * files located in this package.
 */
class AliasesTest extends IdentifiedExecutionTest {

	@Inject AliasesTestCompiler compiler

	override protected createChangePropagationSpecifications() {
		compiler.changePropagationDefinitions
	}
}

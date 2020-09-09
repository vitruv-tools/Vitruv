package tools.vitruv.dsls.commonalities.ui.tests.conditions

import com.google.inject.Inject
import tools.vitruv.dsls.commonalities.ui.executiontests.IdentifiedExecutionTest

/**
 * Inherits the Identified execution tests, but uses the modified commonalities
 * files located in this package.
 * <p>
 * TODO: Expand these tests to make use of more complex conditions and containment hierarchies.
 */
class ConditionsTest extends IdentifiedExecutionTest {

	@Inject ConditionsTestCompiler compiler

	override protected createChangePropagationSpecifications() {
		compiler.changePropagationDefinitions
	}
}

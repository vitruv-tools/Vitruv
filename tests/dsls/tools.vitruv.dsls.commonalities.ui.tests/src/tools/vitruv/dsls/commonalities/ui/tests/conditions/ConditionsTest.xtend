package tools.vitruv.dsls.commonalities.ui.tests.conditions

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.runner.RunWith
import tools.vitruv.dsls.commonalities.testutils.CombinedUiInjectorProvider
import tools.vitruv.dsls.commonalities.ui.executiontests.IdentifiedExecutionTest

/**
 * Inherits the Identified execution tests, but uses the modified commonalities
 * files located in this package.
 * <p>
 * TODO: Expand these tests to make use of more complex conditions and containment hierarchies.
 */
@RunWith(XtextRunner)
@InjectWith(CombinedUiInjectorProvider)
class ConditionsTest extends IdentifiedExecutionTest {

	@Inject ConditionsTestCompiler compiler

	override protected createChangePropagationSpecifications() {
		compiler.changePropagationDefinitions
	}
}

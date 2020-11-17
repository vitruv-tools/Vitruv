package tools.vitruv.dsls.commonalities.ui.tests.aliases

import com.google.inject.Inject
import tools.vitruv.dsls.commonalities.ui.executiontests.IdentifiedExecutionTest
import tools.vitruv.dsls.commonalities.testutils.ExecutionTestCompiler

/**
 * Inherits the Identified execution tests, but uses the modified commonalities
 * files located in this package.
 */
class AliasesTest extends IdentifiedExecutionTest {

	@Inject
	new(ExecutionTestCompiler.Factory factory) {
		super(
			factory.createCompiler [
				projectName = 'commonalities-test-aliases'
				commonalities = #['Identified.commonality', 'Sub.commonality']
				domainDependencies = #[
					'tools.vitruv.testutils.domains',
					'tools.vitruv.testutils.metamodels'
				]
			]
		)
	}
}

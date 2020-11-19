package tools.vitruv.dsls.commonalities.ui.tests.conditions

import tools.vitruv.dsls.commonalities.ui.executiontests.IdentifiedExecutionTest
import tools.vitruv.dsls.commonalities.testutils.ExecutionTestCompiler

/**
 * Inherits the Identified execution tests, but uses the modified commonalities
 * files located in this package.
 * <p>
 * TODO: Expand these tests to make use of more complex conditions and containment hierarchies.
 */
class ConditionsTest extends IdentifiedExecutionTest {
	override createCompiler(ExecutionTestCompiler.Factory factory) {
		factory.createCompiler [
			projectName = 'commonalities-test-conditions'
			commonalities = #['Identified.commonality', 'Sub.commonality']
			domainDependencies = #[
				'tools.vitruv.testutils.domains',
				'tools.vitruv.testutils.metamodels'
			]
		]
	}
}

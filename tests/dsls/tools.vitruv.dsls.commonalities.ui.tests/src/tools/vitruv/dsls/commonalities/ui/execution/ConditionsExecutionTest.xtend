package tools.vitruv.dsls.commonalities.ui.execution

import tools.vitruv.dsls.commonalities.testutils.ExecutionTestCompiler
import org.junit.jupiter.api.DisplayName

/**
 * Inherits the Identified execution tests, but uses the modified commonalities
 * files located in this package.
 * <p>
 * TODO: Expand these tests to make use of more complex conditions and containment hierarchies.
 */
@DisplayName('executing commonalities with conditions')
class ConditionsExecutionTest extends IdentifiedExecutionTest {
	override createCompiler(ExecutionTestCompiler.Factory factory) {
		factory.createCompiler [
			commonalities = #['IdentifiedWithConditions.commonality', 'SubIdentified.commonality']
			domainDependencies = #[
				'tools.vitruv.testutils.domains',
				'tools.vitruv.testutils.metamodels'
			]
		]
	}
}

package tools.vitruv.dsls.commonalities.ui.execution

import tools.vitruv.dsls.commonalities.testutils.ExecutionTestCompiler
import org.junit.jupiter.api.DisplayName

/**
 * Inherits the Identified execution tests, but uses the modified commonalities
 * files located in this package.
 */
@DisplayName('executing commonalities with aliases')
class AliasesExecutionTest extends IdentifiedExecutionTest {
	override createCompiler(ExecutionTestCompiler.Factory factory) {
		factory.createCompiler [
			commonalities = #['IdentifiedWithAliases.commonality', 'SubWithAliases.commonality']
			domainDependencies = #[
				'tools.vitruv.testutils.domains',
				'tools.vitruv.testutils.metamodels'
			]
		]
	}
}

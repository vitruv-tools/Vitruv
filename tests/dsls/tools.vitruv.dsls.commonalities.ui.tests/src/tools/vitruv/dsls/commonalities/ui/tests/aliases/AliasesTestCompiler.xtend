package tools.vitruv.dsls.commonalities.ui.tests.aliases

import com.google.inject.Singleton
import tools.vitruv.dsls.commonalities.ui.executiontests.IdentifiedExecutionTestCompiler

/**
 * Uses the same domain dependencies and commonality file names as the
 * Identified execution test, but uses the files located in this package.
 */
@Singleton
class AliasesTestCompiler extends IdentifiedExecutionTestCompiler {

	static val TEST_PROJECT_NAME = "commonalities-test-aliases"

	override protected getProjectName() {
		return TEST_PROJECT_NAME
	}
}

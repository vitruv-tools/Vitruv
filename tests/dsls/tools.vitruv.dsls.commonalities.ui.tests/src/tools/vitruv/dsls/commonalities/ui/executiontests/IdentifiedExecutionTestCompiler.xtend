package tools.vitruv.dsls.commonalities.ui.executiontests

import com.google.inject.Singleton
import tools.vitruv.dsls.commonalities.testutils.ExecutionTestCompiler

@Singleton
class IdentifiedExecutionTestCompiler extends ExecutionTestCompiler {

	static val TEST_PROJECT_NAME = 'commonalities-test-identified'
	static val COMMONALITY_FILES = #['Identified.commonality', 'Sub.commonality']
	static val DOMAIN_DEPENDENCIES = #[
		'tools.vitruv.testutils.domains',
		'tools.vitruv.testutils.metamodels'
	]

	override protected getProjectName() {
		return TEST_PROJECT_NAME
	}

	override protected getCommonalityFiles() {
		return COMMONALITY_FILES
	}

	override protected getDomainDependencies() {
		return DOMAIN_DEPENDENCIES
	}
}

package tools.vitruv.dsls.commonalities.ui.executiontests

import com.google.inject.Singleton
import tools.vitruv.dsls.commonalities.ui.tests.util.ExecutionTestCompiler

@Singleton
class IdentifiedExecutionTestCompiler extends ExecutionTestCompiler {

	static val COMMONALITY_FILES = #['Identified.com', 'Sub.com']
	static val DOMAIN_DEPENDENCIES = #[
		'tools.vitruv.testutils.domains',
		'tools.vitruv.testutils.metamodels'
	]

	override protected getCommonalityFiles() {
		return COMMONALITY_FILES
	}

	override protected getDomainDependencies() {
		return DOMAIN_DEPENDENCIES
	}
}

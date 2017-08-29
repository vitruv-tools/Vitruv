package tools.vitruv.dsls.commonalities.ui.executiontests

import allElementTypes2.AllElementTypes2Factory
import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith
import tools.vitruv.dsls.commonalities.ui.tests.CommonalitiesLanguageUiInjectorProvider
import tools.vitruv.framework.domains.VitruvDomainProvider
import tools.vitruv.framework.testutils.domains.AllElementTypes2DomainProvider

@RunWith(XtextRunner)
@InjectWith(CommonalitiesLanguageUiInjectorProvider)
class IdentifiedExecutionTest extends CommonalitiesExecutionTest {
	
	@Inject ExecutionTestCompiler compiler

	override protected cleanup() {
	}

	override protected setup() {
	}

	override protected createChangePropagationSpecifications() {
		compiler.changePropagationDefinitions
	}
	
	override protected unresolveChanges() {
		true
	}

	@Test
	def void rootInsert() {
		val root = AllElementTypes2Factory.eINSTANCE.createRoot2 => [
			id2 = 'testid'
		]
		createAndSynchronizeModel(AllElementTypes2DomainProvider.sourceModelName, root)
	}
	
	def private String getSourceModelName(Class<? extends VitruvDomainProvider<?>> domain) {
		'''model/source.«domain.newInstance.domain.fileExtensions.head»'''
	}
}

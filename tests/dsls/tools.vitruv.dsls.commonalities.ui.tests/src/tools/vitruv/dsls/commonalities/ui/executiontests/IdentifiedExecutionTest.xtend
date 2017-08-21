package tools.vitruv.dsls.commonalities.ui.executiontests

import tools.vitruv.framework.testutils.domains.AllElementTypes2DomainProvider
import org.junit.runner.RunWith
import org.eclipse.xtext.testing.XtextRunner
import org.eclipse.xtext.testing.InjectWith
import com.google.inject.Inject
import org.junit.Test
import tools.vitruv.dsls.commonalities.ui.tests.CommonalitiesLanguageUiInjectorProvider
import allElementTypes.AllElementTypesFactory
import tools.vitruv.framework.domains.VitruvDomainProvider

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

	@Test
	def void rootInsert() {
		val root = AllElementTypesFactory.eINSTANCE.createRoot => [
			id = 'testid'
		]
		createAndSynchronizeModel(AllElementTypes2DomainProvider.sourceModelName, root)
	}
	
	def private String getSourceModelName(Class<? extends VitruvDomainProvider<?>> domain) {
		'''model/source.«domain.newInstance.domain.fileExtensions.head»'''
	}
}

package tools.vitruv.dsls.commonalities.ui.executiontests

import allElementTypes2.AllElementTypes2Factory
import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith
import tools.vitruv.dsls.commonalities.ui.tests.CommonalitiesLanguageUiInjectorProvider

import static tools.vitruv.framework.tests.matchers.ModelMatchers.*
import static org.hamcrest.MatcherAssert.assertThat
import allElementTypes.AllElementTypesFactory
import pcm_mockup.Pcm_mockupFactory
import uml_mockup.Uml_mockupFactory

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
		createAndSynchronizeModel('testid.allElementTypes2', root2 => [id2 = 'testid'])
		
		assertThat(resourceAt('testid.allElementTypes2'), contains(root2 => [id2 = 'testid']))
		assertThat(resourceAt('testid.allElementTypes'), contains(root => [id = 'testid']))
		assertThat(resourceAt('testid.pcm_mockup'), contains(repository => [id = 'testid']))
		assertThat(resourceAt('testid.uml_mockup'), contains(uPackage => [id = 'testid']))
	}
	
	def private static root2() {
		AllElementTypes2Factory.eINSTANCE.createRoot2
	}
	
	def private static root() {
		AllElementTypesFactory.eINSTANCE.createRoot
	}
	
	def private static repository() {
		Pcm_mockupFactory.eINSTANCE.createRepository
	}
	
	def private static uPackage() {
		Uml_mockupFactory.eINSTANCE.createUPackage
	}
}

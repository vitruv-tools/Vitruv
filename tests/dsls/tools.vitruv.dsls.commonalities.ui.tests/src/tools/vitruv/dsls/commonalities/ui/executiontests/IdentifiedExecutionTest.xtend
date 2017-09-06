package tools.vitruv.dsls.commonalities.ui.executiontests

import allElementTypes.AllElementTypesFactory
import allElementTypes2.AllElementTypes2Factory
import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import pcm_mockup.Pcm_mockupFactory
import tools.vitruv.dsls.commonalities.ui.tests.CommonalitiesLanguageUiInjectorProvider
import uml_mockup.Uml_mockupFactory

import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.framework.tests.matchers.ModelMatchers.*

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
	
	@Test
	@Ignore
	def void setSimpleAttribute() {
		val insertedRoot2 = root2 => [id2 = 'startid']
		createAndSynchronizeModel('startid.allElementTypes2', insertedRoot2)
		
		saveAndSynchronizeChanges(insertedRoot2 => [id2 = '1st id'])
		assertThat(resourceAt('1st id.allElementTypes2'), contains(root2 => [id2 = '1st id']))
		assertThat(resourceAt('1st id.allElementTypes'), contains(root => [id = '1st id']))
		assertThat(resourceAt('1st id.pcm_mockup'), contains(repository => [id = '1st id']))
		assertThat(resourceAt('1st id.uml_mockup'), contains(uPackage => [id = '1st id']))
	}
	
	@Test
	def void rootWithReferenceInsert() {
		createAndSynchronizeModel('testid.allElementTypes2', root2 => [
			id2 = 'testid'
			multiValuedContainmentEReference2 += nonRoot2 => [id2 = 'testname']
		])
		
		assertThat(resourceAt('testid.allElementTypes2'), contains(root2 => [
			id2 = 'testid'
			multiValuedContainmentEReference2 += nonRoot2 => [id2 = 'testname']
		]))
		assertThat(resourceAt('testid.allElementTypes'), contains(root => [
			id = 'testid'
			multiValuedContainmentEReference += nonRoot => [id = 'testname']
		]))
		assertThat(resourceAt('testid.pcm_mockup'), contains(repository => [
			id = 'testid'
			components += component => [name = 'testname']
		]))
		assertThat(resourceAt('testid.uml_mockup'), contains(uPackage => [
			id = 'testid'
			classes += uClass => [name = 'testname']
		]))
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
	
	def private static nonRoot2() {
		AllElementTypes2Factory.eINSTANCE.createNonRoot2
	}
	
	def private static nonRoot() {
		AllElementTypesFactory.eINSTANCE.createNonRoot
	}
	
	def private static component() {
		Pcm_mockupFactory.eINSTANCE.createComponent
	}
	
	def private static uClass() {
		Uml_mockupFactory.eINSTANCE.createUClass
	}
}

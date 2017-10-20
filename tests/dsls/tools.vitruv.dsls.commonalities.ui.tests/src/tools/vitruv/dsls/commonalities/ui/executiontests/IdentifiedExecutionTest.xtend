package tools.vitruv.dsls.commonalities.ui.executiontests

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import allElementTypes2.AllElementTypes2Factory
import allElementTypes2.Root2
import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import pcm_mockup.Pcm_mockupFactory
import pcm_mockup.Repository
import tools.vitruv.dsls.commonalities.ui.tests.CommonalitiesLanguageUiInjectorProvider
import uml_mockup.UClass
import uml_mockup.Uml_mockupFactory

import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.framework.tests.matchers.ModelMatchers.*
import org.eclipse.emf.ecore.util.EcoreUtil

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
	def void multiRootInsert() {
		createAndSynchronizeModel('first.allElementTypes2', root2 => [id2 = 'first'])
		createAndSynchronizeModel('second.allElementTypes2', root2 => [id2 = 'second'])
		createAndSynchronizeModel('third.allElementTypes2', root2 => [id2 = 'third'])

		assertThat(resourceAt('first.allElementTypes2'), contains(root2 => [id2 = 'first']))
		assertThat(resourceAt('first.allElementTypes'), contains(root => [id = 'first']))
		assertThat(resourceAt('first.pcm_mockup'), contains(repository => [id = 'first']))
		assertThat(resourceAt('first.uml_mockup'), contains(uPackage => [id = 'first']))

		assertThat(resourceAt('second.allElementTypes2'), contains(root2 => [id2 = 'second']))
		assertThat(resourceAt('second.allElementTypes'), contains(root => [id = 'second']))
		assertThat(resourceAt('second.pcm_mockup'), contains(repository => [id = 'second']))
		assertThat(resourceAt('second.uml_mockup'), contains(uPackage => [id = 'second']))

		assertThat(resourceAt('third.allElementTypes2'), contains(root2 => [id2 = 'third']))
		assertThat(resourceAt('third.allElementTypes'), contains(root => [id = 'third']))
		assertThat(resourceAt('third.pcm_mockup'), contains(repository => [id = 'third']))
		assertThat(resourceAt('third.uml_mockup'), contains(uPackage => [id = 'third']))
	}
	
	@Test
	@Ignore
	// TODO does not work: ID resolution fails.
	def void rootDelete() {
		createAndSynchronizeModel('first.allElementTypes2', root2 => [id2 = 'first'])
		createAndSynchronizeModel('second.allElementTypes2', root2 => [id2 = 'second'])
		createAndSynchronizeModel('third.allElementTypes2', root2 => [id2 = 'third'])

		EcoreUtil.delete(Root.from('second.allElementTypes'))
		assertThat(resourceAt('second.allElementTypes'), doesNotExist)
	}

	@Test
	@Ignore
	def void setIdAttribute() {
		createAndSynchronizeModel('startid.allElementTypes2', root2 => [id2 = 'startid'])

		saveAndSynchronizeChanges(Root2.from('startid.allElementTypes2') => [id2 = '1st id'])
		assertThat(resourceAt('startid.allElementTypes2'), contains(root2 => [id2 = '1st id']))
		assertThat(resourceAt('startid.allElementTypes'), contains(root => [id = '1st id']))
		assertThat(resourceAt('startid.pcm_mockup'), contains(repository => [id = '1st id']))
		assertThat(resourceAt('startid.uml_mockup'), contains(uPackage => [id = '1st id']))

		// TODO this test case fails from here on, but it really shouldn’t! UUID resolution fails, see #175
		saveAndSynchronizeChanges(Root.from('startid.allElementTypes') => [id = '2nd id'])
		assertThat(resourceAt('startid.allElementTypes2'), contains(root2 => [id2 = '2nd id']))
		assertThat(resourceAt('startid.allElementTypes'), contains(root => [id = '2nd id']))
		assertThat(resourceAt('startid.pcm_mockup'), contains(repository => [id = '2nd id']))
		assertThat(resourceAt('startid.uml_mockup'), contains(uPackage => [id = '2nd id']))

		saveAndSynchronizeChanges(Repository.from('startid.pcm_mockup') => [id = '3th id'])
		assertThat(resourceAt('startid.allElementTypes2'), contains(root2 => [id2 = '3th id']))
		assertThat(resourceAt('startid.allElementTypes'), contains(root => [id = '3th id']))
		assertThat(resourceAt('startid.pcm_mockup'), contains(repository => [id = '3th id']))
		assertThat(resourceAt('startid.uml_mockup'), contains(uPackage => [id = '3th id']))

		saveAndSynchronizeChanges(UClass.from('startid.uml_mockup') => [id = '4th id'])
		assertThat(resourceAt('startid.allElementTypes2'), contains(root2 => [id2 = '4th id']))
		assertThat(resourceAt('startid.allElementTypes'), contains(root => [id = '4th id']))
		assertThat(resourceAt('startid.pcm_mockup'), contains(repository => [id = '4th id']))
		assertThat(resourceAt('startid.uml_mockup'), contains(uPackage => [id = '4th id']))
	}

	@Test
	def void setSimpleAttribute() {
		createAndSynchronizeModel('test.allElementTypes2', root2 => [
			singleValuedEAttribute2 = 0
			id2 = 'test'
		])

		saveAndSynchronizeChanges(Root2.from('test.allElementTypes2') => [singleValuedEAttribute2 = 1])
		assertThat(resourceAt('test.allElementTypes2'), contains(root2 => [
			singleValuedEAttribute2 = 1
			id2 = 'test'
		]))
		assertThat(resourceAt('test.allElementTypes'), contains(root => [
			singleValuedEAttribute = 1
			id = 'test'
		]))

		saveAndSynchronizeChanges(Root.from('test.allElementTypes') => [singleValuedEAttribute = 2])
		assertThat(resourceAt('test.allElementTypes2'), contains(root2 => [
			singleValuedEAttribute2 = 2
			id2 = 'test'
		]))
		assertThat(resourceAt('test.allElementTypes'), contains(root => [
			singleValuedEAttribute = 2
			id = 'test'
		]))

		saveAndSynchronizeChanges(Root2.from('test.allElementTypes2') => [singleValuedEAttribute2 = 3])
		assertThat(resourceAt('test.allElementTypes2'), contains(root2 => [
			singleValuedEAttribute2 = 3
			id2 = 'test'
		]))
		assertThat(resourceAt('test.allElementTypes'), contains(root => [
			singleValuedEAttribute = 3
			id = 'test'
		]))

		saveAndSynchronizeChanges(Root.from('test.allElementTypes') => [singleValuedEAttribute = 4])
		assertThat(resourceAt('test.allElementTypes2'), contains(root2 => [
			singleValuedEAttribute2 = 4
			id2 = 'test'
		]))
		assertThat(resourceAt('test.allElementTypes'), contains(root => [
			singleValuedEAttribute = 4
			id = 'test'
		]))
	}

	@Test
	def void setMultiValue() {
		createAndSynchronizeModel('test.allElementTypes2', root2 => [
			multiValuedEAttribute2 += #[1, 2, 3]
			id2 = 'test'
		])
		assertThat(resourceAt('test.allElementTypes2'), contains(root2 => [
			multiValuedEAttribute2 += #[1, 2, 3]
			id2 = 'test'
		]))
		assertThat(resourceAt('test.allElementTypes'), contains(root => [
			multiValuedEAttribute += #[1, 2, 3]
			id = 'test'
		]))

		saveAndSynchronizeChanges(Root2.from('test.allElementTypes2') => [multiValuedEAttribute2 += 4])
		assertThat(resourceAt('test.allElementTypes2'), contains(root2 => [
			multiValuedEAttribute2 += #[1, 2, 3, 4]
			id2 = 'test'
		]))
		assertThat(resourceAt('test.allElementTypes'), contains(root => [
			multiValuedEAttribute += #[1, 2, 3, 4]
			id = 'test'
		]))

		saveAndSynchronizeChanges(Root.from('test.allElementTypes') => [multiValuedEAttribute += 5])
		assertThat(resourceAt('test.allElementTypes2'), contains(root2 => [
			multiValuedEAttribute2 += #[1, 2, 3, 4, 5]
			id2 = 'test'
		]))
		assertThat(resourceAt('test.allElementTypes'), contains(root => [
			multiValuedEAttribute += #[1, 2, 3, 4, 5]
			id = 'test'
		]))

	/*		Vitruvius doesn’t correctly translate the changes?
	 * 		saveAndSynchronizeChanges(Root2.from('test.allElementTypes2') => [multiValuedEAttribute2 -= #[1, 3, 5]])
	 * 		assertThat(resourceAt('test.allElementTypes2'), contains(root2 => [
	 * 			multiValuedEAttribute2 += #[2, 4]
	 * 			id2 = 'test'
	 * 		]))
	 * 		assertThat(resourceAt('test.allElementTypes'), contains(root => [
	 * 			multiValuedEAttribute += #[2, 4]
	 * 			id = 'test'
	 * 		]))

	 * 		saveAndSynchronizeChanges(Root.from('test.allElementTypes') => [multiValuedEAttribute -= #[2]])
	 * 		assertThat(resourceAt('test.allElementTypes2'), contains(root2 => [
	 * 			multiValuedEAttribute2 += 4
	 * 			id2 = 'test'
	 * 		]))
	 * 		assertThat(resourceAt('test.allElementTypes'), contains(root => [
	 * 			multiValuedEAttribute += 4
	 * 			id = 'test'
	 ]))*/
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
		], ignoring('id')))
		assertThat(resourceAt('testid.uml_mockup'), contains(uPackage => [
			id = 'testid'
			classes += uClass => [name = 'testname']
		], ignoring('id')))
	}
	
	@Test
	def void multiReferenceInsert() {
		createAndSynchronizeModel('testid.allElementTypes2', root2 => [
			id2 = 'testid'
			multiValuedContainmentEReference2 += #[
				nonRoot2 => [id2 = 'first'],
				nonRoot2 => [id2 = 'second']
			]
		])
		assertThat(resourceAt('testid.allElementTypes2'), contains(root2 => [
			id2 = 'testid'
			multiValuedContainmentEReference2 += #[
				nonRoot2 => [id2 = 'first'],
				nonRoot2 => [id2 = 'second']
			]
		]))
		assertThat(resourceAt('testid.allElementTypes'), contains(root => [
			id = 'testid'
			multiValuedContainmentEReference += #[
				nonRoot => [id = 'first'],
				nonRoot => [id = 'second']				
			]
		]))
		assertThat(resourceAt('testid.pcm_mockup'), contains(repository => [
			id = 'testid'
			components += #[
				component => [name = 'first'],
				component => [name = 'second']	
			]
		], ignoring('id')))
		assertThat(resourceAt('testid.uml_mockup'), contains(uPackage => [
			id = 'testid'
			classes += #[
				uClass => [name = 'first'],
				uClass => [name = 'second']				
			]
		], ignoring('id')))
		
		saveAndSynchronizeChanges(Repository.from('testid.pcm_mockup') => [
			components += component => [name = 'third']
		])
		assertThat(resourceAt('testid.allElementTypes2'), contains(root2 => [
			id2 = 'testid'
			multiValuedContainmentEReference2 += #[
				nonRoot2 => [id2 = 'first'],
				nonRoot2 => [id2 = 'second'],
				nonRoot2 => [id2 = 'third']
			]
		]))
		assertThat(resourceAt('testid.allElementTypes'), contains(root => [
			id = 'testid'
			multiValuedContainmentEReference += #[
				nonRoot => [id = 'first'],
				nonRoot => [id = 'second'],
				nonRoot => [id = 'third']					
			]
		]))
		assertThat(resourceAt('testid.pcm_mockup'), contains(repository => [
			id = 'testid'
			components += #[
				component => [name = 'first'],
				component => [name = 'second'],
				component => [name = 'third']	
			]
		], ignoring('id')))
		assertThat(resourceAt('testid.uml_mockup'), contains(uPackage => [
			id = 'testid'
			classes += #[
				uClass => [name = 'first'],
				uClass => [name = 'second'],
				uClass => [name = 'third']				
			]
		], ignoring('id')))
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

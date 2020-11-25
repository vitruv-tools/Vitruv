package tools.vitruv.dsls.commonalities.ui.tests.identified

import pcm_mockup.Repository
import tools.vitruv.dsls.commonalities.testutils.CommonalitiesExecutionTest
import uml_mockup.UPackage

import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.*
import tools.vitruv.dsls.commonalities.testutils.ExecutionTestCompiler
import org.junit.jupiter.api.Test
import allElementTypes.Root
import allElementTypes2.Root2
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.newRoot
import static tools.vitruv.testutils.metamodels.AllElementTypes2Creators.newRoot2
import static tools.vitruv.testutils.metamodels.AllElementTypes2Creators.newNonRoot2
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.newNonRoot
import static extension tools.vitruv.testutils.domains.DomainModelCreators.*
import static tools.vitruv.testutils.metamodels.PcmMockupCreators.newPcmRepository
import static tools.vitruv.testutils.metamodels.PcmMockupCreators.newPcmComponent
import static tools.vitruv.testutils.metamodels.UmlMockupCreators.newUmlPackage
import static tools.vitruv.testutils.metamodels.UmlMockupCreators.newUmlClass

class IdentifiedExecutionTest extends CommonalitiesExecutionTest {
	override createCompiler(ExecutionTestCompiler.Factory factory) {
		factory.createCompiler [
			commonalities = #['Identified.commonality', 'Sub.commonality']
			domainDependencies = #[
				'tools.vitruv.testutils.domains',
				'tools.vitruv.testutils.metamodels'
			]
		]
	}

	@Test
	def void rootInsert() {
		resourceAt('testid'.allElementTypes2).recordAndPropagate [
			contents += newRoot2 => [id2 = 'testid']
		]

		assertThat(resourceAt('testid'.allElementTypes2), contains(newRoot2 => [id2 = 'testid']))
		assertThat(resourceAt('testid'.allElementTypes), contains(newRoot => [id = 'testid']))
		assertThat(resourceAt('testid'.pcm_mockup), contains(newPcmRepository => [id = 'testid']))
		assertThat(resourceAt('testid'.uml_mockup), contains(newUmlPackage => [id = 'testid']))
	}

	@Test
	def void multiRootInsert() {
		#['first', 'second', 'third'].forEach [ name |
			resourceAt(name.allElementTypes2).recordAndPropagate [
				contents += newRoot2 => [id2 = name]
			]
		]

		assertThat(resourceAt('first'.allElementTypes2), contains(newRoot2 => [id2 = 'first']))
		assertThat(resourceAt('first'.allElementTypes), contains(newRoot => [id = 'first']))
		assertThat(resourceAt('first'.pcm_mockup), contains(newPcmRepository => [id = 'first']))
		assertThat(resourceAt('first'.uml_mockup), contains(newUmlPackage => [id = 'first']))

		assertThat(resourceAt('second'.allElementTypes2), contains(newRoot2 => [id2 = 'second']))
		assertThat(resourceAt('second'.allElementTypes), contains(newRoot => [id = 'second']))
		assertThat(resourceAt('second'.pcm_mockup), contains(newPcmRepository => [id = 'second']))
		assertThat(resourceAt('second'.uml_mockup), contains(newUmlPackage => [id = 'second']))

		assertThat(resourceAt('third'.allElementTypes2), contains(newRoot2 => [id2 = 'third']))
		assertThat(resourceAt('third'.allElementTypes), contains(newRoot => [id = 'third']))
		assertThat(resourceAt('third'.pcm_mockup), contains(newPcmRepository => [id = 'third']))
		assertThat(resourceAt('third'.uml_mockup), contains(newUmlPackage => [id = 'third']))
	}

	@Test
	def void rootDelete() {
		#['first', 'second', 'third'].forEach [ name |
			resourceAt(name.allElementTypes2).recordAndPropagate [
				contents += newRoot2 => [id2 = name]
			]
		]

		resourceAt('second'.allElementTypes).recordAndPropagate [
			contents.clear()
		]
		assertThat(resourceAt('second'.allElementTypes), doesNotExist)
	// TODO Extend assertions
	}

	@Test
	def void setIdAttribute() {
		resourceAt('startid'.allElementTypes2).recordAndPropagate[contents += newRoot2 => [id2 = 'startid']]

		Root2.from('startid'.allElementTypes2).recordAndPropagate[id2 = '1st id']
		assertThat(resourceAt('startid'.allElementTypes2), contains(newRoot2 => [id2 = '1st id']))
		assertThat(resourceAt('startid'.allElementTypes), contains(newRoot => [id = '1st id']))
		assertThat(resourceAt('startid'.pcm_mockup), contains(newPcmRepository => [id = '1st id']))
		assertThat(resourceAt('startid'.uml_mockup), contains(newUmlPackage => [id = '1st id']))

		Root.from('startid'.allElementTypes).recordAndPropagate[id = '2nd id']
		assertThat(resourceAt('startid'.allElementTypes2), contains(newRoot2 => [id2 = '2nd id']))
		assertThat(resourceAt('startid'.allElementTypes), contains(newRoot => [id = '2nd id']))
		assertThat(resourceAt('startid'.pcm_mockup), contains(newPcmRepository => [id = '2nd id']))
		assertThat(resourceAt('startid'.uml_mockup), contains(newUmlPackage => [id = '2nd id']))

		Repository.from('startid'.pcm_mockup).recordAndPropagate[id = '3th id']
		assertThat(resourceAt('startid'.allElementTypes2), contains(newRoot2 => [id2 = '3th id']))
		assertThat(resourceAt('startid'.allElementTypes), contains(newRoot => [id = '3th id']))
		assertThat(resourceAt('startid'.pcm_mockup), contains(newPcmRepository => [id = '3th id']))
		assertThat(resourceAt('startid'.uml_mockup), contains(newUmlPackage => [id = '3th id']))

		UPackage.from('startid'.uml_mockup).recordAndPropagate[id = '4th id']
		assertThat(resourceAt('startid'.allElementTypes2), contains(newRoot2 => [id2 = '4th id']))
		assertThat(resourceAt('startid'.allElementTypes), contains(newRoot => [id = '4th id']))
		assertThat(resourceAt('startid'.pcm_mockup), contains(newPcmRepository => [id = '4th id']))
		assertThat(resourceAt('startid'.uml_mockup), contains(newUmlPackage => [id = '4th id']))
	}

	@Test
	def void setSimpleAttribute() {
		resourceAt('test'.allElementTypes2).recordAndPropagate [
			contents += newRoot2 => [
				singleValuedEAttribute2 = 0
				id2 = 'test'
			]

		]

		Root2.from('test'.allElementTypes2).recordAndPropagate[singleValuedEAttribute2 = 1]
		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
			singleValuedEAttribute2 = 1
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(newRoot => [
			singleValuedEAttribute = 1
			id = 'test'
		]))

		Root.from('test'.allElementTypes).recordAndPropagate[singleValuedEAttribute = 2]
		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
			singleValuedEAttribute2 = 2
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(newRoot => [
			singleValuedEAttribute = 2
			id = 'test'
		]))

		Root2.from('test'.allElementTypes2).recordAndPropagate[singleValuedEAttribute2 = 3]
		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
			singleValuedEAttribute2 = 3
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(newRoot => [
			singleValuedEAttribute = 3
			id = 'test'
		]))

		Root.from('test'.allElementTypes).recordAndPropagate[singleValuedEAttribute = 4]
		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
			singleValuedEAttribute2 = 4
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(newRoot => [
			singleValuedEAttribute = 4
			id = 'test'
		]))
	}

	@Test
	def void setMultiValue() {
		resourceAt('test'.allElementTypes2).recordAndPropagate [
			contents += newRoot2 => [
				multiValuedEAttribute2 += #[1, 2, 3]
				id2 = 'test'
			]
		]
		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
			multiValuedEAttribute2 += #[1, 2, 3]
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(newRoot => [
			multiValuedEAttribute += #[1, 2, 3]
			id = 'test'
		]))

		Root2.from('test'.allElementTypes2).recordAndPropagate[multiValuedEAttribute2 += 4]
		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
			multiValuedEAttribute2 += #[1, 2, 3, 4]
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(newRoot => [
			multiValuedEAttribute += #[1, 2, 3, 4]
			id = 'test'
		]))

		Root.from('test'.allElementTypes).recordAndPropagate[multiValuedEAttribute += 5]
		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
			multiValuedEAttribute2 += #[1, 2, 3, 4, 5]
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(newRoot => [
			multiValuedEAttribute += #[1, 2, 3, 4, 5]
			id = 'test'
		]))

	/*		Vitruvius doesn’t correctly translate the changes?
	 * 		saveAndSynchronizeChanges(newRoot2.from('test'.allElementTypes2) => [multiValuedEAttribute2 -= #[1, 3, 5]])
	 * 		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
	 * 			multiValuedEAttribute2 += #[2, 4]
	 * 			id2 = 'test'
	 * 		]))
	 * 		assertThat(resourceAt('test'.allElementTypes), contains(root => [
	 * 			multiValuedEAttribute += #[2, 4]
	 * 			id = 'test'
	 * 		]))

	 * 		saveAndSynchronizeChanges(Root.from('test'.allElementTypes) => [multiValuedEAttribute -= #[2]])
	 * 		assertThat(resourceAt('test'.allElementTypes2), contains(newRoot2 => [
	 * 			multiValuedEAttribute2 += 4
	 * 			id2 = 'test'
	 * 		]))
	 * 		assertThat(resourceAt('test'.allElementTypes), contains(root => [
	 * 			multiValuedEAttribute += 4
	 * 			id = 'test'
	 ]))*/
	}

	@Test
	def void rootWithReferenceInsert() {
		resourceAt('testid'.allElementTypes2).recordAndPropagate [
			contents += newRoot2 => [
				id2 = 'testid'
				multiValuedContainmentEReference2 += newNonRoot2 => [id2 = 'testname']
			]
		]

		assertThat(resourceAt('testid'.allElementTypes2), contains(newRoot2 => [
			id2 = 'testid'
			multiValuedContainmentEReference2 += newNonRoot2 => [id2 = 'testname']
		]))
		assertThat(resourceAt('testid'.allElementTypes), contains(newRoot => [
			id = 'testid'
			multiValuedContainmentEReference += newNonRoot => [id = 'testname']
		]))
		assertThat(resourceAt('testid'.pcm_mockup), contains(newPcmRepository => [
			id = 'testid'
			components += newPcmComponent => [name = 'testname']
		], ignoringFeatures('id')))
		assertThat(resourceAt('testid'.uml_mockup), contains(newUmlPackage => [
			id = 'testid'
			classes += newUmlClass => [name = 'testname']
		], ignoringFeatures('id')))
	}

	@Test
	def void multiReferenceInsert() {
		resourceAt('testid'.allElementTypes2).recordAndPropagate [
			contents += newRoot2 => [
				id2 = 'testid'
				multiValuedContainmentEReference2 += #[
					newNonRoot2 => [id2 = 'first'],
					newNonRoot2 => [id2 = 'second']
				]
			]
		]
		assertThat(resourceAt('testid'.allElementTypes2), contains(newRoot2 => [
			id2 = 'testid'
			multiValuedContainmentEReference2 += #[
				newNonRoot2 => [id2 = 'first'],
				newNonRoot2 => [id2 = 'second']
			]
		]))
		assertThat(resourceAt('testid'.allElementTypes), contains(newRoot => [
			id = 'testid'
			multiValuedContainmentEReference += #[
				newNonRoot => [id = 'first'],
				newNonRoot => [id = 'second']
			]
		]))
		assertThat(resourceAt('testid'.pcm_mockup), contains(newPcmRepository => [
			id = 'testid'
			components += #[
				newPcmComponent => [name = 'first'],
				newPcmComponent => [name = 'second']
			]
		], ignoringFeatures('id')))
		assertThat(resourceAt('testid'.uml_mockup), contains(newUmlPackage => [
			id = 'testid'
			classes += #[
				newUmlClass => [name = 'first'],
				newUmlClass => [name = 'second']
			]
		], ignoringFeatures('id')))

		Repository.from('testid'.pcm_mockup).recordAndPropagate [
			components += newPcmComponent => [name = 'third']
		]
		assertThat(resourceAt('testid'.allElementTypes2), contains(newRoot2 => [
			id2 = 'testid'
			multiValuedContainmentEReference2 += #[
				newNonRoot2 => [id2 = 'first'],
				newNonRoot2 => [id2 = 'second'],
				newNonRoot2 => [id2 = 'third']
			]
		]))
		assertThat(resourceAt('testid'.allElementTypes), contains(newRoot => [
			id = 'testid'
			multiValuedContainmentEReference += #[
				newNonRoot => [id = 'first'],
				newNonRoot => [id = 'second'],
				newNonRoot => [id = 'third']
			]
		]))
		assertThat(resourceAt('testid'.pcm_mockup), contains(newPcmRepository => [
			id = 'testid'
			components += #[
				newPcmComponent => [name = 'first'],
				newPcmComponent => [name = 'second'],
				newPcmComponent => [name = 'third']
			]
		], ignoringFeatures('id')))
		assertThat(resourceAt('testid'.uml_mockup), contains(newUmlPackage => [
			id = 'testid'
			classes += #[
				newUmlClass => [name = 'first'],
				newUmlClass => [name = 'second'],
				newUmlClass => [name = 'third']
			]
		], ignoringFeatures('id')))
	}
}

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
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet
import static tools.vitruv.testutils.metamodels.AllElementTypes2Creators.aet2
import static extension tools.vitruv.testutils.domains.DomainModelCreators.*
import static tools.vitruv.testutils.metamodels.PcmMockupCreators.pcm
import static tools.vitruv.testutils.metamodels.UmlMockupCreators.uml

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
		resourceAt('testid'.allElementTypes2).propagate [
			contents += aet2.Root2 => [id2 = 'testid']
		]

		assertThat(resourceAt('testid'.allElementTypes2), contains(aet2.Root2 => [id2 = 'testid']))
		assertThat(resourceAt('testid'.allElementTypes), contains(aet.Root => [id = 'testid']))
		assertThat(resourceAt('testid'.pcm_mockup), contains(pcm.Repository => [id = 'testid']))
		assertThat(resourceAt('testid'.uml_mockup), contains(uml.Package => [id = 'testid']))
	}

	@Test
	def void multiRootInsert() {
		#['first', 'second', 'third'].forEach [ name |
			resourceAt(name.allElementTypes2).propagate [
				contents += aet2.Root2 => [id2 = name]
			]
		]

		assertThat(resourceAt('first'.allElementTypes2), contains(aet2.Root2 => [id2 = 'first']))
		assertThat(resourceAt('first'.allElementTypes), contains(aet.Root => [id = 'first']))
		assertThat(resourceAt('first'.pcm_mockup), contains(pcm.Repository => [id = 'first']))
		assertThat(resourceAt('first'.uml_mockup), contains(uml.Package => [id = 'first']))

		assertThat(resourceAt('second'.allElementTypes2), contains(aet2.Root2 => [id2 = 'second']))
		assertThat(resourceAt('second'.allElementTypes), contains(aet.Root => [id = 'second']))
		assertThat(resourceAt('second'.pcm_mockup), contains(pcm.Repository => [id = 'second']))
		assertThat(resourceAt('second'.uml_mockup), contains(uml.Package => [id = 'second']))

		assertThat(resourceAt('third'.allElementTypes2), contains(aet2.Root2 => [id2 = 'third']))
		assertThat(resourceAt('third'.allElementTypes), contains(aet.Root => [id = 'third']))
		assertThat(resourceAt('third'.pcm_mockup), contains(pcm.Repository => [id = 'third']))
		assertThat(resourceAt('third'.uml_mockup), contains(uml.Package => [id = 'third']))
	}

	@Test
	def void rootDelete() {
		#['first', 'second', 'third'].forEach [ name |
			resourceAt(name.allElementTypes2).propagate [
				contents += aet2.Root2 => [id2 = name]
			]
		]

		resourceAt('second'.allElementTypes).propagate [
			contents.clear()
		]
		assertThat(resourceAt('second'.allElementTypes), doesNotExist)
	// TODO Extend assertions
	}

	@Test
	def void setIdAttribute() {
		resourceAt('startid'.allElementTypes2).propagate[contents += aet2.Root2 => [id2 = 'startid']]

		Root2.from('startid'.allElementTypes2).propagate[id2 = '1st id']
		assertThat(resourceAt('startid'.allElementTypes2), contains(aet2.Root2 => [id2 = '1st id']))
		assertThat(resourceAt('startid'.allElementTypes), contains(aet.Root => [id = '1st id']))
		assertThat(resourceAt('startid'.pcm_mockup), contains(pcm.Repository => [id = '1st id']))
		assertThat(resourceAt('startid'.uml_mockup), contains(uml.Package => [id = '1st id']))

		Root.from('startid'.allElementTypes).propagate[id = '2nd id']
		assertThat(resourceAt('startid'.allElementTypes2), contains(aet2.Root2 => [id2 = '2nd id']))
		assertThat(resourceAt('startid'.allElementTypes), contains(aet.Root => [id = '2nd id']))
		assertThat(resourceAt('startid'.pcm_mockup), contains(pcm.Repository => [id = '2nd id']))
		assertThat(resourceAt('startid'.uml_mockup), contains(uml.Package => [id = '2nd id']))

		Repository.from('startid'.pcm_mockup).propagate[id = '3th id']
		assertThat(resourceAt('startid'.allElementTypes2), contains(aet2.Root2 => [id2 = '3th id']))
		assertThat(resourceAt('startid'.allElementTypes), contains(aet.Root => [id = '3th id']))
		assertThat(resourceAt('startid'.pcm_mockup), contains(pcm.Repository => [id = '3th id']))
		assertThat(resourceAt('startid'.uml_mockup), contains(uml.Package => [id = '3th id']))

		UPackage.from('startid'.uml_mockup).propagate[id = '4th id']
		assertThat(resourceAt('startid'.allElementTypes2), contains(aet2.Root2 => [id2 = '4th id']))
		assertThat(resourceAt('startid'.allElementTypes), contains(aet.Root => [id = '4th id']))
		assertThat(resourceAt('startid'.pcm_mockup), contains(pcm.Repository => [id = '4th id']))
		assertThat(resourceAt('startid'.uml_mockup), contains(uml.Package => [id = '4th id']))
	}

	@Test
	def void setSimpleAttribute() {
		resourceAt('test'.allElementTypes2).propagate [
			contents += aet2.Root2 => [
				singleValuedEAttribute2 = 0
				id2 = 'test'
			]

		]

		Root2.from('test'.allElementTypes2).propagate[singleValuedEAttribute2 = 1]
		assertThat(resourceAt('test'.allElementTypes2), contains(aet2.Root2 => [
			singleValuedEAttribute2 = 1
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(aet.Root => [
			singleValuedEAttribute = 1
			id = 'test'
		]))

		Root.from('test'.allElementTypes).propagate[singleValuedEAttribute = 2]
		assertThat(resourceAt('test'.allElementTypes2), contains(aet2.Root2 => [
			singleValuedEAttribute2 = 2
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(aet.Root => [
			singleValuedEAttribute = 2
			id = 'test'
		]))

		Root2.from('test'.allElementTypes2).propagate[singleValuedEAttribute2 = 3]
		assertThat(resourceAt('test'.allElementTypes2), contains(aet2.Root2 => [
			singleValuedEAttribute2 = 3
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(aet.Root => [
			singleValuedEAttribute = 3
			id = 'test'
		]))

		Root.from('test'.allElementTypes).propagate[singleValuedEAttribute = 4]
		assertThat(resourceAt('test'.allElementTypes2), contains(aet2.Root2 => [
			singleValuedEAttribute2 = 4
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(aet.Root => [
			singleValuedEAttribute = 4
			id = 'test'
		]))
	}

	@Test
	def void setMultiValue() {
		resourceAt('test'.allElementTypes2).propagate [
			contents += aet2.Root2 => [
				multiValuedEAttribute2 += #[1, 2, 3]
				id2 = 'test'
			]
		]
		assertThat(resourceAt('test'.allElementTypes2), contains(aet2.Root2 => [
			multiValuedEAttribute2 += #[1, 2, 3]
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(aet.Root => [
			multiValuedEAttribute += #[1, 2, 3]
			id = 'test'
		]))

		Root2.from('test'.allElementTypes2).propagate[multiValuedEAttribute2 += 4]
		assertThat(resourceAt('test'.allElementTypes2), contains(aet2.Root2 => [
			multiValuedEAttribute2 += #[1, 2, 3, 4]
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(aet.Root => [
			multiValuedEAttribute += #[1, 2, 3, 4]
			id = 'test'
		]))

		Root.from('test'.allElementTypes).propagate[multiValuedEAttribute += 5]
		assertThat(resourceAt('test'.allElementTypes2), contains(aet2.Root2 => [
			multiValuedEAttribute2 += #[1, 2, 3, 4, 5]
			id2 = 'test'
		]))
		assertThat(resourceAt('test'.allElementTypes), contains(aet.Root => [
			multiValuedEAttribute += #[1, 2, 3, 4, 5]
			id = 'test'
		]))

	/*		Vitruvius doesn’t correctly translate the changes?
	 * 		saveAndSynchronizeChanges(root2.from('test'.allElementTypes2) => [multiValuedEAttribute2 -= #[1, 3, 5]])
	 * 		assertThat(resourceAt('test'.allElementTypes2), contains(aet2.Root2 => [
	 * 			multiValuedEAttribute2 += #[2, 4]
	 * 			id2 = 'test'
	 * 		]))
	 * 		assertThat(resourceAt('test'.allElementTypes), contains(aet.Root => [
	 * 			multiValuedEAttribute += #[2, 4]
	 * 			id = 'test'
	 * 		]))

	 * 		saveAndSynchronizeChanges(Root.from('test'.allElementTypes) => [multiValuedEAttribute -= #[2]])
	 * 		assertThat(resourceAt('test'.allElementTypes2), contains(aet2.Root2 => [
	 * 			multiValuedEAttribute2 += 4
	 * 			id2 = 'test'
	 * 		]))
	 * 		assertThat(resourceAt('test'.allElementTypes), contains(aet.Root => [
	 * 			multiValuedEAttribute += 4
	 * 			id = 'test'
	 ]))*/
	}

	@Test
	def void rootWithReferenceInsert() {
		resourceAt('testid'.allElementTypes2).propagate [
			contents += aet2.Root2 => [
				id2 = 'testid'
				multiValuedContainmentEReference2 += aet2.NonRoot2 => [id2 = 'testname']
			]
		]

		assertThat(resourceAt('testid'.allElementTypes2), contains(aet2.Root2 => [
			id2 = 'testid'
			multiValuedContainmentEReference2 += aet2.NonRoot2 => [id2 = 'testname']
		]))
		assertThat(resourceAt('testid'.allElementTypes), contains(aet.Root => [
			id = 'testid'
			multiValuedContainmentEReference += aet.NonRoot => [id = 'testname']
		]))
		assertThat(resourceAt('testid'.pcm_mockup), contains(pcm.Repository => [
			id = 'testid'
			components += pcm.Component => [name = 'testname']
		], ignoringFeatures('id')))
		assertThat(resourceAt('testid'.uml_mockup), contains(uml.Package => [
			id = 'testid'
			classes += uml.Class => [name = 'testname']
		], ignoringFeatures('id')))
	}

	@Test
	def void multiReferenceInsert() {
		resourceAt('testid'.allElementTypes2).propagate [
			contents += aet2.Root2 => [
				id2 = 'testid'
				multiValuedContainmentEReference2 += #[
					aet2.NonRoot2 => [id2 = 'first'],
					aet2.NonRoot2 => [id2 = 'second']
				]
			]
		]
		assertThat(resourceAt('testid'.allElementTypes2), contains(aet2.Root2 => [
			id2 = 'testid'
			multiValuedContainmentEReference2 += #[
				aet2.NonRoot2 => [id2 = 'first'],
				aet2.NonRoot2 => [id2 = 'second']
			]
		]))
		assertThat(resourceAt('testid'.allElementTypes), contains(aet.Root => [
			id = 'testid'
			multiValuedContainmentEReference += #[
				aet.NonRoot => [id = 'first'],
				aet.NonRoot => [id = 'second']
			]
		]))
		assertThat(resourceAt('testid'.pcm_mockup), contains(pcm.Repository => [
			id = 'testid'
			components += #[
				pcm.Component => [name = 'first'],
				pcm.Component => [name = 'second']
			]
		], ignoringFeatures('id')))
		assertThat(resourceAt('testid'.uml_mockup), contains(uml.Package => [
			id = 'testid'
			classes += #[
				uml.Class => [name = 'first'],
				uml.Class => [name = 'second']
			]
		], ignoringFeatures('id')))

		Repository.from('testid'.pcm_mockup).propagate [
			components += pcm.Component => [name = 'third']
		]
		assertThat(resourceAt('testid'.allElementTypes2), contains(aet2.Root2 => [
			id2 = 'testid'
			multiValuedContainmentEReference2 += #[
				aet2.NonRoot2 => [id2 = 'first'],
				aet2.NonRoot2 => [id2 = 'second'],
				aet2.NonRoot2 => [id2 = 'third']
			]
		]))
		assertThat(resourceAt('testid'.allElementTypes), contains(aet.Root => [
			id = 'testid'
			multiValuedContainmentEReference += #[
				aet.NonRoot => [id = 'first'],
				aet.NonRoot => [id = 'second'],
				aet.NonRoot => [id = 'third']
			]
		]))
		assertThat(resourceAt('testid'.pcm_mockup), contains(pcm.Repository => [
			id = 'testid'
			components += #[
				pcm.Component => [name = 'first'],
				pcm.Component => [name = 'second'],
				pcm.Component => [name = 'third']
			]
		], ignoringFeatures('id')))
		assertThat(resourceAt('testid'.uml_mockup), contains(uml.Package => [
			id = 'testid'
			classes += #[
				uml.Class => [name = 'first'],
				uml.Class => [name = 'second'],
				uml.Class => [name = 'third']
			]
		], ignoringFeatures('id')))
	}
}

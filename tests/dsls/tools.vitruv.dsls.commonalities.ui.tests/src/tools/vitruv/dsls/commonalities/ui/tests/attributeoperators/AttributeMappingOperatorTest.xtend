package tools.vitruv.dsls.commonalities.ui.tests.attributeoperators

import tools.vitruv.dsls.commonalities.testutils.CommonalitiesExecutionTest

import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.*
import tools.vitruv.dsls.commonalities.testutils.ExecutionTestCompiler
import org.junit.jupiter.api.Test
import static extension tools.vitruv.testutils.domains.DomainModelCreators.allElementTypes
import static extension tools.vitruv.testutils.domains.DomainModelCreators.allElementTypes2
import static extension tools.vitruv.testutils.metamodels.AllElementTypes2Creators.*
import static extension tools.vitruv.testutils.metamodels.AllElementTypesCreators.*

class AttributeMappingOperatorTest extends CommonalitiesExecutionTest {
	override createCompiler(ExecutionTestCompiler.Factory factory) {
		factory.createCompiler [
			commonalities = #['Identified.commonality']
			domainDependencies = #[
				'tools.vitruv.testutils.domains',
				'tools.vitruv.testutils.metamodels',
				'tools.vitruv.dsls.commonalities.testutils'
			]
		]
	}

	// Value is multiplied by 1000
	@Test
	def void singleToSingleValuedAttribute() {
		resourceAt('testid'.allElementTypes).propagate [
			contents += aet.Root => [
				id = 'testid'
				singleValuedPrimitiveTypeEAttribute = 123
			]
		]

		assertThat(resourceAt('testid'.allElementTypes), contains(aet.Root => [
			id = 'testid'
			singleValuedPrimitiveTypeEAttribute = 123
		], ignoringUnsetFeatures))
		assertThat(resourceAt('testid'.allElementTypes2), contains(aet2.Root2 => [
			id2 = 'testid'
			singleValuedPrimitiveTypeEAttribute2 = 123000
		], ignoringUnsetFeatures))
	}

	// Value is divided by 1000 and rounded towards zero
	@Test
	def void singleToSingleValuedAttributeReverse() {
		resourceAt('testid'.allElementTypes2).propagate [
			contents += aet2.Root2 => [
				id2 = 'testid'
				singleValuedPrimitiveTypeEAttribute2 = 123500
			]
		]

		assertThat(resourceAt('testid'.allElementTypes2), contains(aet2.Root2 => [
			id2 = 'testid'
			singleValuedPrimitiveTypeEAttribute2 = 123500
		], ignoringUnsetFeatures))
		assertThat(resourceAt('testid'.allElementTypes), contains(aet.Root => [
			id = 'testid'
			singleValuedPrimitiveTypeEAttribute = 123
		], ignoringUnsetFeatures))
	}

	@Test
	def void singleToMultiValuedAttribute() {
		resourceAt('testid'.allElementTypes).propagate [
			contents += aet.Root => [
				id = 'testid'
				singleValuedEAttribute = 324
			]
		]

		assertThat(resourceAt('testid'.allElementTypes), contains(aet.Root => [
			id = 'testid'
			singleValuedEAttribute = 324
		], ignoringUnsetFeatures))
		assertThat(resourceAt('testid'.allElementTypes2), contains(aet2.Root2 => [
			id2 = 'testid'
			multiValuedEAttribute2 += #[3, 2, 4]
		], ignoringUnsetFeatures))
	}

	@Test
	def void multiToSingleValuedAttribute() {
		resourceAt('testid'.allElementTypes2).propagate [
			contents += aet2.Root2 => [
				id2 = 'testid'
				multiValuedEAttribute2 += #[3, 2, 4]
			]
		]

		assertThat(resourceAt('testid'.allElementTypes2), contains(aet2.Root2 => [
			id2 = 'testid'
			multiValuedEAttribute2 += #[3, 2, 4]
		], ignoringUnsetFeatures))
		assertThat(resourceAt('testid'.allElementTypes), contains(aet.Root => [
			id = 'testid'
			singleValuedEAttribute = 324
		], ignoringUnsetFeatures))
	}
}

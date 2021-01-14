package tools.vitruv.dsls.commonalities.ui.execution

import tools.vitruv.dsls.commonalities.testutils.CommonalitiesExecutionTest

import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.*
import tools.vitruv.dsls.commonalities.testutils.ExecutionTestCompiler
import org.junit.jupiter.api.Test
import static extension tools.vitruv.testutils.domains.DomainModelCreators.allElementTypes
import static extension tools.vitruv.testutils.domains.DomainModelCreators.allElementTypes2
import static extension tools.vitruv.testutils.metamodels.AllElementTypes2Creators.*
import static extension tools.vitruv.testutils.metamodels.AllElementTypesCreators.*
import org.junit.jupiter.api.DisplayName

@DisplayName('executing a commonality with attribute mapping operators')
class AttributeMappingOperatorExecutionTest extends CommonalitiesExecutionTest {
	override createCompiler(ExecutionTestCompiler.Factory factory) {
		factory.createCompiler [
			commonalities = #['Operators.commonality']
			domainDependencies = #[
				'tools.vitruv.testutils.domains',
				'tools.vitruv.dsls.commonalities.testutils'
			]
		]
	}

	@Test
	@DisplayName('maps a simple attribute')
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
		]))
		assertThat(resourceAt('testid'.allElementTypes2), contains(aet2.Root2 => [
			id2 = 'testid'
			singleValuedPrimitiveTypeEAttribute2 = 123000
		], ignoringFeatures('multiValuedEAttribute2')))
	}

	// Value is divided by 1000 and rounded towards zero
	@Test
	@DisplayName('maps a simple attribute (reverse)')
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
		]))
		assertThat(resourceAt('testid'.allElementTypes), contains(aet.Root => [
			id = 'testid'
			singleValuedPrimitiveTypeEAttribute = 123
		]))
	}

	@Test
	@DisplayName('maps a single-valued attribute to a multi-valued one')
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
		]))
		assertThat(resourceAt('testid'.allElementTypes2), contains(aet2.Root2 => [
			id2 = 'testid'
			multiValuedEAttribute2 += #[3, 2, 4]
		]))
	}

	@Test
	@DisplayName('maps a multi-valued attribute to single-valued one')
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
		]))
		assertThat(resourceAt('testid'.allElementTypes), contains(aet.Root => [
			id = 'testid'
			singleValuedEAttribute = 324
		]))
	}
}

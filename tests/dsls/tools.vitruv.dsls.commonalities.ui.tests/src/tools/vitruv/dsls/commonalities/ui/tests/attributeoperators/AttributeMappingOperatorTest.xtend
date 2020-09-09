package tools.vitruv.dsls.commonalities.ui.tests.attributeoperators

import allElementTypes.AllElementTypesFactory
import allElementTypes2.AllElementTypes2Factory
import com.google.inject.Inject
import org.junit.Test
import tools.vitruv.dsls.commonalities.testutils.CommonalitiesExecutionTest

import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.*

class AttributeMappingOperatorTest extends CommonalitiesExecutionTest {

	@Inject AttributeMappingOperatorTestCompiler compiler

	override protected cleanup() {
	}

	override protected setup() {
	}

	override protected createChangePropagationSpecifications() {
		compiler.changePropagationDefinitions
	}

	// Value is multiplied by 1000
	@Test
	def void singleToSingleValuedAttribute() {
		createAndSynchronizeModel('testid.allElementTypes', root => [
			id = 'testid'
			singleValuedPrimitiveTypeEAttribute = 123
		])

		assertThat(resourceAt('testid.allElementTypes'), contains(root => [
			id = 'testid'
			singleValuedPrimitiveTypeEAttribute = 123
		], ignoringUnsetFeatures))
		assertThat(resourceAt('testid.allElementTypes2'), contains(root2 => [
			id2 = 'testid'
			singleValuedPrimitiveTypeEAttribute2 = 123000
		], ignoringUnsetFeatures))
	}

	// Value is divided by 1000 and rounded towards zero
	@Test
	def void singleToSingleValuedAttributeReverse() {
		createAndSynchronizeModel('testid.allElementTypes2', root2 => [
			id2 = 'testid'
			singleValuedPrimitiveTypeEAttribute2 = 123500
		])

		assertThat(resourceAt('testid.allElementTypes2'), contains(root2 => [
			id2 = 'testid'
			singleValuedPrimitiveTypeEAttribute2 = 123500
		], ignoringUnsetFeatures))
		assertThat(resourceAt('testid.allElementTypes'), contains(root => [
			id = 'testid'
			singleValuedPrimitiveTypeEAttribute = 123
		], ignoringUnsetFeatures))
	}

	@Test
	def void singleToMultiValuedAttribute() {
		createAndSynchronizeModel('testid.allElementTypes', root => [
			id = 'testid'
			singleValuedEAttribute = 324
		])

		assertThat(resourceAt('testid.allElementTypes'), contains(root => [
			id = 'testid'
			singleValuedEAttribute = 324
		], ignoringUnsetFeatures))
		assertThat(resourceAt('testid.allElementTypes2'), contains(root2 => [
			id2 = 'testid'
			multiValuedEAttribute2 += #[3, 2, 4]
		], ignoringUnsetFeatures))
	}

	@Test
	def void multiToSingleValuedAttribute() {
		createAndSynchronizeModel('testid.allElementTypes2', root2 => [
			id2 = 'testid'
			multiValuedEAttribute2 += #[3, 2, 4]
		])

		assertThat(resourceAt('testid.allElementTypes2'), contains(root2 => [
			id2 = 'testid'
			multiValuedEAttribute2 += #[3, 2, 4]
		], ignoringUnsetFeatures))
		assertThat(resourceAt('testid.allElementTypes'), contains(root => [
			id = 'testid'
			singleValuedEAttribute = 324
		], ignoringUnsetFeatures))
	}

	private static def root2() {
		AllElementTypes2Factory.eINSTANCE.createRoot2
	}

	private static def root() {
		AllElementTypesFactory.eINSTANCE.createRoot
	}
}

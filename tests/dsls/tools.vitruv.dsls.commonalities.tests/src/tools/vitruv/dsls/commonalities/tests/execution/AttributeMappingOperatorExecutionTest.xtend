package tools.vitruv.dsls.commonalities.tests.execution

import static org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import static extension tools.vitruv.testutils.domains.DomainModelCreators.allElementTypes
import static extension tools.vitruv.testutils.domains.DomainModelCreators.allElementTypes2
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.InjectWith
import tools.vitruv.dsls.commonalities.tests.CommonalitiesLanguageInjectorProvider
import org.junit.jupiter.api.TestInstance
import javax.inject.Inject
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import org.junit.jupiter.api.BeforeAll
import tools.vitruv.testutils.TestProject
import java.nio.file.Path
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet
import static tools.vitruv.testutils.metamodels.AllElementTypes2Creators.aet2
import static tools.vitruv.testutils.matchers.ModelMatchers.contains
import static tools.vitruv.testutils.matchers.ModelMatchers.ignoringFeatures
import tools.vitruv.testutils.VitruvApplicationTest
import tools.vitruv.dsls.commonalities.tests.util.TestCommonalitiesGenerator

@ExtendWith(InjectionExtension)
@InjectWith(CommonalitiesLanguageInjectorProvider)
@TestInstance(PER_CLASS)
@DisplayName('executing a commonality with attribute mapping operators')
class AttributeMappingOperatorExecutionTest extends VitruvApplicationTest {
	@Inject TestCommonalitiesGenerator generator
	
	@Accessors(PROTECTED_GETTER)
	var Iterable<? extends ChangePropagationSpecification> changePropagationSpecifications
	
	@BeforeAll
	def void generate(@TestProject(variant = "commonalities") Path testProject) {
		changePropagationSpecifications = generator.generate(testProject,
			'WithAttributeMappingOperators.commonality' -> '''
				import tools.vitruv.dsls.commonalities.tests.operators.multiply
				import tools.vitruv.dsls.commonalities.tests.operators.digits
				
				concept operators
				
				commonality WithAttributeMappingOperators {
				
					with AllElementTypes:(Root in Resource)
					with AllElementTypes2:(Root2 in Resource)
				
					has id {
						= AllElementTypes:Root.id
						= AllElementTypes2:Root2.id2
						-> AllElementTypes:Resource.name
						-> AllElementTypes2:Resource.name
					}
				
					has number {
						// number <-> number mapping:
						= multiply(AllElementTypes:Root.singleValuedPrimitiveTypeEAttribute, 1000)
						= AllElementTypes2:Root2.singleValuedPrimitiveTypeEAttribute2
					}
				
					has numberList {
						// number <-> numberList mapping:
						= digits(AllElementTypes:Root.singleValuedEAttribute)
						= AllElementTypes2:Root2.multiValuedEAttribute2
					}
				}
			'''
		)
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

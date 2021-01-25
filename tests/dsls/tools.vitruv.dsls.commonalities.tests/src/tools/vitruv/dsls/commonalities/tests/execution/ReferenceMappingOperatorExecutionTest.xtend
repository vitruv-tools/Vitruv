package tools.vitruv.dsls.commonalities.tests.execution

import tools.vitruv.testutils.VitruvApplicationTest
import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.InjectWith
import tools.vitruv.dsls.commonalities.tests.CommonalitiesLanguageInjectorProvider
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.DisplayName
import tools.vitruv.dsls.commonalities.tests.util.TestCommonalitiesGenerator
import javax.inject.Inject
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.framework.change.processing.ChangePropagationSpecification
import org.junit.jupiter.api.BeforeAll
import tools.vitruv.testutils.TestProject
import java.nio.file.Path
import org.junit.jupiter.api.Test

@ExtendWith(InjectionExtension)
@InjectWith(CommonalitiesLanguageInjectorProvider)
@TestInstance(PER_CLASS)
@DisplayName('executing a commonality with attribute mapping operators')
class ReferenceMappingOperatorExecutionTest extends VitruvApplicationTest {
	@Inject TestCommonalitiesGenerator generator
	
	@Accessors(PROTECTED_GETTER)
	var Iterable<? extends ChangePropagationSpecification> changePropagationSpecifications
	
	@BeforeAll
	def void generate(@TestProject(variant = "commonalities") Path testProject) {
		changePropagationSpecifications = generator.generate(testProject,
			'WithReferenceMappingOperators.commonality' -> '''
				import tools.vitruv.dsls.commonalities.tests.operators.mock
				
				concept operators
				
				commonality WithReferenceMappingOperators {
					with AllElementTypes:(Root in Resource)
					with AllElementTypes2:(Root2 in Resource)
				
					has self referencing operators:WithReferenceMappingOperators {
						= AllElementTypes:Root.mock(ref Root.singleValuedEAttribute)
					}
				}
			'''
		)
	}

	@Test
	@DisplayName('generates')
	def void generates() {
		// TODO validate something if the ref keyword stays, else remove this test
	}
}
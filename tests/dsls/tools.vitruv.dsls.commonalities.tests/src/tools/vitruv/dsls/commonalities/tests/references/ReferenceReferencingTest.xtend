package tools.vitruv.dsls.commonalities.tests.references

import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import tools.vitruv.testutils.printing.ModelPrinterChange
import org.eclipse.xtext.testing.InjectWith
import tools.vitruv.dsls.commonalities.tests.CommonalitiesLanguageInjectorProvider
import tools.vitruv.testutils.printing.UseModelPrinter
import tools.vitruv.dsls.commonalities.tests.util.CommonalitiesLanguageElementsPrinter
import org.junit.jupiter.api.DisplayName
import javax.inject.Inject
import tools.vitruv.dsls.commonalities.tests.util.CommonalityParseHelper
import org.junit.jupiter.api.Test
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply
import static tools.vitruv.dsls.commonalities.tests.util.CommonalitiesLanguageCreators.commonalities
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.usingEqualsForReferencesTo

@ExtendWith(InjectionExtension, ModelPrinterChange)
@InjectWith(CommonalitiesLanguageInjectorProvider)
@UseModelPrinter(CommonalitiesLanguageElementsPrinter)
@DisplayName("referencing Commonalities in References")
class ReferenceReferencingTest {
	// BEWARE: EMF Compare will fail to match referenced Commonalities correctly (because it uses URI fragments,
	// which are, of course, equal for all Commonalities). Hence, the usingEqualsFor option is crucial!
	@Inject extension CommonalityParseHelper
	
	@Test
	@DisplayName("resolves a reference to the declaring Commonality")
	def void selfReference() {
		val commonality = parseAndValidate('''
			concept test
			
			commonality Test {
				with AllElementTypes:Root
				
				has selfref referencing test:Test {}
			}
		''').commonality
		assertThat(commonality.references.get(0), equalsDeeply(
			commonalities.CommonalityReference => [
				name = "selfref"
				referenceType = commonality
			], usingEqualsForReferencesTo(commonalities.Commonality.eClass)
		))
	}
	
	@Test
	@DisplayName("resolves a reference to another Commonality")
	def void foreignReference() {
		inSameResourceSet [
			val referenced = parseInSet('''
				concept Referenced
				
				commonality Target {}
			''')
			assertThat(parseAndValidateInSet('''
				concept test
				
				commonality Test {
					with AllElementTypes:Root
					
					has foreignref referencing Referenced:Target {}
				}
			''').commonality.references.get(0), equalsDeeply(
				commonalities.CommonalityReference => [
					name = "foreignref"
					referenceType = referenced.commonality
				], usingEqualsForReferencesTo(commonalities.Commonality.eClass)				
			))
		]
	}
}
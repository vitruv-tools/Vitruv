package tools.vitruv.dsls.commonalities.tests.references

import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.InjectWith
import tools.vitruv.dsls.commonalities.tests.CommonalitiesLanguageInjectorProvider
import org.junit.jupiter.api.DisplayName
import javax.inject.Inject
import tools.vitruv.dsls.commonalities.tests.util.CommonalityParseHelper
import org.junit.jupiter.api.Test
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply
import static tools.vitruv.dsls.commonalities.tests.util.CommonalitiesLanguageCreators.commonalities
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.usingEqualsFor
import static extension tools.vitruv.testutils.domains.DomainModelCreators.*
import tools.vitruv.testutils.printing.ModelPrinterChange
import tools.vitruv.testutils.printing.UseModelPrinter
import tools.vitruv.dsls.commonalities.tests.util.CommonalitiesLanguageElementsPrinter

@ExtendWith(InjectionExtension, ModelPrinterChange)
@InjectWith(CommonalitiesLanguageInjectorProvider)
@UseModelPrinter(CommonalitiesLanguageElementsPrinter)
@DisplayName("referencing domains in Participations")
class ParticipationReferencingTest {
	@Inject extension CommonalityParseHelper
	
	@Test
	@DisplayName("resolves a reference to a VitruvDomain")
	def void domainReference() {
		assertThat(parseAndValidate('''
			concept test
			
			commonality Test {
				with AllElementTypes:Root
			}
		''').commonality.participations.get(0), equalsDeeply(
			commonalities.Participation => [
				domainName = "AllElementTypes"
				parts += commonalities.ParticipationClass => [
					superMetaclass = commonalities.languageElements.EClassMetaclass
						.forEClass(aet.Root.eClass)
						.fromDomain(commonalities.languageElements.VitruviusDomain.forVitruvDomain(aet.domain))
				]
			], usingEqualsFor(commonalities.languageElements.EClassMetaclass.eClass)))
	}
	
	@Test
	@DisplayName("resolves a reference to another Commonality") 
	def void commoanlityReference() {
		inSameResourceSet [
			val referenced = parseInSet('''
				concept Referenced
				
				commonality Target {}
			''') 
			assertThat(parseAndValidateInSet('''
				concept test
				
				commonality Test {
					with Referenced:Target
				}
			''').commonality.participations.get(0), equalsDeeply(
				commonalities.Participation => [
					domainName = "Referenced"
					parts += commonalities.ParticipationClass => [
						superMetaclass = referenced.commonality
					]
				]
			))
		]
	}
}
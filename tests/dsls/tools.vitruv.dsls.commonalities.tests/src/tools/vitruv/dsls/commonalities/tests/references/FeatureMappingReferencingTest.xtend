package tools.vitruv.dsls.commonalities.tests.references

import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import tools.vitruv.testutils.printing.ModelPrinterChange
import tools.vitruv.dsls.commonalities.tests.CommonalitiesLanguageInjectorProvider
import org.eclipse.xtext.testing.InjectWith
import tools.vitruv.testutils.printing.UseModelPrinter
import tools.vitruv.dsls.commonalities.tests.util.CommonalitiesLanguageElementsPrinter
import org.junit.jupiter.api.DisplayName
import javax.inject.Inject
import tools.vitruv.dsls.commonalities.tests.util.CommonalityParseHelper
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply
import static tools.vitruv.dsls.commonalities.tests.util.CommonalitiesLanguageCreators.commonalities
import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet
import static extension tools.vitruv.testutils.domains.DomainModelCreators.*
import static allElementTypes.AllElementTypesPackage.Literals.*
import static tools.vitruv.testutils.matchers.ModelMatchers.usingEqualsFor
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import static tools.vitruv.testutils.matchers.ModelMatchers.ignoringFeatures
import static tools.vitruv.dsls.commonalities.language.LanguagePackage.Literals.*

@ExtendWith(InjectionExtension, ModelPrinterChange)
@InjectWith(CommonalitiesLanguageInjectorProvider)
@UseModelPrinter(CommonalitiesLanguageElementsPrinter)
@DisplayName("referencing Participations in features")
class FeatureMappingReferencingTest {
	@Inject extension CommonalityParseHelper
	
	@ParameterizedTest(name = 'participation = "{0}", reference = "{1}"')
	@DisplayName("reference a Participation attribute in a SimpleAttributeMapping")
	@CsvSource(
		"AllElementTypes:Root,AllElementTypes:Root.id",
		"(AllElementTypes as AET):(Root as Start),AET:Start.id"
	)
	def void referenceParticipationInSimpleAttributeMapping(String participation, String reference) {
		val commonality = parseAndValidate('''
			concept test
			
			commonality Test {
				with «participation»
				
				has name {
					= «reference»
				}
			}
		''').commonality
		assertThat(commonality.attributes.get(0).mappings.get(0), equalsDeeply(
			commonalities.SimpleAttributeMapping => [
				readAndWrite = true
				attribute = commonalities.ParticipationAttribute => [
					participationClass = commonality.participations.get(0).allClasses.get(0)
					attribute = commonalities.languageElements.EFeatureAttribute
						.fromMetaclass(
							commonalities.languageElements.EClassMetaclass
								.fromDomain(commonalities.languageElements.VitruviusDomain.forVitruvDomain(aet.domain))
								.forEClass(aet.Root.eClass)
						).forEFeature(IDENTIFIED__ID)
				]
			], usingEqualsForEmfReferences
		))
	}
	
	@ParameterizedTest(name = 'participation = "{0}", reference = "{1}"')
	@DisplayName("reference a Participation attribute in a SimpleReferenceMapping")
	@CsvSource(
		"AllElementTypes:Root,AllElementTypes:Root.recursiveRoot",
		"(AllElementTypes as AET):(Root as Start),AET:Start.recursiveRoot"
	)
	def void referenceParticipationInSimpleReferenceMapping(String participation, String reference) {
		val commonality = parseAndValidate('''
			concept test
			
			commonality Test {
				with «participation»
				
				has selfref referencing test:Test {
					 = «reference»
				}
			}
		''').commonality
		assertThat(commonality.references.get(0).mappings.get(0), equalsDeeply(
			commonalities.SimpleReferenceMapping => [
				readAndWrite = true
				reference = commonalities.ParticipationAttribute => [
					participationClass = commonality.participations.get(0).allClasses.get(0)
					attribute = commonalities.languageElements.EFeatureAttribute
						.fromMetaclass(
							commonalities.languageElements.EClassMetaclass
								.fromDomain(commonalities.languageElements.VitruviusDomain.forVitruvDomain(aet.domain))
								.forEClass(aet.Root.eClass)
						).forEFeature(ROOT__RECURSIVE_ROOT)
				]
			], usingEqualsForEmfReferences
		))
	}
	
	@ParameterizedTest(name = 'participation = "{0}", reference = "{1}"')
	@DisplayName("reference a Participation attribute in an OperatorAttributeMapping")
	@CsvSource(
		"AllElementTypes:Root,AllElementTypes:Root.singleValuedEAttribute",
		"(AllElementTypes as AET):(Root as Start),AET:Start.singleValuedEAttribute"
	)
	def void referenceParticipationInOperatorAttributeMapping(String participation, String reference) {
		val commonality = parseAndValidate('''
			import tools.vitruv.dsls.commonalities.tests.operators.digits
		
			concept test
			
			commonality Test {
				with «participation»
				
				has digits {
					= digits(«reference»)
				}
			}
		''').commonality
		assertThat(commonality.attributes.get(0).mappings.get(0), equalsDeeply(
			commonalities.OperatorAttributeMapping => [
				readAndWrite = true
				operands += commonalities.ParticipationAttributeOperand => [
					participationAttribute = commonalities.ParticipationAttribute => [
					participationClass = commonality.participations.get(0).allClasses.get(0)
					attribute = commonalities.languageElements.EFeatureAttribute
						.fromMetaclass(
							commonalities.languageElements.EClassMetaclass
								.fromDomain(commonalities.languageElements.VitruviusDomain.forVitruvDomain(aet.domain))
								.forEClass(aet.Root.eClass)
						).forEFeature(ROOT__SINGLE_VALUED_EATTRIBUTE)
					]
				]
			], usingEqualsForEmfReferences, ignoringFeatures(OPERATOR_ATTRIBUTE_MAPPING__OPERATOR)
		))
	}
	
	
	@ParameterizedTest(name = 'participation = "{0}", class reference = "{1}", attribute reference = "{2}"')
	@DisplayName("reference a Participation class and attribute in an OperatorReferenceMapping")
	@CsvSource(delimiter = ";", value = #[
		"AllElementTypes:(Root, NonRoot);AllElementTypes:Root;NonRoot.id",
		"(AllElementTypes as AET):(Root as Start, NonRoot as End);AET:Start;End.id"
	])
	def void referenceParticipationInOperatorReferenceMapping(String participation, String classReference, String attributeReference) {
		val commonality = parseAndValidate('''
			import tools.vitruv.dsls.commonalities.tests.operators.mock
			
			concept test
			
			commonality Test {
				with «participation»
				
				has selfref referencing test:Test {
					 = «classReference».mock(«attributeReference»)
				}
			}
		''').commonality
		assertThat(commonality.references.get(0).mappings.get(0), equalsDeeply(
			commonalities.OperatorReferenceMapping => [
				readAndWrite = true
				participationClass = commonality.participations.get(0).allClasses.get(0)
				operands += commonalities.ParticipationAttributeOperand => [
					participationAttribute = commonalities.ParticipationAttribute => [
						participationClass = commonality.participations.get(0).allClasses.get(1)
						attribute = commonalities.languageElements.EFeatureAttribute
							.fromMetaclass(
								commonalities.languageElements.EClassMetaclass
									.fromDomain(commonalities.languageElements.VitruviusDomain.forVitruvDomain(aet.domain))
									.forEClass(aet.NonRoot.eClass)
							).forEFeature(IDENTIFIED__ID)
					]
				]
			], usingEqualsForEmfReferences, ignoringFeatures(OPERATOR_REFERENCE_MAPPING__OPERATOR)
		))
	}
	
	@ParameterizedTest(name = 'participation = "{0}", referenced participation = "{1}", class reference = "{2}", attribute reference = "{3}"')
	@DisplayName("reference a referenced Participation class in an OperatorReferenceMapping")
	@CsvSource(delimiter = ";", value = #[
		"AllElementTypes:Root;AllElementTypes:NonRoot;AllElementTypes:Root;NonRoot.id",
		"(AllElementTypes as AET):(Root as Start);(AllElementTypes as Elements):(NonRoot as End);AET:Start;End.id"
	])
	def void referenceReferencedParticipationInOperatorReferenceMapping(
		String participation,
		String referencedParticipation,
		String classReference,
		String attributeReference
	) {
		inSameResourceSet [
			val referenced = parseInSet('''
				concept Referenced 
				
				commonality Target {
					with «referencedParticipation»
				}
			''')
			val commonality = parseAndValidateInSet('''
				import tools.vitruv.dsls.commonalities.tests.operators.mock
				
				concept test
				
				commonality Test {
					with «participation»
					
					has foreignref referencing Referenced:Target {
						 = «classReference».mock(ref «attributeReference»)
					}
				}
			''').commonality
			assertThat(commonality.references.get(0).mappings.get(0), equalsDeeply(
				commonalities.OperatorReferenceMapping => [
					readAndWrite = true
					participationClass = commonality.participations.get(0).allClasses.get(0)
					operands += commonalities.ReferencedParticipationAttributeOperand => [
						participationAttribute = commonalities.ParticipationAttribute => [
							participationClass = referenced.commonality.participations.get(0).allClasses.get(0)
							attribute = commonalities.languageElements.EFeatureAttribute
								.fromMetaclass(
									commonalities.languageElements.EClassMetaclass
										.fromDomain(commonalities.languageElements.VitruviusDomain.forVitruvDomain(aet.domain))
										.forEClass(aet.NonRoot.eClass)
								).forEFeature(IDENTIFIED__ID)
						]
					]
				], usingEqualsForEmfReferences, ignoringFeatures(OPERATOR_REFERENCE_MAPPING__OPERATOR)
			))
		]
	}
	
	
	static def usingEqualsForEmfReferences(){
		usingEqualsFor(
			commonalities.languageElements.EClassMetaclass.eClass,
			commonalities.languageElements.EFeatureAttribute.eClass
		)
	}
}
package tools.vitruv.dsls.commonalities.tests.syntax

import org.junit.jupiter.api.DisplayName
import javax.inject.Inject
import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.InjectWith
import tools.vitruv.dsls.commonalities.tests.CommonalitiesLanguageInjectorProvider
import org.junit.jupiter.api.Test
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply
import static tools.vitruv.dsls.commonalities.tests.util.CommonalitiesLanguageCreators.commonalities
import static tools.vitruv.testutils.matchers.ModelMatchers.ignoringFeatures
import static tools.vitruv.dsls.commonalities.language.LanguagePackage.Literals.*
import static java.lang.System.lineSeparator
import tools.vitruv.dsls.commonalities.language.Participation
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import tools.vitruv.dsls.commonalities.tests.util.CommonalityParseHelper

@ExtendWith(InjectionExtension)
@InjectWith(CommonalitiesLanguageInjectorProvider)
@DisplayName("parsing Participations")
class ParticipationParsingTest {
	@Inject extension CommonalityParseHelper

	@ParameterizedTest(name="{0}")
	@DisplayName("single ParticipationClass")
	@ValueSource(strings=#[
		'with theDomain:TheClass',
		'with (theDomain):TheClass',
		'with theDomain:(TheClass)',
		'with (theDomain):(TheClass)'
	])
	def void singleParticipation(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				parts += commonalities.ParticipationClass
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("single ParticipationClass with domain alias")
	@ValueSource(strings=#[
		'with (theDomain as otherDomain):TheClass',
		'with (theDomain as otherDomain):(TheClass)'
	])
	def void singleParticipationWithDomainAlias(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				domainAlias = 'otherDomain'
				parts += commonalities.ParticipationClass
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("single ParticipationClass with singleton")
	@ValueSource(strings=#[
		'with theDomain:(single TheClass)',
		'with (theDomain):(single TheClass)'
	])
	def void singleParticipationWithSingleton(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				parts += commonalities.ParticipationClass => [
					singleton = true
				]
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("single ParticipationClass with class alias")
	@ValueSource(strings=#[
		'with theDomain:(TheClass as OtherClass)',
		'with (theDomain):(TheClass as OtherClass)'
	])
	def void singleParticipationWithClassAlias(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				parts += commonalities.ParticipationClass => [
					classAlias = 'OtherClass'
				]
			]
		))
	}

	@Test
	@DisplayName("single Participation with domain alias, singleton, and class alias")
	def void singleParticipationAllFeatures() {
		assertThat(parseParticipation(
			'with (theDomain as otherDomain):(single TheClass as OtherClass)'
		), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				domainAlias = 'otherDomain'
				parts += commonalities.ParticipationClass => [
					singleton = true
					classAlias = 'OtherClass'
				]
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("multiple ParticipationClasses")
	@ValueSource(strings=#[
		'with theDomain:(FirstClass, SecondClass, ThirdClass)',
		'with (theDomain):(FirstClass, SecondClass, ThirdClass)',
		'with theDomain:((FirstClass), SecondClass, ThirdClass)',
		'with theDomain:((FirstClass), (SecondClass), ThirdClass)',
		'with theDomain:(FirstClass, SecondClass, (ThirdClass))',
		'with (theDomain):((FirstClass), SecondClass, ThirdClass)',
		'with (theDomain):((FirstClass), SecondClass, (ThirdClass))',
		'with (theDomain):(FirstClass, SecondClass, (ThirdClass))'
	])
	def void tupleParticipation(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				parts += commonalities.ParticipationClass
				parts += commonalities.ParticipationClass
				parts += commonalities.ParticipationClass
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("multiple ParticipationClasses with domain alias")
	@ValueSource(strings=#[
		'with (theDomain as otherDomain):(FirstClass, SecondClass, ThirdClass)',
		'with (theDomain as otherDomain):((FirstClass), SecondClass, ThirdClass)',
		'with (theDomain as otherDomain):((FirstClass), SecondClass, (ThirdClass))',
		'with (theDomain as otherDomain):(FirstClass, SecondClass, (ThirdClass))'
	])
	def void tupleParticipationWithDomainAlias(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				domainAlias = 'otherDomain'
				parts += commonalities.ParticipationClass
				parts += commonalities.ParticipationClass
				parts += commonalities.ParticipationClass
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("multiple ParticipationClasses with class alias at the second position")
	@ValueSource(strings=#[
		'with theDomain:(FirstClass, SecondClass as OtherSecondClass, ThirdClass)',
		'with (theDomain):(FirstClass, SecondClass as OtherSecondClass, ThirdClass)',
		'with theDomain:((FirstClass), SecondClass as OtherSecondClass, ThirdClass)',
		'with theDomain:((FirstClass), (SecondClass as OtherSecondClass), ThirdClass)',
		'with theDomain:(FirstClass, SecondClass as OtherSecondClass, (ThirdClass))',
		'with (theDomain):((FirstClass), SecondClass as OtherSecondClass, ThirdClass)',
		'with (theDomain):((FirstClass), SecondClass as OtherSecondClass, (ThirdClass))',
		'with (theDomain):(FirstClass, (SecondClass as OtherSecondClass), (ThirdClass))'
	])
	def void tupleParticipationWithClassAlias(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				parts += commonalities.ParticipationClass
				parts += commonalities.ParticipationClass => [
					classAlias = 'OtherSecondClass'
				]
				parts += commonalities.ParticipationClass
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("multiple ParticipationClasses with multiple class aliases")
	@ValueSource(strings=#[
		'with theDomain:(FirstClass as OtherFirstClass, SecondClass, ThirdClass as OtherThirdClass)',
		'with (theDomain):(FirstClass as OtherFirstClass, SecondClass, ThirdClass as OtherThirdClass)',
		'with theDomain:((FirstClass as OtherFirstClass), SecondClass, ThirdClass as OtherThirdClass)',
		'with theDomain:((FirstClass as OtherFirstClass), (SecondClass), ThirdClass as OtherThirdClass)',
		'with theDomain:(FirstClass as OtherFirstClass, SecondClass, (ThirdClass as OtherThirdClass))',
		'with (theDomain):((FirstClass as OtherFirstClass), SecondClass, ThirdClass as OtherThirdClass)',
		'with (theDomain):((FirstClass as OtherFirstClass), SecondClass, (ThirdClass as OtherThirdClass))',
		'with (theDomain):(FirstClass as OtherFirstClass, SecondClass, (ThirdClass as OtherThirdClass))'
	])
	def void tupleParticipationWithClassAliases(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				parts += commonalities.ParticipationClass => [
					classAlias = 'OtherFirstClass'
				]
				parts += commonalities.ParticipationClass
				parts += commonalities.ParticipationClass => [
					classAlias = 'OtherThirdClass'
				]
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("multiple ParticipationClasses with singleton")
	@ValueSource(strings=#[
		'with theDomain:(FirstClass, single SecondClass, ThirdClass)',
		'with (theDomain):(FirstClass, single SecondClass, ThirdClass)',
		'with theDomain:((FirstClass), single SecondClass, ThirdClass)',
		'with theDomain:((FirstClass), (single SecondClass), ThirdClass)',
		'with theDomain:(FirstClass, single SecondClass, (ThirdClass))',
		'with (theDomain):((FirstClass), single SecondClass, ThirdClass)',
		'with (theDomain):((FirstClass), single SecondClass, (ThirdClass))',
		'with (theDomain):(FirstClass, single SecondClass, (ThirdClass))'
	])
	def void tupleParticipationWithSingleton(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				parts += commonalities.ParticipationClass
				parts += commonalities.ParticipationClass => [
					singleton = true
				]
				parts += commonalities.ParticipationClass
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("multiple ParticipationClasses with multiple singletons")
	@ValueSource(strings=#[
		'with theDomain:(single FirstClass, SecondClass, single ThirdClass)',
		'with (theDomain):(single FirstClass, SecondClass, single ThirdClass)',
		'with theDomain:((single FirstClass), SecondClass, single ThirdClass)',
		'with theDomain:((single FirstClass), (SecondClass), single ThirdClass)',
		'with theDomain:(single FirstClass, SecondClass, (single ThirdClass))',
		'with (theDomain):((single FirstClass), SecondClass, single ThirdClass)',
		'with (theDomain):((single FirstClass), SecondClass, (single ThirdClass))',
		'with (theDomain):(single FirstClass, SecondClass, (single ThirdClass))'
	])
	def void tupleParticipationWithSingletons(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				parts += commonalities.ParticipationClass => [
					singleton = true
				]
				parts += commonalities.ParticipationClass
				parts += commonalities.ParticipationClass => [
					singleton = true
				]
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("multiple ParticipationClasses with domain alias, class alias and singleton")
	@ValueSource(strings=#[
		'with (theDomain as otherDomain):(single FirstClass as OtherFirstClass, single SecondClass, ThirdClass as OtherThirdClass)',
		'with (theDomain as otherDomain):((single FirstClass as OtherFirstClass), single SecondClass, ThirdClass as OtherThirdClass)',
		'with (theDomain as otherDomain):((single FirstClass as OtherFirstClass), single SecondClass, (ThirdClass as OtherThirdClass))',
		'with (theDomain as otherDomain):(single FirstClass as OtherFirstClass, single SecondClass, (ThirdClass as OtherThirdClass))'
	])
	def void tupleParticipationWithAllFeatures(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				domainAlias = 'otherDomain'
				parts += commonalities.ParticipationClass => [
					singleton = true
					classAlias = 'OtherFirstClass'
				]
				parts += commonalities.ParticipationClass => [
					singleton = true
				]
				parts += commonalities.ParticipationClass => [
					classAlias = 'OtherThirdClass'
				]
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("single ParticipationRelation")
	@ValueSource(strings=#[
		'with theDomain:(FirstClass op SecondClass)',
		'with (theDomain):(FirstClass op SecondClass)',
		'with theDomain:((FirstClass) op SecondClass)',
		'with (theDomain):(FirstClass op (SecondClass))',
		'with (theDomain):((FirstClass) op (SecondClass))'
	])
	def void singleParticipationRelation(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				parts += commonalities.ParticipationRelation => [
					leftParts += commonalities.ParticipationClass
					rightParts += commonalities.ParticipationClass
				]
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("single ParticipationRelation with multiple operands left")
	@ValueSource(strings=#[
		'with theDomain:((FirstClass, SecondClass, ThirdClass) op FourthClass)',
		'with theDomain:((FirstClass, SecondClass, ThirdClass) op (FourthClass))',
		'with theDomain:(((FirstClass), SecondClass, (ThirdClass)) op (FourthClass))',
		'with (theDomain):((FirstClass, SecondClass, ThirdClass) op FourthClass)',
		'with (theDomain):((FirstClass, SecondClass, ThirdClass) op (FourthClass))',
		'with (theDomain):(((FirstClass), SecondClass, (ThirdClass)) op (FourthClass))'
	])
	def void singleParticipationRelationsMultipleLefts(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				parts += commonalities.ParticipationRelation => [
					leftParts += commonalities.ParticipationClass
					leftParts += commonalities.ParticipationClass
					leftParts += commonalities.ParticipationClass
					rightParts += commonalities.ParticipationClass
				]
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("single ParticipationRelation with multiple operands right")
	@ValueSource(strings=#[
		'with theDomain:(FirstClass op (SecondClass, ThirdClass, FourthClass))',
		'with theDomain:((FirstClass) op (SecondClass, ThirdClass, FourthClass))',
		'with theDomain:((FirstClass) op ((SecondClass), ThirdClass, (FourthClass)))',
		'with (theDomain):(FirstClass op (SecondClass, ThirdClass, FourthClass))',
		'with (theDomain):((FirstClass) op (SecondClass, ThirdClass, FourthClass))',
		'with (theDomain):((FirstClass) op ((SecondClass), ThirdClass, (FourthClass)))'
	])
	def void singleParticipationRelationsMultipleRights(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				parts += commonalities.ParticipationRelation => [
					leftParts += commonalities.ParticipationClass
					rightParts += commonalities.ParticipationClass
					rightParts += commonalities.ParticipationClass
					rightParts += commonalities.ParticipationClass
				]
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("single ParticipationRelation with multiple operands, singletons, domain aliases, and class aliases")
	@ValueSource(strings=#[
		'with (theDomain as otherDomain):((single FirstClass as OtherClass, SecondClass) op (single ThirdClass, FourthClass as OtherFourthClass))',
		'with (theDomain as otherDomain):((single FirstClass as OtherClass, (SecondClass)) op (single ThirdClass, FourthClass as OtherFourthClass))',
		'with (theDomain as otherDomain):(((single FirstClass as OtherClass), (SecondClass)) op ((single ThirdClass), (FourthClass as OtherFourthClass)))'
	])
	def void singleParticipationRelationsAllFeatures(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				domainAlias = 'otherDomain'
				parts += commonalities.ParticipationRelation => [
					leftParts += commonalities.ParticipationClass => [
						singleton = true
						classAlias = 'OtherClass'
					]
					leftParts += commonalities.ParticipationClass
					rightParts += commonalities.ParticipationClass => [
						singleton = true
					]
					rightParts += commonalities.ParticipationClass => [
						classAlias = 'OtherFourthClass'
					]
				]
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("multiple ParticipationRelations")
	@ValueSource(strings=#[
		'with theDomain:(FirstClass op SecondClass, ThirdClass op FourthClass)',
		'with theDomain:((FirstClass) op SecondClass, ThirdClass op (FourthClass))',
		'with theDomain:((FirstClass) op (SecondClass), (ThirdClass) op (FourthClass))',
		'with (theDomain):(FirstClass op SecondClass, ThirdClass op FourthClass)',
		'with (theDomain):((FirstClass) op SecondClass, ThirdClass op (FourthClass))',
		'with (theDomain):((FirstClass) op (SecondClass), (ThirdClass) op (FourthClass))'
	])
	def void multipleParticipationRelations(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				parts += commonalities.ParticipationRelation => [
					leftParts += commonalities.ParticipationClass
					rightParts += commonalities.ParticipationClass
				]
				parts += commonalities.ParticipationRelation => [
					leftParts += commonalities.ParticipationClass
					rightParts += commonalities.ParticipationClass
				]
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("multiple ParticipationRelations with multiple operands, singletons, domain aliases, and class aliases")
	@ValueSource(strings=#[
		'with (theDomain as otherDomain):((Class1, single Class2 as OtherClass2) op Class3, (Class4 as OtherClass4, single Class5) op (Class6, single Class7))',
		'with (theDomain as otherDomain):(((Class1), single Class2 as OtherClass2) op (Class3), (Class4 as OtherClass4, single Class5) op ((Class6), single Class7))',
		'with (theDomain as otherDomain):(((Class1), (single Class2 as OtherClass2)) op (Class3), ((Class4 as OtherClass4), (single Class5)) op ((Class6), (single Class7)))'
	])
	def void multipleParticipationRelationsAllFeatures(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				domainAlias = 'otherDomain'
				parts += commonalities.ParticipationRelation => [
					leftParts += commonalities.ParticipationClass
					leftParts += commonalities.ParticipationClass => [
						singleton = true
						classAlias = 'OtherClass2'
					]
					rightParts += commonalities.ParticipationClass
				]
				parts += commonalities.ParticipationRelation => [
					leftParts += commonalities.ParticipationClass => [
						classAlias = 'OtherClass4'
					]
					leftParts += commonalities.ParticipationClass => [
						singleton = true
					]
					rightParts += commonalities.ParticipationClass
					rightParts += commonalities.ParticipationClass => [
						singleton = true
					]
				]
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("right nested ParticipationRelations")
	@ValueSource(strings=#[
		'with theDomain:(FirstClass op (SecondClass op (ThirdClass op FourthClass)))',
		'with theDomain:(FirstClass op ((SecondClass) op (ThirdClass op (FourthClass))))',
		'with theDomain:((FirstClass) op ((SecondClass) op ((ThirdClass) op (FourthClass))))',
		'with (theDomain):(FirstClass op (SecondClass op (ThirdClass op FourthClass)))',
		'with (theDomain):(FirstClass op ((SecondClass) op (ThirdClass op (FourthClass))))',
		'with (theDomain):((FirstClass) op ((SecondClass) op ((ThirdClass) op (FourthClass))))'
	])
	def void participationRelationsRightNested(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				parts += commonalities.ParticipationRelation => [
					leftParts += commonalities.ParticipationClass
					rightParts += commonalities.ParticipationRelation => [
						leftParts += commonalities.ParticipationClass
						rightParts += commonalities.ParticipationRelation => [
							leftParts += commonalities.ParticipationClass
							rightParts += commonalities.ParticipationClass
						]
					]
				]
			]
		))
	}

	@ParameterizedTest(name="{0}")
	@DisplayName("left nested ParticipationRelations")
	@ValueSource(strings=#[
		'with theDomain:(((FirstClass op SecondClass) op ThirdClass) op FourthClass)',
		'with theDomain:(((FirstClass op (SecondClass)) op ThirdClass) op (FourthClass))',
		'with theDomain:((((FirstClass) op (SecondClass)) op (ThirdClass)) op (FourthClass))',
		'with (theDomain):(((FirstClass op SecondClass) op ThirdClass) op FourthClass)',
		'with (theDomain):(((FirstClass op (SecondClass)) op ThirdClass) op (FourthClass))',
		'with (theDomain):((((FirstClass) op (SecondClass)) op (ThirdClass)) op (FourthClass))'
	])
	def void participationRelationsleftNested(String participationDeclaration) {
		assertThat(parseParticipation(participationDeclaration), equalsParticipation(
			commonalities.Participation => [
				domainName = 'theDomain'
				parts += commonalities.ParticipationRelation => [
					leftParts += commonalities.ParticipationRelation => [
						leftParts += commonalities.ParticipationRelation => [
							leftParts += commonalities.ParticipationClass
							rightParts += commonalities.ParticipationClass
						]
						rightParts += commonalities.ParticipationClass
					]
					rightParts += commonalities.ParticipationClass
				]
			]
		))
	}

	@Test
	@DisplayName('multiple participations, with relations, singletons, domain alias, and class aliases')
	def void allFeatures() {
		assertThat(parseParticipation(
			'with firstDomain:((FirstClass op1 (single SecondClass)) op2 (ThirdClass op3 FourthClass))',
			'with (theDomain as secondDomain):(FirstClass, (single SecondClass) op1 (SomeClass as ThirdClass, FourthClass op2 (single SomeClass as FifthClass)), SixthClass op3 SeventhClass)'
		), equalsParticipation(
			commonalities.Participation => [
				domainName = 'firstDomain'
				parts += commonalities.ParticipationRelation => [
					leftParts += commonalities.ParticipationRelation => [
						leftParts += commonalities.ParticipationClass
						rightParts += commonalities.ParticipationClass => [
							singleton = true
						]
					]
					rightParts += commonalities.ParticipationRelation => [
						leftParts += commonalities.ParticipationClass
						rightParts += commonalities.ParticipationClass
					]
				]
			],
			commonalities.Participation => [
				domainName = 'theDomain'
				domainAlias = 'secondDomain'
				parts += commonalities.ParticipationClass
				parts += commonalities.ParticipationRelation => [
					leftParts += commonalities.ParticipationClass => [
						singleton = true
					]
					rightParts += commonalities.ParticipationClass => [
						classAlias = 'ThirdClass'
					]
					rightParts += commonalities.ParticipationRelation => [
						leftParts += commonalities.ParticipationClass
						rightParts += commonalities.ParticipationClass => [
							singleton = true
							classAlias = 'FifthClass'
						]						
					]
				]
				parts += commonalities.ParticipationRelation => [
					leftParts += commonalities.ParticipationClass
					rightParts += commonalities.ParticipationClass
				]
			]
		))
	}

	private def parseParticipation(String... participations) {
		parse('''
			concept test
			
			commonality Example {
				«FOR participation : participations SEPARATOR lineSeparator»«participation»«ENDFOR»
			}
		''')
	}

	private def equalsParticipation(Participation... expectedParticipations) {
		equalsDeeply(commonalities.CommonalityFile => [
			concept = commonalities.Concept => [name = "test"]
			commonality = commonalities.Commonality => [
				name = "Example"
				participations += expectedParticipations
			]
		], ignoringFeatures(PARTICIPATION_CLASS__SUPER_METACLASS, PARTICIPATION_RELATION__OPERATOR))
	}
}

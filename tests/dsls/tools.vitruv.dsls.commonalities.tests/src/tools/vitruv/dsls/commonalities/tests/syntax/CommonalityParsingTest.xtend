package tools.vitruv.dsls.commonalities.tests.syntax

import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith

import static org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.^extension.ExtendWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply
import static tools.vitruv.dsls.commonalities.tests.util.CommonalitiesLanguageCreators.commonalities
import tools.vitruv.dsls.commonalities.tests.CommonalitiesLanguageInjectorProvider
import tools.vitruv.dsls.commonalities.tests.util.CommonalityParseHelper

@ExtendWith(InjectionExtension)
@InjectWith(CommonalitiesLanguageInjectorProvider)
@DisplayName("parsing whole Commonalities")
class CommonalityParsingTest {
	@Inject extension CommonalityParseHelper

	@Test
	@DisplayName("parses a minimal commonality")
	def void minimalCommonality() {
		assertThat(parse('''
			concept test
			
			commonality Example {}
		'''), equalsDeeply(commonalities.CommonalityFile => [
			concept = commonalities.Concept => [name = "test"]
			commonality = commonalities.Commonality => [
				name = "Example"
			]
		]))
	}
}

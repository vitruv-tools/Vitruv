package tools.vitruv.dsls.commonalities

import com.google.inject.Singleton
import com.google.inject.Inject
import org.eclipse.xtext.testing.util.ParseHelper
import tools.vitruv.dsls.commonalities.language.CommonalityFile
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.notNullValue
import static tools.vitruv.testutils.matchers.ModelMatchers.hasNoErrors

@Singleton
class CommonalityParseHelper {
	@Inject
	ParseHelper<CommonalityFile> parseHelper

	def CommonalityFile parse(CharSequence input) {
		val result = parseHelper.parse(input)
		val errorMessage = '''Error while parsing:
			 «input»
			 '''
		assertThat(errorMessage, result, is(notNullValue))
		assertThat(errorMessage, result.eResource, hasNoErrors)
		return result
	}
}

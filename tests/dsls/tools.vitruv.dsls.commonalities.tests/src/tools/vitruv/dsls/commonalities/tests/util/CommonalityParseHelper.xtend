package tools.vitruv.dsls.commonalities.tests.util

import com.google.inject.Singleton
import com.google.inject.Inject
import org.eclipse.xtext.testing.util.ParseHelper
import tools.vitruv.dsls.commonalities.language.CommonalityFile
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.notNullValue
import static tools.vitruv.testutils.matchers.ModelMatchers.hasNoErrors
import org.eclipse.xtext.testing.validation.ValidationTestHelper
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtext.resource.XtextResourceSet
import javax.inject.Provider

@Singleton
class CommonalityParseHelper {
	@Inject ParseHelper<CommonalityFile> parseHelper
	@Inject ValidationTestHelper validator
	@Inject Provider<XtextResourceSet> resourceSetProvider
	
	def CommonalityFile parseInSet(ResourceSet resourceSet, CharSequence input) {
		val result = parseHelper.parse(input, resourceSet)
		val errorMessage = '''
			Cannot parse:
			«input»
		'''
		assertThat(errorMessage, result, is(notNullValue))
		assertThat(errorMessage, result.eResource, hasNoErrors)
		return result
	}

	def CommonalityFile parse(CharSequence input) {
		parseInSet(resourceSetProvider.get(), input)
	}
	
	def CommonalityFile parseAndValidateInSet(ResourceSet resourceSet, CharSequence input) {
		val result = parseInSet(resourceSet, input)
		validator.validate(result)
		val errorMessage = '''
			Validation failed: 
			«input»
		'''		
		assertThat(errorMessage, result.eResource, hasNoErrors)
		return result
	}
	
	def CommonalityFile parseAndValidate(CharSequence input) {
		parseAndValidateInSet(resourceSetProvider.get(), input)
	}
	
	def inSameResourceSet((ResourceSet) => void action) {
		action.apply(resourceSetProvider.get())
	}
}

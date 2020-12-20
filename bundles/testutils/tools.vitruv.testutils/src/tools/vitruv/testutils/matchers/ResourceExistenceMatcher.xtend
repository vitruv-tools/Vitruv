package tools.vitruv.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.Description
import org.eclipse.emf.ecore.resource.Resource

@FinalFieldsConstructor
package class ResourceExistenceMatcher extends TypeSafeMatcher<Resource> {
	val boolean shouldExist

	override describeTo(Description description) {
		val qualifier = if (shouldExist) '' else 'not'
		description.appendText('''the resource «qualifier» to exist''');
	}

	override matchesSafely(Resource item) {
		item.resourceSet.URIConverter.exists(item.URI, emptyMap) == shouldExist
	}

	override describeMismatchSafely(Resource item, Description mismatchDescription) {
		val qualifier = if (shouldExist) 'no' else 'a'
		mismatchDescription.appendText('''there was «qualifier» resource at ''').appendValue(item.URI)
	}
}

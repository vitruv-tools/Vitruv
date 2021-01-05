package tools.vitruv.testutils.matchers

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

import static java.lang.System.lineSeparator
import static extension tools.vitruv.testutils.printing.ModelPrinting.*

@FinalFieldsConstructor
package class ResourceContainmentMatcher extends TypeSafeMatcher<Resource> {
	val Matcher<? super EObject> delegateMatcher
	boolean exists

	override describeMismatchSafely(Resource item, Description mismatchDescription) {
		if (!exists) {
			mismatchDescription.appendText("there is no resource at ").appendValue(item.URI)
		} else if (item.contents.isEmpty) {
			mismatchDescription.appendModelValue(item).appendText(" was empty.")
		} else if (item.contents.size > 1) {
			mismatchDescription.appendModelValue(item).appendText(" contained ").appendValue(item.contents.size).
				appendText(" instead of just one content element.")
		} else {
			delegateMatcher.describeMismatch(item.contents.get(0), mismatchDescription)
			mismatchDescription.appendText(lineSeparator()).appendText('    in the resource at ').appendModelValue(item.URI)
		}
	}

	override describeTo(Description description) {
		description.appendText("a resource containing ").appendDescriptionOf(delegateMatcher)
	}

	override matchesSafely(Resource item) {
		exists = item.resourceSet.URIConverter.exists(item.URI, emptyMap)
		return exists && item.contents.size == 1 && delegateMatcher.matches(item.contents.get(0))
	}
}

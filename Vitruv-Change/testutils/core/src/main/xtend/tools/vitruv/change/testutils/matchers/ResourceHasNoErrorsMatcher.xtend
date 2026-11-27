package tools.vitruv.change.testutils.matchers

import org.eclipse.emf.ecore.resource.Resource
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import static java.lang.System.lineSeparator

package class ResourceHasNoErrorsMatcher extends TypeSafeMatcher<Resource> {
	override matchesSafely(Resource resource) {
		resource.errors.isEmpty
	}

	override describeTo(Description description) {
		description.appendText('an EMF resource without errors');
	}

	override describeMismatchSafely(Resource resource, Description mismatchDescription) {
		mismatchDescription.appendText('found these errors:')
		resource.errors.forEach [
			mismatchDescription.appendText(lineSeparator).appendText('      • ').appendText(message).appendText(' (@')
			if (location !== null) {
				mismatchDescription.appendText(location).appendText('#')
			} else {
				mismatchDescription.appendText("line ")
			}
			mismatchDescription.appendText(line.toString).appendText(':').appendText(column.toString).appendText(')')
		]
		mismatchDescription.appendText(lineSeparator)
	}
}

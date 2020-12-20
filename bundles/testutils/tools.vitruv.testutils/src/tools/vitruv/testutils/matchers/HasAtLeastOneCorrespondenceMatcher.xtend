package tools.vitruv.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.TypeSafeMatcher
import org.eclipse.emf.ecore.EObject
import org.hamcrest.Matcher
import java.util.Set
import org.hamcrest.Description
import static org.hamcrest.CoreMatchers.hasItem

@FinalFieldsConstructor
package class HasAtLeastOneCorrespondenceMatcher extends TypeSafeMatcher<EObject> {
	val CorrespondenceSource correspondenceSource
	val Matcher<? super EObject> correspondenceMatcher
	var Matcher<Iterable<? super EObject>> anyMatcher = null
	var Set<? extends Object> correspondences = null

	override matchesSafely(EObject item) {
		correspondences = correspondenceSource.getCorrespespondingObjects(item).toSet
		if (correspondences.isEmpty) {
			return false
		}
		if (correspondenceMatcher !== null) {
			anyMatcher = hasItem(correspondenceMatcher)
			return anyMatcher.matches(correspondences)
		}
		return true
	}

	override describeMismatchSafely(EObject item, Description mismatchDescription) {
		if (correspondences.isEmpty) {
			mismatchDescription.appendText("found no such correspondences")
		} else {
			anyMatcher.describeMismatch(correspondences, mismatchDescription)
		}
	}

	override describeTo(Description description) {
		description.appendText("at least one corresponding object").appendDescriptionOf(correspondenceSource)
		if (correspondenceMatcher !== null) {
			description.appendText(" that is: ").appendDescriptionOf(correspondenceMatcher)
		}
	}
}

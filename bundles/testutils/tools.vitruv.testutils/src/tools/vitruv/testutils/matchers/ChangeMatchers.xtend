package tools.vitruv.testutils.matchers

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.hamcrest.TypeSafeMatcher
import tools.vitruv.framework.change.description.VitruviusChange
import org.hamcrest.Description
import tools.vitruv.framework.change.description.CompositeChange
import org.hamcrest.Matcher

@Utility
class ChangeMatchers {
	def static Matcher<? super VitruviusChange> isValid() {
		return new IsValidMatcher
	}	
}

package class IsValidMatcher extends TypeSafeMatcher<VitruviusChange> {
	override matchesSafely(VitruviusChange item) {
		item.validate
	}

	override describeMismatchSafely(VitruviusChange item, Description mismatchDescription) {
		if (!item.containsConcreteChange) {
			mismatchDescription.appendText("the change contains no concrete change")
		} else if (item.URI === null) {
			mismatchDescription.appendText("the change has no URI")
		} else if (item instanceof CompositeChange) {
			mismatchDescription.appendText("the change affects multiple models: ").appendValueList('[', ', ', ']', item.
				changes.map[(it as VitruviusChange).URI])
		}
		mismatchDescription.appendText(":\n").appendValue(item)
	}

	override describeTo(Description description) {
		description.appendText("a valid change")
	}
}

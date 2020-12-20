package tools.vitruv.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.TypeSafeMatcher
import org.eclipse.emf.ecore.EObject
import java.util.Set
import org.hamcrest.Matcher
import org.hamcrest.Description
import static tools.vitruv.testutils.printing.PrintMode.MULTI_LINE
import static extension tools.vitruv.testutils.printing.ModelPrinting.*

@FinalFieldsConstructor
package class HasExactlyOneCorrespondenceMatcher extends TypeSafeMatcher<EObject> {
	val CorrespondenceSource correspondenceSource
	val Matcher<? super EObject> correspondenceMatcher
	var Set<? extends EObject> correspondences = null

	override matchesSafely(EObject item) {
		correspondences = correspondenceSource.getCorrespespondingObjects(item).toSet
		correspondences.size == 1 &&
			(correspondenceMatcher === null || correspondenceMatcher.matches(correspondences.get(0)))
	}

	override describeMismatchSafely(EObject item, Description mismatchDescription) {
		if (correspondences.isEmpty) {
			mismatchDescription.appendText("found no such correspondences")
		} else if (correspondences.size > 1) {
			mismatchDescription.appendText("found more than one such correspondence: ").
				appendModelValueSet(correspondences, MULTI_LINE)
		} else {
			correspondenceMatcher.describeMismatch(correspondences.get(0), mismatchDescription)
		}
	}

	override describeTo(Description description) {
		description.appendText("exactly one corresponding object").appendDescriptionOf(correspondenceSource)
		if (correspondenceMatcher !== null) {
			description.appendText(" that is: ").appendDescriptionOf(correspondenceMatcher)
		}
	}
}

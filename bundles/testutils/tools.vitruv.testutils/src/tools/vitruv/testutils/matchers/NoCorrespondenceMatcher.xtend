package tools.vitruv.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.TypeSafeMatcher
import org.eclipse.emf.ecore.EObject
import java.util.Set
import org.hamcrest.Description
import static tools.vitruv.testutils.printing.PrintMode.MULTI_LINE
import static extension tools.vitruv.testutils.printing.ModelPrinting.*

@FinalFieldsConstructor
package class NoCorrespondenceMatcher extends TypeSafeMatcher<EObject> {
	val CorrespondenceSource correspondenceSource
	var Set<? extends EObject> correspondences

	override matchesSafely(EObject item) {
		correspondences = correspondenceSource.getCorrespespondingObjects(item).toSet
		return correspondences.isEmpty
	}

	override describeMismatchSafely(EObject item, Description mismatchDescription) {
		mismatchDescription.appendText("found correspondences: ").appendModelValueSet(correspondences, MULTI_LINE)
	}

	override describeTo(Description description) {
		description.appendText("an object with no correspondences").appendDescriptionOf(correspondenceSource)
	}
}

package tools.vitruv.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.TypeSafeMatcher
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EObject
import org.hamcrest.Description
import static tools.vitruv.testutils.matchers.MatcherUtil.a
import org.hamcrest.Matcher
import org.hamcrest.core.IsCollectionContaining
import static extension tools.vitruv.testutils.printing.ModelPrinting.*

@FinalFieldsConstructor
package class EObjectFeatureMatcher extends TypeSafeMatcher<EObject> {
	val EStructuralFeature feature
	val Matcher<?> featureMatcher

	override protected matchesSafely(EObject item) {
		feature.EContainingClass.isInstance(item) && featureMatcher.matches(item.eGet(feature))
	}

	override describeTo(Description description) {
		description.appendText(a(feature.EContainingClass.name)).appendText(" whose ").appendText(feature.name).
			appendText( //
			switch (featureMatcher) {
				EObjectFeatureMatcher,
				IsCollectionContaining<?>: " is "
				default: " "
			}).appendDescriptionOf(featureMatcher)
	}

	override describeMismatchSafely(EObject item, Description mismatchDescription) {
		if (!feature.EContainingClass.isInstance(item)) {
			mismatchDescription.appendText(" was ").appendText(a(item.eClass.name))
		} else {
			mismatchDescription.appendText("->").appendText(feature.name).appendText( //
				switch (featureMatcher) {
					IsCollectionContaining<?> case feature.isOrdered: "[*]"
					IsCollectionContaining<?>: "{*}"
					EObjectFeatureMatcher: ""
					default: " "
				}
			)
			featureMatcher.describeMismatch(item.eGet(feature), mismatchDescription)
			mismatchDescription.appendText(" for")
		}
		mismatchDescription.appendText(":").appendText(System.lineSeparator).appendModelValue(item)
	}
}
package tools.vitruv.change.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.TypeSafeMatcher
import org.eclipse.emf.ecore.EClassifier
import org.hamcrest.Description
import org.eclipse.emf.ecore.EObject
import static extension tools.vitruv.change.testutils.printing.ModelPrinting.*
import static tools.vitruv.change.testutils.printing.TestMessages.a

@FinalFieldsConstructor
package class InstanceOfEClassifierMatcher extends TypeSafeMatcher<Object> {
	val EClassifier expectedType

	override protected matchesSafely(Object item) {
		expectedType.isInstance(item)
	}

	override describeTo(Description description) {
		description.appendText("an instance of ").appendModelValue(expectedType)
	}

	override describeMismatchSafely(Object item, Description mismatchDescription) {
		mismatchDescription.appendText("is ").appendText(a( //
			switch (item) {
				EObject: item.eClass.name
				default: item.class.simpleName
			}
		)).appendText(":").appendText(System.lineSeparator).appendModelValue(item)
	}
}

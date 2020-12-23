package tools.vitruv.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.TypeSafeMatcher
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.hamcrest.Description
import static extension tools.vitruv.testutils.printing.ModelPrinting.*

@FinalFieldsConstructor
package class EObjectResourceMatcher extends TypeSafeMatcher<EObject> {
	val Resource expectedResource

	override matchesSafely(EObject item) {
		item.eResource.URI == expectedResource.URI
	}

	override describeTo(Description description) {
		description.appendText('an EObject that is contained in the resource at ').appendValue(expectedResource.URI)
	}

	override describeMismatchSafely(EObject item, Description mismatchDescription) {
		mismatchDescription.appendModelValue(item)
		val actualResource = item.eResource
		if (actualResource === null) {
			mismatchDescription.appendText(' is not in any resource')
		} else {
			mismatchDescription.appendText(' is in ').appendModelValue(actualResource)
		}
	}
}

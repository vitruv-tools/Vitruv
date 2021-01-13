package tools.vitruv.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.TypeSafeMatcher
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil
import org.hamcrest.Description
import static extension tools.vitruv.testutils.printing.ModelPrinting.*

@FinalFieldsConstructor
package class ResourceExistingMatcher extends TypeSafeMatcher<URI> {
	val boolean shouldExist

	override matchesSafely(URI item) {
		URIUtil.existsResourceAtUri(item) == shouldExist
	}

	override describeTo(Description description) {
		description.appendText(
			if (shouldExist) {
				"an URI pointing to an existing resource"
			} else {
				"an URI not pointing to any resource"
			}
		)
	}

	override describeMismatchSafely(URI item, Description mismatchDescription) {
		val qualifier = if (shouldExist) "no" else "a"
		mismatchDescription.appendText('''there was «qualifier» resource at ''').appendModelValue(item)
	}
}

package tools.vitruv.testutils.matchers

import org.eclipse.emf.ecore.EObject
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply

/**Class to instantiate either a listContains-matcher or a listDoesNotContain-matcher (not yet).
 * @author Dirk Neumann 
 */
class EListSingleContainmentMatcher extends TypeSafeMatcher<Iterable<? extends EObject>> {
	EObject itemOfInterest
	ModelDeepEqualityOption[] options
	boolean included

	/**Constructor.	 * 
	 * @param itemOfInterest item which should (not) be included
	 * @param included Should the item be included (true) or not (false)?
	 * @param options the options for the deep equality check
	 */
	new(EObject itemOfInterest, boolean included, ModelDeepEqualityOption[] options) {
		this.itemOfInterest = itemOfInterest as EObject
		this.options = options
		this.included = included
	}

	override protected matchesSafely(Iterable<? extends EObject> items) {
		items.exists[equalsDeeply(itemOfInterest, options).matches(it)] == included
	}

	override describeTo(Description description) {
		description.appendText("a list which contains something deeply equal to " + itemOfInterest.toString())
	}

}

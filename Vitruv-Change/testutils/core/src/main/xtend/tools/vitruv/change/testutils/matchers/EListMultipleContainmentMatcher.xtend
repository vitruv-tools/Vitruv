package tools.vitruv.change.testutils.matchers

import org.eclipse.emf.ecore.EObject
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

/**Class to instantiate either a containsAllOf-matcher or a containsNoneOf-matcher.
 * @author Dirk Neumann 
 */
package class EListMultipleContainmentMatcher extends TypeSafeMatcher<Iterable<? extends EObject>> {
	Iterable<? extends EObject> itemsOfInterest;
	Matcher<Object> base;
	boolean included;

	/**Constructor.	 * 
	 * @param itemsOfInterest items which should (not) be included
	 * @param included Should all of them (true) or none of them (false) be included?
	 * @param options the options for the deep equality check.
	 */
	package new(Iterable<? extends EObject> itemsOfInterest, boolean included, ModelDeepEqualityOption[] options) {
		this.itemsOfInterest = itemsOfInterest;
		this.included = included;
		val matchers = itemsOfInterest.map[new EListSingleContainmentMatcher(it, included, options)]
		this.base = CoreMatchers.allOf(matchers)
	}

	override protected matchesSafely(Iterable<? extends EObject> items) {
		this.base.matches(items)
	}

	override describeTo(Description description) {
		description.appendText("a list which contains ")
		if (included) {
			description.appendText(String.format("all elements from the following list:%n"))
		} else {
			description.appendText(String.format("none of the elements from the following list:%n"))
		}
		for (EObject e : itemsOfInterest) {
			description.appendText(String.format(e.toString() + "%n"))
		}
	}
}

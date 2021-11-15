package tools.vitruv.testutils.matchers

import org.eclipse.emf.ecore.EObject
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply

/**Class to instantiate either a listContains-matcher or a listDoesNotContain-matcher (not yet).
 * @author Dirk Neumann 
 */
class EListSingleContainmentMatcher extends TypeSafeMatcher<Iterable<? extends EObject>>{
	EObject searchedItem;
	ModelDeepEqualityOption[] options;
	boolean included;
	
	/**Constructor.	 * 
	 * @param searchedItem item of interest
	 * @param included should the item be included (true) or not (false)
	 * @param options ...
	 */
	new(EObject searchedItem, boolean included, ModelDeepEqualityOption[] options) {
		this.searchedItem = searchedItem as EObject
		this.options = options;	
		this.included = included;
	}
	
	override protected matchesSafely(Iterable<? extends EObject> items) {
		(items as Iterable<EObject>).exists[equalsDeeply(searchedItem, options).matches(it)] == included
	}
	
	override describeTo(Description description) {
		description.appendText("a list which contains something deeply equal to " + searchedItem.toString())
	}
	
}
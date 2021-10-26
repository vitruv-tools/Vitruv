package tools.vitruv.testutils.matchers

import org.hamcrest.TypeSafeMatcher
import org.eclipse.emf.ecore.EObject
import org.hamcrest.Description
import tools.vitruv.testutils.matchers.ModelDeepEqualityOption
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply
import org.eclipse.emf.common.util.EList
import org.hamcrest.Matcher
import java.util.ArrayList
import org.hamcrest.CoreMatchers

/**Class to instantiate either a listContains-matcher or a listDoesNotContain-matcher (not yet).
 * @author Dirk Neumann 
 */
class EListSingleContainmentMatcher extends TypeSafeMatcher<EList<EObject>>{
	EObject searchedItem;
	ModelDeepEqualityOption[] options;
	boolean included;
	
	/**Constructor.	 * 
	 * @param searchedItem item of interest
	 * @param included should the item be included (true) or not (false)
	 * @param options ...
	 */
	new(Object searchedItem, boolean included, ModelDeepEqualityOption[] options) {
		this.searchedItem = searchedItem as EObject
		this.options = options;	
		this.included = included;
	}
	
	override protected matchesSafely(EList<EObject> items) {
		items.exists[x| equalsDeeply(searchedItem,options).matches(x)] == included
	}
	
	override describeTo(Description description) {
		description.appendText("a list which contains something deeply equal to " + searchedItem.toString())
	}
	
}
package tools.vitruv.testutils.matchers

import org.eclipse.emf.ecore.EObject
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

/**Class to instantiate either a containsAllOf-matcher or a containsNoneOf-matcher.
 * @author Dirk Neumann 
 */
package class EListMultipleContainmentMatcher extends TypeSafeMatcher<Iterable<? extends EObject>>{
	Iterable<? extends EObject> shouldBeContained;
	Matcher<Object> base;
	boolean included;
	
	/**Constructor.	 * 
	 * @param list items of interest
	 * @param included should all of them (true) or none of them (false) be included
	 * @param options ...
	 */
	package new(Iterable<? extends EObject> list, boolean included, ModelDeepEqualityOption[] options) {
		this.shouldBeContained = list;
		this.included = included;		
		val matchers = shouldBeContained.map[new EListSingleContainmentMatcher(it, included, options)]		
		this.base = CoreMatchers.allOf(matchers)
	}
	
	override protected matchesSafely(Iterable<? extends EObject> items) {
		this.base.matches(items)
	}
	
	override describeTo(Description description) {
		description.appendText("a list which contains ")
		if(included){
			description.appendText("all elements from the following list:\n")
		}
		else{
			description.appendText("none of the elements from the following list:\n")			
		}
		for(EObject e : shouldBeContained){
			description.appendText(e.toString() + "\n")
		}
	}
}
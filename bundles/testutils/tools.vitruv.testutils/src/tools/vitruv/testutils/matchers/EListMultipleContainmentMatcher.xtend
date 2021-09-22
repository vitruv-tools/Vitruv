package tools.vitruv.testutils.matchers

import org.hamcrest.TypeSafeMatcher
import org.hamcrest.Description
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.hamcrest.Matcher
import org.hamcrest.BaseMatcher
import tools.vitruv.testutils.matchers.ModelDeepEqualityOption
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply
import static tools.vitruv.testutils.matchers.ModelMatchers.contains
import org.hamcrest.CoreMatchers
import java.util.List
import java.util.ArrayList

package class EListMultipleContainmentMatcher extends TypeSafeMatcher<EList<EObject>>{
	EList<EObject> shouldBeContained;
	ModelDeepEqualityOption[] options;
	Matcher base;
	boolean included;
	
	package new(EList<EObject> list, boolean included, ModelDeepEqualityOption[] options) {
		this.shouldBeContained = list;
		this.options = options;
		this.included = included;
		val ArrayList<Matcher> lm = new ArrayList<Matcher>()		
		shouldBeContained.forEach[x| lm.add(new EListSingleContainmentMatcher(x, included, options))]		
		this.base = CoreMatchers.allOf(lm)
	}
	
	override protected matchesSafely(EList<EObject> items) {
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
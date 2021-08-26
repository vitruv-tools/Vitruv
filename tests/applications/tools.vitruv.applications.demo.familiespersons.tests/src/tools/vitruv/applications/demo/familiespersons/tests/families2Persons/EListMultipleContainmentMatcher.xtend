package tools.vitruv.applications.demo.familiespersons.tests.families2Persons

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

//import static tools.vitruv.applications.demo.familiespersons.tests.families2Persons.FamiliesPersonsTest.equalPersons

package class EListMultipleContainmentMatcher extends TypeSafeMatcher<EList<EObject>>{ //extends TypeSafeMatcher<EList<Object>> {
	EList<EObject> shouldBeContained;
	ModelDeepEqualityOption[] options;
	Matcher base;
	
	package new(EList<EObject> list, ModelDeepEqualityOption[] options) {
		this.shouldBeContained = list;
		this.options = options;
		val ArrayList<Matcher> lm = new ArrayList<Matcher>()		
		shouldBeContained.forEach[x| lm.add(new EListSingleContainmentMatcher(x, options))]		
		this.base = CoreMatchers.allOf(lm)
	}
	
	override protected matchesSafely(EList<EObject> items) {
		this.base.matches(items)
//		//listModelItem.equals(searchedOne)
//		val List<Matcher> lm = new ArrayList<Matcher>()
//		items.forEach[x| lm.add(equalsDeeply(x))]
//		
//		
//		
//		CoreMatchers.allOf()
//		shouldBeContained.forall[searchedOne|items.exists[listModelItem|equalPersons(listModelItem,searchedOne)]];
	}
	
	override describeTo(Description description) {
	}
	
}
package tools.vitruv.testutils.matchers

import org.hamcrest.TypeSafeMatcher
import org.hamcrest.Description
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject

class EListContainmentMatcher extends TypeSafeMatcher<EList<? super Object>> {
	
	override protected matchesSafely(EList<? super Object> item) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}
	
	override describeTo(Description description) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	
	
}
package tools.vitruv.dsls.commonalities.tests.matchers

import org.eclipse.emf.ecore.resource.Resource
import org.hamcrest.Description
import org.hamcrest.TypeSafeDiagnosingMatcher

class EResourceMatchers {
	def static hasNoErrors() {
		return new TypeSafeDiagnosingMatcher<Resource>() {
			
			override protected matchesSafely(Resource resource, Description mismatchDescription) {
				if (!resource.errors.isEmpty) {
					mismatchDescription.appendText('found errors: ').appendValueList('[', ', ', ']', resource.errors);
					return false;
				}
				
				return true;
			}
			
			override describeTo(Description description) {
				description.appendText('an EMF resource without errors');
			}
		}
	}
}
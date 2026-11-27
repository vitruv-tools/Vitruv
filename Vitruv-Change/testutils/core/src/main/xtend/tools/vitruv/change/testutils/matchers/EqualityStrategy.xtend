package tools.vitruv.change.testutils.matchers

import org.eclipse.emf.ecore.EObject

/**
 * Customizes how {@link ModelDeepEqualityMatcher} compares objects in <em>non-containment-references</em>.
 */
interface EqualityStrategy extends ModelDeepEqualityOption {
	def abstract Result compare(EObject left, EObject right)
	
	enum Result {
		EQUAL,
		UNEQUAL,
		UNKNOWN
	}
}
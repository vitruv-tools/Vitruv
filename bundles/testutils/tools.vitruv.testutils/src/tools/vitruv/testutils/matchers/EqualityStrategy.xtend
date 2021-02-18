package tools.vitruv.testutils.matchers

import org.eclipse.emf.ecore.EObject
import java.util.Set

interface EqualityStrategy extends ModelDeepEqualityOption {
	def abstract Result compare(EObject left, EObject right)
	def abstract Set<String> getTargetNsUris()
	
	enum Result {
		EQUAL,
		UNEQUAL,
		UNKNOWN
	}
}
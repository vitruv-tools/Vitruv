package tools.vitruv.testutils.matchers

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.compare.match.eobject.EqualityHelperExtensionProvider.SpecificMatch
import org.eclipse.emf.compare.utils.IEqualityHelper
import java.util.Set

// sealed class
abstract class ModelDeepEqualityOption {
	private new() {}
	
	def abstract void describeTo(StringBuilder builder)
	
	def String describe() {
		val target = new StringBuilder
		describeTo(target)
		target.toString
	}
	
	static abstract class EqualityFeatureFilter extends ModelDeepEqualityOption {
		def boolean includeFeature(EObject object, EStructuralFeature feature)
	}
	
	static abstract class EqualityStrategy extends ModelDeepEqualityOption {
		def abstract SpecificMatch compare(EObject left, EObject right, IEqualityHelper equalityHelper)
		def abstract Set<String> getTargetNsUris()
	}
}
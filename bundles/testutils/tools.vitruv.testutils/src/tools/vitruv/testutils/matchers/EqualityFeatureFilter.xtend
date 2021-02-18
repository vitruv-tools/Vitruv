package tools.vitruv.testutils.matchers

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature

interface EqualityFeatureFilter extends ModelDeepEqualityOption {
	def boolean includeFeature(EObject object, EStructuralFeature feature)
}
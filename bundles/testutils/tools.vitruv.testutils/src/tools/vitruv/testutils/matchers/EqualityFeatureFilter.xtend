package tools.vitruv.testutils.matchers

import org.eclipse.emf.ecore.EStructuralFeature

interface EqualityFeatureFilter extends ModelDeepEqualityOption {
	def boolean includeFeature(EStructuralFeature feature)
}
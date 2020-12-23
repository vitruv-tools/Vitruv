package tools.vitruv.testutils.matchers

import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EObject

interface EqualityFeatureFilter extends Described {
	def boolean includeFeature(EObject object, EStructuralFeature feature)
}

package tools.vitruv.testutils.matchers

import org.eclipse.emf.ecore.EStructuralFeature

interface DescribedFeatureFilter {
	def boolean includeFeature(EStructuralFeature feature)

	def String describe()
}

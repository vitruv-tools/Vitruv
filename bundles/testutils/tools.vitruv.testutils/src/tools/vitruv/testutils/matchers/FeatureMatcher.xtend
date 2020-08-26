package tools.vitruv.testutils.matchers

import java.util.function.Consumer
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.hamcrest.Description

interface FeatureMatcher {
	def boolean isForFeature(EObject expectedObject, EStructuralFeature feature)

	def Consumer<Description> getMismatch(Object expectedValue, Object itemValue)
}

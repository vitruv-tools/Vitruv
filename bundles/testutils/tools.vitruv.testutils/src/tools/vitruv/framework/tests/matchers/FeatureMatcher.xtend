package tools.vitruv.framework.tests.matchers

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EStructuralFeature
import org.hamcrest.Description
import java.util.function.Consumer

interface FeatureMatcher {
	def boolean isForFeature(EClass checkedClass, EStructuralFeature feature)

	def Consumer<Description> getMismatch(Object expectedValue, Object itemValue)
}

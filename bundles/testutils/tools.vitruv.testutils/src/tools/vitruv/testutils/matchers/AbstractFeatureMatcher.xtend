package tools.vitruv.testutils.matchers

import java.lang.reflect.Method
import java.util.function.Consumer
import org.hamcrest.Description

abstract class AbstractFeatureMatcher<FeatureType> implements FeatureMatcher {

	val Class<?> expectedFeatureType
	protected var Consumer<Description> mismatch

	protected new() {
		expectedFeatureType = findExpectedType()
	}

	def private findExpectedType() {
		for (var Class<?> fromClass = this.class; fromClass != AbstractFeatureMatcher; fromClass = fromClass.
			superclass) {
			for (method : fromClass.declaredMethods) {
				val expectedType = method.findExpectedFeatureType
				if (expectedType !== null) {
					return expectedType
				}
			}
		}
		throw new Error("Cannot determine correct type for checkFeatureValuesSavely method.")
	}

	def private findExpectedFeatureType(Method method) {
		if (method.name == 'checkFeatureValuesSavely' && method.parameterCount == 2 && !method.isSynthetic) {
			return method.parameterTypes.get(0)
		}
	}

	override getMismatch(Object expectedValue, Object itemValue) {
		if (!expectedFeatureType.isInstance(expectedValue)) {
			throw new IllegalStateException("The expected value is not an instance of " + expectedFeatureType)
		}
		if (expectedFeatureType.isInstance(itemValue)) {
			return [
				appendText("has the wrong type ").appendValue(itemValue.class.name).appendText(", expected ").
					appendValue(expectedFeatureType.name).appendText(".")
			]
		}
		if (expectedFeatureType.isInstance(expectedValue) && expectedFeatureType.isInstance(itemValue)) {
			return getMismatchSafely(expectedValue as FeatureType, itemValue as FeatureType)
		}

	}

	def protected Consumer<Description> getMismatchSafely(FeatureType expectedValue, FeatureType itemValue)
}

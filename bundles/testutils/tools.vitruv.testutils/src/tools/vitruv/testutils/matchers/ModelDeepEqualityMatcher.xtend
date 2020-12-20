package tools.vitruv.testutils.matchers

import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.emf.ecore.EObject
import java.util.Deque
import java.util.function.Consumer
import org.hamcrest.Description
import java.util.ArrayDeque
import org.hamcrest.TypeSafeMatcher
import java.util.Map
import java.util.HashMap
import org.eclipse.emf.ecore.EStructuralFeature
import static extension tools.vitruv.testutils.printing.ModelPrinting.*
import static org.junit.jupiter.api.Assertions.*
import org.eclipse.emf.ecore.InternalEObject
import java.util.Collection

@FinalFieldsConstructor
package class ModelDeepEqualityMatcher extends TypeSafeMatcher<EObject> {
	package val EObject expectedObject
	var Deque<String> navigationStack = new ArrayDeque()
	var Consumer<Description> mismatch
	// Bidirectional mapping between objects which have been compared and considered equal.
	// Note that each object can be considered equal to at most one other object. This ensures that two objects are
	// considered structurally equal only if the graphs formed by all their referenced objects have the same topology.
	// This is similar to EMF's implementation of EcoreUtil.equals(EObject, EObject).
	var Map<Object, Object> objectMapping = new HashMap
	val FeatureMatcher[] featureMatchers

	override matchesSafely(EObject item) {
		return expectedObject.equalsDeeply(item, false)
	}

	def private findFeatureChecker(EObject expectedObject, EStructuralFeature feature) {
		for (featureChecker : featureMatchers) {
			if (featureChecker.isForFeature(expectedObject, feature)) {
				return featureChecker
			}
		}
		return new FeatureMatcher() {
			override isForFeature(EObject expectedObject, EStructuralFeature feature) {
				true
			}

			override getMismatch(Object expectedValue, Object itemValue) {
				if (expectedValue.equalsDeeply(itemValue, feature.ordered)) {
					return null
				} else {
					return mismatch
				}
			}

		}
	}

	private def mapObjects(EObject expected, EObject item) {
		objectMapping.put(expected, item)
		objectMapping.put(item, expected)
	}

	private def unmapObjects(EObject expected, EObject item) {
		objectMapping.remove(expected)
		objectMapping.remove(item)
	}

	// The comparison is done similarly to EMF's implementation of EcoreUtil.equals(EObject, EObject).
	// The difference is that we issue additional diagnostic messages if the object comparison fails at any point.
	def private dispatch boolean equalsDeeply(EObject expected, EObject item, boolean ordered) {
		// Null comparisons:
		if (expected === null) {
			if (item === null) {
				return true
			} else {
				equalityMismatch(expected, item)
				return false
			}
		}
		assertTrue(expected !== null)
		if (item === null) {
			equalityMismatch(expected, item)
			return false
		}

		// Check if the expected object has already been compared:
		val mappedItem = objectMapping.get(expected)
		if (mappedItem === item) {
			return true
		} else if (mappedItem !== null) {
			// The object has already been mapped to some other object.
			mismatch = [
				appendText("did not match the topologically expected object. Expected: ").appendModelValue(mappedItem).
					appendText(" (mapped and equal to ").appendModelValue(expected).appendText(") but found ").
					appendModelValue(item).appendText(".")
			]
			return false
		}

		// Check if the given item object has already been compared:
		val mappedExpected = objectMapping.get(item)
		if (mappedExpected === expected) {
			return true
		} else if (mappedExpected !== null) {
			// The object has already been mapped to some other object.
			mismatch = [
				appendText(
					"has already been compared and mapped to some other object in the expected model tree. Expected ").
					appendModelValue(expected).appendText(" but the object ").appendModelValue(item).appendText(
						" has already been mapped to ").appendModelValue(mappedExpected).appendText(".")
			]
			return false
		}

		// Check if objects are the same instance:
		if (expected === item) {
			mapObjects(expected, item)
			return true
		}

		// Compare proxies:
		if (expected.eIsProxy()) {
			// item has to be a proxy as well and their URIs need to match:
			if ((expected as InternalEObject).eProxyURI().equals((item as InternalEObject).eProxyURI())) {
				mapObjects(expected, item)
				return true
			} else {
				mismatch = [
					appendText("did not match the expected proxy. Expected ").appendModelValue(expected).appendText(
						" but found ").appendModelValue(item).appendText(".")
				]
				return false
			}
		} else if (item.eIsProxy) {
			mismatch = [
				appendText("is an unexpected proxy. Expected ").appendModelValue(expected).appendText(" but found ").
					appendModelValue(item).appendText(".")
			]
			return false
		}

		// Compare classes:
		if (expected.eClass !== item.eClass) {
			mismatch = [
				appendText("had the wrong EClass. Expected ").appendModelValue(expected.eClass.name).appendText(
					" but found ").appendModelValue(item.eClass.name).appendText(".")
			]
			return false
		}

		// Consider the objects to be equal for now. This helps with situations in which the below feature comparisons
		// recursively try to compare the same objects again.
		mapObjects(expected, item)

		// Compare feature values:
		for (feature : expected.eClass.EAllStructuralFeatures.filter[!derived]) {
			navigationStack.push('''.«feature.name»''')
			val matcherMismatch = findFeatureChecker(expected, feature).getMismatch(expected.eGet(feature),
				item.eGet(feature))
			if (matcherMismatch !== null) {
				mismatch = matcherMismatch
				unmapObjects(expected, item)
				return false;
			}
			navigationStack.pop()
		}
		return true;
	}

	def private dispatch boolean equalsDeeply(Collection<?> expected, Collection<?> item, boolean ordered) {
		if (expected.size() > item.size()) {
			mismatch = [
				appendText("did not contain enough elements. Expected ").appendValue(expected.size).appendText(
					" elements but found only ").appendValue(item.size).appendText(".")
			]
			return false;
		} else if (expected.size() < item.size()) {
			mismatch = [
				appendText("contained too many elements. Expected ").appendValue(expected.size).appendText(
					" elements but found ").appendValue(item.size).appendText(".")
			]
			return false;
		} else {
			var count = 0;
			val expectedIter = expected.iterator()
			val itemOrdered = if (ordered) item else item.iterator.toList
			var itemIter = itemOrdered.iterator()
			var usedItemIndeces = if (ordered) null else newBooleanArrayOfSize(itemOrdered.size)

			while (expectedIter.hasNext()) {
				navigationStack.push('''[«count»]''')
				val expectedElement = expectedIter.next
				val actualElement = itemIter.next
				// Capture the current navigation stack:
				val originalNavigationStack = new ArrayDeque(navigationStack)
				if (!expectedElement.equalsDeeply(actualElement, false)) {
					if (!ordered) {
						// If not ordered, retry with all elements not matched yet.
						if (!itemOrdered.containsDeepEqual(expectedElement, usedItemIndeces)) {
							val notFoundCount = count
							// Restore the original navigation stack:
							this.navigationStack = originalNavigationStack
							navigationStack.pop()
							mismatch = [
								appendText('''did not contain an element equal to the «notFoundCount». expected element ''').
									appendValue(expectedElement).appendText('.')
							]
							return false
						} else {
							// Restore the original navigation stack and continue:
							navigationStack = originalNavigationStack
						}
					} else {
						return false
					}
				} else if (!ordered) {
					usedItemIndeces.set(count, true)
				}
				navigationStack.pop()
				count++
			}
		}
		return true
	}

	def private containsDeepEqual(Collection<?> unordered, Object expected, boolean[] usedItemIndeces) {
		var itemCount = 0
		val itemIter = unordered.iterator()
		while (itemIter.hasNext()) {
			if (itemIter.next.equalsDeeply(expected, false) && !usedItemIndeces.get(itemCount)) {
				usedItemIndeces.set(itemCount, true)
				return true
			}
			itemCount++
		}
		return false
	}

	def private dispatch boolean equalsDeeply(Object expected, Object item, boolean ordered) {
		if (expected != item) {
			equalityMismatch(expected, item)
			return false
		}
		return true
	}

	def private dispatch boolean equalsDeeply(Void expected, Object item, boolean ordered) {
		equalityMismatch(expected, item)
		false
	}

	def private dispatch boolean equalsDeeply(Object expected, Void item, boolean ordered) {
		equalityMismatch(expected, item)
		false
	}

	def private dispatch boolean equalsDeeply(Void expected, Void item, boolean ordered) {
		true
	}

	def private equalityMismatch(Object expected, Object item) {
		mismatch = [
			appendText("had the wrong value. Expected ").appendModelValue(expected).appendText(" but found ").
				appendModelValue(item)
		]
	}

	override describeTo(Description description) {
		description.appendText('''a «expectedObject.eClass.name» deeply equal to ''').appendModelValue(expectedObject)
	}

	override describeMismatchSafely(EObject item, Description mismatchDescription) {
		if (navigationStack.isEmpty) {
			mismatchDescription.appendText('''The «item.eClass.name» ''')
		} else {
			mismatchDescription.appendText('''The value at object«navigationStack.descendingIterator.join» ''')
		}
		mismatch.accept(mismatchDescription)
	}
}

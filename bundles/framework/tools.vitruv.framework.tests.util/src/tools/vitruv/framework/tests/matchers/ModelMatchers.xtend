package tools.vitruv.framework.tests.matchers

import org.eclipse.emf.ecore.EObject
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import java.util.Collection
import java.util.Stack
import org.hamcrest.TypeSafeMatcher
import java.util.function.Consumer
import java.util.Map
import java.util.HashMap
import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collections
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EClass

@Utility
class ModelMatchers {
	def static Matcher<Resource> contains(EObject root, FeatureMatcher... featureMatchers) {
		new ResourceContainmentMatcher(root, featureMatchers)
	}
	
	def static Matcher<Resource> doesNotExist() {
		new ResourceInexistenceMatcher()
	}

	def static Matcher<EObject> equalsDeeply(EObject object, FeatureMatcher... featureMatchers) {
		new ModelTreeEqualityMatcher(object, featureMatchers)
	}
	
	def static ignoring(String featureName) {
		return new IgnoreNamedFeature(featureName)
	}
}

package class ResourceContainmentMatcher extends TypeSafeMatcher<Resource> {
	val Matcher<EObject> delegateMatcher
	val EObject expectedObject
	int contentsSize
	boolean exists

	package new(EObject expectedObject, FeatureMatcher... featureMatchers) {
		this.expectedObject = expectedObject
		delegateMatcher = new ModelTreeEqualityMatcher(expectedObject, featureMatchers)
	}

	override protected describeMismatchSafely(Resource item, Description mismatchDescription) {
		if (!exists) {
			mismatchDescription.appendText("there is no resource at ").appendValue(item.URI)
		} else if (contentsSize == 0) {
			mismatchDescription.appendText("the resource was empty.")
		} else if (contentsSize > 1) {
			mismatchDescription.appendText("the resource contained ").appendValue(contentsSize).appendText(
				" instead of just one content element.")
		} else {
			delegateMatcher.describeMismatch(expectedObject, mismatchDescription)
		}
	}

	override describeTo(Description description) {
		description.appendText("A resource containing the object tree rooted at ").appendValue(expectedObject);
	}

	override protected matchesSafely(Resource item) {
		exists = item.resourceSet.URIConverter.exists(item.URI, Collections.emptyMap)
		if (!exists) return false
		contentsSize = item.contents.size
		if (contentsSize != 1) return false
		return delegateMatcher.matches(item.contents.get(0))
	}

}


package class ResourceInexistenceMatcher extends TypeSafeMatcher<Resource> {
	boolean exists

	override protected describeMismatchSafely(Resource item, Description mismatchDescription) {
		mismatchDescription.appendText("there was a resource at ").appendValue(item.URI)
	}

	override describeTo(Description description) {
		description.appendText("the resource not to exist");
	}

	override protected matchesSafely(Resource item) {
		exists = item.resourceSet.URIConverter.exists(item.URI, Collections.emptyMap)
		return !exists
	}

}

@FinalFieldsConstructor
package class ModelTreeEqualityMatcher extends TypeSafeMatcher<EObject> {

	package val EObject expectedObject
	var Stack<String> navigationStack = new Stack
	var Consumer<Description> mismatch
	var Map<Object, Object> checkCache = new HashMap
	val FeatureMatcher[] featureMatchers

	override protected matchesSafely(EObject item) {
		return expectedObject.equalsDeeply(item, false)
	}
	
	def private findFeatureChecker(EClass eClass, EStructuralFeature feature) {
		for (featureChecker : featureMatchers) {
			if (featureChecker.isForFeature(eClass, feature)) {
				return featureChecker
			}
		}
		return new FeatureMatcher() {
			override isForFeature(EClass checkedClass, EStructuralFeature feature) {
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

	def private dispatch boolean equalsDeeply(EObject expected, EObject item, boolean ordered) {
		if (checkCache.get(item) == expected) {
			return true;
		}
		if (expected.eClass !== item.eClass) {
			mismatch = [
				appendText("had the wrong EClass. Expected ").appendValue(expected.eClass.name).appendText(
					" but found ").appendValue(item.eClass.name).appendText(".")
			]
			return false
		}
		for (feature : expected.eClass.EAllStructuralFeatures) {
			navigationStack.push('''.«feature.name»''')
			val matcherMismatch = findFeatureChecker(expected.eClass, feature).getMismatch(expected.eGet(feature), item.eGet(feature))
			if (matcherMismatch !== null) {
				mismatch = matcherMismatch
				return false;
			}
			navigationStack.pop()
		}
		checkCache.put(item, expected);
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
				if (!expectedElement.equalsDeeply(itemIter.next, false)) {
					if (!ordered) {
						// if not ordered, retry with all elements not matched yet.
						if (!itemOrdered.containsDeepEqual(expectedElement, usedItemIndeces)) {
							val notFoundCount = count
							navigationStack.pop()
							mismatch = [
								appendText('''did not contain an element equal to the «notFoundCount». element.''').
									appendValue(expectedElement)
							]
							return false
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
			appendText("had the wrong value. Expected ").appendValue(expected).appendText(" but found ").
				appendValue(item)
		]
	}

	override describeTo(Description description) {
		description.appendText("An EObject deeply equal to ").appendValue(expectedObject);
	}

	override protected describeMismatchSafely(EObject item, Description mismatchDescription) {
		if (navigationStack.isEmpty) {
			mismatchDescription.appendText("The EObject ")
		} else {
			mismatchDescription.appendText('''The element at object«navigationStack.join» ''')
		}
		mismatch.accept(mismatchDescription)
	}
}

class IgnoreNamedFeature implements FeatureMatcher {
	val String featureName

	package new(String featureName) {
		this.featureName = featureName
	}

	override isForFeature(EClass checkedClass, EStructuralFeature feature) {
		feature.name == featureName
	}

	override getMismatch(Object expectedValue, Object itemValue) {
		null
	}
}

package tools.vitruv.testutils.matchers

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collection
import java.util.Collections
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.Stack
import java.util.function.Consumer
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

import static tools.vitruv.framework.util.XtendAssertHelper.*

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
	
	def static ignoringAllExcept(String... featureNames) {
		return new IgnoreAllExceptNamedFeatures(featureNames)
	}
	
	// ignores features that are unset in the expected (!) object
	def static ignoringUnsetFeatures() {
		return new IgnoreUnsetFeatures()
	}

	def static ignoringFeaturesOfType(EClassifier featureType) {
		return new IgnoreTypedFeatures(featureType)
	}

	def static ignoringAllExceptFeaturesOfType(EClassifier... featureTypes) {
		return new IgnoreAllExceptTypedFeatures(featureTypes)
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
	// Bidirectional mapping between objects which have been compared and considered equal.
	// Note that each object can be considered equal to at most one other object. This ensures that two objects are
	// considered structurally equal only if the graphs formed by all their referenced objects have the same topology.
	// This is similar to EMF's implementation of EcoreUtil.equals(EObject, EObject).
	var Map<Object, Object> objectMapping = new HashMap
	val FeatureMatcher[] featureMatchers

	override protected matchesSafely(EObject item) {
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
				appendText("did not match the topologically expected object. Expected ").appendValue(mappedItem)
					.appendText(" (mapped and equal to ").appendValue(expected).appendText(") but found ")
					.appendValue(item).appendText(".")
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
				appendText("has already been compared and mapped to some other object in the expected model tree. Expected ")
					.appendValue(expected).appendText(" but the object ").appendValue(item)
					.appendText(" has already been mapped to ").appendValue(mappedExpected).appendText(".")
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
					appendText("did not match the expected proxy. Expected ").appendValue(expected)
						.appendText(" but found ").appendValue(item).appendText(".")
				]
				return false
			}
		} else if (item.eIsProxy) {
			mismatch = [
				appendText("is an unexpected proxy. Expected ").appendValue(expected).appendText(" but found ")
					.appendValue(item).appendText(".")
			]
			return false
		}

		// Compare classes:
		if (expected.eClass !== item.eClass) {
			mismatch = [
				appendText("had the wrong EClass. Expected ").appendValue(expected.eClass.name).appendText(
					" but found ").appendValue(item.eClass.name).appendText(".")
			]
			return false
		}

		// Consider the objects to be equal for now. This helps with situations in which the below feature comparisons
		// recursively try to compare the same objects again.
		mapObjects(expected, item)

		// Compare feature values:
		for (feature : expected.eClass.EAllStructuralFeatures.filter[!derived]) {
			navigationStack.push('''.«feature.name»''')
			val matcherMismatch = findFeatureChecker(expected, feature).getMismatch(expected.eGet(feature), item.eGet(feature))
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

	override isForFeature(EObject expectedObject, EStructuralFeature feature) {
		feature.name == featureName
	}

	override getMismatch(Object expectedValue, Object itemValue) {
		null
	}
}

class IgnoreAllExceptNamedFeatures implements FeatureMatcher {
	val List<String> featureNames

	package new(String... featureNames) {
		this.featureNames = featureNames
	}

	override isForFeature(EObject expectedObject, EStructuralFeature feature) {
		!featureNames.contains(feature.name)
	}

	override getMismatch(Object expectedValue, Object itemValue) {
		null
	}
}

class IgnoreUnsetFeatures implements FeatureMatcher {

	package new() {
	}

	override isForFeature(EObject expectedObject, EStructuralFeature feature) {
		!expectedObject.eIsSet(feature)
	}

	override getMismatch(Object expectedValue, Object itemValue) {
		null
	}
}

class IgnoreTypedFeatures implements FeatureMatcher {
	val EClassifier featureType

	package new(EClassifier featureType) {
		this.featureType = featureType
	}

	override isForFeature(EObject expectedObject, EStructuralFeature feature) {
		feature.EType == featureType
	}

	override getMismatch(Object expectedValue, Object itemValue) {
		null
	}
}

class IgnoreAllExceptTypedFeatures implements FeatureMatcher {
	val List<EClassifier> featureTypes

	package new(EClassifier... featureTypes) {
		this.featureTypes = featureTypes
	}

	override isForFeature(EObject expectedObject, EStructuralFeature feature) {
		!featureTypes.contains(feature.EType)
	}

	override getMismatch(Object expectedValue, Object itemValue) {
		null
	}
}

package tools.vitruv.testutils.matchers

import edu.kit.ipd.sdq.activextendannotations.Utility
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil
import java.util.ArrayDeque
import java.util.Collection
import java.util.Deque
import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.function.Consumer
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

import static com.google.common.base.Preconditions.checkArgument
import static java.util.Collections.emptyMap
import static tools.vitruv.framework.util.XtendAssertHelper.*

import static extension tools.vitruv.testutils.matchers.ModelPrinter.*
import java.util.Set
import java.util.HashSet

@Utility
class ModelMatchers {
	def static Matcher<? super Resource> containsModelOf(Resource expected, FeatureMatcher... featureMatchers) {
		checkArgument(expected.contents.size > 0, 'The resource to compare with must contain a root element!')
		checkArgument(expected.contents.size < 2,
			'The resource to compare with must contain only one root element!')

		contains(expected.contents.get(0))
	}

	def static Matcher<? super Resource> contains(EObject root, FeatureMatcher... featureMatchers) {
		contains(equalsDeeply(root, featureMatchers))
	}

	def static Matcher<? super Resource> contains(Matcher<? super EObject> rootMatcher) {
		new ResourceContainmentMatcher(rootMatcher)
	}

	def static Matcher<? super URI> isResource() {
		new ResourceExistingMatcher(true)
	}
	
	def static Matcher<? super URI> isNoResource() {
		new ResourceExistingMatcher(false)
	}
	
	def static Matcher<? super Resource> exists() {
		new ResourceExistenceMatcher(true)
	}

	def static Matcher<? super Resource> doesNotExist() {
		new ResourceExistenceMatcher(false)
	}

	def static Matcher<? super EObject> isContainedIn(Resource resource) {
		new EObjectResourceMatcher(resource)
	}

	def static Matcher<? super EObject> equalsDeeply(EObject object, FeatureMatcher... featureMatchers) {
		new ModelTreeEqualityMatcher(object, featureMatchers)
	}

	def static FeatureMatcher ignoringFeatures(String... featureNames) {
		return new IgnoreNamedFeatures(new HashSet(featureNames))
	}

	def static FeatureMatcher ignoringAllFeaturesExcept(String... featureNames) {
		return new IgnoreAllExceptNamedFeatures(new HashSet(featureNames))
	}

	/**
	 * ignores features that are unset in the expected (!) object
	 */
	def static FeatureMatcher ignoringUnsetFeatures() {
		return new IgnoreUnsetFeatures()
	}

	def static FeatureMatcher ignoringFeaturesOfType(EClassifier featureType) {
		return new IgnoreTypedFeatures(featureType)
	}

	def static FeatureMatcher ignoringAllExceptFeaturesOfType(EClassifier... featureTypes) {
		return new IgnoreAllExceptTypedFeatures(featureTypes)
	}
}

@FinalFieldsConstructor
package class ResourceExistingMatcher extends TypeSafeMatcher<URI> {
	val boolean shouldExist

	override matchesSafely(URI item) {
		URIUtil.existsResourceAtUri(item) == shouldExist
	}

	override describeTo(Description description) {
		description.appendText(
			if (shouldExist) {
				"an URI pointing to an existing resource"
			} else {
				"an URI not pointing to any resource"
			}
		)
	}

	override describeMismatchSafely(URI item, Description mismatchDescription) {
		val qualifier = if (shouldExist) "no" else "a"
		mismatchDescription.appendText('''there was «qualifier» resource at ''').appendValue(item)
	}
}

@FinalFieldsConstructor
package class ResourceContainmentMatcher extends TypeSafeMatcher<Resource> {
	val Matcher<? super EObject> delegateMatcher
	boolean exists

	override describeMismatchSafely(Resource item, Description mismatchDescription) {
		if (!exists) {
			mismatchDescription.appendText("there is no resource at ").appendValue(item.URI)
		} else if (item.contents.isEmpty) {
			mismatchDescription.appendResourceValue(item).appendText(" was empty.")
		} else if (item.contents.size > 1) {
			mismatchDescription.appendResourceValue(item).appendText(" contained ").appendValue(item.contents.size).
				appendText(" instead of just one content element.")
		} else {
			delegateMatcher.describeMismatch(item.contents.get(0), mismatchDescription)
		}
	}

	override describeTo(Description description) {
		description.appendText("a resource containing ").appendDescriptionOf(delegateMatcher)
	}

	override matchesSafely(Resource item) {
		exists = item.resourceSet.URIConverter.exists(item.URI, emptyMap)
		return exists && item.contents.size == 1 && delegateMatcher.matches(item.contents.get(0))
	}

}

@FinalFieldsConstructor
package class ResourceExistenceMatcher extends TypeSafeMatcher<Resource> {
	val boolean shouldExist

	override describeTo(Description description) {
		val qualifier = if (shouldExist) '' else 'not'
		description.appendText('''the resource «qualifier» to exist''');
	}

	override matchesSafely(Resource item) {
		item.resourceSet.URIConverter.exists(item.URI, emptyMap) == shouldExist
	}

	override describeMismatchSafely(Resource item, Description mismatchDescription) {
		val qualifier = if (shouldExist) 'no' else 'a'
		mismatchDescription.appendText('''there was «qualifier» resource at ''').appendValue(item.URI)
	}
}

@FinalFieldsConstructor
package class EObjectResourceMatcher extends TypeSafeMatcher<EObject> {
	val Resource expectedResource

	override matchesSafely(EObject item) {
		item.eResource == expectedResource
	}

	override describeTo(Description description) {
		description.appendText('''an EObject that is contained in ''').appendResourceValue(expectedResource)
	}

	override describeMismatchSafely(EObject item, Description mismatchDescription) {
		mismatchDescription.appendEObjectValue(item)
		val actualResource = item.eResource
		if (actualResource === null) {
			mismatchDescription.appendText('is not in any resource')
		} else {
			mismatchDescription.appendText('is in ').appendResourceValue(actualResource)
		}
	}
}

@FinalFieldsConstructor
package class ModelTreeEqualityMatcher extends TypeSafeMatcher<EObject> {
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
				appendText("did not match the topologically expected object. Expected ").appendPrettyValue(mappedItem).
					appendText(" (mapped and equal to ").appendPrettyValue(expected).appendText(") but found ").
					appendPrettyValue(item).appendText(".")
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
					appendPrettyValue(expected).appendText(" but the object ").appendPrettyValue(item).appendText(
						" has already been mapped to ").appendPrettyValue(mappedExpected).appendText(".")
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
					appendText("did not match the expected proxy. Expected ").appendPrettyValue(expected).appendText(
						" but found ").appendPrettyValue(item).appendText(".")
				]
				return false
			}
		} else if (item.eIsProxy) {
			mismatch = [
				appendText("is an unexpected proxy. Expected ").appendPrettyValue(expected).appendText(" but found ").
					appendPrettyValue(item).appendText(".")
			]
			return false
		}

		// Compare classes:
		if (expected.eClass !== item.eClass) {
			mismatch = [
				appendText("had the wrong EClass. Expected ").appendPrettyValue(expected.eClass.name).appendText(
					" but found ").appendPrettyValue(item.eClass.name).appendText(".")
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
			appendText("had the wrong value. Expected ").appendPrettyValue(expected).appendText(" but found ").
				appendPrettyValue(item)
		]
	}

	override describeTo(Description description) {
		description.appendText('''a «expectedObject.eClass.name» deeply equal to ''').appendEObjectValue(expectedObject)
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

@FinalFieldsConstructor
package class IgnoreNamedFeatures implements FeatureMatcher {
	val Set<String> featureNames

	override isForFeature(EObject expectedObject, EStructuralFeature feature) {
		featureNames.contains(feature.name)
	}

	override getMismatch(Object expectedValue, Object itemValue) {
		null
	}
}

@FinalFieldsConstructor
package class IgnoreAllExceptNamedFeatures implements FeatureMatcher {
	val Set<String> featureNames

	override isForFeature(EObject expectedObject, EStructuralFeature feature) {
		!featureNames.contains(feature.name)
	}

	override getMismatch(Object expectedValue, Object itemValue) {
		null
	}
}

package class IgnoreUnsetFeatures implements FeatureMatcher {
	override isForFeature(EObject expectedObject, EStructuralFeature feature) {
		!expectedObject.eIsSet(feature)
	}

	override getMismatch(Object expectedValue, Object itemValue) {
		null
	}
}

@FinalFieldsConstructor
package class IgnoreTypedFeatures implements FeatureMatcher {
	val EClassifier featureType

	override isForFeature(EObject expectedObject, EStructuralFeature feature) {
		feature.EType == featureType
	}

	override getMismatch(Object expectedValue, Object itemValue) {
		null
	}
}

package class IgnoreAllExceptTypedFeatures implements FeatureMatcher {
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

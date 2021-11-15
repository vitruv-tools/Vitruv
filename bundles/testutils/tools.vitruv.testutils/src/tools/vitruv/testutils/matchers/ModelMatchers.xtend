package tools.vitruv.testutils.matchers

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Set
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.hamcrest.Matcher

import static com.google.common.base.Preconditions.checkArgument

@Utility
class ModelMatchers {
	def static Matcher<? super Resource> containsModelOf(Resource expected, ModelDeepEqualityOption... options) {
		contains(checkResourceContent(expected), options)
	}

	def private static EObject checkResourceContent(Resource resource) {
		checkArgument(
			resource.contents.size > 0,
			'The resource to compare with must contain a root element, but was empty: ' + resource.URI
		)
		checkArgument(
			resource.contents.size < 2,
			'''The resource to compare with must contain only one root element, but contained «resource.contents.size»: «resource.URI»'''
		)
		resource.contents.get(0)
	}

	def static Matcher<? super Resource> contains(EObject root, ModelDeepEqualityOption... options) {
		contains(ModelMatchers.equalsDeeply(root, options))
	}

	def static Matcher<? super Resource> contains(Matcher<? super EObject> rootMatcher) {
		new ResourceContainmentMatcher(rootMatcher)
	}

	/**Checks if for all items from {@paramref searchedItems} a similar one is included in the given list.
	 * Formally: assertThat(L1, containsAllOf(L2)) == \forall(x in L2): \exists(y in L1): equals(x,y) 
	 * @param searchedItems items, of which all should be contained.
	 * @param options ... 
	 */
	def static Matcher<? super Iterable<? extends EObject>> containsAllOf(Iterable<? extends EObject> searchedItems,
		ModelDeepEqualityOption... options) {
		new EListMultipleContainmentMatcher(searchedItems, true, options)
	}
	/**Checks if for all items from {@paramref searchedItems} no similar one is included in the given list.
	 * Formally: assertThat(L1, containsNoneOf(L2)) == \forall(x in L2): \not \exists(y in L1): equals(x,y) 
	 * @param searchedItems items, of which none should be contained.
	 * @param options ... 
	 */
	def static Matcher<? super Iterable<? extends EObject>> containsNoneOf(Iterable<? extends EObject> searchedItems,
		ModelDeepEqualityOption... options) {
		new EListMultipleContainmentMatcher(searchedItems, false, options)
	}	
	/**Checks if for the item {@paramref searchedItem} a similar one is included in the given list.
	 * Formally: assertThat(L1, listContains(e)) == \exists(y in L1): equals(e,y) 
	 * @param searchedItem item, which should be contained.
	 * @param options ... 
	 */
	def static Matcher<? extends Iterable<? extends EObject>> listContains(EObject searchedItem, ModelDeepEqualityOption... options) {
		new EListSingleContainmentMatcher(searchedItem, true, options)
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

	/**
	 * A highly configurable matcher for comparing EObjects with detailed difference printing.
	 */
	def static <O extends EObject> Matcher<? super O> equalsDeeply(O object, ModelDeepEqualityOption... options) {
		new ModelDeepEqualityMatcher(object, options)
	}

	def static EqualityFeatureFilter ignoringFeatures(EStructuralFeature... features) {
		new IgnoreFeatures(Set.of(features))
	}

	def static EqualityFeatureFilter ignoringAllFeaturesExcept(EStructuralFeature... features) {
		new IncludeOnlyFeatures(Set.of(features))
	}
	
	def static EqualityFeatureFilter ignoringFeatures(String... featureNames) {
		new IgnoreNamedFeatures(Set.of(featureNames))
	}

	def static EqualityFeatureFilter ignoringAllFeaturesExcept(String... featureNames) {
		new IgnoreAllExceptNamedFeatures(Set.of(featureNames))
	}

	def static EqualityFeatureFilter ignoringFeaturesOfType(EClassifier... featureTypes) {
		new IgnoreTypedFeatures(Set.of(featureTypes))
	}

	def static EqualityFeatureFilter ignoringAllExceptFeaturesOfType(EClassifier... featureTypes) {
		new IgnoreAllExceptTypedFeatures(Set.of(featureTypes))
	}
	
	/**
	 * Uses {@link Object#equals} to compare instances of the provided {@code eClasses} if they are referenced in
	 * <em>non-containment</em> references.
	 */
	def static EqualityStrategy usingEqualsForReferencesTo(EClass... eClasses) {
		new EqualsBasedEqualityStrategy(Set.of(eClasses))
	}

	def static Matcher<? super EObject> whose(EStructuralFeature feature, Matcher<?> featureMatcher) {
		new EObjectFeatureMatcher(feature, featureMatcher)
	}

	def static Matcher<? super Object> isInstanceOf(EClassifier classifier) {
		new InstanceOfEClassifierMatcher(classifier)
	}
	
	def static Matcher<? super Resource> hasNoErrors() {
		new ResourceHasNoErrorsMatcher
	}
}

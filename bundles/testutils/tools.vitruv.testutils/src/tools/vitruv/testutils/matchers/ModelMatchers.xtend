package tools.vitruv.testutils.matchers

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.hamcrest.Matcher
import static com.google.common.base.Preconditions.checkArgument
import java.util.Set
import tools.vitruv.testutils.matchers.ModelDeepEqualityOption.EqualityFeatureFilter
import org.eclipse.emf.ecore.EClass
import tools.vitruv.testutils.matchers.ModelDeepEqualityOption.EqualityStrategy

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
		contains(equalsDeeply(root, options))
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

	/**
	 * A highly configurable matcher for comparing EObjects with detailed difference printing.
	 */
	def static Matcher<? super EObject> equalsDeeply(EObject object, ModelDeepEqualityOption... options) {
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
	 * Limits an {@link EqualityFeatureFilter} to the provided {@code types}.
	 */
	def static EqualityFeatureFilter on(EqualityFeatureFilter target, Class<? extends EObject>... types) {
		new TypeIncludingFeatureFilter(Set.of(types), target)
	}

	/**
	 * Creates an exception for an {@link EqualityFeatureFilter} by making it not apply to the provided {@code types}.
	 */
	def static EqualityFeatureFilter unlessOn(EqualityFeatureFilter target, Class<? extends EObject>... types) {
		new TypeExcludingFeatureFilter(Set.of(types), target)
	}
	
	/**
	 * Ignores all features that are unset on the reference object.
	 */
	def static EqualityFeatureFilter ignoringUnsetFeatures() {
		new IgnoreUnsetFeaturesFilter
	}
	
	/**
	 * Uses {@link Object#equals} to instances of the provided {@code eClasses}.
	 */
	def static EqualityStrategy usingEqualsFor(EClass... eClasses) {
		new EqualsStrategy(Set.of(eClasses))
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

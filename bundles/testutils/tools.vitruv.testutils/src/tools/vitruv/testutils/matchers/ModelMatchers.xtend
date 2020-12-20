package tools.vitruv.testutils.matchers

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.resource.Resource
import org.hamcrest.Matcher
import static com.google.common.base.Preconditions.checkArgument
import java.util.HashSet

@Utility
class ModelMatchers {
	def static Matcher<? super Resource> containsModelOf(Resource expected, FeatureMatcher... featureMatchers) {
		checkArgument(
			expected.contents.size > 0,
			'The resource to compare with must contain a root element, but was empty: ' + expected.URI
		)
		checkArgument(
			expected.contents.size < 2,
			'''The resource to compare with must contain only one root element, but contained «expected.contents.size»: «expected.URI»'''
		)

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
		new ModelDeepEqualityMatcher(object, featureMatchers)
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

	def static Matcher<? super EObject> whose(EStructuralFeature feature, Matcher<?> featureMatcher) {
		return new EObjectFeatureMatcher(feature, featureMatcher)
	}

	def static Matcher<? super Object> isInstanceOf(EClassifier classifier) {
		return new InstanceOfEClassifierMatcher(classifier)
	}
}

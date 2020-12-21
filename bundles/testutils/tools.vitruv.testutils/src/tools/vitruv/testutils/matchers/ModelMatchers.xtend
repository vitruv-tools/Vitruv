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

@Utility
class ModelMatchers {
	def static Matcher<? super Resource> containsModelOf(Resource expected, DescribedFeatureFilter... featureFilters) {
		checkArgument(
			expected.contents.size > 0,
			'The resource to compare with must contain a root element, but was empty: ' + expected.URI
		)
		checkArgument(
			expected.contents.size < 2,
			'''The resource to compare with must contain only one root element, but contained «expected.contents.size»: «expected.URI»'''
		)

		contains(expected.contents.get(0), featureFilters)
	}

	def static Matcher<? super Resource> contains(EObject root, DescribedFeatureFilter... featureFilters) {
		contains(equalsDeeply(root, featureFilters))
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

	def static Matcher<? super EObject> equalsDeeply(EObject object, DescribedFeatureFilter... featureMatchers) {
		new ModelDeepEqualityMatcher(object, featureMatchers)
	}

	def static DescribedFeatureFilter ignoringFeatures(String... featureNames) {
		return new IgnoreNamedFeatures(Set.of(featureNames))
	}

	def static DescribedFeatureFilter ignoringAllFeaturesExcept(String... featureNames) {
		return new IgnoreAllExceptNamedFeatures(Set.of(featureNames))
	}

	def static DescribedFeatureFilter ignoringFeaturesOfType(EClassifier... featureTypes) {
		return new IgnoreTypedFeatures(Set.of(featureTypes))
	}

	def static DescribedFeatureFilter ignoringAllExceptFeaturesOfType(EClassifier... featureTypes) {
		return new IgnoreAllExceptTypedFeatures(Set.of(featureTypes))
	}

	def static Matcher<? super EObject> whose(EStructuralFeature feature, Matcher<?> featureMatcher) {
		return new EObjectFeatureMatcher(feature, featureMatcher)
	}

	def static Matcher<? super Object> isInstanceOf(EClassifier classifier) {
		return new InstanceOfEClassifierMatcher(classifier)
	}
}

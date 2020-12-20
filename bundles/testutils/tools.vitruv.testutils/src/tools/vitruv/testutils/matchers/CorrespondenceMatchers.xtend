package tools.vitruv.testutils.matchers

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.hamcrest.Matcher

@Utility
class CorrespondenceMatchers {
	def static Matcher<? super EObject> hasNoCorrespondences(CorrespondenceModelContainer container,
		CorrespondenceOption... options) {
		new NoCorrespondenceMatcher(new CorrespondenceSource(container.correspondenceModel, options))
	}

	def static Matcher<? super EObject> hasCorrespondence(CorrespondenceModelContainer container,
		List<CorrespondenceOption> options, Matcher<? super EObject> correspondenceMatcher) {
		new HasAtLeastOneCorrespondenceMatcher(new CorrespondenceSource(container.correspondenceModel, options),
			correspondenceMatcher)
	}

	def static Matcher<? super EObject> hasCorrespondence(CorrespondenceModelContainer container,
		Matcher<? super EObject> correspondenceMatcher) {
		hasCorrespondence(container, #[], correspondenceMatcher)
	}

	def static Matcher<? super EObject> hasCorrespondence(CorrespondenceModelContainer container,
		CorrespondenceOption option, Matcher<? super EObject> correspondenceMatcher) {
		hasCorrespondence(container, #[option], correspondenceMatcher)
	}

	def static Matcher<? super EObject> hasCorrespondence(CorrespondenceModelContainer container,
		CorrespondenceOption firstOption, CorrespondenceOption secondOption,
		Matcher<? super EObject> correspondenceMatcher) {
		hasCorrespondence(container, #[firstOption, secondOption], correspondenceMatcher)
	}

	def static Matcher<? super EObject> hasCorrespondence(CorrespondenceModelContainer container,
		CorrespondenceOption... options) {
		hasCorrespondence(container, options, null)
	}

	def static Matcher<? super EObject> hasOneCorrespondence(CorrespondenceModelContainer container,
		List<CorrespondenceOption> options, Matcher<? super EObject> correspondenceMatcher) {
		new HasExactlyOneCorrespondenceMatcher(new CorrespondenceSource(container.correspondenceModel, options),
			correspondenceMatcher)
	}

	def static Matcher<? super EObject> hasOneCorrespondence(CorrespondenceModelContainer container,
		Matcher<? super EObject> correspondenceMatcher) {
		hasOneCorrespondence(container, #[], correspondenceMatcher)
	}

	def static Matcher<? super EObject> hasOneCorrespondence(CorrespondenceModelContainer container,
		CorrespondenceOption option, Matcher<? super EObject> correspondenceMatcher) {
		hasOneCorrespondence(container, #[option], correspondenceMatcher)
	}

	def static Matcher<? super EObject> hasOneCorrespondence(CorrespondenceModelContainer container,
		CorrespondenceOption firstOption, CorrespondenceOption secondOption,
		Matcher<? super EObject> correspondenceMatcher) {
		hasOneCorrespondence(container, #[firstOption, secondOption], correspondenceMatcher)
	}

	def static Matcher<? super EObject> hasOneCorrespondence(CorrespondenceModelContainer container,
		CorrespondenceOption... options) {
		hasOneCorrespondence(container, options, null)
	}

	def static CorrespondenceOption ofType(Class<? extends EObject> expectedType) {
		new TypeOption(expectedType)
	}

	def static CorrespondenceOption taggedWith(String tag) {
		new TagOption(tag)
	}
}

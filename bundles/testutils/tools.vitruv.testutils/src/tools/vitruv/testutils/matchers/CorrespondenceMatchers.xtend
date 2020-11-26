package tools.vitruv.testutils.matchers

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.Description
import org.hamcrest.Matcher

import static org.hamcrest.CoreMatchers.hasItem
import tools.vitruv.framework.correspondence.CorrespondenceModelView
import tools.vitruv.framework.correspondence.Correspondence
import java.util.function.BiFunction
import org.hamcrest.SelfDescribing
import org.hamcrest.TypeSafeMatcher
import static extension tools.vitruv.testutils.matchers.ModelPrinter.appendPrettyValueSet

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

interface CorrespondenceOption {
	def Iterable<? extends Correspondence> filterCorrespondences(Iterable<? extends Correspondence> results)

	def Iterable<? extends EObject> filterResultObjects(Iterable<? extends EObject> results)

	def void describeTo(Description description)

	def static filterBy(Iterable<? extends Correspondence> results, Iterable<? extends CorrespondenceOption> options) {
		options.fold(results)[previousResults, option|option.filterCorrespondences(previousResults)]
	}
}

@FinalFieldsConstructor
package class CorrespondenceSource implements SelfDescribing {
	val CorrespondenceModelView<?> correspondenceModel
	val List<CorrespondenceOption> options

	def package getCorrespespondingObjects(EObject item) {
		correspondenceModel.getCorrespondences(#[item]).filterWithOptions [
			$0.filterCorrespondences($1)
		].map [
			correspondenceModel.getCorrespondingEObjectsInCorrespondence(it, #[item])
		].flatten.filterWithOptions[$0.filterResultObjects($1)]
	}

	override describeTo(Description description) {
		options.forEach[describeTo(description)]
	}

	def private <T> Iterable<? extends T> filterWithOptions(Iterable<? extends T> elements,
		BiFunction<CorrespondenceOption, Iterable<? extends T>, Iterable<? extends T>> filterFunction) {
		options.fold(elements)[lastElements, option|filterFunction.apply(option, lastElements)]
	}
}

@FinalFieldsConstructor
package class TypeOption implements CorrespondenceOption {
	val Class<? extends EObject> expectedType

	override filterCorrespondences(Iterable<? extends Correspondence> results) {
		results
	}

	override filterResultObjects(Iterable<? extends EObject> results) {
		results.filter(expectedType)
	}

	override describeTo(Description description) {
		description.appendText(" of type ").appendText('''<«expectedType.name»>''')
	}
}

@FinalFieldsConstructor
package class TagOption implements CorrespondenceOption {
	val String expectedTag

	override filterCorrespondences(Iterable<? extends Correspondence> results) {
		results.filter[tag == expectedTag]
	}

	override filterResultObjects(Iterable<? extends EObject> results) {
		results
	}

	override describeTo(Description description) {
		description.appendText(" tagged with ").appendValue(expectedTag)
	}
}

@FinalFieldsConstructor
package class NoCorrespondenceMatcher extends TypeSafeMatcher<EObject> {
	val CorrespondenceSource correspondenceSource
	var List<? extends EObject> correspondences

	override matchesSafely(EObject item) {
		correspondences = correspondenceSource.getCorrespespondingObjects(item).toList
		return correspondences.isEmpty
	}

	override describeMismatchSafely(EObject item, Description mismatchDescription) {
		mismatchDescription.appendText("found correspondences: ").appendPrettyValueSet(correspondences)
	}

	override describeTo(Description description) {
		description.appendText("an object with no correspondences").appendDescriptionOf(correspondenceSource)
	}
}

@FinalFieldsConstructor
package class HasExactlyOneCorrespondenceMatcher extends TypeSafeMatcher<EObject> {
	val CorrespondenceSource correspondenceSource
	val Matcher<? super EObject> correspondenceMatcher
	var List<? extends EObject> correspondences = null

	override matchesSafely(EObject item) {
		correspondences = correspondenceSource.getCorrespespondingObjects(item).toList
		correspondences.size == 1 &&
			(correspondenceMatcher === null || correspondenceMatcher.matches(correspondences.get(0)))
	}

	override describeMismatchSafely(EObject item, Description mismatchDescription) {
		if (correspondences.isEmpty) {
			mismatchDescription.appendText("found no such correspondences")
		} else if (correspondences.size > 1) {
			mismatchDescription.appendText("found more than one such correspondence: ").
				appendPrettyValueSet(correspondences)
		} else {
			correspondenceMatcher.describeMismatch(correspondences.get(0), mismatchDescription)
		}
	}

	override describeTo(Description description) {
		description.appendText("exactly one corresponding object").appendDescriptionOf(correspondenceSource)
		if (correspondenceMatcher !== null) {
			description.appendText(" that is: ").appendDescriptionOf(correspondenceMatcher)
		}
	}
}

@FinalFieldsConstructor
package class HasAtLeastOneCorrespondenceMatcher extends TypeSafeMatcher<EObject> {
	val CorrespondenceSource correspondenceSource
	val Matcher<? super EObject> correspondenceMatcher
	var Matcher<Iterable<? super EObject>> anyMatcher = null
	var List<? extends Object> correspondences = null

	override matchesSafely(EObject item) {
		correspondences = correspondenceSource.getCorrespespondingObjects(item).toList
		if (correspondences.isEmpty) {
			return false
		}
		if (correspondenceMatcher !== null) {
			anyMatcher = hasItem(correspondenceMatcher)
			return anyMatcher.matches(correspondences)
		}
		return true
	}

	override describeMismatchSafely(EObject item, Description mismatchDescription) {
		if (correspondences.isEmpty) {
			mismatchDescription.appendText("found no such correspondences")
		} else {
			anyMatcher.describeMismatch(correspondences, mismatchDescription)
		}
	}

	override describeTo(Description description) {
		description.appendText("at least one corresponding object").appendDescriptionOf(correspondenceSource)
		if (correspondenceMatcher !== null) {
			description.appendText(" that is: ").appendDescriptionOf(correspondenceMatcher)
		}
	}
}

package tools.vitruv.testutils.matchers

import java.util.HashSet
import java.util.Iterator
import java.util.List
import java.util.Set
import java.util.regex.Pattern
import org.eclipse.emf.compare.AttributeChange
import org.eclipse.emf.compare.Comparison
import org.eclipse.emf.compare.Diff
import org.eclipse.emf.compare.DifferenceKind
import org.eclipse.emf.compare.EMFCompare
import org.eclipse.emf.compare.Match
import org.eclipse.emf.compare.ReferenceChange
import org.eclipse.emf.compare.diff.DefaultDiffEngine
import org.eclipse.emf.compare.diff.DiffBuilder
import org.eclipse.emf.compare.diff.FeatureFilter
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl
import org.eclipse.emf.compare.rcp.EMFCompareRCPPlugin
import org.eclipse.emf.compare.scope.DefaultComparisonScope
import org.eclipse.emf.compare.utils.UseIdentifiers
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import tools.vitruv.testutils.printing.HamcrestDescriptionPrintTarget
import tools.vitruv.testutils.printing.PrintIdProvider
import tools.vitruv.testutils.printing.PrintResult
import tools.vitruv.testutils.printing.PrintTarget

import static tools.vitruv.testutils.matchers.MatcherUtil.a
import static tools.vitruv.testutils.printing.PrintMode.*
import static java.lang.Integer.MAX_VALUE

import static extension tools.vitruv.testutils.printing.ModelPrinting.*
import static extension tools.vitruv.testutils.printing.PrintResultExtension.*
import org.eclipse.emf.compare.postprocessor.IPostProcessor
import org.eclipse.emf.common.util.Monitor
import org.eclipse.emf.compare.postprocessor.BasicPostProcessorDescriptorImpl
import tools.vitruv.testutils.printing.ModelPrinting
import tools.vitruv.testutils.printing.ModelPrinter

@FinalFieldsConstructor
package class ModelDeepEqualityMatcher extends TypeSafeMatcher<EObject> {
	package val EObject expectedObject
	val List<EqualityFeatureFilter> featureFilters
	var Comparison comparison
	val idProvider = new PrintIdProvider

	override matchesSafely(EObject item) {
		comparison = buildEmfCompare(item).compare(new DefaultComparisonScope(item, expectedObject, null))
		return comparison.differences.isEmpty
	}

	override describeTo(Description description) {
		description.appendText(a(expectedObject.eClass.name)).appendText(' deeply equal to ').
			appendModelValue(expectedObject, idProvider)
	}

	override describeMismatchSafely(EObject item, Description mismatchDescription) {
		comparison.getMatch(expectedObject)
		mismatchDescription.appendText('found the following differences: ')
		new ComparisonPrinter(idProvider, comparison, emfCompareFeatureFilter, ModelPrinting.printer) //
		.printDifferenceRecursively(new HamcrestDescriptionPrintTarget(mismatchDescription), expectedObject)
		mismatchDescription.appendText('    for object ').appendModelValue(item, idProvider)
		if (!featureFilters.isEmpty) {
			mismatchDescription.appendText(System.lineSeparator).appendText(System.lineSeparator) //
			.appendText('    (the comparison ').appendText(featureFilters.join('; ')[describe()]).appendText(')')
		}
	}

	private def buildEmfCompare(EObject item) {
		(EMFCompare.builder => [
			matchEngineFactoryRegistry = EMFCompareRCPPlugin.getDefault.matchEngineFactoryRegistry => [
				// identifiers are often wrong or irrelevant in tests. Content-based matching yield better results
				// in these cases.
				add(new MatchEngineFactoryImpl(UseIdentifiers.NEVER) => [
					ranking = MAX_VALUE
				])
			]
			diffEngine = new DefaultDiffEngine(new DiffBuilder) {
				override createFeatureFilter() { emfCompareFeatureFilter }
			}
			postProcessorRegistry = EMFCompareRCPPlugin.getDefault.postProcessorRegistry => [
				put(
					AlwaysMatchRoots.name,
					new BasicPostProcessorDescriptorImpl(new AlwaysMatchRoots(expectedObject, item),
						Pattern.compile('.*'), null)
				)
			]
		]).build()
	}

	/**
	 * Differences become difficult to read if EMF Compare does to match the root objects together. Some differences
	 * can even not be recovered. Hence, we enforce to match the root objects.
	 */
	@FinalFieldsConstructor
	private static class AlwaysMatchRoots implements IPostProcessor {
		val EObject expectedObject
		val EObject item

		override postComparison(Comparison comparison, Monitor monitor) {}

		override postConflicts(Comparison comparison, Monitor monitor) {}

		override postDiff(Comparison comparison, Monitor monitor) {}

		override postEquivalences(Comparison comparison, Monitor monitor) {}

		override postMatch(Comparison comparison, Monitor monitor) {
			val expectedMatch = comparison.getMatch(expectedObject)
			val itemMatch = comparison.getMatch(item)
			if (expectedMatch != itemMatch) {
				comparison.matches -= itemMatch
				expectedMatch.left = item
			}
		}

		override postRequirements(Comparison comparison, Monitor monitor) {}
	}

	private def getEmfCompareFeatureFilter() {
		new FeatureFilter() {
			override getAttributesToCheck(Match match) {
				filter(match, super.getAttributesToCheck(match))
			}

			override getReferencesToCheck(Match match) {
				filter(match, super.getReferencesToCheck(match))
			}

			private def <T extends EStructuralFeature> filter(Match match, Iterator<T> iterator) {
				if (featureFilters.isEmpty)
					iterator
				else {
					val object = match.right ?: match.left
					iterator.toIterable.filter [ feature |
						!featureFilters.exists[!includeFeature(object, feature)]
					].iterator()
				}
			}
		}
	}

	@FinalFieldsConstructor
	private static class ComparisonPrinter {
		val PrintIdProvider idProvider
		val Comparison comparison
		val FeatureFilter featureFilter
		extension val ModelPrinter modelPrinter
		val Set<EObject> seen = new HashSet

		def private PrintResult printDifferenceRecursively(extension PrintTarget target, EObject object) {
			printIterableElements(getDifferencesWithContext("", object), MULTI_LINE) [ subTarget, difference |
				subTarget.printDifference(difference.key, difference.value)
			]
		}

		def private Iterable<Pair<String, Diff>> getDifferencesWithContext(String context, EObject object) {
			if (object === null || seen.contains(object)) return emptyList()
			seen += object

			val thisMatch = comparison.getMatch(object)
			if (thisMatch === null) return emptyList()

			thisMatch.differences.map[difference|context -> difference] //
			+ featureFilter.getReferencesToCheck(thisMatch).toIterable.flatMap [ reference |
				val referenceContext = context + '.' + reference.name
				if (reference.isMany) {
					(object.eGet(reference) as Iterable<? extends EObject>).indexed.flatMap [ el |
						val elementIndicator = if (reference.isOrdered) '''[«el.key»]''' else '''{«el.key»}'''
						getDifferencesWithContext(referenceContext + elementIndicator, el.value)
					]
				} else {
					getDifferencesWithContext(referenceContext, object.eGet(reference) as EObject)
				}
			]
		}

		def private PrintResult printDifference(extension PrintTarget target, String context, Diff difference) {
			print('• ') + switch (difference) {
				AttributeChange:
					target.printFeatureDifference(difference.attribute, context, difference, difference.value)
				ReferenceChange:
					target.printFeatureDifference(difference.reference, context, difference, difference.value)
				default:
					target.printObject(idProvider, difference)
			}
		}

		def private PrintResult printFeatureDifference(extension PrintTarget target, EStructuralFeature feature,
			String context, Diff difference, Object value) {
			print(context) + print('.') + print(feature.name) + print(' ') + print(difference.kind.verb) + print(' ') //
			+ printValue(value) [subTarget, theValue | printObject(subTarget, idProvider, theValue) ]
		}

		def private String getVerb(DifferenceKind kind) {
			switch (kind) {
				case ADD: "contained the unexpected value"
				case DELETE: "was missing the value"
				case CHANGE: "had the wrong value"
				case MOVE: "was moved"
			}
		}
	}
}

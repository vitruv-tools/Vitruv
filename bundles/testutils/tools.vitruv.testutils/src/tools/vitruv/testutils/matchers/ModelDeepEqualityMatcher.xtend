package tools.vitruv.testutils.matchers

import java.util.HashSet
import java.util.List
import java.util.Set
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

import static java.util.Spliterators.spliteratorUnknownSize
import static java.util.Spliterator.*
import static java.util.stream.StreamSupport.stream
import static tools.vitruv.testutils.matchers.MatcherUtil.a
import static tools.vitruv.testutils.printing.ModelPrinting.getPrinter
import static tools.vitruv.testutils.printing.PrintMode.*
import static tools.vitruv.testutils.printing.PrintResult.*

import static extension tools.vitruv.testutils.printing.ModelPrinting.*
import static extension tools.vitruv.testutils.printing.PrintResultExtension.*
import java.util.Iterator
import org.eclipse.emf.compare.rcp.EMFCompareRCPPlugin

@FinalFieldsConstructor
package class ModelDeepEqualityMatcher extends TypeSafeMatcher<EObject> {
	package val EObject expectedObject
	val List<EqualityFeatureFilter> featureFilters
	var Comparison comparison
	val idProvider = new PrintIdProvider

	override matchesSafely(EObject item) {
		comparison = emfCompare.compare(new DefaultComparisonScope(item, expectedObject, null))
		return comparison.differences.isEmpty
	}

	override describeTo(Description description) {
		description.appendText(a(expectedObject.eClass.name)).appendText(' deeply equal to ').
			appendModelValue(expectedObject, idProvider)
	}

	override describeMismatchSafely(EObject item, Description mismatchDescription) {
		comparison.getMatch(expectedObject)
		mismatchDescription.appendText('found the following differences: ')
		new ComparisonPrinter(idProvider, comparison) //
		.printDifferenceRecursively(new HamcrestDescriptionPrintTarget(mismatchDescription), expectedObject)
		mismatchDescription.appendText('for object ').appendModelValue(item, idProvider)
		if (!featureFilters.isEmpty) {
			mismatchDescription.appendText(System.lineSeparator).appendText(System.lineSeparator) //
			.appendText('(matching ').appendText(featureFilters.join('; ')[describe()]).appendText(')')
		}
	}

	private def getEmfCompare() {
		(EMFCompare.builder => [
			matchEngineFactoryRegistry = EMFCompareRCPPlugin.getDefault.matchEngineFactoryRegistry => [
				add(new MatchEngineFactoryImpl(UseIdentifiers.NEVER) => [
					ranking = 11 // default engine ranking is 10, must be higher to override.
				])
			]
			if (!featureFilters.isEmpty) {
				diffEngine = new DefaultDiffEngine(new DiffBuilder) {
					override createFeatureFilter() {
						new FeatureFilter() {
							override getAttributesToCheck(Match match) {
								filter(match, super.getAttributesToCheck(match))
							}

							override getReferencesToCheck(Match match) {
								filter(match, super.getReferencesToCheck(match))
							}

							private def <T extends EStructuralFeature> filter(Match match, Iterator<T> iterator) {
								val object = match.right ?: match.left
								stream(spliteratorUnknownSize(iterator, IMMUTABLE + NONNULL + DISTINCT), false).filter [ feature |
									!featureFilters.exists[!includeFeature(object, feature)]
								].iterator()
							}
						}
					}
				}
			}
		]).build()
	}

	@FinalFieldsConstructor
	private static class ComparisonPrinter {
		val PrintIdProvider idProvider
		val Comparison comparison
		val Set<EObject> seen = new HashSet

		def private PrintResult printDifferenceRecursively(PrintTarget target, EObject object) {
			target.printDifferenceRecursively("", object)
		}

		def private PrintResult printDifferenceRecursively(
			extension PrintTarget target,
			String context,
			EObject object
		) {
			if (object === null || seen.contains(object)) return PRINTED_NO_OUTPUT
			seen += object

			val thisMatch = comparison.getMatch(object)
			if (thisMatch === null) return PRINTED_NO_OUTPUT

			printIterableElements(thisMatch.differences, MULTI_LINE) [ subTarget, difference |
				subTarget.printDifference(context, difference)
			] + object.eClass.EAllReferences.map [ reference |
				val referenceContext = context + '.' + reference.name
				if (reference.isMany) {
					val format = if (reference.isOrdered) "{%d}" else "[%d]"
					(object.eGet(reference) as Iterable<? extends EObject>).indexed.map [
						target.printDifferenceRecursively(referenceContext + String.format(format, key), value)
					].combine()
				} else {
					target.printDifferenceRecursively(referenceContext, object.eGet(reference) as EObject)
				}
			].combine()
		}

		def private PrintResult printDifference(extension PrintTarget target, String context, Diff difference) {
			print('• ') + switch (difference) {
				AttributeChange:
					target.printFeatureDifference(difference.attribute, context, difference, difference.value)
				ReferenceChange:
					target.printFeatureDifference(difference.reference, context, difference, difference.value)
				default:
					target.printValue(difference)[valueTarget, diff|printer.printObject(valueTarget, idProvider, diff)]
			}
		}

		def private PrintResult printFeatureDifference(extension PrintTarget target, EStructuralFeature feature,
			String context, Diff difference, Object value) {
			print(context) + print('.') + print(feature.name) + print(' ') + print(difference.kind.verb) + print(' ') //
			+ target.printValue(value)[valueTarget, theValue|printer.printObject(valueTarget, idProvider, theValue)]
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

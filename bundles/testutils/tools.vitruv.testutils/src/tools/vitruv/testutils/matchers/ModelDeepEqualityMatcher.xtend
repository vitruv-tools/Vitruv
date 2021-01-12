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
import org.eclipse.core.runtime.Platform
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl
import org.eclipse.emf.compare.postprocessor.PostProcessorDescriptorRegistryImpl
import java.util.HashMap
import java.util.Collection
import java.util.function.Predicate
import static com.google.common.base.Preconditions.checkState

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
			matchEngineFactoryRegistry = appropriateMatchEngineFactoryRegistry => [
				// identifiers are often wrong or irrelevant in tests. Content-based matching yield better results
				// in these cases.
				add(new MatchEngineFactoryImpl(UseIdentifiers.NEVER) => [
					ranking = MAX_VALUE
				])
			]
			diffEngine = new DefaultDiffEngine(new DiffBuilder) {
				override createFeatureFilter() { emfCompareFeatureFilter }
			}
			postProcessorRegistry = appropriatePostProcessorRegistry => [
				put(
					RepairWrongUnmatches.name,
					new BasicPostProcessorDescriptorImpl(
						new RepairWrongUnmatches(item, expectedObject, emfCompareFeatureFilter),
						Pattern.compile('.*'), null)
				)
			]
		]).build()
	}

	private def getAppropriateMatchEngineFactoryRegistry() {
		if (Platform.isRunning)
			EMFCompareRCPPlugin.^default.matchEngineFactoryRegistry
		else
			MatchEngineFactoryRegistryImpl.createStandaloneInstance
	}

	private def getAppropriatePostProcessorRegistry() {
		if (Platform.isRunning)
			EMFCompareRCPPlugin.^default.postProcessorRegistry
		else
			new PostProcessorDescriptorRegistryImpl
	}

	/* Differences become difficult to read if EMF Compare does to match objects together. If the objects are the two
	 * root objects, some differences can even not be recovered. Furthermore, matching does *not* consider the ignored
	 * features, which can lead to wrong results (two objects might not get matched because they differ only in an
	 * ignored feature or reference. The assertion will then fail, even though it shouldn’t). Hence, we repair any 
	 * missing match by matching it with its topological partner, if they are equal ignoring the references.
	 */
	@FinalFieldsConstructor
	private static class RepairWrongUnmatches implements IPostProcessor {
		val EObject leftRoot
		val EObject rightRoot
		val FeatureFilter featureFilter
		val Set<EObject> checked = new HashSet()
		val HashMap<Pair<EObject, EObject>, Boolean> matchCache = new HashMap()

		override postComparison(Comparison comparison, Monitor monitor) {}

		override postConflicts(Comparison comparison, Monitor monitor) {}

		override postDiff(Comparison comparison, Monitor monitor) {}

		override postEquivalences(Comparison comparison, Monitor monitor) {}

		override postMatch(Comparison comparison, Monitor monitor) {
			ensureMatched(comparison, leftRoot, rightRoot)
			checkMatches(comparison, leftRoot, rightRoot)
		}

		def private ensureMatched(Comparison comparison, EObject left, EObject right) {
			ensureMatched(comparison, left, right, comparison.getMatch(left), comparison.getMatch(right))
		}

		def private ensureMatched(Comparison comparison, EObject left, EObject right, Match leftMatch,
			Match rightMatch) {
			if (leftMatch != rightMatch) combineMatches(comparison, left, right, leftMatch, rightMatch)
		}

		def private combineMatches(Comparison comparison, EObject left, EObject right, Match leftMatch,
			Match rightMatch) {
			// these checks guarantee that we do not throw away any values
			checkState(
				rightMatch.left === null,
				'''«right» should be matched with «left», but is already matched with «rightMatch.left»!'''
			)
			checkState(
				leftMatch.right === null,
				'''«left» should be matched with «right», but is already matched with «leftMatch.right»!'''
			)
			(leftMatch.eContainer as Match).submatches -= leftMatch
			comparison.matches -= leftMatch
			rightMatch.left = left
			return rightMatch
		}

		def private void checkMatches(Comparison comparison, EObject left, EObject right) {
			if (checked.contains(left) && checked.contains(right)) return;
			checked += left
			checked += right

			var leftMatch = comparison.getMatch(left)
			var rightMatch = comparison.getMatch(right)
			if (leftMatch.right === null && rightMatch.left === null &&
				shouldBeMatched(comparison, left, right, rightMatch)) {
				leftMatch = combineMatches(comparison, left, right, leftMatch, rightMatch)
				rightMatch = leftMatch
			}

			if (leftMatch == rightMatch) {
				iterateReferenceMatches(comparison, left, right, rightMatch)
			}
		}

		def private iterateReferenceMatches(Comparison comparison, EObject left, EObject right, Match rightMatch) {
			featureFilter.getReferencesToCheck(rightMatch).forEach [ reference |
				if (!reference.isMany) {
					checkMatches(comparison, left.eGet(reference) as EObject, right.eGet(reference) as EObject)
				} else {
					val leftItems = left.eGet(reference) as List<? extends EObject>
					val rightItems = right.eGet(reference) as List<? extends EObject>
					if (leftItems.size == rightItems.size) {
						if (reference.isOrdered) {
							if (leftItems.size == rightItems.size) {
								for (var i = 0; i < leftItems.size; i += 1) {
									checkMatches(comparison, leftItems.get(i), rightItems.get(i))
								}
							}
						} else {
							val leftToMatch = new HashSet(rightItems)
							for (rightItem : rightItems) {
								val rightItemMatch = comparison.getMatch(rightItem)
								val leftCandidate = rightItemMatch.left ?: leftToMatch.findFirst [
									shouldBeMatched(comparison, it, rightItem, rightItemMatch)
								]
								if (leftCandidate !== null) {
									leftToMatch -= leftCandidate
									checkMatches(comparison, leftCandidate, rightItem)
								}
							}
						}
					}
				}
			]
		}

		def private boolean shouldBeMatched(Comparison comparison, EObject left, EObject right) {
			shouldBeMatched(comparison, left, right, comparison.getMatch(right))
		}

		def private boolean shouldBeMatched(Comparison comparison, EObject left, EObject right, Match rightMatch) {
			// if we match values that should not be matched, the error messages will be harder to read.
			// if do not match values that should be matched, the result can be wrong.
			// hence, it is better to match too much than not enough
			val pair = left -> right
			matchCache.get(pair) ?: {
				val quickResult = (left.eClass == right.eClass) && featureFilter.getAttributesToCheck(rightMatch).all [ attribute |
					left.eGet(attribute) == right.eGet(attribute)
				]
				// save attribute result already here, in case we come back while checking references
				matchCache.put(pair, quickResult)
				if (quickResult) {
					val referenceResult = featureFilter.getReferencesToCheck(rightMatch).all [ reference |
						if (!reference.isMany) {
							shouldBeMatched(comparison, left.eGet(reference) as EObject,
								right.eGet(reference) as EObject)
						} else {
							val leftObjects = left.eGet(reference) as Collection<? extends EObject>
							val rightObjects = right.eGet(reference) as Collection<? extends EObject>
							leftObjects.size != rightObjects.size || {
								if (reference.isOrdered) {
									leftObjects.allIndexed [ leftItem, index |
										val rightItem = rightObjects.get(index)
										shouldBeMatched(comparison, leftItem, rightItem)
									]
								} else {
									leftObjects.all [ leftItem |
										rightObjects.exists [ rightItem |
											shouldBeMatched(comparison, leftItem, rightItem)
										]
									]
								}
							}
						}
					]
					matchCache.put(pair, referenceResult)
					referenceResult
				} else
					false
			}
		}

		override postRequirements(Comparison comparison, Monitor monitor) {}

		def private static <T> boolean all(Iterator<? extends T> elements, Predicate<T> predicate) {
			!elements.exists(predicate.negate())
		}

		def private static <T> boolean all(Iterable<? extends T> elements, Predicate<T> predicate) {
			!elements.exists(predicate.negate())
		}

		def private static <T> boolean allIndexed(Collection<? extends T> elements, (T, Integer)=>Boolean predicate) {
			for (var i = 0; i < elements.size; i++) {
				if (!predicate.apply(elements.get(i), i)) {
					return false
				}
			}
			return true
		}
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

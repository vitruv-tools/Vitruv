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
import static tools.vitruv.testutils.printing.PrintResult.*
import edu.kit.ipd.sdq.activextendannotations.CloseResource

import tools.vitruv.testutils.printing.DefaultPrintIdProvider
import tools.vitruv.testutils.printing.PrintIdProvider
import org.eclipse.emf.ecore.EReference
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*


package class ModelDeepEqualityMatcher extends TypeSafeMatcher<EObject> {
	val EObject expectedObject
	val List<? extends EqualityFeatureFilter> featureFilters
	val idProvider = new ComparisonAwarePrintIdProvider
	val ModelPrinter descriptionPrinter
	val FeatureFilter emfCompareFeatureFilter
	var Comparison comparison

	package new(EObject expectedEObject, List<? extends EqualityFeatureFilter> featureFilters) {
		this.expectedObject = expectedEObject
		this.featureFilters = featureFilters
		descriptionPrinter = new IgnoredFeaturesPrinter(featureFilters)
		emfCompareFeatureFilter = if (featureFilters.isEmpty)
			new FeatureFilter()
		else
			new EmfCompareEqualityFeatureFilter(featureFilters)
	}

	override matchesSafely(EObject item) {
		comparison = buildEmfCompare(item).compare(new DefaultComparisonScope(item, expectedObject, null))
		return comparison.differences.isEmpty
	}

	override describeTo(Description description) {
		describeTo(ModelPrinting.prepend(descriptionPrinter), description)
	}

	def private void describeTo(@CloseResource AutoCloseable printerChange, Description description) {
		description.appendText(a(expectedObject.eClass.name)).appendText(' deeply equal to ').
			appendModelValue(expectedObject, idProvider)
	}

	override describeMismatchSafely(EObject item, Description mismatchDescription) {
		describeMismatchSafely(ModelPrinting.prepend(descriptionPrinter), item, mismatchDescription)
	}

	private def void describeMismatchSafely(@CloseResource AutoCloseable printerChange, EObject item,
		Description mismatchDescription) {
		comparison.getMatch(expectedObject)
		mismatchDescription.appendText('found the following differences: ')

		val previouslyPrinted = idProvider.alreadyPrinted
		idProvider.alreadyPrinted = new HashSet()
		new ComparisonPrinter(idProvider, comparison, emfCompareFeatureFilter, ModelPrinting.printer) //
		.printDifferenceRecursively(new HamcrestDescriptionPrintTarget(mismatchDescription), expectedObject)

		idProvider.alreadyPrinted = previouslyPrinted
		idProvider.comparison = comparison
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
					RepairWrongMatches.name,
					new BasicPostProcessorDescriptorImpl(
						new RepairWrongMatches(item, expectedObject, emfCompareFeatureFilter),
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

	@FinalFieldsConstructor
	private static class EmfCompareEqualityFeatureFilter extends FeatureFilter {
		val List<? extends EqualityFeatureFilter> featureFilters

		override getAttributesToCheck(Match match) {
			filter(match, super.getAttributesToCheck(match))
		}

		override getReferencesToCheck(Match match) {
			filter(match, super.getReferencesToCheck(match))
		}

		private def <T extends EStructuralFeature> filter(Match match, Iterator<T> iterator) {
			val object = match.right ?: match.left
			iterator.filter [ feature |
				featureFilters.all[includeFeature(object, feature)]
			]
		}
	}

	/* 
	 * Fixes all the quirks of EMF Compare’s matching algorithm that stem from a combination of a bad implementation on
	 * EMF Compare’s behalf and the fact that EMF Compare was written with another intention (visualizing changes).
	 * Differences become difficult to read if EMF Compare does to match objects together. If the objects are the two
	 * root objects, some differences can even not be recovered. Furthermore, matching does *not* consider the ignored
	 * features, which can lead to wrong results:
	 *   * Two objects might not get matched because they differ only in an ignored feature or reference.
	 *   * Objects that can only be navigated via an ignored reference should not be compared at all, however, EMF 
	 *     Compare considers them.
	 * This assertion will fail in those cases will then even though it shouldn’t.
	 * Hence, we repair any missing match by matching it with its topological partner. Furthermore, we remove all 
	 * matches for objects that cannot be navigated to.
	 */
	@FinalFieldsConstructor
	private static class RepairWrongMatches implements IPostProcessor {
		val EObject leftRoot
		val EObject rightRoot
		val FeatureFilter featureFilter
		val Set<EObject> navigable = new HashSet()
		val Set<EObject> checked = new HashSet()
		val HashMap<Pair<EObject, EObject>, Boolean> matchCache = new HashMap()

		override postComparison(Comparison comparison, Monitor monitor) {}

		override postConflicts(Comparison comparison, Monitor monitor) {}

		override postDiff(Comparison comparison, Monitor monitor) {}

		override postEquivalences(Comparison comparison, Monitor monitor) {}

		override postMatch(Comparison comparison, Monitor monitor) {
			ensureMatched(comparison, leftRoot, rightRoot)
			checkMatches(comparison, leftRoot, rightRoot)
			collectNavigableObjects(comparison, leftRoot)
			collectNavigableObjects(comparison, rightRoot)
			removeNotNavigableMatches(comparison)
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
			rightMatch.submatches += leftMatch.submatches
			val leftContainer = leftMatch.eContainer.eGet(leftMatch.eContainingFeature) as Collection<EObject>
			leftContainer -= leftMatch
			rightMatch.left = left
			return rightMatch
		}

		// this is rather expensive because of the comparison.getMatch calls. However, we cannot merge this with 
		// iterateReferenceMatches because iterateReferenceMatches may not visit all objects.
		def private void collectNavigableObjects(Comparison comparison, EObject object) {
			if (object === null || navigable.contains(object)) return; 
			navigable += object
			val match = comparison.getMatch(object)
			if (match !== null) return;
			
			for (val references = featureFilter.getReferencesToCheck(match); references.hasNext();) {
				val reference = references.next() 
				if (reference.isMany) {
					for (referenced : object.eGet(reference) as Collection<? extends EObject>) {
						collectNavigableObjects(comparison, referenced)
					}
				} else {
					collectNavigableObjects(comparison, object.eGet(reference) as EObject)
				}
			}
		}

		def private void removeNotNavigableMatches(Comparison comparison) {
			// fix the list before navigating it to avoid a ConcurrentModificationException
			for (match : comparison.allMatches.toList) {
				if ((match.left === null || !navigable.contains(match.left)) &&
					(match.right === null || !navigable.contains(match.right))) {
					val matchContainer = match.eContainer
					switch (matchContainer) {
						Match: {
							matchContainer.submatches += match.submatches
							matchContainer.submatches -= match
						}
						case comparison: {
							comparison.matches += match.submatches
							comparison.matches -= match
						}
						default: throw new IllegalStateException('''Unexpected match container: «matchContainer» (of «match»)''')
					}
				}
			}
		}

		def private void checkMatches(Comparison comparison, EObject left, EObject right) {
			// if we match values that should not be matched, the error messages will be harder to read.
			// if do not match values that should be matched, the result can be wrong.
			// hence, it is better to match too much than not enough
			if (left === null || right === null || (checked.contains(left) && checked.contains(right))) return;
			checked += left
			checked += right

			var leftMatch = comparison.getMatch(left)
			var rightMatch = comparison.getMatch(right)
			if (leftMatch === null && rightMatch === null) return;
			checkState(leftMatch !== null, "right match without left match: %s", rightMatch)
			checkState(rightMatch !== null, "left match without right match: %s", leftMatch)
			
			if (leftMatch.right === null && rightMatch.left === null) {
				leftMatch = combineMatches(comparison, left, right, leftMatch, rightMatch)
				rightMatch = leftMatch
			}

			if (leftMatch == rightMatch) {
				iterateReferenceMatches(comparison, left, right, rightMatch)
			}
		}

		def private iterateReferenceMatches(Comparison comparison, EObject left, EObject right, Match rightMatch) {
			for (val references = featureFilter.getReferencesToCheck(rightMatch); references.hasNext();) {
				val reference = references.next()
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
							// tricky, because we can’t use topology here. Hence: leave everything that already has
							// a match, and try to find matches that definitely belong together.
							val leftToMatch = new HashSet(rightItems)
							for (rightItem : rightItems) {
								val rightItemMatch = comparison.getMatch(rightItem)
								val leftCandidate = rightItemMatch.left ?: leftToMatch.findFirst [
									shouldDefinitelyBeMatched(comparison, it, rightItem, rightItemMatch)
								]
								if (leftCandidate !== null) {
									leftToMatch -= leftCandidate
									checkMatches(comparison, leftCandidate, rightItem)
								}
							}
						}
					}
				}
			}
		}

		def private boolean shouldBeMatched(Comparison comparison, EObject left, EObject right) {
			shouldDefinitelyBeMatched(comparison, left, right, comparison.getMatch(right))
		}

		def private boolean shouldDefinitelyBeMatched(Comparison comparison, EObject left, EObject right,
			Match rightMatch) {
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
	}

	@FinalFieldsConstructor
	private static class IgnoredFeaturesPrinter implements ModelPrinter {
		val List<? extends EqualityFeatureFilter> featureFilters

		override withSubPrinter(ModelPrinter subPrinter) {
			this
		}

		override PrintResult printFeature(
			extension PrintTarget target,
			PrintIdProvider idProvider,
			EObject object,
			EStructuralFeature feature
		) {
			if (featureFilters.exists[!includeFeature(object, feature)]) {
				print(feature.name) + print('=…')
			} else
				NOT_RESPONSIBLE
		}
	}

	private static class ComparisonAwarePrintIdProvider implements PrintIdProvider {
		val delegate = new DefaultPrintIdProvider
		var alreadyPrinted = new HashSet<EObject>()
		var Comparison comparison

		override <T extends EObject> ifAlreadyPrintedElse(T object, (T, String)=>PrintResult existingPrinter,
			(T, String)=>PrintResult newPrinter) {
			delegate.printWithId(replaceWithRight(object)) [ toPrint, id |
				if (alreadyPrinted.contains(toPrint)) {
					existingPrinter.apply(toPrint, id)
				} else {
					alreadyPrinted += toPrint
					newPrinter.apply(toPrint, id)
				}
			]
		}

		def <T extends EObject> replaceWithRight(T object) {
			val match = comparison?.getMatch(object)
			if (match !== null && match.allDifferences.isEmpty) {
				match.right as T ?: object
			} else
				object
		}
	}

	@FinalFieldsConstructor
	private static class ComparisonPrinter {
		val PrintIdProvider idProvider
		val Comparison comparison
		val FeatureFilter featureFilter
		extension val ModelPrinter modelPrinter
		val Set<Match> seen = new HashSet

		def private PrintResult printDifferenceRecursively(extension PrintTarget target, EObject root) {
			printIterableElements(root.getDifferencesWithContext(""), MULTI_LINE) [ subTarget, difference |
				subTarget.printDifference(difference.key, difference.value)
			]
		}

		def private Iterable<Pair<String, Diff>> getDifferencesWithContext(EObject object, String context) {
			if (object === null) return emptyList()
			val thisMatch = comparison.getMatch(object)
			if (thisMatch === null || seen.contains(thisMatch)) return emptyList()
			seen += thisMatch

			thisMatch.differences.map[difference|context -> difference] //
			+ featureFilter.getReferencesToCheck(thisMatch).toIterable.flatMap [ reference |
				(thisMatch.left?.getReferenceDifferencesWithContext(context, reference) ?: emptyList()) +
					(thisMatch.right?.getReferenceDifferencesWithContext(context, reference) ?: emptyList())
			]
		}

		def private getReferenceDifferencesWithContext(EObject object, String context, EReference reference) {
			val referenceContext = '''«context».«reference.name»'''
			if (reference.isMany) {
				(object.eGet(reference) as Iterable<? extends EObject>).flatMapFixedIndexed [ index, element |
					val elementIndicator = if (reference.isOrdered) '''[«index»]''' else '''{«index»}'''
					element.getDifferencesWithContext(referenceContext + elementIndicator)
				]
			} else {
				(object.eGet(reference) as EObject).getDifferencesWithContext(referenceContext)
			}
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
			print(context) //
			+ (if (difference.match.left !== null) {
				print(' (') + idProvider.printWithId(difference.match.left)[_, id|print(id)] + print(')')
			} else {
				PRINTED_NO_OUTPUT
			}) + print('.') + print(feature.name) + print(' ') + print(difference.kind.verb) + print(': ') //
			+ printValue(value)[subTarget, theValue|printObject(subTarget, idProvider, theValue)]
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

	/**
	 * An iterator for the whole match hierarchy under and including {@code match}, in bottom-up order.
	 */
	def private static Iterable<Match> getAllMatches(Match match) {
		match.submatches.flatMap[allMatches] + match.submatches
	}

	/**
	 * An iterator for the whole match hierarchy under {@code comparison}, in bottom-up order.
	 */
	def private static Iterable<Match> getAllMatches(Comparison comparison) {
		comparison.matches.flatMap[allMatches] + comparison.matches
	}

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

package tools.vitruv.testutils.matchers

import java.util.HashSet
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
import org.eclipse.emf.compare.scope.DefaultComparisonScope
import org.eclipse.emf.compare.utils.UseIdentifiers
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import tools.vitruv.testutils.printing.PrintResult
import tools.vitruv.testutils.printing.PrintTarget

import static tools.vitruv.testutils.printing.PrintMode.*
import static java.lang.Integer.MAX_VALUE

import static extension tools.vitruv.testutils.printing.ModelPrinting.*
import static extension tools.vitruv.testutils.printing.PrintResultExtension.*
import org.eclipse.emf.compare.postprocessor.IPostProcessor
import org.eclipse.emf.common.util.Monitor
import org.eclipse.emf.compare.postprocessor.BasicPostProcessorDescriptorImpl
import tools.vitruv.testutils.printing.ModelPrinting
import tools.vitruv.testutils.printing.ModelPrinter
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryRegistryImpl
import org.eclipse.emf.compare.postprocessor.PostProcessorDescriptorRegistryImpl
import java.util.Collection
import static tools.vitruv.testutils.printing.PrintResult.*
import edu.kit.ipd.sdq.activextendannotations.CloseResource

import tools.vitruv.testutils.printing.DefaultPrintIdProvider
import tools.vitruv.testutils.printing.PrintIdProvider
import org.eclipse.emf.ecore.EReference
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import org.eclipse.emf.compare.match.eobject.EqualityHelperExtensionProviderDescriptorRegistryImpl
import tools.vitruv.testutils.matchers.EqualityFeatureFilter
import tools.vitruv.testutils.matchers.EqualityStrategy
import static extension tools.vitruv.testutils.printing.TestMessages.*
import org.eclipse.emf.compare.match.IMatchEngine
import org.eclipse.emf.compare.scope.IComparisonScope
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.emf.compare.match.DefaultMatchEngine
import org.eclipse.emf.compare.match.eobject.WeightProviderDescriptorRegistryImpl
import org.eclipse.emf.compare.match.DefaultComparisonFactory
import org.eclipse.emf.compare.match.DefaultEqualityHelperFactory
import org.eclipse.emf.compare.match.resource.StrategyResourceMatcher
import org.eclipse.emf.compare.utils.EqualityHelper
import com.google.common.cache.LoadingCache
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.compare.match.eobject.WeightProvider
import org.eclipse.xtend.lib.annotations.Delegate
import static com.google.common.base.Preconditions.checkState

package class ModelDeepEqualityMatcher<O extends EObject> extends TypeSafeMatcher<O> {
	val O expectedObject
	val List<? extends ModelDeepEqualityOption> options
	val idProvider = new ComparisonAwarePrintIdProvider
	val ModelPrinter descriptionPrinter
	val EqualityFeatureFilter featureFilter
	val FeatureFilter emfCompareFeatureFilter
	var Comparison comparison

	package new(O expectedEObject, List<? extends ModelDeepEqualityOption> options) {
		this.expectedObject = expectedEObject
		this.options = options
		val featureFilters = options.filter(EqualityFeatureFilter).toList
		featureFilter = new MultiEqualityFeatureFilter(featureFilters)
		descriptionPrinter = new IgnoredFeaturesPrinter(featureFilter)
		emfCompareFeatureFilter = if (featureFilters.isEmpty)
			new FixOrderingFeatureFilter()
		else
			new EmfCompareEqualityFeatureFilter(featureFilter)
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

	def private void describeMismatchSafely(@CloseResource AutoCloseable printerChange, EObject item,
		Description mismatchDescription) {
		comparison.getMatch(expectedObject)
		mismatchDescription.appendText('found the following differences:')

		idProvider.comparison = comparison
		mismatchDescription.appendPrintResult [ target |
			new ComparisonPrinter(idProvider, comparison, emfCompareFeatureFilter, ModelPrinting.printer)
				.printDifferences(target, expectedObject)
		]

		mismatchDescription.appendText('    for object ').appendModelValue(item, idProvider)
		if (!options.isEmpty) {
			mismatchDescription.appendText(System.lineSeparator).appendText(System.lineSeparator).appendText(
				'    (the comparison ').appendText(options.joinSemantic('and', ';')[target, it|describeTo(target)]).
				appendText(')')
		}
	}

	def private buildEmfCompare(EObject item) {
		// most EMF Compare factories have a RCP version hat can be configured via extension points. We don’t 
		// use those on purpose, because we want to achieve consistent results, no matter which other Eclipse 
		// bundles might be loaded in a test. 
		(EMFCompare.builder => [
			matchEngineFactoryRegistry = MatchEngineFactoryRegistryImpl.createStandaloneInstance => [
				add(new ParametersAwareMatchEngineFactory(
					// identifiers are often wrong or irrelevant in tests. Content-based matching yield better results
					// in these cases.
					UseIdentifiers.NEVER,
					new MultiEqualityStrategy(options.filter(EqualityStrategy).toList),
					featureFilter
				))
			]
			diffEngine = new DefaultDiffEngine(new DiffBuilder) {
				override createFeatureFilter() { emfCompareFeatureFilter }
			}
			postProcessorRegistry = new PostProcessorDescriptorRegistryImpl => [
				put(
					MatchRoots.name,
					new BasicPostProcessorDescriptorImpl(
						new MatchRoots(item, expectedObject),
						Pattern.compile('.*'), null
					)
				)
			]
		]).build()
	}

	private static class FixOrderingFeatureFilter extends FeatureFilter {
		override checkForOrderingChanges(EStructuralFeature feature) {
			// EMF Compare’s default implementation always checks for ordering changes for containment references.
			// This seems to make little sense and produces wrong results.
			feature.isMany && feature.isOrdered
		}
	}

	@FinalFieldsConstructor
	private static class EmfCompareEqualityFeatureFilter extends FixOrderingFeatureFilter {
		val EqualityFeatureFilter featureFilter

		override getAttributesToCheck(Match match) {
			super.getAttributesToCheck(match).filter [featureFilter.includeFeature(it)]
		}

		override getReferencesToCheck(Match match) {
			super.getReferencesToCheck(match).filter [featureFilter.includeFeature(it)]
		}
	}

	/* 
	 * It is central for tests that we match both roots together, otherwise there might be no differences at all.
	 * The request ‘compare these two objects’ has to be translated to EMF Compare by matching those two objects.
	 */
	@FinalFieldsConstructor
	private static class MatchRoots implements IPostProcessor {
		val EObject leftRoot
		val EObject rightRoot

		override postComparison(Comparison comparison, Monitor monitor) {}

		override postConflicts(Comparison comparison, Monitor monitor) {}

		override postDiff(Comparison comparison, Monitor monitor) {}

		override postEquivalences(Comparison comparison, Monitor monitor) {}

		override postMatch(Comparison comparison, Monitor monitor) {
			ensureMatched(comparison, leftRoot, rightRoot)
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
			checkState(leftMatch.right === null, 
				"%s should be matched with %s, but is already matched with %s!", left, right, leftMatch.right)
			checkState(rightMatch.left === null, 
				"%s should be matched with %s, but is already matched with %s!", right, left, rightMatch.left)
			rightMatch.submatches += leftMatch.submatches
			val leftContainer = leftMatch.eContainer.eGet(leftMatch.eContainingFeature) as Collection<EObject>
			leftContainer -= leftMatch
			rightMatch.left = left
		}

		override postRequirements(Comparison comparison, Monitor monitor) {}
	}

	@FinalFieldsConstructor
	private static class IgnoredFeaturesPrinter implements ModelPrinter {
		val EqualityFeatureFilter featureFilter

		override withSubPrinter(ModelPrinter subPrinter) {
			this
		}

		override PrintResult printFeatureValue(
			PrintTarget target,
			PrintIdProvider idProvider,
			EObject object,
			EStructuralFeature feature,
			Object value
		) {
			printIfIgnored(target, feature)
		}

		override PrintResult printFeatureValueSet(
			PrintTarget target,
			PrintIdProvider idProvider,
			EObject object,
			EStructuralFeature feature,
			Collection<?> values
		) {
			printIfIgnored(target, feature)
		}

		override PrintResult printFeatureValueList(
			PrintTarget target,
			PrintIdProvider idProvider,
			EObject object,
			EStructuralFeature feature,
			Collection<?> values
		) {
			printIfIgnored(target, feature)
		}

		def private printIfIgnored(extension PrintTarget target, EStructuralFeature feature) {
			if (!featureFilter.includeFeature(feature)) {
				print('…')
			} else {
				NOT_RESPONSIBLE
			}
		}
	}

	private static class ComparisonAwarePrintIdProvider implements PrintIdProvider {
		val delegate = new DefaultPrintIdProvider
		var Comparison comparison

		override getFallbackId(Object object) {
			switch (object) {
				EObject: delegate.getFallbackId(replaceWithRight(object))
				default: delegate.getFallbackId(object)
			}
		}

		def <T extends EObject> replaceWithRight(T object) {
			val match = comparison?.getMatch(object)
			// use the same ID if all attributes are equal
			if (match !== null && !match.allDifferences.exists[it instanceof AttributeChange]) {
				match.right as T ?: object
			} else
				object
		}
	}

	@FinalFieldsConstructor
	private static class ComparisonPrinter {
		static val DIFFERENCE_PRINT_MODE = multiLineIfAtLeast(1).withSeparator('')
		val PrintIdProvider idProvider
		val Comparison comparison
		val FeatureFilter featureFilter
		extension val ModelPrinter modelPrinter
		val Set<Diff> seenDiffs = new HashSet
		val Set<Match> seenMatches = new HashSet

		def private PrintResult printDifferences(extension PrintTarget target, EObject root) {
			printIterableElements(root.getDifferencesWithContext(), DIFFERENCE_PRINT_MODE) [ subTarget, difference |
				subTarget.printDifference(difference.key, difference.value)
			]
		}

		def private Iterable<Pair<String, Diff>> getDifferencesWithContext(EObject object) {
			val viaContainment = object.getDifferencesWithContext("", true)
			seenMatches.clear()
			val viaNonContainment = object.getDifferencesWithContext("", false)
			return viaContainment + viaNonContainment
		}

		def private Iterable<Pair<String, Diff>> getDifferencesWithContext(EObject object, String context,
			boolean containment) {
			if (object === null) return emptyList()
			val thisMatch = comparison.getMatch(object)
			if (thisMatch === null || !(seenMatches += thisMatch)) return emptyList()

			thisMatch.differences.filter[seenDiffs += it].map[difference|context -> difference] +
				featureFilter.getReferencesToCheck(thisMatch).filter[isContainment == containment].toIterable.
					flatMapFixed [ reference |
						(thisMatch.left?.getReferenceDifferencesWithContext(context, reference, containment) 
							?: emptyList)
						+ (thisMatch.right?.getReferenceDifferencesWithContext(context, reference, containment) 
							?: emptyList)
					]
		}

		def private getReferenceDifferencesWithContext(EObject object, String context, EReference reference,
			boolean containment) {
			val referenceContext = '''«context».«reference.name»'''
			if (reference.isMany) {
				(object.eGet(reference) as Iterable<? extends EObject>).flatMapFixedIndexed [ index, element |
					val elementIndicator = if (reference.isOrdered) '''[«index»]''' else '''{«index»}'''
					element.getDifferencesWithContext(referenceContext + elementIndicator, containment)
				]
			} else {
				(object.eGet(reference) as EObject).getDifferencesWithContext(referenceContext, containment)
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
			print(context) + (if (difference.match.left !== null) {
				print(' (') + modelPrinter.printObjectShortened(target, idProvider, difference.match.left) + print(')')
			} else {
				PRINTED_NO_OUTPUT
			}) + print('.') + print(feature.name) + print(' ') + print(difference.kind.verb) + print(': ') +
				target.printFeatureValue(idProvider, difference.match.left ?: difference.match.right, feature, value)
		}

		def private String getVerb(DifferenceKind kind) {
			switch (kind) {
				case ADD: "contained the unexpected value"
				case DELETE: "was missing the value"
				case CHANGE: "had the wrong value"
				case MOVE: "had a different position for"
			}
		}
	}

	private static class EqualityStrategyEqualityHelper extends EqualityHelper {
		val EqualityStrategy equalityStrategy

		private new(LoadingCache<EObject, URI> uriCache, EqualityStrategy equalityStrategy) {
			super(uriCache)
			this.equalityStrategy = equalityStrategy
		}

		override matchingEObjects(EObject object1, EObject object2) {
			return switch (equalityStrategy.compare(object1, object2)) {
				case EqualityStrategy.Result.EQUAL: true
				case EqualityStrategy.Result.UNEQUAL: false
				case EqualityStrategy.Result.UNKNOWN: super.matchingEObjects(object1, object2)
			}
		}
	}

	@FinalFieldsConstructor
	private static class FilteredFeaturesAwareWeightProvider implements WeightProvider {
		@Delegate val WeightProvider delegate
		val EqualityFeatureFilter featureFilter

		override getWeight(EStructuralFeature attribute) {
			if (featureFilter.includeFeature(attribute)) delegate.getWeight(attribute) else 0
		}
	}

	@FinalFieldsConstructor
	private static class ParametersAwareMatchEngineFactory implements IMatchEngine.Factory {
		val UseIdentifiers useIdentifiers
		val EqualityStrategy equalityStrategy
		val EqualityFeatureFilter featureFilter
		@Accessors
		var int ranking = MAX_VALUE

		override getMatchEngine() {
			val comparisonFactory = new DefaultComparisonFactory(
				new DefaultEqualityHelperFactory() {
					override createEqualityHelper() {
						new EqualityStrategyEqualityHelper(
							EqualityHelper.createDefaultCache(cacheBuilder),
							equalityStrategy
						)
					}
				}
			)
			val eObjectMatcher = DefaultMatchEngine.createDefaultEObjectMatcher(
				useIdentifiers,
				parameterAwareWeightProviderDescriptorRegistry,
				EqualityHelperExtensionProviderDescriptorRegistryImpl.createStandaloneInstance
			)
			return new DefaultMatchEngine(eObjectMatcher, new StrategyResourceMatcher, comparisonFactory)
		}

		override isMatchEngineFactoryFor(IComparisonScope scope) {
			true
		}

		def private parameterAwareWeightProviderDescriptorRegistry() {
			val resultRegistry = new WeightProviderDescriptorRegistryImpl
			val sourceRegistry = WeightProviderDescriptorRegistryImpl.createStandaloneInstance
			for (descriptor : sourceRegistry.descriptors) {
				resultRegistry.put(descriptor.weightProvider.class.name, new WeightProviderDescriptorImpl(
					new FilteredFeaturesAwareWeightProvider(descriptor.weightProvider, featureFilter),
					descriptor.ranking,
					descriptor.nsURI
				))
			}
			return resultRegistry
		}
	}
	
	// EMF Compare’s implementation has discouraged access, so we roll our own
	@FinalFieldsConstructor
	@Accessors 
	private static class WeightProviderDescriptorImpl implements WeightProvider.Descriptor {
		val WeightProvider weightProvider
		val int ranking
		val Pattern nsURI
	}
}

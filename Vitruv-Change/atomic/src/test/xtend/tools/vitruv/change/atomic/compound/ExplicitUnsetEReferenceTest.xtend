package tools.vitruv.change.atomic.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.Identified
import allElementTypes.NonRoot
import allElementTypes.Root
import java.util.ArrayList
import java.util.List
import java.util.stream.Stream
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Named
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.EChangeTest
import tools.vitruv.change.atomic.feature.reference.ReplaceSingleValuedEReference
import tools.vitruv.change.atomic.uuid.Uuid

import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*

/**
 * Test class for the concrete {@link ExplicitUnsetEReference} EChange,
 * which unsets a single or multi valued reference.
 */
class ExplicitUnsetEReferenceTest extends EChangeTest {
	var Root affectedEObject
	var EReference affectedFeature
	var EList<NonRoot> referenceContent

	var NonRoot oldValue
	var NonRoot oldValue2
	var NonRoot oldValue3

	@BeforeEach
	def void beforeTest() {
		affectedEObject = rootObject
		oldValue = aet.NonRoot.withUuid
		oldValue2 = aet.NonRoot.withUuid
		oldValue3 = aet.NonRoot.withUuid
	}

	/**
	 * Tests whether the {@link ExplicitUnsetEReference} EChange resolves to the correct type.
	 */
	@Test
	def void resolveToCorrectType() {
		// Set state before
		isSingleValuedNonContainmentTest

		// Create change
		val unresolvedChange = createUnresolvedChange()

		// Resolve
		val resolvedChange = unresolvedChange.applyForwardAndResolve
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	@ParameterizedTest
	@MethodSource("testConfigurations")
	def void applyForwardTest(boolean isContainment, boolean isSingleValued) {
		// Set state before
		if (isSingleValued) {
			isContainment ? isSingleValuedContainmentTest : isSingleValuedNonContainmentTest
		}
		else {
			isContainment ? isMultiValuedContainmentTest : isMultiValuedNonContainmentTest
		}

		// Test
		applyForwardTest
	}

	@ParameterizedTest
	@MethodSource("testConfigurations")
	def void applyBackwardTest(boolean isContainment, boolean isSingleValued) {
		// Set state before
		if (isSingleValued) {
			isContainment ? isSingleValuedContainmentTest : isSingleValuedNonContainmentTest
		}
		else {
			isContainment ? isMultiValuedContainmentTest : isMultiValuedNonContainmentTest
		}

		// Test
		applyBackwardTest
	}

	/**
	 * Starts a test with a single valued non containment reference
	 * and sets the state before.
	 */
	def private void isSingleValuedNonContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE
		prepareStateBefore
	}

	/**
	 * Starts a test with a single valued containment reference
	 * and sets the state before.
	 */
	def private void isSingleValuedContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		prepareStateBefore
	}

	/**
	 * Starts a test with a multi valued non containment reference
	 * and sets the state before.
	 */
	def private void isMultiValuedNonContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_UNSETTABLE_NON_CONTAINMENT_EREFERENCE
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		prepareStateBefore
	}

	/**
	 * Starts a test with a multi valued containment reference
	 * and sets the state before.
	 */
	def private void isMultiValuedContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_UNSETTABLE_CONTAINMENT_EREFERENCE
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		prepareStateBefore
	}

	/**
	 * Sets the state before the change, depending on single or multi valued
	 * reference, or containment / non containment.
	 */
	def private void prepareStateBefore() {
		if (!affectedFeature.containment) {
			prepareResource
		}
		prepareReference
	}

	/**
	 * Prepares the resource and puts every value
	 * of the feature in the resource.
	 */
	def private void prepareResource() {
		resource.contents.add(oldValue)
		resource.contents.add(oldValue2)
		resource.contents.add(oldValue3)
	}

	/**
	 * Prepares the reference and puts the
	 * affected values into the reference.
	 */
	def private void prepareReference() {
		if (!affectedFeature.many) {
			affectedEObject.eSet(affectedFeature, oldValue)
		} else {
			referenceContent.add(oldValue)
			referenceContent.add(oldValue2)
			referenceContent.add(oldValue3)
		}
	}

	/**
	 * The model is in state before the change.
	 */
	def private void assertIsStateBefore() {
		assertTrue(affectedEObject.eIsSet(affectedFeature))
		if (!affectedFeature.isContainment) {
			assertResourceIsStateBefore
		}
		assertReferenceIsStateBefore
	}

	/**
	 * The affected reference is in state before the change.
	 */
	def private void assertReferenceIsStateBefore() {
		if (!affectedFeature.containment) {
			if (!affectedFeature.many) {
				assertEquals(oldValue, affectedEObject.eGet(affectedFeature))
			} else {
				assertEquals(oldValue, referenceContent.get(0))
				assertEquals(oldValue2, referenceContent.get(1))
				assertEquals(oldValue3, referenceContent.get(2))
			}
		} else {
			if (!affectedFeature.many) {
				assertThat(oldValue, equalsDeeply(affectedEObject.eGet(affectedFeature) as Identified))
			} else {
				assertThat(oldValue, equalsDeeply(referenceContent.get(0)))
				assertThat(oldValue2, equalsDeeply(referenceContent.get(1)))
				assertThat(oldValue3, equalsDeeply(referenceContent.get(2)))
			}
		}
	}

	/**
	 * The resource is in state before the change.
	 */
	def private void assertResourceIsStateBefore() {
		if (!affectedFeature.containment) {
			// Root object at index 0
			assertThat(oldValue, equalsDeeply(resource.contents.get(1) as Identified))
			assertThat(oldValue2, equalsDeeply(resource.contents.get(2) as Identified))
			assertThat(oldValue3, equalsDeeply(resource.contents.get(3) as Identified))
		} else {
			assertEquals(resource.contents.size, 1)
		}
	}

	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
		assertFalse(affectedEObject.eIsSet(affectedFeature))
		assertResourceIsStateAfter
	}

	/**
	 * Resource is in state after the change.
	 */
	def private void assertResourceIsStateAfter() {
		assertResourceIsStateBefore
	}

	/**
	 * Creates new unresolved change.
	 */
	def private List<EChange<Uuid>> createUnresolvedChange() {
		var List<EChange<? extends EObject>> changes = new ArrayList<EChange<? extends EObject>>()
		if (!affectedFeature.containment) {
			if (!affectedFeature.many) {
				val change = atomicFactory.createReplaceSingleReferenceChange(affectedEObject, affectedFeature,
					oldValue, null)
				change.setIsUnset = true
				changes.add(change)
			} else {
				changes.add(atomicFactory.createRemoveReferenceChange(affectedEObject, affectedFeature, oldValue3, 2))
				changes.add(atomicFactory.createRemoveReferenceChange(affectedEObject, affectedFeature, oldValue2, 1))
				changes.add(atomicFactory.createRemoveReferenceChange(affectedEObject, affectedFeature, oldValue, 0))
				changes.add(atomicFactory.createUnsetFeatureChange(affectedEObject, affectedFeature))
			}
		} else {
			if (!affectedFeature.many) {
				val change = compoundFactory.createReplaceAndDeleteNonRootChange(affectedEObject, affectedFeature,
					oldValue)
				(change.get(0) as ReplaceSingleValuedEReference<?>).isUnset = true
				changes.addAll(change)
			} else {
				changes.addAll(
					compoundFactory.createRemoveAndDeleteNonRootChange(affectedEObject, affectedFeature, oldValue3, 2))
				changes.addAll(
					compoundFactory.createRemoveAndDeleteNonRootChange(affectedEObject, affectedFeature, oldValue2, 1))
				changes.addAll(
					compoundFactory.createRemoveAndDeleteNonRootChange(affectedEObject, affectedFeature, oldValue, 0))
				changes.add(atomicFactory.createUnsetFeatureChange(affectedEObject, affectedFeature))
			}
		}

		return changes.unresolve
	}

	/**
	 * Starts a test with applying the change forward.
	 */
	def private void applyForwardTest() {
		// State before
		assertIsStateBefore

		// Create and resolve change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.applyForwardAndResolve

		// State after
		assertIsStateAfter
	}

	/**
	 * Starts a test with applying the change backward.
	 */
	def private void applyBackwardTest() {
		// State before
		assertIsStateBefore

		// Create and resolve change
		val resolvedChange = createUnresolvedChange().applyForwardAndResolve

		// State after
		assertIsStateAfter

		// Apply forward
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore
	}
	
	static def testConfigurations() {
		return Stream.of(
			Arguments.of(Named.of("containment", true), Named.of("single-valued", true)),
			Arguments.of(Named.of("non-containment", false), Named.of("single-valued", true)),
			Arguments.of(Named.of("containment", true), Named.of("multi-valued", false)),
			Arguments.of(Named.of("non-containment", false), Named.of("multi-valued", false))
		);
	}
}

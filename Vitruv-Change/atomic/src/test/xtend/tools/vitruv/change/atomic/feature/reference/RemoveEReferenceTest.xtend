package tools.vitruv.change.atomic.feature.reference

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.junit.jupiter.api.Test
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.uuid.Uuid

import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply

/**
 * Test class for the concrete {@link RemoveEReference} EChange, 
 * which removes a reference from a multivalued attribute.
 */
class RemoveEReferenceTest extends ReferenceEChangeTest {
	var EReference affectedFeature
	var EList<NonRoot> referenceContent

	/**
	 * Tests whether resolving the {@link RemoveEReference} EChange
	 * returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Set state before
		isNonContainmentTest

		// Create change
		val unresolvedChange = createUnresolvedChange(newValue, 0)

		// Resolve
		val resolvedChange = unresolvedChange.applyForwardAndResolve
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests applying the {@link RemoveEReference} EChange forward by
	 * removing inserted values from a multivalued reference. 
	 * The reference is a non containment reference, so the values has
	 * to be a root object in the resource.
	 */
	@Test
	def void applyForwardNonContainmentTest() {
		// Set state before
		isNonContainmentTest

		// Create change (resolved)
		val unresolvedChange = createUnresolvedChange(newValue, 0)
		unresolvedChange.applyForwardAndResolve

		assertEquals(referenceContent.size, 1)
		assertEquals(referenceContent.get(0), newValue2)

		// Create change 2 (resolved)
		val unresolvedChange2 = createUnresolvedChange(newValue2, 0)
		unresolvedChange2.applyForwardAndResolve

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests applying the {@link RemoveEReference} EChange forward by 
	 * removing inserted values from a multivalued reference.
	 * The reference is a containment reference, so the values
	 * will be in the staging area after removing them.
	 */
	@Test
	def void applyForwardContainmentTest() {
		// Set state before
		isContainmentTest

		// Create change (resolved)
		val unresolvedChange = createUnresolvedChange(newValue, 0)
		unresolvedChange.applyForwardAndResolve

		assertEquals(referenceContent.size, 1)
		assertEquals(referenceContent.get(0), newValue2)

		// Create change 2 (resolved)
		val unresolvedChange2 = createUnresolvedChange(newValue2, 0)
		unresolvedChange2.applyForwardAndResolve

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests applying a {@link RemoveEReference} EChange backward. The reference is
	 * a non containment reference so the values has to be in the resource.
	 */
	@Test
	def void applyBackwardNonContainmentTest() {
		// Set state before
		isNonContainmentTest

		// Create change and apply forward
		val resolvedChange = createUnresolvedChange(newValue, 0).applyForwardAndResolve

		// Create change 2 and apply forward			
		val resolvedChange2 = createUnresolvedChange(newValue2, 0).applyForwardAndResolve

		// State after
		assertIsStateAfter

		// apply backward 2
		resolvedChange2.applyBackward
		assertEquals(referenceContent.size, 1)
		assertEquals(referenceContent.get(0), newValue2)

		// apply backward 1
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Tests applying a {@link RemoveEReference} EChange backward. The reference is
	 * a containment reference so the values has to be in the staging area
	 * before they are reinserted.
	 */
	@Test
	def void applyBackwardContainmentTest() {
		// Set state before
		isContainmentTest

		// Create change and apply forward
		val resolvedChange = createUnresolvedChange(newValue, 0).applyForwardAndResolve

		// Create change 2 and apply forward			
		val resolvedChange2 = createUnresolvedChange(newValue2, 0).applyForwardAndResolve

		// State after
		assertIsStateAfter

		// apply backward 2
		resolvedChange2.applyBackward

		assertEquals(referenceContent.size, 1)
		assertEquals(referenceContent.get(0), newValue2)

		// apply backward 1
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Tests a {@link RemoveEReference} EChange with invalid index.
	 */
	@Test
	def void invalidIndexTest() {
		// Set state before
		isNonContainmentTest
		var index = 5 // Valid index is 0 or 1
		assertEquals(referenceContent.size, 2)
		assertTrue(referenceContent.get(0) == newValue)

		// Create and resolve
		val resolvedChange = createUnresolvedChange(newValue, index)

		// Apply
		assertThrows(IllegalStateException) [resolvedChange.applyForwardAndResolve]
	}

	/**
	 * Tests a {@link RemoveEReference} EChange with with an affected object which has no 
	 * such reference.
	 */
	@Test
	def void invalidAttributeTest() {
		// Set state before
		isNonContainmentTest
		val invalidAffectedEObject = newValue2 // NonRoot element
		val resolvedChange = atomicFactory.<EObject>createRemoveReferenceChange(invalidAffectedEObject,
			affectedFeature, newValue, 0)

		// NonRoot has no such feature
		assertEquals(invalidAffectedEObject.eClass.getFeatureID(affectedFeature), -1)

		// Apply
		assertThrows(IllegalStateException) [resolvedChange.applyBackward]
	}

	/**
	 * Starts a test with a containment feature and sets state before.
	 */
	def private void isContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		prepareReference
		assertIsStateBefore
	}

	/**
	 * Starts a test with a non containment feature and sets state before.
	 */
	def private void isNonContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		prepareReference
		prepareResource
		assertIsStateBefore
	}

	/**
	 * Prepares the multivalued reference used in the tests 
	 * and fills it with the new values.
	 */
	def private void prepareReference() {
		referenceContent.add(newValue)
		referenceContent.add(newValue2)
	}

	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		assertEquals(referenceContent.size, 2)
		assertThat(newValue, equalsDeeply(referenceContent.get(0) as EObject))
		assertThat(newValue2, equalsDeeply(referenceContent.get(1) as EObject))
	}

	/**
	 * Model is in state after the changes.
	 */
	def private void assertIsStateAfter() {
		assertEquals(referenceContent.size, 0)
	}

	/**
	 * Creates new unresolved change.
	 */
	def private EChange<Uuid> createUnresolvedChange(NonRoot oldValue, int index) {
		return atomicFactory.createRemoveReferenceChange(affectedEObject, affectedFeature, oldValue, index).unresolve
	}
}

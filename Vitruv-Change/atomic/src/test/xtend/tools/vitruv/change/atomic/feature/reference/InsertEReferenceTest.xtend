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
import static org.junit.jupiter.api.Assertions.assertSame
import static org.junit.jupiter.api.Assertions.assertThrows
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply

/**
 * Test class for the concrete {@link InsertEReferenceValue} EChange,
 * which inserts a reference in a multivalued attribute.
 */
class InsertEReferenceTest extends ReferenceEChangeTest {
	var EReference affectedFeature
	var EList<NonRoot> referenceContent

	/**
	 * Tests whether resolving the {@link InsertEReference} EChange
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
	 * Tests applying the {@link InsertEReference} EChange forward by 
	 * inserting new values in a multivalued reference.
	 * The affected feature is a non containment reference, so the 
	 * new value is already in the resource.
	 */
	@Test
	def void applyForwardNonContainmentTest() {
		// Set state before
		isNonContainmentTest

		// Create change (resolved)
		val unresolvedChange = createUnresolvedChange(newValue, 0)
		unresolvedChange.applyForwardAndResolve

		assertEquals(referenceContent.size, 1)
		assertSame(referenceContent.get(0), newValue)

		// Create change 2 (resolved)
		val unresolvedChange2 = createUnresolvedChange(newValue2, 1)
		unresolvedChange2.applyForwardAndResolve

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests applying the {@link InsertEReference} EChange forward by 
	 * inserting new values from a multivalued reference.
	 * The affected feature is a containment reference, so the
	 * new value is from the staging area.
	 */
	@Test
	def void applyForwardContainmentTest() {
		// Set state before
		isContainmentTest

		// Create change (resolved)		
		val unresolvedChange = createUnresolvedChange(newValue, 0)
		unresolvedChange.applyForwardAndResolve

		assertEquals(referenceContent.size, 1)
		assertSame(referenceContent.get(0), newValue)

		// Prepare and create change 2
		val unresolvedChange2 = createUnresolvedChange(newValue2, 1)
		unresolvedChange2.applyForwardAndResolve

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests applying two {@link InsertEReference} EChanges backward by
	 * removing new added values from a multivalued reference.
	 * The affected feature is a non containment reference, so the
	 * removed values are already in the resource.
	 */
	@Test
	def void applyBackwardNonContainmentTest() {
		// Set state before
		isNonContainmentTest

		// Create change and apply forward
		val resolvedChange = createUnresolvedChange(newValue, 0).applyForwardAndResolve

		// Create change 2 and apply forward			
		val resolvedChange2 = createUnresolvedChange(newValue2, 1).applyForwardAndResolve

		// State after
		assertIsStateAfter

		// Apply backward 2
		resolvedChange2.applyBackward

		assertEquals(referenceContent.size, 1)
		assertSame(referenceContent.get(0), newValue)

		// Apply backward 1
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Tests applying two {@link InsertEReference} EChanges backward by 
	 * removing new added values from a multivalued reference.
	 * The affected feature is a containment reference, so the
	 * removed values will be placed in the staging area after removing them.
	 */
	@Test
	def void applyBackwardContainmentTest() {
		// Set state before
		isContainmentTest

		// Create change and apply forward
		val resolvedChange = createUnresolvedChange(newValue, 0).applyForwardAndResolve

		// Create change 2 and apply forward			
		val resolvedChange2 = createUnresolvedChange(newValue2, 1).applyForwardAndResolve

		// State after
		assertIsStateAfter

		// Apply backward 2
		resolvedChange2.applyBackward

		assertEquals(referenceContent.size, 1)
		assertSame(referenceContent.get(0), newValue)

		// Apply backward 1
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Tests the {@link InsertEReference} EChange with invalid index.
	 */
	@Test
	def void invalidIndexTest() {
		// Set state before
		isNonContainmentTest
		val index = 5 // Valid index in empty list is only 0
		assertEquals(referenceContent.size, 0)

		// Create and resolve
		val resolvedChange = createUnresolvedChange(newValue, index)

		// Apply	
		assertThrows(IllegalStateException) [resolvedChange.applyForwardAndResolve]
	}

	/**
	 * Tests an affected object which has no such reference.
	 */
	@Test
	def void invalidAttributeTest() {
		// Set state before
		isNonContainmentTest
		val invalidAffectedEObject = newValue2 // NonRoot element
		// Create and resolve change
		val resolvedChange = atomicFactory.<EObject>createInsertReferenceChange(invalidAffectedEObject,
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
		assertIsStateBefore
	}

	/**
	 * Starts a test with a non containment feature and sets state before.
	 */
	def private void isNonContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		prepareResource
		assertIsStateBefore
	}

	/**
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore() {
		assertEquals(referenceContent.size, 0)
	}

	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
		assertEquals(referenceContent.size, 2)
		assertThat(newValue, equalsDeeply(referenceContent.get(0)))
		assertThat(newValue2, equalsDeeply(referenceContent.get(1)))
	}

	/**
	 * Creates new unresolved change.
	 */
	def private EChange<Uuid> createUnresolvedChange(NonRoot newValue, int index) {
		return atomicFactory.createInsertReferenceChange(affectedEObject, affectedFeature, newValue, index).unresolve
	}
}

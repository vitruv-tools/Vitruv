package tools.vitruv.framework.tests.echange.feature.reference

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EReference
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertSame
import static org.junit.jupiter.api.Assertions.assertNotSame
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply
import org.junit.jupiter.api.BeforeEach
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolver.*

/**
 * Test class for the concrete {@link InsertEReferenceValue} EChange,
 * which inserts a reference in a multivalued attribute.
 */
class InsertEReferenceTest extends ReferenceEChangeTest {
	var EReference affectedFeature
	var EList<NonRoot> referenceContent

	@BeforeEach
	def void before() {
		newValue.registerAsPreexisting
		newValue2.registerAsPreexisting
	}

	/**
	 * Test resolves a {@link InsertEReference} EChange with correct parameters on
	 * a model which is in state before the change.
	 * The affected feature is a non containment reference, so the new reference
	 * is in the resource already.
	 */
	@Test
	def void resolveBeforeNonContainmentTest() {
		// Set state before
		isNonContainmentTest

		// Create change
		val unresolvedChange = createUnresolvedChange(newValue, 0)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore as InsertEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)
	}

	/**
	 * Test resolves a {@link InsertEReference} EChange with correct parameters on
	 * a model which is in state before the change.
	 * The affected feature is a containment reference, so the new reference 
	 * is in the staging area.
	 */
	@Test
	def void resolveBeforeContainmentTest() {
		// Set state before
		isContainmentTest

		// Create change
		val unresolvedChange = createUnresolvedChange(newValue, 0)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore as InsertEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)
	}

	/**
	 * Test resolves a {@link InsertEReference} EChange with correct parameters on 
	 * a model which is in state after the change.
	 * The affected feature is a non containment reference, so the inserted
	 * reference is already a root element.
	 */
	@Test
	def void resolveAfterNonContainmentTest() {
		// Set state before
		isNonContainmentTest

		// Create change
		val unresolvedChange = createUnresolvedChange(newValue, 0)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)

		// Set state after
		prepareReference(newValue)

		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter as InsertEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)
	}

	/**
	 * Test resolves a {@link InsertEReference} EChange with correct parameters on
	 * a model which is in the state after the change.
	 * The affected feature is a containment reference, so the inserted 
	 * reference is in the resource after the change.
	 */
	@Test
	def void resolveAfterContainmentTest() {
		// Set state before
		isContainmentTest

		// Create change
		val unresolvedChange = createUnresolvedChange(newValue, 0)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)

		// Set state after
		prepareReference(newValue)

		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter as InsertEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)
	}

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
		val resolvedChange = unresolvedChange.resolveAfter
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
		val resolvedChange = createUnresolvedChange(newValue, 0).resolveBefore

		// Apply forward
		resolvedChange.assertApplyForward
		assertEquals(referenceContent.size, 1)
		assertSame(referenceContent.get(0), newValue)

		// Create change 2 (resolved)
		val resolvedChange2 = createUnresolvedChange(newValue2, 1).resolveBefore

		// Apply forward 2
		resolvedChange2.assertApplyForward

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
		val resolvedChange = createUnresolvedChange(newValue, 0).resolveBefore

		// Apply forward
		resolvedChange.assertApplyForward

		assertEquals(referenceContent.size, 1)
		assertSame(referenceContent.get(0), newValue)

		// Prepare and create change 2
		val resolvedChange2 = createUnresolvedChange(newValue2, 1).resolveBefore

		// Apply forward 2
		resolvedChange2.assertApplyForward

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
		val resolvedChange = createUnresolvedChange(newValue, 0).resolveBefore
		resolvedChange.applyForward

		// Create change 2 and apply forward			
		val resolvedChange2 = createUnresolvedChange(newValue2, 1).resolveBefore
		resolvedChange2.applyForward

		// State after
		assertIsStateAfter

		// Apply backward 2
		resolvedChange2.assertApplyBackward

		assertEquals(referenceContent.size, 1)
		assertSame(referenceContent.get(0), newValue)

		// Apply backward 1
		resolvedChange.assertApplyBackward

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
		val resolvedChange = createUnresolvedChange(newValue, 0).resolveBefore
		resolvedChange.assertApplyForward

		// Create change 2 and apply forward			
		val resolvedChange2 = createUnresolvedChange(newValue2, 1).resolveBefore
		resolvedChange2.assertApplyForward

		// State after
		assertIsStateAfter

		// Apply backward 2
		resolvedChange2.assertApplyBackward

		assertEquals(referenceContent.size, 1)
		assertSame(referenceContent.get(0), newValue)

		// Apply backward 1
		resolvedChange.assertApplyBackward

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
		var index = 5 // Valid index in empty list is only 0
		assertEquals(referenceContent.size, 0)

		// Create and resolve
		val resolvedChange = createUnresolvedChange(newValue, index).resolveBefore as InsertEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)

		// Apply	
		assertThrows(IllegalStateException) [resolvedChange.applyForward]
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
		val resolvedChange = atomicFactory.<NonRoot, NonRoot>createInsertReferenceChange(invalidAffectedEObject,
			affectedFeature, newValue, 0).resolveBefore
		assertTrue(resolvedChange.isResolved)

		// NonRoot has no such feature
		assertEquals(invalidAffectedEObject.eClass.getFeatureID(affectedFeature), -1)

		// Apply
		assertThrows(IllegalStateException) [resolvedChange.applyForward]
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
	 * Prepares the multivalued reference used in the tests 
	 * and fills it with a new value.
	 */
	def private void prepareReference(NonRoot object) {
		referenceContent.add(object)
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
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(InsertEReference<Root, NonRoot> change, Root affectedEObject,
		NonRoot newValue) {
		assertFalse(change.isResolved)
		assertNotSame(change.affectedEObject, affectedEObject)
		assertNotSame(change.newValue, newValue)
	}

	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(InsertEReference<Root, NonRoot> change, Root affectedEObject,
		NonRoot newValue) {
		assertTrue(change.isResolved)
		assertSame(change.affectedEObject, affectedEObject)
		assertSame(change.newValue, newValue)
	}

	/**
	 * Creates new unresolved change.
	 */
	def private InsertEReference<Root, NonRoot> createUnresolvedChange(NonRoot newValue, int index) {
		return atomicFactory.createInsertReferenceChange(affectedEObject, affectedFeature, newValue, index)
	}
}

package tools.vitruv.framework.tests.echange.feature.reference

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference

import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertSame
import static org.junit.jupiter.api.Assertions.assertNotSame
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply
import org.junit.jupiter.api.BeforeEach
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*

/**
 * Test class for the concrete {@link RemoveEReference} EChange, 
 * which removes a reference from a multivalued attribute.
 */
class RemoveEReferenceTest extends ReferenceEChangeTest {
	var EReference affectedFeature
	var EList<NonRoot> referenceContent

	@BeforeEach
	def void before() {
		newValue.registerAsPreexisting
		newValue2.registerAsPreexisting
	}
	
	/**
	 * Test resolves a {@link RemoveEReference} EChange with correct parameters.
	 * The model is in state before the change was applied forward.
	 * The resource that will be removed is in a non containment reference,
	 * so the object needs to be in the resource.
	 */
	@Test
	def void resolveBeforeNonContainmentTest() {
		// Set state before
		isNonContainmentTest

		// Create change
		val unresolvedChange = createUnresolvedChange(newValue, 0)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore as RemoveEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)
	}

	/**
	 * Test resolves a {@link RemoveEReference} EChange with correct parameters.
	 * The model is in state before the change was applied forward.
	 * The resource that will be removed is in a containment reference,
	 * so the object is not in the resource.
	 */
	@Test
	def void resolveBeforeContainmentTest() {
		// Set state before
		isContainmentTest

		// Create change
		val unresolvedChange = createUnresolvedChange(newValue, 0)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore as RemoveEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)
	}

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
		val resolvedChange = unresolvedChange.resolveBefore
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
		val resolvedChange = createUnresolvedChange(newValue, 0).resolveBefore

		// Apply forward
		resolvedChange.assertApplyForward

		assertEquals(referenceContent.size, 1)
		assertEquals(referenceContent.get(0), newValue2)

		// Create change 2 (resolved)
		val resolvedChange2 = createUnresolvedChange(newValue2, 0).resolveBefore

		// Apply forward 2
		resolvedChange2.assertApplyForward

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
		val resolvedChange = createUnresolvedChange(newValue, 0).resolveBefore

		// Apply forward
		resolvedChange.assertApplyForward

		assertEquals(referenceContent.size, 1)
		assertEquals(referenceContent.get(0), newValue2)

		// Create change 2 (resolved)
		val resolvedChange2 = createUnresolvedChange(newValue2, 0).resolveBefore

		// Apply forward 2
		resolvedChange2.assertApplyForward

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
		val resolvedChange = createUnresolvedChange(newValue, 0).resolveBefore
		resolvedChange.assertApplyForward

		// Create change 2 and apply forward			
		val resolvedChange2 = createUnresolvedChange(newValue2, 0).resolveBefore
		resolvedChange2.assertApplyForward

		// State after
		assertIsStateAfter

		// apply backward 2
		resolvedChange2.assertApplyBackward
		assertEquals(referenceContent.size, 1)
		assertEquals(referenceContent.get(0), newValue2)

		// apply backward 1
		resolvedChange.assertApplyBackward

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
		val resolvedChange = createUnresolvedChange(newValue, 0).resolveBefore
		resolvedChange.assertApplyForward

		// Create change 2 and apply forward			
		val resolvedChange2 = createUnresolvedChange(newValue2, 0).resolveBefore
		resolvedChange2.assertApplyForward

		// State after
		assertIsStateAfter

		// apply backward 2
		resolvedChange2.assertApplyBackward

		assertEquals(referenceContent.size, 1)
		assertEquals(referenceContent.get(0), newValue2)

		// apply backward 1
		resolvedChange.assertApplyBackward

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
		val resolvedChange = createUnresolvedChange(newValue, index).resolveBefore as RemoveEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)

		// Apply
		assertThrows(IllegalStateException) [resolvedChange.applyForward]
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
		val resolvedChange = atomicFactory.<NonRoot, NonRoot>createRemoveReferenceChange(invalidAffectedEObject,
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
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(RemoveEReference<Root, NonRoot> change, Root affectedEObject,
		NonRoot newValue) {
		assertFalse(change.isResolved)
		assertNotSame(change.affectedEObject, affectedEObject)
		assertNotSame(change.oldValue, newValue)
	}

	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(RemoveEReference<Root, NonRoot> change, Root affectedEObject,
		NonRoot newValue) {
		assertTrue(change.isResolved)
		assertSame(change.affectedEObject, affectedEObject)
		assertSame(change.oldValue, newValue)
	}

	/**
	 * Creates new unresolved change.
	 */
	def private RemoveEReference<Root, NonRoot> createUnresolvedChange(NonRoot oldValue, int index) {
		return atomicFactory.createRemoveReferenceChange(affectedEObject, affectedFeature, oldValue, index)
	}
}

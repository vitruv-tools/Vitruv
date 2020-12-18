package tools.vitruv.framework.tests.echange.feature.reference

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertSame
import static org.junit.jupiter.api.Assertions.assertNotSame
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.*

/**
 * Test class for the concrete {@link ReplaceSingleValuedEReference} EChange,
 * which replaces the value of a reference with a new one.
 */
class ReplaceSingleValuedEReferenceTest extends ReferenceEChangeTest {
	var NonRoot oldValue
	var EReference affectedFeature

	@BeforeEach
	def void prepareElements() {
		oldValue = aet.NonRoot
	}

	/**
	 * Test resolves a {@link ReplaceSingleValuedEReference} EChange with correct parameters.
	 * The model is in the state before the change.
	 * The reference is a non containment reference so the new object is in
	 * the resource.
	 */
	@Test
	def void resolveBeforeSingleValuedNonContainmentTest() {
		// Set state before
		isNonContainmentTest

		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.assertIsNotResolved(affectedEObject, oldValue, newValue)

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore as ReplaceSingleValuedEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)
	}

	/**
	 * Test resolves a {@link ReplaceSingleValuedEReference} EChange with correct parameters.
	 * The model is in the state before the change.
	 * The reference is a containment reference, so the new object is in the staging area
	 */
	@Test
	def void resolveBeforeSingleValuedContainmentTest2() {
		// Set state before
		isContainmentTest

		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.assertIsNotResolved(affectedEObject, oldValue, newValue)

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore as ReplaceSingleValuedEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)
	}

	/**
	 * Test resolves a {@link ReplaceSingleValuedEReference} EChange with correct parameters.
	 * The model is in the state after the change.
	 * The reference is a non containment reference, so the old value is in the resource.
	 */
	@Test
	def void resolveAfterSingleValuedNonContainmentTest() {
		// Set state before
		isNonContainmentTest

		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.assertIsNotResolved(affectedEObject, oldValue, newValue)

		// Set state after
		prepareReference(newValue)

		// Resolve	
		val resolvedChange = unresolvedChange.resolveAfter as ReplaceSingleValuedEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)
	}

	/**
	 * Test resolves a {@link ReplaceSingleValuedEReference} EChange with correct parameters.
	 * The model is in the state after the change.
	 * The reference is a containment reference, so the old value is in the staging area.
	 */
	@Test
	def void resolveAfterSingleValuedContainmentTest() {
		// Set state before
		isContainmentTest

		// Create change
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.assertIsNotResolved(affectedEObject, oldValue, newValue)

		// Set state after
		prepareReference(newValue)

		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter as ReplaceSingleValuedEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)
	}

	/**
	 * Tests whether resolving the {@link ReplaceSingleValuedEReference} EChange
	 * returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Set state before
		isNonContainmentTest

		// Create change
		val unresolvedChange = createUnresolvedChange()

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests applying the {@link ReplaceSingleValuedEReference} EChange forward 
	 * by replacing a single value non containment reference in the root element.
	 */
	@Test
	def void applyForwardSingleValuedNonContainmentTest() {
		// Set state before
		isNonContainmentTest

		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().resolveBefore as ReplaceSingleValuedEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)

		// State before
		assertIsStateBefore(null)

		// Apply forward
		resolvedChange.assertApplyForward

		assertIsStateAfter(null)
	}

	/**
	 * Tests applying the {@link ReplaceSingleValuedEReference} EChange forward
	 * by replacing a single value containment reference in the root element.
	 */
	@Test
	def void applyForwardSingleValuedContainmentTest() {
		// Set state before
		isContainmentTest

		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().resolveBefore as ReplaceSingleValuedEReference<Root, NonRoot>

		// State before
		assertIsStateBefore(newValue)

		// Apply forward
		resolvedChange.assertApplyForward

		// State after
		assertIsStateAfter(oldValue)
	}

	/** 
	 * Tests applying the {@link ReplaceSingleValuedEReference} EChange forward
	 * by replacing a single value containment reference in the root element.
	 * The new value is null.
	 */
	@Test
	def void applyForwardSingleValuedContainmentNewValueNullTest() {
		// Set state before
		newValue = null
		isContainmentTest

		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().resolveBefore as ReplaceSingleValuedEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)

		// State before
		assertIsStateBefore(null)

		// Apply forward
		resolvedChange.assertApplyForward

		// State after
		assertIsStateAfter(oldValue)
	}

	/** 
	 * Tests applying the {@link ReplaceSingleValuedEReference} EChange forward
	 * by replacing a single value containment reference in the root element.
	 * The old value is null.
	 */
	@Test
	def void applyForwardSingleValuedContainmentOldValueNullTest() {
		// Set state before
		oldValue = null
		isContainmentTest

		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().resolveBefore as ReplaceSingleValuedEReference<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, oldValue, newValue)

		// State before
		assertIsStateBefore(newValue)

		// Apply forward
		resolvedChange.assertApplyForward

		// State after
		assertIsStateAfter(null)
	}

	/**
	 * Tests applying a {@link ReplaceSingleValuedEReference} EChange backward
	 * by replacing a single value non containment reference with its old value.
	 */
	@Test
	def void applyBackwardSingleValuedNonContainmentTest() {
		// Set state before
		isNonContainmentTest

		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().resolveBefore as ReplaceSingleValuedEReference<Root, NonRoot>

		// Set state after
		prepareReference(newValue)
		assertIsStateAfter(null)

		// Apply backward
		resolvedChange.assertApplyBackward

		// State before
		assertIsStateBefore(null)
	}

	/**
	 * Tests applying a {@link ReplaceSingleValuedEReference} EChange backward
	 * by replacing a single value containment reference with its old value.
	 */
	@Test
	def void applyBackwardSingleValuedContainmentTest() {
		// Set state before
		isContainmentTest

		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().resolveBefore as ReplaceSingleValuedEReference<Root, NonRoot>

		// Set state after
		prepareReference(newValue)
		assertIsStateAfter(oldValue)

		// Apply backward
		resolvedChange.assertApplyBackward

		// State before
		assertIsStateBefore(newValue)
	}

	/**
	 * Tests applying a {@link ReplaceSingleValuedEReference} EChange backward
	 * by replacing a single value containment reference with its old value.
	 * The new value was null.
	 */
	@Test
	def void applyBackwardSingleValuedContainmentNewValueNullTest() {
		// Set state before
		newValue = null
		isContainmentTest

		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().resolveBefore as ReplaceSingleValuedEReference<Root, NonRoot>

		// Set state after
		prepareReference(newValue)
		assertIsStateAfter(oldValue)

		// Apply backward
		resolvedChange.assertApplyBackward

		// State before
		assertIsStateBefore(null)
	}

	/**
	 * Tests applying a {@link ReplaceSingleValuedEReference} EChange backward
	 * by replacing a single value containment reference with its old value.
	 * The old value was null.
	 */
	@Test
	def void replaceSingleValuedEReferenceApplyBackwardOldValueNullTest() {
		// Set state before
		oldValue = null
		isContainmentTest

		// Create change (resolved)
		val resolvedChange = createUnresolvedChange().resolveBefore

		// Set state after
		prepareReference(newValue)
		assertIsStateAfter(null)

		// Apply backward
		resolvedChange.assertApplyBackward

		// State before
		assertIsStateBefore(newValue)
	}

	/**
	 * Starts a test with a containment feature and sets state before.
	 */
	def private void isContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		prepareReference(oldValue)
	}

	/**
	 * Starts a test with a non containment feature and sets state before.
	 */
	def private void isNonContainmentTest() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE
		prepareReference(oldValue)
		prepareResource
	}

	/**
	 * Sets the value of the affected feature.
	 * @param object The new value of the affected feature.
	 */
	def private void prepareReference(EObject object) {
		affectedEObject.eSet(affectedFeature, object)
	}

	/**
	 * Prepares all new and old values and stores them in a resource.
	 */
	override protected void prepareResource() {
		super.prepareResource
		resource.contents.add(oldValue)
	}

	/**
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore(NonRoot valueInStagingArea) {
		resourceIsStateBefore
		oldValue.assertEqualsOrCopy(affectedEObject.eGet(affectedFeature) as EObject)
	}

	/**
	 * Resource is in state before the change.
	 */
	def private void resourceIsStateBefore() {
		if (!affectedFeature.containment) {
			assertEquals(resourceContent.size, 4)
			newValue.assertEqualsOrCopy(resourceContent.get(1))
			newValue2.assertEqualsOrCopy(resourceContent.get(2))
			oldValue.assertEqualsOrCopy(resourceContent.get(3))
		} else {
			assertEquals(resourceContent.size, 1)
		}
	}

	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter(NonRoot valueInStagingArea) {
		resourceIsStateBefore
		newValue.assertEqualsOrCopy(affectedEObject.eGet(affectedFeature) as EObject)
	}

	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(ReplaceSingleValuedEReference<Root, NonRoot> change,
		Root affectedEObject, NonRoot oldValue, NonRoot newValue) {
		assertFalse(change.isResolved)
		assertNotSame(change.affectedEObject, affectedEObject)
		assertNotSame(change.oldValue, oldValue)
		assertNotSame(change.newValue, newValue)
	}

	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(ReplaceSingleValuedEReference<Root, NonRoot> change, Root affectedEObject,
		NonRoot oldValue, NonRoot newValue) {
		assertTrue(change.isResolved)
		assertSame(change.affectedEObject, affectedEObject)
		assertSame(change.oldValue, oldValue)
		assertSame(change.newValue, newValue)
	}

	/**
	 * Creates new unresolved change.
	 */
	def private ReplaceSingleValuedEReference<Root, NonRoot> createUnresolvedChange() {
		return atomicFactory.createReplaceSingleReferenceChange(affectedEObject, affectedFeature, oldValue, newValue)
	}
}

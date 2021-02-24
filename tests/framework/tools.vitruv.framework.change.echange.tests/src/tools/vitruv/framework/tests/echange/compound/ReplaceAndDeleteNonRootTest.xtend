package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.ecore.EReference
import tools.vitruv.framework.tests.echange.EChangeTest

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import tools.vitruv.framework.change.echange.EChange
import java.util.List
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNull
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.*
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply

/**
 * Test class for the concrete {@link ReplaceAndDeleteNonRoot} EChange,
 * which removes a non root EObject from a single valued containment reference
 * and delets it.
 */
class ReplaceAndDeleteNonRootTest extends EChangeTest {
	var Root affectedEObject
	var EReference affectedFeature
	var NonRoot oldNonRootObject

	@BeforeEach
	def void beforeTest() {
		affectedEObject = rootObject
		uuidGeneratorAndResolver.generateUuid(affectedEObject)
		affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		oldNonRootObject = aet.NonRoot
		uuidGeneratorAndResolver.generateUuid(oldNonRootObject)
		prepareStateBefore
	}

	/**
	 * Resolves a {@link ReplaceAndDeleteNonRoot} EChange. The model is in state
	 * before the change, so the old non root element is still in the single valued
	 * containment reference.
	 */
	@Test
	def void resolveBeforeTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(oldNonRootObject)
		unresolvedChange.assertIsNotResolved

		// Set state after
		prepareStateAfter

		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter
		resolvedChange.assertIsResolved(affectedEObject, oldNonRootObject)

		// Resolving applies all changes and reverts them, so the model should be unaffected.
		assertIsStateAfter
	}

	/**
	 * Resolves a {@link ReplaceAndDeleteNonRoot} EChange. The model is in state
	 * after the change, so the old non root element is deleted and the single valued
	 * containment reference is null.
	 */
	@Test
	def void resolveAfterTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(oldNonRootObject)
		unresolvedChange.assertIsNotResolved

		// Set state after
		prepareStateAfter

		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter
		resolvedChange.assertIsResolved(affectedEObject, oldNonRootObject)

		// Resolving applies all changes and reverts them, so the model should be unaffected.
		assertIsStateAfter
	}

	/**
	 * Tests whether resolving the {@link ReplaceAndDeleteNonRoot} EChange
	 * returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(oldNonRootObject)

		// Resolve		
		val resolvedChange = unresolvedChange.resolveBefore
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests the {@link ReplaceAndDeleteNonRoot} EChange by applying it forward.
	 * Removes a non root object from a single valued containment reference and deletes it.
	 */
	@Test
	def void applyForwardTest() {
		// Create and resolve and apply
		val resolvedChange = createUnresolvedChange(oldNonRootObject).resolveBefore
		resolvedChange.assertApplyForward

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests the {@link ReplaceAndDeleteNonRoot} EChange by applying it backward.
	 * Creates and inserts an old non root object into a single valued containment
	 * reference.
	 */
	@Test
	def void applyBackwardTest() {
		// Create and resolve
		val resolvedChange = createUnresolvedChange(oldNonRootObject).resolveBefore

		// Set state after
		prepareStateAfter

		// Apply
		resolvedChange.assertApplyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Sets the state before the change.
	 */
	def private void prepareStateBefore() {
		affectedEObject.eSet(affectedFeature, oldNonRootObject)
		assertIsStateBefore
	}

	/**
	 * Sets the state after the change.
	 */
	def private void prepareStateAfter() {
		affectedEObject.eSet(affectedFeature, null)
		assertIsStateAfter
	}

	/**
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore() {
		assertThat(oldNonRootObject, equalsDeeply(affectedEObject.eGet(affectedFeature) as NonRoot))
	}

	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
		assertNull(affectedEObject.eGet(affectedFeature))
	}

	/**
	 * Change is not resolved.
	 */
	def protected static void assertIsNotResolved(List<? extends EChange> changes) {
		EChangeTest.assertIsNotResolved(changes)
		assertEquals(2, changes.size)
		val replaceChange = assertType(changes.get(0), ReplaceSingleValuedEReference)
		val deleteChange = assertType(changes.get(1), DeleteEObject)
		assertEquals(replaceChange.oldValueID, deleteChange.affectedEObjectID)
	}

	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(List<EChange> changes, Root affectedEObject, NonRoot oldValue) {
		changes.assertIsResolved
		assertEquals(2, changes.size)
		val ReplaceSingleValuedEReference<?,?> replaceChange = assertType(changes.get(0), ReplaceSingleValuedEReference)
		val deleteChange = assertType(changes.get(1), DeleteEObject)
		assertThat(replaceChange.oldValue, equalsDeeply(oldValue))
		assertThat(deleteChange.affectedEObject, equalsDeeply(oldValue))
		assertThat(replaceChange.affectedEObject, equalsDeeply(affectedEObject))
	}

	/**
	 * Creates new unresolved change.
	 */
	def private List<EChange> createUnresolvedChange(NonRoot oldNonRoot) {
		return compoundFactory.createReplaceAndDeleteNonRootChange(affectedEObject, affectedFeature, oldNonRoot)
	}
}

package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EReference
import tools.vitruv.framework.tests.echange.feature.reference.ReferenceEChangeTest

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import tools.vitruv.framework.change.echange.EChange
import java.util.List
import tools.vitruv.framework.tests.echange.EChangeTest
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import tools.vitruv.framework.change.echange.eobject.DeleteEObject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNull
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply

/**
 * Test class for the concrete {@link RemoveAndDeleteNonRoot} EChange,
 * which removes a non root element reference from a containment reference 
 * list and deletes it.
 */
class RemoveAndDeleteNonRootTest extends ReferenceEChangeTest {
	var EReference affectedFeature
	var EList<NonRoot> referenceContent

	@BeforeEach
	def void prepareState() {
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		prepareStateBefore
	}

	/**
	 * Resolves a {@link RemoveAndDeleteNonRoot} EChange. The model is in state
	 * before the change, so the non root element is in a containment reference.
	 */
	@Test
	def void resolveBeforeTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(affectedEObject, newValue, 0)
		unresolvedChange.assertIsNotResolved

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore
		resolvedChange.assertIsResolved(affectedEObject, newValue)

		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		assertIsStateBefore
	}

	/**
	 * Tests whether resolving the {@link RemoveAndDeleteNonRoot} EChange
	 * returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(affectedEObject, newValue, 0)

		// Resolve		
		val resolvedChange = unresolvedChange.resolveBefore
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests the {@link RemoveAndDeleteNonRoot} EChange by applying it forward.
	 * Removes and deletes a non root element from a containment reference.
	 */
	@Test
	def void applyForwardTest() {
		// Create and resolve change 1
		val resolvedChange = createUnresolvedChange(affectedEObject, newValue, 0).resolveBefore

		// Apply forward 1
		resolvedChange.assertApplyForward

		assertEquals(referenceContent.size, 1)
		assertFalse(referenceContent.contains(newValue))
		assertTrue(referenceContent.contains(newValue2))

		// Create and resolve change 2
		val resolvedChange2 = createUnresolvedChange(affectedEObject, newValue2, 0).resolveBefore

		// Apply forward 2
		resolvedChange2.assertApplyForward

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests the {@link RemoveAndDeleteNonRoot} EChange by applying it backward.
	 * Creates and reinserts the removed object.
	 */
	@Test
	def void applyBackwardTest() {
		// Create and resolve and apply change 1
		val resolvedChange = createUnresolvedChange(affectedEObject, newValue, 0).resolveBefore
		resolvedChange.assertApplyForward

		// Create and resolve and apply change 2
		val resolvedChange2 = createUnresolvedChange(affectedEObject, newValue2, 0).resolveBefore
		resolvedChange2.assertApplyForward

		// State after change
		assertIsStateAfter

		// Apply backward 2
		resolvedChange2.assertApplyBackward

		assertEquals(referenceContent.size, 1)
		assertTrue(referenceContent.contains(newValue2))

		// Apply backward 1
		resolvedChange.assertApplyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Sets the state of the model before the changes.
	 */
	def private void prepareStateBefore() {
		referenceContent.add(newValue)
		referenceContent.add(newValue2)
		assertIsStateBefore
	}

	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		assertEquals(referenceContent.size, 2)
		assertThat(newValue, equalsDeeply(referenceContent.get(0)))
		assertThat(newValue2, equalsDeeply(referenceContent.get(1)))
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
	def protected static void assertIsNotResolved(List<? extends EChange> changes) {
		EChangeTest.assertIsNotResolved(changes)
		assertEquals(2, changes.size)
		val removeChange = assertType(changes.get(0), RemoveEReference)
		val deleteChange = assertType(changes.get(1), DeleteEObject)
		assertEquals(removeChange.oldValueID, deleteChange.affectedEObjectID)
	}

	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(List<EChange> changes, Root affectedRootObject,
		NonRoot removedNonRootObject) {
		changes.assertIsResolved
		assertEquals(2, changes.size)
		val RemoveEReference<?,?> removeChange = assertType(changes.get(0), RemoveEReference)
		val deleteChange = assertType(changes.get(1), DeleteEObject)
		assertThat(deleteChange.affectedEObject, equalsDeeply(removedNonRootObject))
		assertThat(removeChange.oldValue, equalsDeeply(removedNonRootObject))
		assertThat(removeChange.affectedEObject, equalsDeeply(affectedRootObject))

	}

	/**
	 * Creates new unresolved change.
	 */
	def private List<EChange> createUnresolvedChange(Root affectedRootObject, NonRoot newNonRoot, int index) {
		return compoundFactory.createRemoveAndDeleteNonRootChange(affectedRootObject, affectedFeature, newNonRoot,
			index)
	}

}

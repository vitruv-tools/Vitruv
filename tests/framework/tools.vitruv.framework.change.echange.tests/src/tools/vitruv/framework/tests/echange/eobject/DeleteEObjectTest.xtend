package tools.vitruv.framework.tests.echange.eobject

import allElementTypes.Root
import tools.vitruv.framework.change.echange.eobject.DeleteEObject

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * Test class for the concrete {@link DeleteEObject} EChange,
 * which deletes a EObject from the staging area.
 */
class DeleteEObjectTest extends EObjectTest {
	@BeforeEach
	def void beforeTest() {
		prepareStateBefore(createdObject)
		uuidGeneratorAndResolver.generateUuid(createdObject) // Is used as existing object, so needs to have a UUID
		uuidGeneratorAndResolver.generateUuid(createdObject2) // Is used as existing object, so needs to have a UUID
	}

	/**
	 * Tests whether resolving the {@link DeleteEObjectTest} EChange returns
	 * the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(createdObject)

		// Resolve		
		val resolvedChange = unresolvedChange.resolveBefore
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests a {@link DeleteEObject} EChange by deleting a 
	 * created EObject from the staging area.
	 */
	@Test
	def void applyForwardTest() {
		// Create change and resolve
		val resolvedChange = createUnresolvedChange(createdObject).resolveBefore as DeleteEObject<Root>

		// Apply forward
		resolvedChange.assertApplyForward

		// State after
		assertIsStateAfter

		// Now another change would remove a object and put it in the staging area
		prepareStateBefore(createdObject2)

		// Create change and resolve 2
		val resolvedChange2 = createUnresolvedChange(createdObject2).resolveBefore as DeleteEObject<Root>

		// Apply forward 2
		resolvedChange2.assertApplyForward

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests a {@link DeleteEObject} EChange by reverting it.
	 * Adds a deleted object to the staging area again.
	 */
	@Test
	def void applyBackwardTest() {
		// Set state after
		prepareStateAfter

		// Create change and resolve 1
		val resolvedChange = createUnresolvedChange(createdObject).resolveAfter as DeleteEObject<Root>

		// Apply backward 1
		resolvedChange.assertApplyBackward

		// State before
		assertIsStateBefore(createdObject)

		// Now another change would be applied and the object would be inserted in.
		prepareStateAfter

		// Create change and resolve 2
		val resolvedChange2 = createUnresolvedChange(createdObject2).resolveAfter as DeleteEObject<Root>

		// Apply backward 1
		resolvedChange2.assertApplyBackward

		// State before
		assertIsStateBefore(createdObject2)
	}

	/**
	 * Sets the state of the model before a change.
	 */
	def private void prepareStateBefore(Root stagingAreaObject) {
		assertIsStateBefore(stagingAreaObject)
	}

	/**
	 * Sets the state of the model after a change.
	 */
	def private void prepareStateAfter() {
		assertIsStateAfter
	}

	/**
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore(Root stagingAreaObject) {
	}

	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
	}

	/**
	 * Creates new unresolved change.
	 */
	def private DeleteEObject<Root> createUnresolvedChange(Root oldObject) {
		return atomicFactory.createDeleteEObjectChange(oldObject)
	}

}

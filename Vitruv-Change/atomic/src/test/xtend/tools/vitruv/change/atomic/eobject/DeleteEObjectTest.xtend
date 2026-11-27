package tools.vitruv.change.atomic.eobject

import allElementTypes.Root
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.uuid.Uuid

/**
 * Test class for the concrete {@link DeleteEObject} EChange,
 * which deletes a EObject from the staging area.
 */
class DeleteEObjectTest extends EObjectTest {
	@BeforeEach
	def void beforeTest() {
		prepareStateBefore(createdObject)
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
		val resolvedChange = unresolvedChange.applyForwardAndResolve
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests a {@link DeleteEObject} EChange by deleting a 
	 * created EObject from the staging area.
	 */
	@Test
	def void applyForwardTest() {
		// Create change and resolve
		val unresolvedChange = createUnresolvedChange(createdObject)
		unresolvedChange.applyForwardAndResolve

		// State after
		assertIsStateAfter

		// Now another change would remove a object and put it in the staging area
		prepareStateBefore(createdObject2)

		// Create change and resolve 2
		val unresolvedChange2 = createUnresolvedChange(createdObject2)
		unresolvedChange2.applyForwardAndResolve

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests a {@link DeleteEObject} EChange by reverting it.
	 * Adds a deleted object to the staging area again.
	 */
	@Test
	def void applyBackwardTest() {
		// Create change and resolve 1
		val resolvedChange = createUnresolvedChange(createdObject).applyForwardAndResolve

		// Apply backward 1
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore(createdObject)
		
		prepareStateBefore(createdObject2)

		// Now another change would be applied and the object would be inserted in.
		// Create change and resolve 2
		val resolvedChange2 = createUnresolvedChange(createdObject2).applyForwardAndResolve
		
		// Apply backward 2
		resolvedChange2.applyBackward

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
	def private EChange<Uuid> createUnresolvedChange(Root oldObject) {
		return atomicFactory.createDeleteEObjectChange(oldObject).unresolve
	}

}

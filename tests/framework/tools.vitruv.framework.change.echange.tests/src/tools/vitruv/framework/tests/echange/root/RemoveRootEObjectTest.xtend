package tools.vitruv.framework.tests.echange.root

import allElementTypes.Root
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.root.RemoveRootEObject

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertSame
import static org.junit.jupiter.api.Assertions.assertNotSame
import static org.hamcrest.MatcherAssert.assertThat
import static tools.vitruv.testutils.matchers.ModelMatchers.equalsDeeply

/**
 * Test class for the concrete {@link RemoveRootEObject} EChange,
 * which removes a root element from a resource.
 */
class RemoveRootEObjectTest extends RootEChangeTest {
	/**
	 * Inserts new root objects into the resource
	 * to remove them in the tests.
	 */
	@BeforeEach
	def void prepareState() {
		prepareStateBefore
	}

	/**
	 * Test resolves a {@link RemoveRootEObject} EChange, with a root 
	 * object already in the resource. This happens when the model is
	 * in state before the change is applied forward.
	 */
	@Test
	def void resolveBeforeTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)
		unresolvedChange.assertIsNotResolved(newRootObject)
		assertIsStateBefore

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore as RemoveRootEObject<Root>
		resolvedChange.assertIsResolved(newRootObject, resource)
		assertIsStateBefore
	}

	/**
	 * Test resolves a {@link RemoveRootEObject} EChange, with a root
	 * object in the staging area. This happens when the model is
	 * in state after the change was applied.
	 */
	@Test
	def void resolveAfterTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)
		unresolvedChange.assertIsNotResolved(newRootObject)

		// Resolve
		newRootObject.registerAsPreexisting
		val resolvedChange = unresolvedChange.resolveAfter as RemoveRootEObject<Root>
		resolvedChange.assertIsResolved(newRootObject, resource)
	}

	/**
	 * Tests whether resolving the {@link RemoveRootEObject} EChange
	 * returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 0)
		unresolvedChange.assertIsNotResolved(newRootObject)

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore as RemoveRootEObject<Root>
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests applying the {@link RemoveRootEObject} EChange 
	 * by removing two root elements from a resource.
	 */
	@Test
	def void applyForwardTest() {
		// Create and resolve change 1
		val resolvedChange = createUnresolvedChange(newRootObject, 1).resolveBefore as RemoveRootEObject<Root>
		resolvedChange.assertIsResolved(newRootObject, resource)

		// Apply forward 1	
		resolvedChange.assertApplyForward

		assertEquals(resourceContent.size, 2)
		assertThat(newRootObject2, equalsDeeply(resourceContent.get(1)))

		// Create and resolve change 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2, 1).resolveBefore as RemoveRootEObject<Root>
		resolvedChange2.assertIsResolved(newRootObject2, resource)

		// Apply forward 2	
		resolvedChange2.assertApplyForward

		// State after					
		assertIsStateAfter
	}

	/**
	 * Tests applying two {@link RemoveRootEObject} EChanges
	 * by inserts two removed root objects in a resource.
	 */
	@Test
	def void applyBackwardTest() {
		// Create and resolve and apply forward 1
		val resolvedChange = createUnresolvedChange(newRootObject, 0).resolveBefore as RemoveRootEObject<Root>
		resolvedChange.assertApplyForward

		// Create and resolve and apply forward 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2, 0).resolveBefore as RemoveRootEObject<Root>
		resolvedChange2.assertApplyForward

		// State after
		assertIsStateAfter

		// Apply backward 2
		resolvedChange2.assertApplyBackward

		assertEquals(resourceContent.size, 2)
		assertTrue(resourceContent.contains(newRootObject2))

		// Apply backward 1
		resolvedChange.assertApplyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Tests a {@link RemoveRootEObject} EChange with invalid index.
	 */
	@Test
	def void invalidIndexTest() {
		var index = 5

		// Create and resolve change
		val resolvedChange = createUnresolvedChange(newRootObject, index).resolveBefore as RemoveRootEObject<Root>
		assertNull(resolvedChange)
	}

	/**
	 * Sets the state of the model before the changes.
	 */
	def private void prepareStateBefore() {
		resourceContent.add(1, newRootObject)
		resourceContent.add(2, newRootObject2)
		assertIsStateBefore
	}

	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		// index 0 is root object
		assertEquals(resourceContent.size, 3)
		assertThat(newRootObject, equalsDeeply(resourceContent.get(1)))
		assertThat(newRootObject2, equalsDeeply(resourceContent.get(2)))
	}

	/**
	 * Model is in state after the changes.
	 */
	def private void assertIsStateAfter() {
		assertEquals(resourceContent.size, 1)
	}

	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(RemoveRootEObject<Root> change, Root oldValue) {
		assertFalse(change.isResolved)
		assertNotSame(change.oldValue, oldValue)
		assertNull(change.resource)
	}

	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(RemoveRootEObject<Root> change, Root oldValue, Resource resource) {
		assertTrue(change.isResolved)
		assertSame(change.oldValue, oldValue)
		assertSame(change.resource, resource)
	}

	/**
	 * Creates new unresolved change.
	 */
	def private RemoveRootEObject<Root> createUnresolvedChange(Root rootObject, int index) {
		return atomicFactory.createRemoveRootChange(rootObject, resource, index)
	}
}

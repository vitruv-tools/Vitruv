package tools.vitruv.framework.tests.echange.root

import allElementTypes.Root
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.root.InsertRootEObject

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
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
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolver.*

/**
 * Test class for the concrete {@link InsertRootEObject} EChange,
 * which inserts a new root element into a resource.
 */
class InsertRootEObjectTest extends RootEChangeTest {
	/**
	 * Test resolves a {@link InsertRootEObject} EChange with a new root which is
	 * in the staging area and not in the new resource. This happens
	 * when the model is in state before the change and the change will be applied forward.
	 */
	@Test
	def void resolveBefore() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)
		unresolvedChange.assertIsNotResolved(newRootObject)
		assertIsStateBefore

		// Resolve
		newRootObject.registerAsPreexisting
		val resolvedChange = unresolvedChange.resolveBefore as InsertRootEObject<Root>
		resolvedChange.assertIsResolved(newRootObject, resource)
		assertIsStateBefore
	}

	/**
	 * Test resolves a {@link InsertRootEObject} EChange with a root object which is already
	 * in the resource. This happens when the model is in state after the change
	 * and the change will be applied backward.
	 */
	@Test
	def void resolveAfterTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)
		unresolvedChange.assertIsNotResolved(newRootObject)
		assertIsStateBefore

		// Set state after
		resourceContent.add(1, newRootObject)

		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter as InsertRootEObject<Root>
		resolvedChange.assertIsResolved(newRootObject, resource)
	}

	/**
	 * Tests whether resolving the {@link InsertRootEObject} EChange
	 * returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)

		// Resolve
		newRootObject.registerAsPreexisting
		val resolvedChange = unresolvedChange.resolveBefore
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests applying a {@link InsertRootEObject} EChange forward 
	 * by inserting a new root elements into a resource.
	 */
	@Test
	def void applyForwardTest() {
		assertIsStateBefore

		// Create change and resolve 1
		newRootObject.registerAsPreexisting
		val resolvedChange = createUnresolvedChange(newRootObject, 1).resolveBefore as InsertRootEObject<Root>
		resolvedChange.assertIsResolved(newRootObject, resource)

		// Apply forward 1
		resolvedChange.assertApplyForward

		assertEquals(resourceContent.size, 2)
		assertTrue(newRootObject == resourceContent.get(1))

		// Create change and resolve 2
		newRootObject2.registerAsPreexisting
		val resolvedChange2 = createUnresolvedChange(newRootObject2, 2).resolveBefore as InsertRootEObject<Root>
		resolvedChange2.assertIsResolved(newRootObject2, resource)

		// Apply forward 2
		resolvedChange2.assertApplyForward

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests applying two {@link InsertRootEObject} EChanges backward
	 * by removing two inserted root objects from a resource.
	 */
	@Test
	def void applyBackwardTest() {
		// Set state before
		assertIsStateBefore

		// Create change and resolve and apply forward 1
		newRootObject.registerAsPreexisting
		val resolvedChange = createUnresolvedChange(newRootObject, 1).resolveBefore as InsertRootEObject<Root>
		resolvedChange.assertApplyForward

		// Create change and resolve and apply forward 2
		newRootObject2.registerAsPreexisting
		val resolvedChange2 = createUnresolvedChange(newRootObject2, 2).resolveBefore as InsertRootEObject<Root>
		resolvedChange2.assertApplyForward

		// State after
		assertIsStateAfter

		// Apply backward 2
		resolvedChange2.assertApplyBackward

		assertEquals(resourceContent.size, 2)
		assertThat(newRootObject, equalsDeeply(resourceContent.get(1)))

		// Apply backward 1
		resolvedChange.assertApplyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Tests applying the {@link InsertRootEObject} EChange with invalid index.
	 */
	@Test
	def void invalidIndexTest() {
		// Set state before
		var index = 5
		assertTrue(resourceContent.size < index)

		// Create change and resolve
		newRootObject.registerAsPreexisting
		val resolvedChange = createUnresolvedChange(newRootObject, index).resolveBefore as InsertRootEObject<Root>
		resolvedChange.assertIsResolved(newRootObject, resource)

		// Apply		
		assertThrows(IllegalStateException) [resolvedChange.applyForward]
	}

	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		assertEquals(resourceContent.size, 1)
	}

	/**
	 * Model is in state after the changes.
	 */
	def private void assertIsStateAfter() {
		assertEquals(resourceContent.size, 3)
		assertThat(newRootObject, equalsDeeply(resourceContent.get(1)))
		assertThat(newRootObject2, equalsDeeply(resourceContent.get(2)))
	}

	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(InsertRootEObject<Root> change, Root newValue) {
		assertFalse(change.isResolved)
		assertNotSame(change.newValue, newValue)
		assertNull(change.resource)
	}

	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(InsertRootEObject<Root> change, Root newValue, Resource resource) {
		assertTrue(change.isResolved)
		assertSame(change.newValue, newValue)
		assertSame(change.resource, resource)
	}

	/**
	 * Creates new unresolved change.
	 */
	def private InsertRootEObject<Root> createUnresolvedChange(Root rootObject, int index) {
		return atomicFactory.createInsertRootChange(rootObject, resource, index)
	}
}

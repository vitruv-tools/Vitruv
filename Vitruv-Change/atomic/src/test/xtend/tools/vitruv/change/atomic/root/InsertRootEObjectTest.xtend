package tools.vitruv.change.atomic.root

import allElementTypes.Root
import org.eclipse.emf.ecore.EObject
import org.junit.jupiter.api.Test
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.uuid.Uuid

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply

/**
 * Test class for the concrete {@link InsertRootEObject} EChange,
 * which inserts a new root element into a resource.
 */
class InsertRootEObjectTest extends RootEChangeTest {
	
	/**
	 * Tests whether resolving the {@link InsertRootEObject} EChange
	 * returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)

		// Resolve
		val resolvedChange = unresolvedChange.applyForwardAndResolve as InsertRootEObject<EObject>
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
		assertThat(resolvedChange.resource, is(rootObject.eResource))
		assertEquals(resolvedChange.uri, rootObject.eResource.URI.toString)
	}

	/**
	 * Tests applying a {@link InsertRootEObject} EChange forward 
	 * by inserting a new root elements into a resource.
	 */
	@Test
	def void applyForwardTest() {
		assertIsStateBefore

		// Create change and resolve 1
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)
		unresolvedChange.applyForwardAndResolve

		assertEquals(resourceContent.size, 2)
		assertTrue(newRootObject == resourceContent.get(1))

		// Create change and resolve 2
		val unresolvedChange2 = createUnresolvedChange(newRootObject2, 2)
		unresolvedChange2.applyForwardAndResolve

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
		val resolvedChange = createUnresolvedChange(newRootObject, 1).applyForwardAndResolve

		// Create change and resolve and apply forward 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2, 2).applyForwardAndResolve

		// State after
		assertIsStateAfter

		// Apply backward 2
		resolvedChange2.applyBackward

		assertEquals(resourceContent.size, 2)
		assertThat(newRootObject, equalsDeeply(resourceContent.get(1)))

		// Apply backward 1
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Tests applying the {@link InsertRootEObject} EChange with invalid index.
	 */
	@Test
	def void invalidIndexTest() {
		// Set state before
		val index = 5
		assertTrue(resourceContent.size < index)
		
		val invalidChange = createUnresolvedChange(newRootObject, index)

		// Apply		
		assertThrows(IllegalStateException) [invalidChange.applyForwardAndResolve]
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
	 * Creates new unresolved change.
	 */
	def private EChange<Uuid> createUnresolvedChange(Root rootObject, int index) {
		return atomicFactory.createInsertRootChange(rootObject, resource, index).unresolve
	}
}

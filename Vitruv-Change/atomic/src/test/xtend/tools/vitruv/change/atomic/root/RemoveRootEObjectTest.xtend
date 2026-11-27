package tools.vitruv.change.atomic.root

import allElementTypes.Root
import org.eclipse.emf.ecore.EObject
import org.junit.jupiter.api.BeforeEach
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
	 * Tests whether resolving the {@link RemoveRootEObject} EChange
	 * returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)

		// Resolve
		val resolvedChange = unresolvedChange.applyForwardAndResolve as RemoveRootEObject<EObject>
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
		assertThat(resolvedChange.resource, is(rootObject.eResource))
		assertEquals(resolvedChange.uri, rootObject.eResource.URI.toString)
	}

	/**
	 * Tests applying the {@link RemoveRootEObject} EChange 
	 * by removing two root elements from a resource.
	 */
	@Test
	def void applyForwardTest() {
		// Create and resolve change 1
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)
		unresolvedChange.applyForwardAndResolve

		assertEquals(resourceContent.size, 2)
		assertThat(newRootObject2, equalsDeeply(resourceContent.get(1)))

		// Create and resolve change 2
		val unresolvedChange2 = createUnresolvedChange(newRootObject2, 1)
		unresolvedChange2.applyForwardAndResolve

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
		val resolvedChange = createUnresolvedChange(newRootObject, 1).applyForwardAndResolve


		// Create and resolve and apply forward 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2, 1).applyForwardAndResolve

		// State after
		assertIsStateAfter

		// Apply backward 2
		resolvedChange2.applyBackward

		assertEquals(resourceContent.size, 2)
		assertTrue(resourceContent.contains(newRootObject2))

		// Apply backward 1
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Tests a {@link RemoveRootEObject} EChange with invalid index.
	 */
	@Test
	def void invalidIndexTest() {
		val index = 5
		assertTrue(resourceContent.size < index)
		
		val invalidChange = createUnresolvedChange(newRootObject, index)
		assertThrows(IllegalStateException) [invalidChange.applyForwardAndResolve]
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
	 * Creates new unresolved change.
	 */
	def private EChange<Uuid> createUnresolvedChange(Root rootObject, int index) {
		return atomicFactory.createRemoveRootChange(rootObject, resource, index).unresolve
	}
}

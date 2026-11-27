package tools.vitruv.change.atomic.compound

import allElementTypes.Root
import java.util.List
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.root.RootEChangeTest
import tools.vitruv.change.atomic.uuid.Uuid

import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.change.testutils.matchers.ModelMatchers.equalsDeeply

/**
 * Test class for the concrete {@link RemoveAndDeleteRoot} EChange,
 * which removes a root element from a resource and deletes it.
 */
class RemoveAndDeleteRootTest extends RootEChangeTest {
	@BeforeEach
	override void beforeTest() {
		super.beforeTest()
		prepareStateBefore

	}

	/**
	 * Tests whether resolving the {@link RemoveAndDeleteRoot} EChange
	 * returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)

		// Resolve		
		val resolvedChange = unresolvedChange.applyForwardAndResolve
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests the {@link RemoveAndDeleteRoot} EChange by removing
	 * and deleting a root object.
	 */
	@Test
	def void applyForwardTest() {
		// Create and resolve change 1
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)
		unresolvedChange.applyForwardAndResolve

		assertEquals(resourceContent.size, 2)
		assertFalse(resourceContent.contains(newRootObject))
		assertTrue(resourceContent.contains(newRootObject2))

		// Create and resolve change 2
		val unresolvedChange2 = createUnresolvedChange(newRootObject2, 1)
		unresolvedChange2.applyForwardAndResolve

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests the {@link RemoveAndDeleteRoot} EChange by reverting the change.
	 * It creates and inserts a root object.
	 */
	@Test
	def void applyBackwardTest() {
		// Create and resolve and apply change 1
		val resolvedChange = createUnresolvedChange(newRootObject, 1).applyForwardAndResolve

		// Create and resolve and apply change 2
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
	 * Sets the state of the model before the changes.
	 */
	def private void prepareStateBefore() {
		resourceContent.add(newRootObject)
		resourceContent.add(newRootObject2)
		assertIsStateBefore
	}

	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
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
	def private List<EChange<Uuid>> createUnresolvedChange(Root newObject, int index) {
		return compoundFactory.createRemoveAndDeleteRootChange(newObject, resource, index).unresolve
	}
}

package tools.vitruv.change.atomic.compound

import allElementTypes.Root
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.EChangeTest
import tools.vitruv.change.atomic.eobject.CreateEObject
import tools.vitruv.change.atomic.uuid.Uuid

import static org.hamcrest.CoreMatchers.instanceOf
import static org.hamcrest.MatcherAssert.assertThat
import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue
import static tools.vitruv.change.atomic.util.EChangeAssertHelper.*
import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.*

/**
 * Test class for the concrete {@link CreateAndInsertRoot} EChange,
 * which creates a new root EObject and inserts it in a resource.
 */
class CreateAndInsertRootTest extends EChangeTest {
	var EList<EObject> resourceContent

	/**
	 * Calls setup of the superclass and creates two new root elements
	 * which can be inserted.
	 */
	@BeforeEach
	def void beforeTest() {
		resourceContent = resource.contents
		assertIsStateBefore
	}

	/**
	 * Tests whether resolving the {@link CreateAndInsertRoot} EChange
	 * returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(1)

		// Resolve		
		val resolvedChange = unresolvedChange.applyForwardAndResolve
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests applying the {@link CreateAndInsertRoot} EChange forward
	 * by creating and inserting a new root object.
	 */
	@Test
	def void applyForwardTest() {
		// Create and resolve change 1
		val resolvedChange = createUnresolvedChange(1).applyForwardAndResolve

		assertEquals(resourceContent.size, 2)
		val createChange = assertType(resolvedChange.get(0), CreateEObject)
		assertTrue(resourceContent.contains(createChange.affectedElement))

		// Create and resolve change 2
		val unresolvedChange2 = createUnresolvedChange(2)
		unresolvedChange2.applyForwardAndResolve

		// State after
		assertIsStateAfter
	}

	/**
	 * Tests applying the {@link CreateAndInsertRoot} EChange backward 
	 * by reverting the change. It removes and deletes a root object. 
	 */
	@Test
	def void applyBackwardTest() {
		// Create and resolve and apply change 1
		val resolvedChange = createUnresolvedChange(1).applyForwardAndResolve

		// Create and resolve and apply change 2
		val resolvedChange2 = createUnresolvedChange(2).applyForwardAndResolve

		// State after
		assertIsStateAfter

		// Apply backward 2
		resolvedChange2.applyBackward

		assertEquals(resourceContent.size, 2)
		val createChange = assertType(resolvedChange.get(0), CreateEObject)
		val createChange2 = assertType(resolvedChange2.get(0), CreateEObject)
		assertTrue(resourceContent.contains(createChange.affectedElement))
		assertFalse(resourceContent.contains(createChange2.affectedElement))

		// Apply backward 1
		resolvedChange.applyBackward

		// State before
		assertIsStateBefore
	}

	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		assertEquals(resourceContent.size, 1)
	}

	/**
	 * Model is in state after the changes
	 */
	def private void assertIsStateAfter() {
		assertEquals(resourceContent.size, 3)
		assertThat(resourceContent.get(1), instanceOf(Root))
		assertThat(resourceContent.get(2), instanceOf(Root))
	}

	/**
	 * Creates new unresolved change.
	 */
	def private List<EChange<Uuid>> createUnresolvedChange(int index) {
		return compoundFactory.createCreateAndInsertRootChange(aet.Root, resource, index).unresolve
	}
}

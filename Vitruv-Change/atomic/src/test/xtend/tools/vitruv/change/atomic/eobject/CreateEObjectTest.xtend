package tools.vitruv.change.atomic.eobject

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.uuid.Uuid

import static tools.vitruv.change.testutils.metamodels.AllElementTypesCreators.aet

/**
 * Test class for the concrete {@link CreateEObject} EChange,
 * which creates a new EObject and puts it in the staging area.
 */
class CreateEObjectTest extends EObjectTest {
	@BeforeEach
	def void beforeTest() {
		prepareStateBefore
	}

	/**
	 * Tests whether resolving the {@link CreateEObjectTest} EChange returns
	 * the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange()

		// Resolve		
		val resolvedChange = unresolvedChange.applyForwardAndResolve
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}

	/**
	 * Tests applying a {@link CreateEObject} EChange forward by creating a
	 * new EObject and putting it in the staging area.
	 */
	@Test
	def void applyForwardTest() {
		// Create change and resolve
		val unresolvedChange = createUnresolvedChange()
		unresolvedChange.applyForwardAndResolve

		// State after
		assertIsStateAfter()

		// Now another change would take the object and inserts it in a resource
		prepareStateBefore

		// Create change and resolve 2
		val unresolvedChange2 = createUnresolvedChange()
		unresolvedChange2.applyForwardAndResolve

		// State after
		assertIsStateAfter()
	}

	/**
	 * Tests applying a {@link CreateEObject} EChange backward 
	 * by removing a newly created object from the staging area.
	 */
	@Test
	def void applyBackwardTest() {
		// Create change, resolve it and apply it forward
		val resolvedChange = createUnresolvedChange().applyForwardAndResolve

		// Apply backward
		resolvedChange.applyBackward
		assertIsStateAfter()
	}

	/**
	 * Sets the state of the model before the change.
	 */
	def private void prepareStateBefore() {
		assertIsStateBefore
	}

	/**
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore() {
	}

	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
	}

	/**
	 * Creates new unresolved change.
	 */
	def private EChange<Uuid> createUnresolvedChange() {
		return atomicFactory.createCreateEObjectChange(aet.Root).unresolve
	}

}

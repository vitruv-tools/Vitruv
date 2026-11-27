package tools.vitruv.change.atomic.eobject

import allElementTypes.Root
import org.junit.jupiter.api.Test
import tools.vitruv.change.atomic.EChange
import tools.vitruv.change.atomic.uuid.Uuid

import static org.junit.jupiter.api.Assertions.assertThrows

/**
 * Test class for the abstract class {@link EObjectExistenceEChange} EChange,
 * which creates or deletes a new EObject.
 */
class EObjectExistenceEChangeTest extends EObjectTest {
	/**
	 * Test resolves a {@link EObjectExistenceEChangeTest} EChange with a null
	 * affected EObject.
	 */
	@Test
	def void resolveInvalidAffectedEObjectTest() {
		createdObject = null

		// Create change
		assertThrows(IllegalArgumentException, [createUnresolvedChange(createdObject)])
	}

	/**
	 * Creates new unresolved change.
	 */
	def private EChange<Uuid> createUnresolvedChange(Root createdObject) {
		// The concrete change type CreateEObject will be used for the tests.
		return atomicFactory.createCreateEObjectChange(createdObject).unresolve
	}
}

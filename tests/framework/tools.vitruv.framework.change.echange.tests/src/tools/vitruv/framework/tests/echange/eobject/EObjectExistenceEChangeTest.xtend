package tools.vitruv.framework.tests.echange.eobject

import allElementTypes.Root
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange
import tools.vitruv.framework.change.echange.resolve.StagingArea

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*

/**
 * Test class for the abstract class {@link EObjectExistenceEChange} EChange,
 * which creates or deletes a new EObject.
 */
class EObjectExistenceEChangeTest extends EObjectTest {	
	/**
	 * Test resolves a {@link EObjectExistenceEChangeTest} EChange with 
	 * a new object which was not created yet. So the staging area will be filled.
	 */
	@Test
	def public void resovlveBeforeTest() {	
		// State before
		assertIsStateBefore
		
		// Create change
		val unresolvedChange = createUnresolvedChange(createdObject)
		unresolvedChange.assertIsNotResolved(createdObject)
		assertIsStateBefore
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet)
			as CreateEObject<Root>
		resolvedChange.assertIsResolved(createdObject, stagingArea)
		assertIsStateBefore
	}
	
	/**
	 * Test resolves a {@link EObjectExistenceEChangeTest} EChange with a
	 * new object which was already created and was put in the staging area.
	 */
	@Test
	def public void resolveAfterTest() {
		// State before
		assertIsStateBefore
		
		// Create change
		val unresolvedChange = createUnresolvedChange(createdObject)
		unresolvedChange.assertIsNotResolved(createdObject)
				
		// Set state after
		prepareStagingArea(createdObject)
		assertIsStateAfter	
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet)
			as CreateEObject<Root>
		resolvedChange.assertIsResolved(createdObject, stagingArea)	
		
		// State after
		assertIsStateAfter
	}
	
	/**
	 * Test resolves a {@link EObjectExistenceEChangeTest} EChange with a null
	 * affected EObject.
	 */
	@Test
	def public void resolveInvalidAffectedEObjectTest() {
		createdObject = null
		
		// Create change
		val unresolvedChange = createUnresolvedChange(createdObject)
		Assert.assertFalse(unresolvedChange.isResolved)
		
		// Resolve
		Assert.assertNull(unresolvedChange.resolveBefore(resourceSet) as CreateEObject<Root>)
		Assert.assertNull(unresolvedChange.resolveAfter(resourceSet) as CreateEObject<Root>)		
	}
	
	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(EObjectExistenceEChange<Root> change, Root affectedEObject) {
		Assert.assertFalse(change.isResolved)
		Assert.assertNotSame(change.affectedEObject, affectedEObject)
		Assert.assertNull(change.stagingArea)
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(EObjectExistenceEChange<Root> change, Root affectedEObject, 
		StagingArea stagingArea) {
		Assert.assertTrue(change.isResolved)
		affectedEObject.assertEqualsOrCopy(change.affectedEObject)
		Assert.assertSame(change.stagingArea, stagingArea)	
	}
	
	/**
	 * Model is in state before the change.
	 */
	def private void assertIsStateBefore() {
		Assert.assertTrue(stagingArea.empty)		
	}
	
	/**
	 * Model is in state after the change.
	 */
	def private void assertIsStateAfter() {
		Assert.assertFalse(stagingArea.empty)
		createdObject.assertEqualsOrCopy(stagingArea.peek)
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private EObjectExistenceEChange<Root> createUnresolvedChange(Root createdObject) {
		// The concrete change type CreateEObject will be used for the tests.
		return atomicFactory.createCreateEObjectChange(createdObject, resource)
	}
}
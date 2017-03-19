package tools.vitruv.framework.tests.echange.eobject

import allElementTypes.Root
import org.eclipse.emf.ecore.resource.Resource
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange

/**
 * Test class for the abstract class {@link EObjectExistenceEChange} EChange,
 * which creates or deletes a new EObject.
 */
class EObjectExistenceEChangeTest extends EObjectTest {
	
	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(EObjectExistenceEChange<Root> change, Root affectedEObject) {
		Assert.assertFalse(change.isResolved)
		Assert.assertTrue(change.affectedEObject != affectedEObject)
		Assert.assertNull(change.stagingArea)
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolvedNewObject(EObjectExistenceEChange<Root> change, Root affectedEObject, 
		Resource stagingArea) {
		Assert.assertTrue(change.isResolved)
		// Not the same object, but copy => ID the same
		Assert.assertTrue(change.affectedEObject != affectedEObject)
		Assert.assertEquals(change.affectedEObject.id, affectedEObject.id)
		Assert.assertTrue(change.stagingArea == stagingArea)	
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolvedExistingObject(EObjectExistenceEChange<Root> change, Root affectedEObject,
		Resource stagingArea) {
		Assert.assertTrue(change.isResolved)
		Assert.assertTrue(change.affectedEObject == affectedEObject)
		Assert.assertTrue(change.stagingArea == stagingArea)
	}	
	
	/**
	 * Creates new unresolved change.
	 */
	def private EObjectExistenceEChange<Root> createUnresolvedChange(Root createdObject) {
		// The concrete change type CreateEObject will be used for the tests.
		return atomicFactory.<Root>createCreateEObjectChange(createdObject)
	}
		
	/**
	 * Test resolves a {@link EObjectExistenceEChangeTest} EChange with 
	 * a new object which was not created yet. So the staging area will be filled.
	 */
	@Test
	def public void resovlveEObjectExistenceEChangeTest() {	
		// Create change
		val unresolvedChange = createUnresolvedChange(createdObject)
		unresolvedChange.assertIsNotResolved(createdObject)
		// Staging area is unaffected while resolving the change		
		Assert.assertTrue(stagingArea1.contents.empty) 
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet1)
			as CreateEObject<Root>
		resolvedChange.assertIsResolvedNewObject(createdObject, stagingArea1)	
		Assert.assertTrue(stagingArea1.contents.empty)
	}
	
	/**
	 * Test resolves a {@link EObjectExistenceEChangeTest} EChange with a
	 * new object which was already created and was put in the staging area.
	 */
	@Test
	def public void resolveEObjectExistenceEChangeTest2() {
		// Create change
		val unresolvedChange = createUnresolvedChange(createdObject)
		unresolvedChange.assertIsNotResolved(createdObject)
				
		// Set state after
		prepareStagingArea(createdObject)
		// Staging area is unaffected while resolving the change
		Assert.assertFalse(stagingArea1.contents.empty)
		Assert.assertTrue(stagingArea1.contents.get(0) == createdObject)		
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet1)
			as CreateEObject<Root>
		resolvedChange.assertIsResolvedExistingObject(createdObject, stagingArea1)	
		
		Assert.assertFalse(stagingArea1.contents.empty)
		Assert.assertTrue(stagingArea1.contents.get(0) == createdObject)	
	}
	
	/**
	 * Test resolves a {@link EObjectExistenceEChangeTest} EChange with a null
	 * affected EObject.
	 */
	@Test
	def public void resolveEObjectExistenceEChangeInvalidObjectTest() {
		createdObject = null
		
		// Create change
		val unresolvedChange = createUnresolvedChange(createdObject)
		Assert.assertFalse(unresolvedChange.isResolved)
		
		// Resolve
		Assert.assertNull(unresolvedChange.resolveBefore(resourceSet1) as CreateEObject<Root>)
		Assert.assertNull(unresolvedChange.resolveAfter(resourceSet1) as CreateEObject<Root>)		
	}
}
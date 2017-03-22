package tools.vitruv.framework.tests.echange.eobject

import allElementTypes.Root
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.eobject.DeleteEObject

/**
 * Test class for the concrete {@link DeleteEObject} EChange,
 * which deletes a EObject from the staging area.
 */
class DeleteEObjectTest extends EObjectTest {	
	/**
	 * Tests whether resolving the {@link DeleteEObjectTest} EChange returns
	 * the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Set state before
		prepareStagingArea(createdObject)
		
		// Create change
		val unresolvedChange = createUnresolvedChange(createdObject)
			
		// Resolve		
 		val resolvedChange = unresolvedChange.resolveBefore(resourceSet)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests a {@link DeleteEObject} EChange by deleting a 
	 * created EObject from the staging area.
	 */
	@Test
	def public void deleteEObjectApplyForwardTest() {
		// Set state before
		prepareStagingArea(createdObject)
		Assert.assertFalse(stagingArea.contents.empty)
		
		// Create change and resolve
		val resolvedChange = createUnresolvedChange(createdObject).resolveBefore(resourceSet)
			as DeleteEObject<Root>
			
		// Apply forward
		Assert.assertTrue(resolvedChange.applyForward)	
				
		Assert.assertTrue(stagingArea.contents.empty)
		
		// Now another change would remove a object and put it in the staging area
		prepareStagingArea(createdObject2)
		Assert.assertFalse(stagingArea.contents.empty)
		
		// Create change and resolve
		val resolvedChange2 = createUnresolvedChange(createdObject2).resolveBefore(resourceSet)
			as DeleteEObject<Root>
			
		// Apply forward
		Assert.assertTrue(resolvedChange2.applyForward)	
		
		Assert.assertTrue(stagingArea.contents.empty)
	}
	
	/**
	 * Tests a {@link DeleteEObject} EChange by reverting it.
	 * Adds a deleted object to the staging area again.
	 */
	@Test
	def public void deleteEObjectApplyBackwardTest() {
		// Set state after
		Assert.assertTrue(stagingArea.contents.empty)
		
		// Create change and resolve 1
		val resolvedChange = createUnresolvedChange(createdObject).resolveAfter(resourceSet)
			as DeleteEObject<Root>
			
		// Apply backward 1
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertFalse(stagingArea.contents.empty)
		// Staging area contains copy
		Assert.assertFalse(stagingArea.contents.contains(createdObject))
		Assert.assertEquals((stagingArea.contents.get(0) as Root).singleValuedEAttribute, 
			createdObject.singleValuedEAttribute)
		
		// Now another change would be reverted and the object would be inserted.
		stagingArea.contents.clear()
		
		// Staging area is empty again
		Assert.assertTrue(stagingArea.contents.empty)
		
		// Create change and resolve 2
		val resolvedChange2 = createUnresolvedChange(createdObject2).resolveAfter(resourceSet)
			as DeleteEObject<Root>
			
		// Apply backward 1
		Assert.assertTrue(resolvedChange2.applyBackward)
		
		Assert.assertFalse(stagingArea.contents.empty)		
		// Staging area contains copy			
		Assert.assertFalse(stagingArea.contents.contains(createdObject2))
		Assert.assertEquals((stagingArea.contents.get(0) as Root).singleValuedEAttribute, 
			createdObject2.singleValuedEAttribute)
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private DeleteEObject<Root> createUnresolvedChange(Root oldObject) {
		// The concrete change type CreateEObject will be used for the tests.
		return atomicFactory.<Root>createDeleteEObjectChange(oldObject, resource)
	}
	
}
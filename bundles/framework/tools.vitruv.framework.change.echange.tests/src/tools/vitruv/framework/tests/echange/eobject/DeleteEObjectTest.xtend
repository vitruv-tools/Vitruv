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
	 * Creates new unresolved change.
	 */
	def private DeleteEObject<Root> createUnresolvedChange(Root oldObject) {
		// The concrete change type CreateEObject will be used for the tests.
		return atomicFactory.<Root>createDeleteEObjectChange(oldObject)
	}
	
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
 		val resolvedChange = unresolvedChange.resolveBefore(resourceSet1)
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
		Assert.assertFalse(stagingArea1.contents.empty)
		
		// Create change and resolve
		val resolvedChange = createUnresolvedChange(createdObject).resolveBefore(resourceSet1)
			as DeleteEObject<Root>
			
		// Apply forward
		Assert.assertTrue(resolvedChange.applyForward)	
				
		Assert.assertTrue(stagingArea1.contents.empty)
		
		// Now another change would remove a object and put it in the staging area
		prepareStagingArea(createdObject2)
		Assert.assertFalse(stagingArea1.contents.empty)
		
		// Create change and resolve
		val resolvedChange2 = createUnresolvedChange(createdObject2).resolveBefore(resourceSet1)
			as DeleteEObject<Root>
			
		// Apply forward
		Assert.assertTrue(resolvedChange2.applyForward)	
		
		Assert.assertTrue(stagingArea1.contents.empty)
	}
	
	/**
	 * Tests a {@link DeleteEObject} EChange by reverting it.
	 * Adds a deleted object to the staging area again.
	 */
	@Test
	def public void deleteEObjectApplyBackwardTest() {
		// Set state after
		Assert.assertTrue(stagingArea1.contents.empty)
		
		// Create change and resolve 1
		val resolvedChange = createUnresolvedChange(createdObject).resolveAfter(resourceSet1)
			as DeleteEObject<Root>
			
		// Apply backward 1
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertFalse(stagingArea1.contents.empty)
		// Staging area contains copy
		Assert.assertFalse(stagingArea1.contents.contains(createdObject))
		Assert.assertEquals((stagingArea1.contents.get(0) as Root).singleValuedEAttribute, 
			createdObject.singleValuedEAttribute)
		
		// Now another change would be reverted and the object would be inserted.
		stagingArea1.contents.clear()
		
		// Staging area is empty again
		Assert.assertTrue(stagingArea1.contents.empty)
		
		// Create change and resolve 2
		val resolvedChange2 = createUnresolvedChange(createdObject2).resolveAfter(resourceSet1)
			as DeleteEObject<Root>
			
		// Apply backward 1
		Assert.assertTrue(resolvedChange2.applyBackward)
		
		Assert.assertFalse(stagingArea1.contents.empty)		
		// Staging area contains copy			
		Assert.assertFalse(stagingArea1.contents.contains(createdObject2))
		Assert.assertEquals((stagingArea1.contents.get(0) as Root).singleValuedEAttribute, 
			createdObject2.singleValuedEAttribute)
	}
}
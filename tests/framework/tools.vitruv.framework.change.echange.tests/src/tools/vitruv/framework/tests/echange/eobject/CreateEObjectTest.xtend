package tools.vitruv.framework.tests.echange.eobject

import allElementTypes.Root
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.eobject.CreateEObject

/**
 * Test class for the concrete {@link CreateEObject} EChange,
 * which creates a new EObject and puts it in the staging area.
 */
class CreateEObjectTest extends EObjectTest {
	/**
	 * Tests whether resolving the {@link CreateEObjectTest} EChange returns
	 * the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(createdObject)
			
		// Resolve		
 		val resolvedChange = unresolvedChange.resolveBefore(resourceSet)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests applying a {@link CreateEObject} EChange forward by creating a
	 * new EObject and putting it in the staging area.
	 */
	@Test
	def public void createEObjectApplyForwardTest() {
		// Set state before
		Assert.assertTrue(stagingArea.contents.empty)
		
		// Create change and resolve
		val resolvedChange = createUnresolvedChange(createdObject).resolveBefore(resourceSet)
			as CreateEObject<Root>
			
		// Apply forward
		Assert.assertTrue(resolvedChange.applyForward)
		
		// Staging area contains copy
		Assert.assertFalse(stagingArea.contents.contains(createdObject))
		Assert.assertEquals((stagingArea.contents.get(0) as Root).singleValuedEAttribute, 
			createdObject.singleValuedEAttribute)
		
		// Now another change would take the object and inserts it in another resource
		stagingArea.contents.clear()
		
		// Staging area is empty again
		Assert.assertTrue(stagingArea.contents.empty)
		
		// Create change and resolve 2
		val resolvedChange2 = createUnresolvedChange(createdObject2).resolveBefore(resourceSet)
			as CreateEObject<Root>
			
		// Apply forward 2
		Assert.assertTrue(resolvedChange2.applyForward)
		
		Assert.assertFalse(stagingArea.contents.empty)
		// Staging area contains copy
		Assert.assertFalse(stagingArea.contents.contains(createdObject2))
		Assert.assertEquals((stagingArea.contents.get(0) as Root).singleValuedEAttribute, 
			createdObject2.singleValuedEAttribute)	
	}
	
	/**
	 * Tests applying a {@link CreateEObject} EChange backward 
	 * by removing a newly created object from the staging area.
	 */
	@Test
	def public void createEObjectApplyBackwardTest() {
		// Set state after
		prepareStagingArea(createdObject)

		// Create change and resolve
		val resolvedChange = createUnresolvedChange(createdObject).resolveAfter(resourceSet)
			as CreateEObject<Root>
		Assert.assertFalse(stagingArea.contents.empty)
		
		// Apply backward
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertTrue(stagingArea.contents.empty)
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private CreateEObject<Root> createUnresolvedChange(Root newObject) {
		// The concrete change type CreateEObject will be used for the tests.
		return atomicFactory.<Root>createCreateEObjectChange(newObject, resource)
	}
		
}
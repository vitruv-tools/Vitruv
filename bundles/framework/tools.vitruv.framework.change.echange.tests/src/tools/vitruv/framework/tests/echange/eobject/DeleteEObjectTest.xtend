package tools.vitruv.framework.tests.echange.eobject

import allElementTypes.Root
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
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
		prepareStagingArea(defaultCreatedObject)
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createDeleteEObjectChange(defaultCreatedObject, true)
			
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1)
		
		assertDifferentChangeSameClass(unresolvedChange, resolvedChange)
	}
	
	/**
	 * Tests a {@link DeleteEObject} EChange by deleting a 
	 * created EObject from the staging area.
	 */
	@Test
	def public void deleteEObjectApplyForwardTest() {
		// Fill staging area
		prepareStagingArea(defaultCreatedObject)
		
		Assert.assertFalse(stagingArea1.contents.empty)
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createDeleteEObjectChange(defaultCreatedObject, true).
			copyAndResolveBefore(resourceSet1)
			
		Assert.assertTrue(resolvedChange.applyForward)
		Assert.assertTrue(stagingArea1.contents.empty)
		
		// Now another change would remove a object and put it in the staging area
		prepareStagingArea(defaultCreatedObject2)
		
		Assert.assertFalse(stagingArea1.contents.empty)
		
		val resolvedChange2 = TypeInferringAtomicEChangeFactory.
			<Root>createDeleteEObjectChange(defaultCreatedObject2, true).
			copyAndResolveBefore(resourceSet1)
			
		Assert.assertTrue(resolvedChange2.applyForward)
		Assert.assertTrue(stagingArea1.contents.empty)
	}
	
	/**
	 * Tests a {@link DeleteEObject} EChange by reverting it.
	 * Adds a deleted object to the staging area again.
	 */
	@Test
	def public void deleteEObjectApplyBackwardTest() {
		// Staging area is empty
		Assert.assertTrue(stagingArea1.contents.empty)
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createDeleteEObjectChange(defaultCreatedObject, true).
			copyAndResolveAfter(resourceSet1)
			
		Assert.assertTrue(resolvedChange.applyBackward)
		Assert.assertFalse(stagingArea1.contents.empty)
		// Staging area contains copy
		Assert.assertFalse(stagingArea1.contents.contains(defaultCreatedObject))
		val createdObject = stagingArea1.contents.get(0) as Root
		Assert.assertEquals(createdObject.singleValuedEAttribute, defaultCreatedObject.singleValuedEAttribute)
		
		// Now another change would be reverted and the object would be inserted.
		stagingArea1.contents.clear()
		
		// Staging area is empty again
		Assert.assertTrue(stagingArea1.contents.empty)
		
		val resolvedChange2 = TypeInferringAtomicEChangeFactory.
			<Root>createDeleteEObjectChange(defaultCreatedObject2, true).
			copyAndResolveAfter(resourceSet1)	
			
		Assert.assertTrue(resolvedChange2.applyBackward)
		Assert.assertFalse(stagingArea1.contents.empty)		
		// Staging area contains copy			
		Assert.assertFalse(stagingArea1.contents.contains(defaultCreatedObject2))
		val createdObject2 = stagingArea1.contents.get(0) as Root
		Assert.assertEquals(createdObject2.singleValuedEAttribute, defaultCreatedObject2.singleValuedEAttribute)
	}
}
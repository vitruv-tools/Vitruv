package tools.vitruv.framework.tests.echange.eobject

import allElementTypes.Root
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
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
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createCreateEObjectChange(defaultCreatedObject, true)
			
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1)
		
		assertDifferentChangeSameClass(unresolvedChange, resolvedChange)
	}
	
	/**
	 * Tests applying a {@link CreateEObject} EChange forward by creating a
	 * new EObject and putting it in the staging area.
	 */
	@Test
	def public void createEObjectApplyForwardTest() {
		// Staging area is empty
		Assert.assertTrue(stagingArea1.contents.empty)
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createCreateEObjectChange(defaultCreatedObject, true).
			copyAndResolveBefore(resourceSet1)
			
		Assert.assertTrue(resolvedChange.applyForward)
		Assert.assertFalse(stagingArea1.contents.empty)
		// Staging area contains copy
		Assert.assertFalse(stagingArea1.contents.contains(defaultCreatedObject))
		val createdObject = stagingArea1.contents.get(0) as Root
		Assert.assertEquals(createdObject.singleValuedEAttribute, defaultCreatedObject.singleValuedEAttribute)
		
		// Now another change would take the object and inserts it in another resource
		stagingArea1.contents.clear()
		
		// Staging area is empty again
		Assert.assertTrue(stagingArea1.contents.empty)
		
		val resolvedChange2	= TypeInferringAtomicEChangeFactory.
			<Root>createCreateEObjectChange(defaultCreatedObject2, true).
			copyAndResolveBefore(resourceSet1)	
		
		Assert.assertTrue(resolvedChange2.applyForward)
		Assert.assertFalse(stagingArea1.contents.empty)
		// Staging area contains copy
		Assert.assertFalse(stagingArea1.contents.contains(defaultCreatedObject2))
		val createdObject2 = stagingArea1.contents.get(0) as Root
		Assert.assertEquals(createdObject2.singleValuedEAttribute, defaultCreatedObject2.singleValuedEAttribute)	
	}
	
	/**
	 * Tests applying a {@link CreateEObject} EChange backward 
	 * by removing a newly created object from the staging area.
	 */
	@Test
	def public void createEObjectApplyBackwardTest() {
		// Put object in the staging area. State after applying the change.
		prepareStagingArea(defaultCreatedObject)
		
		Assert.assertFalse(stagingArea1.contents.empty)
		Assert.assertTrue(stagingArea1.contents.contains(defaultCreatedObject))
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createCreateEObjectChange(defaultCreatedObject, true).
			copyAndResolveAfter(resourceSet1)
			
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertTrue(stagingArea1.contents.empty)
	}
	
}
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
			
		val resolvedChange = unresolvedChange.resolve(resourceSet1)
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		Assert.assertEquals(unresolvedChange.getClass, resolvedChange.getClass)
	}
	
	/**
	 * Tests a {@link CreateEObject} EChange by creating a
	 * new EObject and putting it in the staging area.
	 */
	@Test
	def public void createEObjectApplyTest() {
		// Staging area is empty
		Assert.assertTrue(stagingArea1.contents.empty)
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createCreateEObjectChange(defaultCreatedObject, true).
			resolve(resourceSet1)
			
		Assert.assertTrue(resolvedChange.apply)
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
			resolve(resourceSet1)	
		
		Assert.assertTrue(resolvedChange2.apply)
		Assert.assertFalse(stagingArea1.contents.empty)
		// Staging area contains copy
		Assert.assertFalse(stagingArea1.contents.contains(defaultCreatedObject2))
		val createdObject2 = stagingArea1.contents.get(0) as Root
		Assert.assertEquals(createdObject2.singleValuedEAttribute, defaultCreatedObject2.singleValuedEAttribute)	
	}
	
	/**
	 * Tests a {@link CreateEObject} EChange by reverting it.
	 * Removes a newly created object from the staging area.
	 */
	@Test
	def public void createEObjectRevertTest() {
		// Put object in the staging area. State after applying the change.
		prepareStagingArea(defaultCreatedObject)
		
		Assert.assertFalse(stagingArea1.contents.empty)
		Assert.assertTrue(stagingArea1.contents.contains(defaultCreatedObject))
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createCreateEObjectChange(defaultCreatedObject, true).
			resolve(resourceSet1)
			
		Assert.assertTrue(resolvedChange.revert)
		
		Assert.assertTrue(stagingArea1.contents.empty)
	}
	
}
package tools.vitruv.framework.tests.echange.root

import allElementTypes.Root
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.util.EChangeUtil

/**
 * Test class for the concrete {@link InsertRootEObject} EChange,
 * which inserts a new root element into a resource.
 */
class InsertRootEObjectTest extends RootEChangeTest {
	protected static val DEFAULT_INDEX = 2
	
	/**
	 * Test resolves a {@link InsertRootEObject} EChange with a new root which is
	 * in the staging area and not in the new resource. This happens
	 * when the change will be applied.
	 */
	@Test
	def public void resolveInsertRootEObjectTest() {
		// Insert object first in resource so the URI is set correctly (object state after inserting it)
		prepareResource(newRootObject, DEFAULT_INDEX)
		
		// Create change
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true)		

		// Remove object from resource and put it in staging area
		resourceContent.remove(newRootObject)
		prepareStagingArea(newRootObject)
			
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertNull(unresolvedChange.resource)
		Assert.assertTrue(unresolvedChange.newValue != newRootObject)
		Assert.assertFalse(resourceContent.contains(newRootObject))
		Assert.assertTrue(EChangeUtil.objectInProgress == newRootObject)
		
		// resolve
		var resolvedChange = unresolvedChange.resolveApply(resourceSet1) as InsertRootEObject<Root>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertNotNull(resolvedChange.resource)
		Assert.assertTrue(resolvedChange.resource.resourceSet == resourceSet1)
		Assert.assertTrue(resolvedChange.newValue == newRootObject)
		Assert.assertNull(EChangeUtil.objectInProgress)
	}
	
	/**
	 * Test resolves a {@link InsertRootEObject} EChange with a root object which is already
	 * in the resource. This happens when the change will be reverted.
	 */
	@Test
	def public void resolveInsertRootEObjectTest2() {	
		// Insert in resource
		prepareResource(newRootObject, DEFAULT_INDEX)
		
		// Create change
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true)
			
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertNull(unresolvedChange.resource)
		Assert.assertTrue(unresolvedChange.newValue != newRootObject)	
		Assert.assertNull(EChangeUtil.objectInProgress)	
		
		// Resolve
		var resolvedChange = unresolvedChange.resolveRevert(resourceSet1) as InsertRootEObject<Root>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertNotNull(resolvedChange.resource)
		Assert.assertTrue(resolvedChange.resource.resourceSet == resourceSet1)
		Assert.assertTrue(resolvedChange.newValue == newRootObject)
		Assert.assertTrue(EChangeUtil.objectInProgress == resolvedChange.newValue)
	}
	
	/**
	 * Tests whether resolving the {@link InsertRootEObject} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		prepareStagingArea(newRootObject)
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true)
			
		val resolvedChange = unresolvedChange.resolveApply(resourceSet1)
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		Assert.assertEquals(unresolvedChange.getClass, resolvedChange.getClass)
	}
	
	/**
	 * Tests resolves a {@link InsertRootEObject} EChange with no root object
	 * in the staging area or in the resource.
	 */
	 @Test
	 def public void resolveInsertRootEObjectFailsTest() {
		// Insert object first in resource so the URI is set correctly (object state after inserting it)
		prepareResource(newRootObject, DEFAULT_INDEX)
		
		// Create change
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true)		

		// Remove object from resource
		resourceContent.remove(newRootObject)	 
		
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertNull(unresolvedChange.resource)
		Assert.assertTrue(unresolvedChange.newValue != newRootObject)
		Assert.assertFalse(resourceContent.contains(newRootObject))	
		Assert.assertTrue(stagingArea1.contents.empty)
		Assert.assertNull(EChangeUtil.objectInProgress)
		
		// resolve
		var resolvedChange = unresolvedChange.resolveApply(resourceSet1) as InsertRootEObject<Root>
		
		Assert.assertFalse(resolvedChange.isResolved)
		Assert.assertNull(resolvedChange.resource)
		Assert.assertFalse(resolvedChange.newValue == newRootObject)
		Assert.assertNull(EChangeUtil.objectInProgress)		
	 }
	
	/**
	 * Tests inserting new root elements into a resource.
	 */
	@Test
	def public void insertRootEObjectApplyTest() {
		val oldSize = resourceContent.size;
				
		// Prepare staging area for the first object
		prepareStagingArea(newRootObject)

		// Insert first root element
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true).
			resolveApply(resourceSet1) as InsertRootEObject<Root>
			
		Assert.assertTrue(resolvedChange.apply)
		
		Assert.assertTrue(stagingArea1.contents.empty)
		Assert.assertEquals(resourceContent.size, oldSize + 1)
		Assert.assertTrue(resourceContent.contains(resolvedChange.newValue))
		
		// Prepare staging area for the second object
		prepareStagingArea(newRootObject2)
		Assert.assertFalse(stagingArea1.contents.empty)
				
		// Insert second root element
		val resolvedChange2 = TypeInferringAtomicEChangeFactory.
			<Root>createInsertRootChange(newRootObject2, fileUri.toString, DEFAULT_INDEX, true).
			resolveApply(resourceSet1) as InsertRootEObject<Root>
			
		Assert.assertTrue(resolvedChange2.apply)
		
		Assert.assertTrue(stagingArea1.contents.empty)
		Assert.assertEquals(resourceContent.size, oldSize + 2)
		Assert.assertTrue(resourceContent.contains(resolvedChange2.newValue))
	}
	
	/**
	 * Reverts two {@link InsertRootEObject} EChanges.
	 */
	@Test
	def public void insertRootEObjectRevertTest() {
		val beforeInsertSize = resourceContent.size;
		
		// Prepare and insert first root element
		prepareStagingArea(newRootObject)
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true).
			resolveApply(resourceSet1) as InsertRootEObject<Root>	

		Assert.assertTrue(resolvedChange.apply)
			
		// Prepare and insert second root element
		prepareStagingArea(newRootObject2)
		val resolvedChange2 = TypeInferringAtomicEChangeFactory.
			<Root>createInsertRootChange(newRootObject2, fileUri.toString, DEFAULT_INDEX, true).
			resolveApply(resourceSet1) as InsertRootEObject<Root>
		
		Assert.assertTrue(resolvedChange2.apply)
		
		// Both objects are in resource and staging area is empty
		Assert.assertEquals(resourceContent.size, beforeInsertSize + 2)	
		Assert.assertTrue(resourceContent.contains(newRootObject))
		Assert.assertTrue(resourceContent.contains(newRootObject2))
		Assert.assertTrue(stagingArea1.contents.empty)	
		
		// Revert second change
		Assert.assertTrue(resolvedChange2.revert)
		
		Assert.assertEquals(resourceContent.size, beforeInsertSize + 1)
		Assert.assertFalse(resourceContent.contains(newRootObject2))
		Assert.assertTrue(resourceContent.contains(newRootObject))
		Assert.assertFalse(stagingArea1.contents.empty)
		
		/* Presumption: after removing a root object, 
		 * it will be deleted (and removed from the staging area) 
		 * by a DeleteEObject EChange, before the next root object will 
		 * be removed.
		 */ 
		stagingArea1.contents.clear
		
		// Revert first change
		Assert.assertTrue(resolvedChange.revert)
			
		Assert.assertEquals(resourceContent.size, beforeInsertSize)
		Assert.assertFalse(resourceContent.contains(newRootObject))
		Assert.assertFalse(stagingArea1.contents.empty)
	}
	
	/**
	 * Tests applying the {@link InsertRootEObject} EChange with invalid index.
	 */
	@Test
	def public void insertRootEObjectInvalidIndexTest() {
		prepareStagingArea(newRootObject)
		val index = 5
		
		Assert.assertTrue(resourceContent.size < index)
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createInsertRootChange(newRootObject, fileUri.toString, index, true).
			resolveApply(resourceSet1) as InsertRootEObject<Root>	
			
		Assert.assertTrue(resolvedChange.isResolved)			
		Assert.assertTrue(resolvedChange.resource.resourceSet == resourceSet1)
		Assert.assertFalse(resolvedChange.apply)
	}
}
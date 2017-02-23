package tools.vitruv.framework.tests.echange.root

import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.root.InsertRootEObject

/**
 * Test class for the concrete {@link InsertRootEObject} EChange,
 * which inserts a new root element into a resource.
 */
class InsertRootEObjectTest extends RootEChangeTest {
	protected static val DEFAULT_INDEX = 2
	
	/**
	 * Test resolves a {@link InsertRootEObject} EChange with a new root which is
	 * not in a resource set.
	 */
	@Test
	def public void resolveInsertRootEObjectTest() {
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true)
			
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertNull(unresolvedChange.resource)
		Assert.assertTrue(unresolvedChange.newValue != newRootObject)
		
		// Cannot be resolved because the new root object is not in the same resource set.
		var resolvedChange = unresolvedChange.resolve(resourceSet1) as InsertRootEObject<Root>
		
		Assert.assertFalse(resolvedChange.isResolved)
		Assert.assertTrue(resolvedChange == unresolvedChange)
		
		// Resolve new root object manually
		resolvedChange = unresolvedChange.resolve(resourceSet1, newRootObject) as InsertRootEObject<Root>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertNotNull(resolvedChange.resource)
		Assert.assertTrue(resolvedChange.resource.resourceSet == resourceSet1)
		Assert.assertTrue(resolvedChange.newValue == newRootObject)
	}
	
	/**
	 * Tests whether resolving the {@link InsertRootEObject} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true)
			
		val resolvedChange = unresolvedChange.resolve(resourceSet1, newRootObject)
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		Assert.assertEquals(unresolvedChange.getClass, resolvedChange.getClass)
	}
	
	/**
	 * Tests inserting new root elements into a resource.
	 */
	@Test
	def public void insertRootEObjectApplyTest() {
		val oldSize = resourceContent.size;
		
		// Insert first root element
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true).
			resolve(resourceSet1, newRootObject) as InsertRootEObject<Root>
			
		Assert.assertTrue(resolvedChange.apply)
		
		Assert.assertEquals(resourceContent.size, oldSize + 1)
		Assert.assertTrue(resourceContent.contains(resolvedChange.newValue))
		
		// Insert second root element
		val resolvedChange2 = TypeInferringAtomicEChangeFactory.
			<Root>createInsertRootChange(newRootObject2, fileUri.toString, DEFAULT_INDEX, true).
			resolve(resourceSet1, newRootObject2) as InsertRootEObject<Root>
			
		Assert.assertTrue(resolvedChange2.apply)
		
		Assert.assertEquals(resourceContent.size, oldSize + 2)
		Assert.assertTrue(resourceContent.contains(resolvedChange2.newValue))
	}
	
	/**
	 * Reverts two {@link InsertRootEObject} EChanges.
	 */
	@Test
	def public void insertRootEObjectRevertTest() {
		val beforeInsertSize = resourceContent.size;
		
		// Insert first root element
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true).
			resolve(resourceSet1, newRootObject) as InsertRootEObject<Root>	

		Assert.assertTrue(resolvedChange.apply)
			
		// Insert second root element
		val resolvedChange2 = TypeInferringAtomicEChangeFactory.
			<Root>createInsertRootChange(newRootObject2, fileUri.toString, DEFAULT_INDEX, true).
			resolve(resourceSet1, newRootObject2) as InsertRootEObject<Root>
			
		Assert.assertTrue(resolvedChange2.apply)
		
		Assert.assertEquals(resourceContent.size, beforeInsertSize + 2)	
		Assert.assertTrue(resourceContent.contains(newRootObject))
		Assert.assertTrue(resourceContent.contains(newRootObject2))
		
		// Revert second change
		Assert.assertTrue(resolvedChange2.revert)
		
		Assert.assertEquals(resourceContent.size, beforeInsertSize + 1)
		Assert.assertFalse(resourceContent.contains(newRootObject2))
		Assert.assertTrue(resourceContent.contains(newRootObject))
		
		// Revert first change
		Assert.assertTrue(resolvedChange.revert)
			
		Assert.assertEquals(resourceContent.size, beforeInsertSize)
		Assert.assertFalse(resourceContent.contains(newRootObject))
	}
	
	/**
	 * Tests the {@link InsertRootEObject} EChange with invalid index.
	 */
	@Test
	def public void insertRootEObjectInvalidIndexTest() {
		val index = 5
		
		Assert.assertTrue(resourceContent.size < index)
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root>createInsertRootChange(newRootObject, fileUri.toString, index, true).
			resolve(resourceSet1, newRootObject) as InsertRootEObject<Root>	
			
		Assert.assertTrue(resolvedChange.isResolved)			
		Assert.assertTrue(resolvedChange.resource.resourceSet == resourceSet1)
		Assert.assertFalse(resolvedChange.apply)
		Assert.assertFalse(resolvedChange.revert)
	}
}
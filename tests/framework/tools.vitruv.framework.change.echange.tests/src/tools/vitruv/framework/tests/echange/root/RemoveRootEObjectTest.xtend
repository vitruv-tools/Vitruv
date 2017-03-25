package tools.vitruv.framework.tests.echange.root

import allElementTypes.Root
import org.eclipse.emf.ecore.resource.Resource
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.root.RemoveRootEObject

/**
 * Test class for the concrete {@link RemoveRootEObject} EChange,
 * which removes a root element from a resource.
 */
class RemoveRootEObjectTest extends RootEChangeTest {
	/**
	 * Inserts new root objects into the resource
	 * to remove them in the tests.
	 */
	@Before
	override public void beforeTest()  {
		super.beforeTest()
		resource.contents.add(DEFAULT_INDEX, newRootObject)
		resource.contents.add(DEFAULT_INDEX + 1, newRootObject2)
	}	
	
	/**
	 * Test resolves a {@link RemoveRootEObject} EChange, with a root 
	 * object already in the resource. This happens when the model is
	 * in state before the change is applied forward.
	 */
	@Test
	def public void resolveRemoveRootEObjectTest() {			
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject)
		unresolvedChange.assertIsNotResolved(newRootObject)	
		Assert.assertTrue(stagingArea.contents.empty)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as RemoveRootEObject<Root>	
		resolvedChange.assertIsResolved(newRootObject, resource)
		Assert.assertTrue(stagingArea.contents.empty)
	}
	
	/**
	 * Test resolves a {@link RemoveRootEObject} EChange, with a root
	 * object in the staging area. This happens when the model is
	 * in state after the change was applied.
	 */
	 @Test
	 def public void resolveRemoveRootEObjectTest2() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject)
		unresolvedChange.assertIsNotResolved(newRootObject)	
		Assert.assertTrue(stagingArea.contents.empty)
		
		// Set state after
	 	prepareStagingArea(newRootObject)

	 	// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) 
			as RemoveRootEObject<Root>	
		resolvedChange.assertIsResolved(newRootObject, resource)
		Assert.assertFalse(stagingArea.contents.empty)
	 }
	
	/**
	 * Tests whether resolving the {@link RemoveRootEObject} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {		
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject)
		unresolvedChange.assertIsNotResolved(newRootObject)	

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as RemoveRootEObject<Root>				
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests applying the {@link RemoveRootEObject} EChange 
	 * by removing two root elements from a resource.
	 */
	@Test
	def public void removeRootEObjectApplyForwardTest() {
		// State before
		val oldSize = resourceContent.size
		Assert.assertTrue(stagingArea.contents.empty)	
		Assert.assertTrue(newRootObject == resourceContent.get(DEFAULT_INDEX))	
		Assert.assertTrue(newRootObject2 == resourceContent.get(DEFAULT_INDEX + 1))		
		
		// Create and resolve change 1
		val resolvedChange = createUnresolvedChange(newRootObject).resolveBefore(resourceSet)
			as RemoveRootEObject<Root>
		resolvedChange.assertIsResolved(newRootObject, resource)
				
		// Apply forward 1	
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertEquals(resourceContent.size, oldSize - 1)
		Assert.assertTrue(newRootObject == stagingArea.contents.get(0))
		Assert.assertTrue(newRootObject2 == resourceContent.get(DEFAULT_INDEX))		
		
		// Now the element is deleted or inserted again.
		stagingArea.contents.clear()

		// Create and resolve change 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2).resolveBefore(resourceSet)
			as RemoveRootEObject<Root>
		resolvedChange2.assertIsResolved(newRootObject2, resource)

		// Apply forward 2	
		Assert.assertTrue(resolvedChange2.applyForward)
							
		Assert.assertEquals(resourceContent.size, oldSize - 2)
		Assert.assertTrue(newRootObject2 == stagingArea.contents.get(0))		
	}
	
	/**
	 * Tests applying two {@link RemoveRootEObject} EChanges
	 * by inserts two removed root objects in a resource.
	 */
	@Test
	def public void removeRootEObjectApplyBackwardTest() {
		// State before
		Assert.assertTrue(stagingArea.contents.empty)	
		val indexRoot1 = resourceContent.indexOf(newRootObject)
		val indexRoot2 = resourceContent.indexOf(newRootObject2)
		
		// Create and resolve and apply forward 1
		val resolvedChange = createUnresolvedChange(newRootObject).resolveBefore(resourceSet)
			as RemoveRootEObject<Root>
		Assert.assertTrue(resolvedChange.applyForward)
		
		// Now the element is deleted or inserted again.
		stagingArea.contents.clear()
		
		// Create and resolve and apply forward 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2).resolveBefore(resourceSet)
			as RemoveRootEObject<Root>
		Assert.assertTrue(resolvedChange2.applyForward)
		
		// State after
		val oldSize = resourceContent.size
		Assert.assertFalse(resourceContent.contains(newRootObject))
		Assert.assertFalse(resourceContent.contains(newRootObject2))
		
		// Apply backward 2
		Assert.assertTrue(resolvedChange2.applyBackward)
		
		Assert.assertEquals(resourceContent.size, oldSize + 1)		
		Assert.assertTrue(resourceContent.contains(newRootObject2))	
			
		// Apply backward 1
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertEquals(resourceContent.size, oldSize + 2)		
		Assert.assertTrue(resourceContent.contains(newRootObject))
		
		// At same index
		Assert.assertEquals(resourceContent.indexOf(newRootObject), indexRoot1)
		Assert.assertEquals(resourceContent.indexOf(newRootObject2), indexRoot2)		
	}
	
	/**
	 * Tests a {@link RemoveRootEObject} EChange with invalid index.
	 */
	@Test
	def public void removeRootEObjectInvalidIndex() {
		index = 5
		Assert.assertTrue(resourceContent.size < index)
		
		// Create and resolve change
		val resolvedChange = createUnresolvedChange(newRootObject).resolveBefore(resourceSet)
			as RemoveRootEObject<Root>
		resolvedChange.assertIsResolved(newRootObject, resource)
			
		// Apply
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertFalse(resolvedChange.applyBackward)
	}
	
	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(RemoveRootEObject<Root> change, Root oldValue) {
		Assert.assertFalse(change.isResolved)
		Assert.assertTrue(change.oldValue != oldValue)
		Assert.assertNull(change.resource)
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(RemoveRootEObject<Root> change, Root oldValue, Resource resource) {
		Assert.assertTrue(change.isResolved)
		Assert.assertTrue(change.oldValue == oldValue)
		Assert.assertTrue(change.resource == resource)	
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private RemoveRootEObject<Root> createUnresolvedChange(Root rootObject) {
		return atomicFactory.<Root>createRemoveRootChange
		(rootObject, resource, index)	
	}
}
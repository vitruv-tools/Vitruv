package tools.vitruv.framework.tests.echange.root

import allElementTypes.Root
import org.eclipse.emf.ecore.resource.Resource
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.root.InsertRootEObject

/**
 * Test class for the concrete {@link InsertRootEObject} EChange,
 * which inserts a new root element into a resource.
 */
class InsertRootEObjectTest extends RootEChangeTest {	
	/**
	 * Test resolves a {@link InsertRootEObject} EChange with a new root which is
	 * in the staging area and not in the new resource. This happens
	 * when the model is in state before the change and the change will be applied forward.
	 */
	@Test
	def public void resolveInsertRootEObjectTest() {
		// Set state before
		prepareStagingArea(newRootObject)
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject)
		unresolvedChange.assertIsNotResolved(newRootObject)		
		Assert.assertTrue(stagingArea.contents.contains(newRootObject))
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as InsertRootEObject<Root>	
		resolvedChange.assertIsResolved(newRootObject, resource)
		Assert.assertTrue(stagingArea.contents.contains(newRootObject))
	}
	
	/**
	 * Test resolves a {@link InsertRootEObject} EChange with a root object which is already
	 * in the resource. This happens when the model is in state after the change
	 * and the change will be applied backward.
	 */
	@Test
	def public void resolveInsertRootEObjectTest2() {	
		// Set state before
		prepareStagingArea(newRootObject)
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject)
		unresolvedChange.assertIsNotResolved(newRootObject)		
		Assert.assertTrue(stagingArea.contents.contains(newRootObject))

		// Set state after
		stagingArea.contents.clear
		prepareResource(newRootObject, index)

		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) 
			as InsertRootEObject<Root>	
		resolvedChange.assertIsResolved(newRootObject, resource)
		Assert.assertTrue(stagingArea.contents.empty)
	}
	
	/**
	 * Tests whether resolving the {@link InsertRootEObject} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Set state before
		prepareStagingArea(newRootObject)
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject)
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests applying a {@link InsertRootEObject} EChange forward 
	 * by inserting a new root elements into a resource.
	 */
	@Test
	def public void insertRootEObjectApplyForwardTest() {	
		// Set state before
		prepareStagingArea(newRootObject)
		val oldSize = resourceContent.size;
		
		// Create change and resolve 1
		val resolvedChange = createUnresolvedChange(newRootObject).resolveBefore(resourceSet)
			as InsertRootEObject<Root>
		resolvedChange.assertIsResolved(newRootObject, resource)
					
		// Apply forward 1
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertTrue(stagingArea.contents.empty)
		Assert.assertEquals(resourceContent.size, oldSize + 1)
		Assert.assertTrue(newRootObject == resourceContent.get(DEFAULT_INDEX))
		
		// Prepare staging area for the second object
		prepareStagingArea(newRootObject2)
		Assert.assertFalse(stagingArea.contents.empty)
				
		// Create change and resolve 2
		val resolvedChange2 = createUnresolvedChange(newRootObject).resolveBefore(resourceSet)
			as InsertRootEObject<Root>
		resolvedChange2.assertIsResolved(newRootObject2, resource)
			
		// Apply forward 2
		Assert.assertTrue(resolvedChange2.applyForward)
		
		Assert.assertTrue(stagingArea.contents.empty)
		Assert.assertEquals(resourceContent.size, oldSize + 2)
		Assert.assertTrue(newRootObject2 == resourceContent.get(DEFAULT_INDEX))
		Assert.assertTrue(newRootObject == resourceContent.get(DEFAULT_INDEX + 1))
	}
	
	/**
	 * Tests applying two {@link InsertRootEObject} EChanges backward
	 * by removing two inserted root objects from a resource.
	 */
	@Test
	def public void insertRootEObjectApplyBackwardTest() {
		// Set state before
		prepareStagingArea(newRootObject)
		
		// Create change and resolve and apply forward 1
		val resolvedChange = createUnresolvedChange(newRootObject).resolveBefore(resourceSet)
			as InsertRootEObject<Root>
		Assert.assertTrue(resolvedChange.applyForward)
		
		// Create change and resolve and apply forward 2
		prepareStagingArea(newRootObject2)
		val resolvedChange2 = createUnresolvedChange(newRootObject2).resolveBefore(resourceSet)
			as InsertRootEObject<Root>
		Assert.assertTrue(resolvedChange2.applyForward)
				
		// State after
		val oldSize = resourceContent.size	
		Assert.assertTrue(newRootObject2 == resourceContent.get(DEFAULT_INDEX))
		Assert.assertTrue(newRootObject == resourceContent.get(DEFAULT_INDEX + 1))
		
		// Apply backward 2
		Assert.assertTrue(resolvedChange2.applyBackward)
		
		Assert.assertEquals(resourceContent.size, oldSize - 1)	
		Assert.assertTrue(newRootObject == resourceContent.get(DEFAULT_INDEX))
		Assert.assertTrue(newRootObject2 == stagingArea.contents.get(0))
			
		// Now another change would take the object from the staging area
		stagingArea.contents.clear
		
		// Apply backward 1
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertEquals(resourceContent.size, oldSize - 2)	
		Assert.assertTrue(newRootObject == stagingArea.contents.get(0))
	}
	
	/**
	 * Tests applying the {@link InsertRootEObject} EChange with invalid index.
	 */
	@Test
	def public void insertRootEObjectInvalidIndexTest() {
		// Set state before
		prepareStagingArea(newRootObject)
		index = 5
		Assert.assertTrue(resourceContent.size < index)
		
		// Create change and resolve
		val resolvedChange = createUnresolvedChange(newRootObject).resolveBefore(resourceSet)
			as InsertRootEObject<Root>	
		resolvedChange.assertIsResolved(newRootObject, resource)	
		
		// Apply		
		Assert.assertFalse(resolvedChange.applyForward)
	}
	
	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(InsertRootEObject<Root> change, Root newValue) {
		Assert.assertFalse(change.isResolved)
		Assert.assertTrue(change.newValue != newValue)
		Assert.assertNull(change.resource)
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(InsertRootEObject<Root> change, Root newValue, Resource resource) {
		Assert.assertTrue(change.isResolved)
		Assert.assertTrue(change.newValue == newValue)
		Assert.assertTrue(change.resource == resource)	
	}

	/**
	 * Creates new unresolved change.
	 */
	def private InsertRootEObject<Root> createUnresolvedChange(Root rootObject) {
		return atomicFactory.<Root>createInsertRootChange
		(rootObject, resource, index)	
	}
}
package tools.vitruv.framework.tests.echange.root

import allElementTypes.Root
import org.eclipse.emf.ecore.resource.Resource
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.root.InsertRootEObject

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*

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
	def public void resolveBefore() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)
		unresolvedChange.assertIsNotResolved(newRootObject)		
		assertIsStateBefore
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as InsertRootEObject<Root>	
		resolvedChange.assertIsResolved(newRootObject, resource)
		assertIsStateBefore
	}
	
	/**
	 * Test resolves a {@link InsertRootEObject} EChange with a root object which is already
	 * in the resource. This happens when the model is in state after the change
	 * and the change will be applied backward.
	 */
	@Test
	def public void resolveAfterTest() {	
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)
		unresolvedChange.assertIsNotResolved(newRootObject)		
		assertIsStateBefore

		// Set state after
		resourceContent.add(1, newRootObject)

		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) 
			as InsertRootEObject<Root>	
		resolvedChange.assertIsResolved(newRootObject, resource)
	}
	
	/**
	 * Tests whether resolving the {@link InsertRootEObject} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests applying a {@link InsertRootEObject} EChange forward 
	 * by inserting a new root elements into a resource.
	 */
	@Test
	def public void applyForwardTest() {	
		assertIsStateBefore
		
		// Create change and resolve 1
		val resolvedChange = createUnresolvedChange(newRootObject, 1).resolveBefore(resourceSet)
			as InsertRootEObject<Root>
		resolvedChange.assertIsResolved(newRootObject, resource)
					
		// Apply forward 1
		resolvedChange.assertApplyForward
		
		Assert.assertEquals(resourceContent.size, 2)
		Assert.assertTrue(newRootObject == resourceContent.get(1))
		
		// Create change and resolve 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2, 2).resolveBefore(resourceSet)
			as InsertRootEObject<Root>
		resolvedChange2.assertIsResolved(newRootObject2, resource)
			
		// Apply forward 2
		resolvedChange2.assertApplyForward
		
		// State after
		assertIsStateAfter
	}
	
	/**
	 * Tests applying two {@link InsertRootEObject} EChanges backward
	 * by removing two inserted root objects from a resource.
	 */
	@Test
	def public void applyBackwardTest() {
		// Set state before
		assertIsStateBefore
		
		// Create change and resolve and apply forward 1
		val resolvedChange = createUnresolvedChange(newRootObject, 1).resolveBefore(resourceSet)
			as InsertRootEObject<Root>
		resolvedChange.assertApplyForward
		
		// Create change and resolve and apply forward 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2, 2).resolveBefore(resourceSet)
			as InsertRootEObject<Root>
		resolvedChange2.assertApplyForward
				
		// State after
		assertIsStateAfter
		
		// Apply backward 2
		resolvedChange2.assertApplyBackward
		
		Assert.assertEquals(resourceContent.size, 2)	
		assertEqualsOrCopy(newRootObject, resourceContent.get(1))
		
		// Apply backward 1
		resolvedChange.assertApplyBackward
		
		// State before
		assertIsStateBefore
	}
	
	/**
	 * Tests applying the {@link InsertRootEObject} EChange with invalid index.
	 */
	@Test
	def public void invalidIndexTest() {
		// Set state before
		var index = 5
		Assert.assertTrue(resourceContent.size < index)
		
		// Create change and resolve
		val resolvedChange = createUnresolvedChange(newRootObject, index).resolveBefore(resourceSet)
			as InsertRootEObject<Root>	
		resolvedChange.assertIsResolved(newRootObject, resource)	
		
		// Apply		
	 	resolvedChange.assertCannotBeAppliedForward	 	
	}

	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		Assert.assertEquals(resourceContent.size, 1)
	}
	
	/**
	 * Model is in state after the changes.
	 */
	def private void assertIsStateAfter() {
		Assert.assertEquals(resourceContent.size, 3)
		newRootObject.assertEqualsOrCopy(resourceContent.get(1))
		newRootObject2.assertEqualsOrCopy(resourceContent.get(2))		
	}
		
	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(InsertRootEObject<Root> change, Root newValue) {
		Assert.assertFalse(change.isResolved)
		Assert.assertNotSame(change.newValue, newValue)
		Assert.assertNull(change.resource)
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(InsertRootEObject<Root> change, Root newValue, Resource resource) {
		Assert.assertTrue(change.isResolved)
		Assert.assertSame(change.newValue, newValue)
		Assert.assertSame(change.resource, resource)	
	}

	/**
	 * Creates new unresolved change.
	 */
	def private InsertRootEObject<Root> createUnresolvedChange(Root rootObject, int index) {
		return atomicFactory.createInsertRootChange(rootObject, resource, index)	
	}
}
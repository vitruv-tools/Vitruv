package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot
import tools.vitruv.framework.tests.echange.EChangeTest

/**
 * Test class for the concrete {@link RemoveAndDeleteRoot} EChange,
 * which removes a root element from a resource and deletes it.
 */
class RemoveAndDeleteRootTest extends EChangeTest {
	protected var Root newRootObject = null
	protected var Root newRootObject2 = null
	protected var EList<EObject> resourceContent = null
	protected var index = DEFAULT_INDEX
	
	protected static val DEFAULT_INDEX = 1
	protected static val Integer DEFAULT_CREATED_OBJECT_1_INTEGER_VALUE = 111
	protected static val Integer DEFAULT_CREATED_OBJECT_2_INTEGER_VALUE = 222
	
	/**
	 * Calls setup of the superclass, creates two new root elements and inserts
	 * it in the resource, which can be removed in the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		newRootObject = AllElementTypesFactory.eINSTANCE.createRoot()
		newRootObject.singleValuedEAttribute = DEFAULT_CREATED_OBJECT_1_INTEGER_VALUE
		newRootObject2 = AllElementTypesFactory.eINSTANCE.createRoot()
		newRootObject2.singleValuedEAttribute = DEFAULT_CREATED_OBJECT_2_INTEGER_VALUE
		resourceContent = resourceSet.getResource(fileUri, false).contents
		resourceContent.add(newRootObject)
		resourceContent.add(newRootObject2)
	}
	
	/**
	 * Resolves the {@link RemoveAndDeleteRoot} EChange. The model is in state
	 * before the change is applied, so the staging area is empty and the root 
	 * object is in the resource.
	 */
	@Test
	def public void resolveRemoveAndDeleteRootTest() {
		// State before
		Assert.assertTrue(stagingArea.contents.empty)
		val size = resourceContent.size
				
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject)
		unresolvedChange.assertIsNotResolved(newRootObject)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as RemoveAndDeleteRoot<Root>
		resolvedChange.assertIsResolved(newRootObject)	
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.		
		Assert.assertEquals(resourceContent.size, size)
		Assert.assertTrue(stagingArea.contents.empty)
	}
	
	/**
	 * Resolves the {@link RemoveAndDeleteRoot} EChange. The model is in state
	 * after the change was applied, so the staging area and object in progress
	 * are empty, and the root element was deleted.
	 */
	@Test
	def public void resolveRemoveAndDeleteRootTest2() {
		// State before
		Assert.assertTrue(stagingArea.contents.empty)
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject)
		unresolvedChange.assertIsNotResolved(newRootObject)
		
		// State after
		prepareStateAfter
		val size = resourceContent.size		
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) 
			as RemoveAndDeleteRoot<Root>
		resolvedChange.assertIsResolved(newRootObject)	
			
		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		Assert.assertEquals(resourceContent.size, size)
		Assert.assertTrue(stagingArea.contents.empty)		
	}
	
	/**
	 * Tests whether resolving the {@link RemoveAndDeleteRoot} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject)
		
		// Resolve		
 		val resolvedChange = unresolvedChange.resolveBefore(resourceSet)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests the {@link RemoveAndDeleteRoot} EChange by removing
	 * and deleting a root object.
	 */
	@Test
	def public void removeAndDeleteRootApplyTest() {
		// State before
		Assert.assertTrue(stagingArea.contents.empty)
		Assert.assertTrue(resourceContent.contains(newRootObject))	
		Assert.assertTrue(resourceContent.contains(newRootObject2))	
		val oldSize = resourceContent.size	
		
		// Create and resolve change 1
		val resolvedChange = createUnresolvedChange(newRootObject).resolveBefore(resourceSet)
			 as RemoveAndDeleteRoot<Root>
			
		// Apply 1
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertEquals(resourceContent.size, oldSize - 1)
		Assert.assertFalse(resourceContent.contains(newRootObject))	
		Assert.assertTrue(resourceContent.contains(newRootObject2))	
		Assert.assertTrue(stagingArea.contents.empty)
		
		// Create and resolve change 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2).resolveBefore(resourceSet)
			 as RemoveAndDeleteRoot<Root>
			
		// Apply 1
		Assert.assertTrue(resolvedChange2.applyForward)
		
		Assert.assertEquals(resourceContent.size, oldSize - 2)
		Assert.assertFalse(resourceContent.contains(newRootObject))	
		Assert.assertFalse(resourceContent.contains(newRootObject2))	
		Assert.assertTrue(stagingArea.contents.empty)
	}
	
	/**
	 * Tests the {@link RemoveAndDeleteRoot} EChange by reverting the change.
	 * It creates and inserts a root object.
	 */
	@Test
	def public void removeAndDeleteRootRevertTest() {
		// State before
		Assert.assertTrue(stagingArea.contents.empty)
		val index1 = resourceContent.indexOf(newRootObject)
		val index2 = resourceContent.indexOf(newRootObject2)

		// Create and resolve and apply change 1
		val resolvedChange = createUnresolvedChange(newRootObject).resolveBefore(resourceSet)
			 as RemoveAndDeleteRoot<Root>
		Assert.assertTrue(resolvedChange.applyForward)
		
		// Create and resolve and apply change 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2).resolveBefore(resourceSet)
			 as RemoveAndDeleteRoot<Root>
		Assert.assertTrue(resolvedChange2.applyForward)
		
		// State after
		val oldSize = resourceContent.size
		Assert.assertTrue(stagingArea.contents.empty)	
		
		// Apply backward 2
		Assert.assertTrue(resolvedChange2.applyBackward)

		Assert.assertEquals(resourceContent.size, oldSize + 1)
		Assert.assertTrue(resourceContent.contains(newRootObject2))
		Assert.assertTrue(stagingArea.contents.empty)	
			
		// Apply backward 1
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertEquals(resourceContent.size, oldSize + 2)
		Assert.assertEquals(resourceContent.indexOf(newRootObject), index1)
		Assert.assertEquals(resourceContent.indexOf(newRootObject2), index2)
		Assert.assertTrue(stagingArea.contents.empty)	
	}
	

	
	/**
	 * Removes the inserted items to set the state after the change.
	 */
	def private void prepareStateAfter() {
		resourceContent.remove(newRootObject)
		resourceContent.remove(newRootObject2)
	}

	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(RemoveAndDeleteRoot<Root> change, Root affectedRootObject) {
		Assert.assertFalse(change.isResolved)
		Assert.assertFalse(change.removeChange.isResolved)
		Assert.assertFalse(change.deleteChange.isResolved)
		Assert.assertTrue(change.removeChange.oldValue != affectedRootObject)
		Assert.assertTrue(change.deleteChange.affectedEObject != affectedRootObject)
		Assert.assertTrue(change.deleteChange.affectedEObject != change.removeChange.oldValue)
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(RemoveAndDeleteRoot<Root> change, Root affectedRootObject) {
		Assert.assertTrue(change.isResolved)
		change.removeChange.oldValue.assertEqualsOrCopy(affectedRootObject)
		change.deleteChange.affectedEObject.assertEqualsOrCopy(affectedRootObject)
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private RemoveAndDeleteRoot<Root> createUnresolvedChange(Root newObject) {
		return compoundFactory.<Root>createRemoveAndDeleteRootChange
		(newObject, resource, index)	
	}	
}
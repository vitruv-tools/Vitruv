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

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import static extension tools.vitruv.framework.change.echange.EChangeResolverAndApplicator.*;

/**
 * Test class for the concrete {@link RemoveAndDeleteRoot} EChange,
 * which removes a root element from a resource and deletes it.
 */
class RemoveAndDeleteRootTest extends EChangeTest {
	protected var Root newRootObject = null
	protected var Root newRootObject2 = null
	protected var EList<EObject> resourceContent = null
	
	@Before
	override public void beforeTest() {
		super.beforeTest()
		newRootObject = AllElementTypesFactory.eINSTANCE.createRoot()
		newRootObject2 = AllElementTypesFactory.eINSTANCE.createRoot()
		resourceContent = resourceSet.getResource(fileUri, false).contents
		prepareStateBefore

	}
	
	/**
	 * Resolves the {@link RemoveAndDeleteRoot} EChange. The model is in state
	 * before the change is applied, so the staging area is empty and the root 
	 * object is in the resource.
	 */
	@Test
	def public void resolveBeforeTest() {		
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 0)
		unresolvedChange.assertIsNotResolved(newRootObject)
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver) 
			as RemoveAndDeleteRoot<Root>
		resolvedChange.assertIsResolved(newRootObject)	
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.		
		assertIsStateBefore
	}
	
	/**
	 * Resolves the {@link RemoveAndDeleteRoot} EChange. The model is in state
	 * after the change was applied, so the staging area and object in progress
	 * are empty, and the root element was deleted.
	 */
	@Test
	def public void resolveAftertTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 0)
		unresolvedChange.assertIsNotResolved(newRootObject)
		
		// State after
		prepareStateAfter	
		
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(uuidGeneratorAndResolver) 
			as RemoveAndDeleteRoot<Root>
		resolvedChange.assertIsResolved(newRootObject)	
			
		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		assertIsStateAfter		
	}
	
	/**
	 * Tests whether resolving the {@link RemoveAndDeleteRoot} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 0)
		
		// Resolve		
 		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests the {@link RemoveAndDeleteRoot} EChange by removing
	 * and deleting a root object.
	 */
	@Test
	def public void applyForwardTest() {
		// Create and resolve change 1
		val resolvedChange = createUnresolvedChange(newRootObject, 1).resolveBefore(uuidGeneratorAndResolver)
			 as RemoveAndDeleteRoot<Root>
			
		// Apply 1
		resolvedChange.assertApplyForward
		
		Assert.assertEquals(resourceContent.size, 2)
		Assert.assertFalse(resourceContent.contains(newRootObject))	
		Assert.assertTrue(resourceContent.contains(newRootObject2))	
		
		// Create and resolve change 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2, 1).resolveBefore(uuidGeneratorAndResolver)
			 as RemoveAndDeleteRoot<Root>
			
		// Apply 1
		resolvedChange2.assertApplyForward
		
		// State after
		assertIsStateAfter
	}
	
	/**
	 * Tests the {@link RemoveAndDeleteRoot} EChange by reverting the change.
	 * It creates and inserts a root object.
	 */
	@Test
	def public void applyBackwardTest() {
		// Create and resolve and apply change 1
		val resolvedChange = createUnresolvedChange(newRootObject, 0).resolveBefore(uuidGeneratorAndResolver)
			 as RemoveAndDeleteRoot<Root>
		resolvedChange.assertApplyForward
		
		// Create and resolve and apply change 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2, 0).resolveBefore(uuidGeneratorAndResolver)
			 as RemoveAndDeleteRoot<Root>
		resolvedChange2.assertApplyForward
		
		// State after
		assertIsStateAfter
		
		// Apply backward 2
		resolvedChange2.assertApplyBackward

		Assert.assertEquals(resourceContent.size, 2)
		Assert.assertTrue(resourceContent.contains(newRootObject2))
			
		// Apply backward 1
		resolvedChange.assertApplyBackward
		
		// State before
		assertIsStateBefore
	}
	
	/**
	 * Sets the state of the model before the changes.
	 */
	def private void prepareStateBefore() {
		resourceContent.add(newRootObject)
		resourceContent.add(newRootObject2)
		assertIsStateBefore
	}
	
	/**
	 * Sets the state of the model after the changes.
	 */
	def private void prepareStateAfter() {
		resourceContent.remove(newRootObject)
		resourceContent.remove(newRootObject2)
		assertIsStateAfter
	}
	
	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		Assert.assertEquals(resourceContent.size, 3)
		newRootObject.assertEqualsOrCopy(resourceContent.get(1))
		newRootObject2.assertEqualsOrCopy(resourceContent.get(2))
	}
	
	/** 
	 * Model is in state after the changes.
	 */
	def private void assertIsStateAfter() {
		Assert.assertEquals(resourceContent.size, 1)
	}

	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(RemoveAndDeleteRoot<Root> change, Root affectedRootObject) {
		Assert.assertFalse(change.isResolved)
		Assert.assertFalse(change.removeChange.isResolved)
		Assert.assertFalse(change.deleteChange.isResolved)
		Assert.assertNull(change.removeChange.oldValue)
		Assert.assertNull(change.deleteChange.affectedEObject)
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
	def private RemoveAndDeleteRoot<Root> createUnresolvedChange(Root newObject, int index) {
		return compoundFactory.createRemoveAndDeleteRootChange(newObject, resource, index)	
	}	
}
package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import tools.vitruv.framework.tests.echange.EChangeTest

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*

/**
 * Test class for the concrete {@link CreateAndInsertRoot} EChange,
 * which creates a new root EObject and inserts it in a resource.
 */
class CreateAndInsertRootTest extends EChangeTest {
	protected var Root newRootObject = null
	protected var Root newRootObject2 = null
	protected var EList<EObject> resourceContent = null;

	
	/**
	 * Calls setup of the superclass and creates two new root elements
	 * which can be inserted.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		newRootObject = AllElementTypesFactory.eINSTANCE.createRoot()
		newRootObject2 = AllElementTypesFactory.eINSTANCE.createRoot()
		resourceContent = resource.contents
		assertIsStateBefore
	}
		
	/**
	 * Resolves the {@link CreateAndInsertRoot} EChange. The model is in state
	 * before the change is applied, so the staging area and object in progress are empty,
	 * and the root object is not in the resource.
	 */
	@Test
	def public void resolveBeforeTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)
		unresolvedChange.assertIsNotResolved(newRootObject)

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as CreateAndInsertRoot<Root>
		resolvedChange.assertIsResolved(newRootObject)		
			
		// Resolving applies all changes and reverts them, so the model should be unaffected.
		assertIsStateBefore
	}
	
	/**
	 * Resolves the {@link CreateAndInsertRoot} EChange. The model is in state 
	 * after the change was applied, so the staging area and object in progress are empty,
	 * and the root object is in the resource.
	 */
	@Test
	def public void resolveAfterTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)
		unresolvedChange.assertIsNotResolved(newRootObject)
		
		// Set state after		
		prepareStateAfter
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) as CreateAndInsertRoot<Root>
		resolvedChange.assertIsResolved(newRootObject)
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.
		assertIsStateAfter
	}
	
	/**
	 * Tests whether resolving the {@link CreateAndInsertRoot} EChange
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
	 * Tests applying the {@link CreateAndInsertRoot} EChange forward
	 * by creating and inserting a new root object.
	 */
	@Test
	def public void applyForwardTest() {
		// Create and resolve change 1
		val resolvedChange = createUnresolvedChange(newRootObject, 1).resolveBefore(resourceSet)
			 as CreateAndInsertRoot<Root>
			
		// Apply 1
		resolvedChange.assertApplyForward
	
		Assert.assertEquals(resourceContent.size, 2)
		Assert.assertTrue(resourceContent.contains(resolvedChange.createChange.affectedEObject))
		Assert.assertTrue(stagingArea.contents.empty)
		
		// Create and resolve change 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2, 2).resolveBefore(resourceSet)
			 as CreateAndInsertRoot<Root>
			
		// Apply 2
		resolvedChange2.assertApplyForward
		
		// State after
		assertIsStateAfter
	}
	
	/**
	 * Tests applying the {@link CreateAndInsertRoot} EChange backward 
	 * by reverting the change. It removes and deletes a root object. 
	 */
	@Test
	def public void applyBackwardTest() {
		// Create and resolve and apply change 1
		val resolvedChange = createUnresolvedChange(newRootObject, 1).resolveBefore(resourceSet)
			 as CreateAndInsertRoot<Root>
		resolvedChange.assertApplyForward

		// Create and resolve and apply change 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2, 2).resolveBefore(resourceSet)
			 as CreateAndInsertRoot<Root>
		resolvedChange2.assertApplyForward

		// State after
		assertIsStateAfter
		
		// Apply backward 2
		resolvedChange2.assertApplyBackward
		
		Assert.assertEquals(resourceContent.size, 2)
		Assert.assertTrue(resourceContent.contains(resolvedChange.createChange.affectedEObject))
		Assert.assertFalse(resourceContent.contains(resolvedChange2.createChange.affectedEObject))		
		Assert.assertTrue(stagingArea.contents.empty)	
		
		// Apply backward 1
		resolvedChange.assertApplyBackward
		
		// State before
		assertIsStateBefore	
	}
	
	/**
	 * Sets the state of the model after the changes.
	 */
	def private void prepareStateAfter() {
		resourceContent.add(newRootObject)
		resourceContent.add(newRootObject2)	
		assertIsStateAfter
	}
	
	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		Assert.assertEquals(resourceContent.size, 1)
		Assert.assertTrue(stagingArea.contents.empty)
	}
	
	/**
	 * Model is in state after the changes
	 */
	def private void assertIsStateAfter() {
		Assert.assertEquals(resourceContent.size, 3)
		newRootObject.assertEqualsOrCopy(resourceContent.get(1))
		newRootObject2.assertEqualsOrCopy(resourceContent.get(2))
		Assert.assertTrue(stagingArea.contents.empty)
	}
	
	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(CreateAndInsertRoot<Root> change, Root newRoot) {
		Assert.assertFalse(change.isResolved)
		Assert.assertFalse(change.createChange.isResolved)
		Assert.assertFalse(change.insertChange.isResolved)
		Assert.assertNotSame(change.createChange.affectedEObject, newRoot)
		Assert.assertNotSame(change.insertChange.newValue, newRoot)
		Assert.assertNotSame(change.createChange.affectedEObject, change.insertChange.newValue)
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(CreateAndInsertRoot<Root> change, Root newRoot) {
		Assert.assertTrue(change.isResolved)
		change.createChange.affectedEObject.assertEqualsOrCopy(newRoot)
		change.insertChange.newValue.assertEqualsOrCopy(newRoot)
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private CreateAndInsertRoot<Root> createUnresolvedChange(Root newObject, int index) {
		return compoundFactory.createCreateAndInsertRootChange(newObject, resource, index)	
	}
}
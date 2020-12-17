package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.tests.echange.EChangeTest

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*
import tools.vitruv.framework.change.echange.EChange
import java.util.List
import tools.vitruv.framework.change.echange.eobject.CreateEObject
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertEquals

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
	@BeforeEach
	def void beforeTest() {
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
	def void resolveBeforeTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)
		unresolvedChange.assertIsNotResolved

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver) 
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
	def void resolveAfterTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)
		unresolvedChange.assertIsNotResolved
		
		// Set state after		
		prepareStateAfter
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(uuidGeneratorAndResolver)
		resolvedChange.assertIsResolved(newRootObject)
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.
		assertIsStateAfter
	}
	
	/**
	 * Tests whether resolving the {@link CreateAndInsertRoot} EChange
	 * returns the same class.
	 */
	@Test
	def void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject, 1)
		
		// Resolve		
 		val resolvedChange = unresolvedChange.resolveBefore(uuidGeneratorAndResolver)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests applying the {@link CreateAndInsertRoot} EChange forward
	 * by creating and inserting a new root object.
	 */
	@Test
	def void applyForwardTest() {
		// Create and resolve change 1
		val resolvedChange = createUnresolvedChange(newRootObject, 1).resolveBefore(uuidGeneratorAndResolver)
			
		// Apply 1
		resolvedChange.assertApplyForward
	
		assertEquals(resourceContent.size, 2)
		val createChange = assertType(resolvedChange.get(0), CreateEObject);
		assertTrue(resourceContent.contains(createChange.affectedEObject))
		
		// Create and resolve change 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2, 2).resolveBefore(uuidGeneratorAndResolver)
			
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
	def void applyBackwardTest() {
		// Create and resolve and apply change 1
		val resolvedChange = createUnresolvedChange(newRootObject, 1).resolveBefore(uuidGeneratorAndResolver)
		resolvedChange.assertApplyForward

		// Create and resolve and apply change 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2, 2).resolveBefore(uuidGeneratorAndResolver)
		resolvedChange2.assertApplyForward

		// State after
		assertIsStateAfter
		
		// Apply backward 2
		resolvedChange2.assertApplyBackward
		
		assertEquals(resourceContent.size, 2)
		val createChange = assertType(resolvedChange.get(0), CreateEObject);
		val createChange2 = assertType(resolvedChange2.get(0), CreateEObject);
		assertTrue(resourceContent.contains(createChange.affectedEObject))
		assertFalse(resourceContent.contains(createChange2.affectedEObject))		
		
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
		assertEquals(resourceContent.size, 1)
	}
	
	/**
	 * Model is in state after the changes
	 */
	def private void assertIsStateAfter() {
		assertEquals(resourceContent.size, 3)
		newRootObject.assertEqualsOrCopy(resourceContent.get(1))
		newRootObject2.assertEqualsOrCopy(resourceContent.get(2))
	}
	
	/**
	 * Change is not resolved.
	 */
	def protected static void assertIsNotResolved(List<? extends EChange> changes) {
		EChangeTest.assertIsNotResolved(changes);
		assertEquals(2, changes.size);
		val createChange = assertType(changes.get(0), CreateEObject);
		val insertChange = assertType(changes.get(1), InsertRootEObject);
		assertEquals(insertChange.newValueID, createChange.affectedEObjectID)
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(List<EChange> changes, Root newRoot) {
		changes.assertIsResolved;
		assertEquals(2, changes.size);
		val createChange = assertType(changes.get(0), CreateEObject);
		val insertChange = assertType(changes.get(1), InsertRootEObject);
		insertChange.newValue.assertEqualsOrCopy(newRoot)
		createChange.affectedEObject.assertEqualsOrCopy(newRoot)
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private List<EChange> createUnresolvedChange(Root newObject, int index) {
		return compoundFactory.createCreateAndInsertRootChange(newObject, resource, index)	
	}
}
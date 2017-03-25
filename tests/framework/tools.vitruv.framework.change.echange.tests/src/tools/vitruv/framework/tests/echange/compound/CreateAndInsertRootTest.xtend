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

/**
 * Test class for the concrete {@link CreateAndInsertRoot} EChange,
 * which creates a new root EObject and inserts it in a resource.
 */
class CreateAndInsertRootTest extends EChangeTest {
	protected var Root newRootObject = null
	protected var Root newRootObject2 = null
	protected var EList<EObject> resourceContent = null;
	protected var int index = DEFAULT_INDEX
	
	protected static val DEFAULT_INDEX = 1
	protected static val Integer DEFAULT_CREATED_OBJECT_1_INTEGER_VALUE = 111
	protected static val Integer DEFAULT_CREATED_OBJECT_2_INTEGER_VALUE = 222	
	
	/**
	 * Calls setup of the superclass and creates two new root elements
	 * which can be inserted.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		newRootObject = AllElementTypesFactory.eINSTANCE.createRoot()
		newRootObject.singleValuedEAttribute = DEFAULT_CREATED_OBJECT_1_INTEGER_VALUE
		newRootObject2 = AllElementTypesFactory.eINSTANCE.createRoot()
		newRootObject2.singleValuedEAttribute = DEFAULT_CREATED_OBJECT_2_INTEGER_VALUE
		resourceContent = resourceSet.getResource(fileUri, false).contents
	}
		
	/**
	 * Resolves the {@link CreateAndInsertRoot} EChange. The model is in state
	 * before the change is applied, so the staging area and object in progress are empty,
	 * and the root object is not in the resource.
	 */
	@Test
	def public void resolveCreateAndInsertRootTest() {
		// State before
		Assert.assertTrue(stagingArea.contents.empty)
		val size = resourceContent.size
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject)
		unresolvedChange.assertIsNotResolved(newRootObject)

		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as CreateAndInsertRoot<Root>
		resolvedChange.assertIsResolved(newRootObject)		
			
		// Resolving applies all changes and reverts them, so the model should be unaffected.
		Assert.assertEquals(resourceContent.size, size)
		Assert.assertTrue(stagingArea.contents.empty)
	}
	
	/**
	 * Resolves the {@link CreateAndInsertRoot} EChange. The model is in state 
	 * after the change was applied, so the staging area and object in progress are empty,
	 * and the root object is in the resource.
	 */
	@Test
	def public void resolveCreateAndInsertRootTest2() {
		// State before	
		Assert.assertTrue(stagingArea.contents.empty)
		
		// Create change
		val unresolvedChange = createUnresolvedChange(newRootObject)
		unresolvedChange.assertIsNotResolved(newRootObject)
		
		// Set state after		
		resourceContent.add(DEFAULT_INDEX, newRootObject)
		val size = resourceContent.size
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) as CreateAndInsertRoot<Root>
		resolvedChange.assertIsResolved(newRootObject)
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.
		Assert.assertEquals(resourceContent.size, size)
		Assert.assertTrue(stagingArea.contents.empty)
	}
	
	/**
	 * Tests whether resolving the {@link CreateAndInsertRoot} EChange
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
	 * Tests applying the {@link CreateAndInsertRoot} EChange forward
	 * by creating and inserting a new root object.
	 */
	@Test
	def public void createAndInsertRootApplyForwardTest() {
		// State before
		Assert.assertTrue(stagingArea.contents.empty)
		val oldSize = resourceContent.size
		
		// Create and resolve change 1
		val resolvedChange = createUnresolvedChange(newRootObject).resolveBefore(resourceSet)
			 as CreateAndInsertRoot<Root>
			
		// Apply 1
		Assert.assertTrue(resolvedChange.applyForward)
	
		Assert.assertEquals(resourceContent.size, oldSize + 1)
		Assert.assertTrue(resourceContent.contains(resolvedChange.createChange.affectedEObject))
		Assert.assertTrue(stagingArea.contents.empty)
		
		// Create and resolve change 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2).resolveBefore(resourceSet)
			 as CreateAndInsertRoot<Root>
			
		// Apply 2
		Assert.assertTrue(resolvedChange2.applyForward)
		
		Assert.assertEquals(resourceContent.size, oldSize + 2)
		Assert.assertTrue(resourceContent.contains(resolvedChange2.createChange.affectedEObject))
		Assert.assertTrue(stagingArea.contents.empty)
	}
	
	/**
	 * Tests applying the {@link CreateAndInsertRoot} EChange backward 
	 * by reverting the change. It removes and deletes a root object. 
	 */
	@Test
	def public void createAndInsertRootApplyBackwardTest() {
		// State before
		Assert.assertTrue(stagingArea.contents.empty)
		
		// Create and resolve and apply change 1
		val resolvedChange = createUnresolvedChange(newRootObject).resolveBefore(resourceSet)
			 as CreateAndInsertRoot<Root>
		Assert.assertTrue(resolvedChange.applyForward)

		// Create and resolve and apply change 2
		val resolvedChange2 = createUnresolvedChange(newRootObject2).resolveBefore(resourceSet)
			 as CreateAndInsertRoot<Root>
		Assert.assertTrue(resolvedChange2.applyForward)

		// State after
		val oldSize = resourceContent.size		
		Assert.assertTrue(resourceContent.contains(resolvedChange.createChange.affectedEObject))		
		Assert.assertTrue(resourceContent.contains(resolvedChange2.createChange.affectedEObject))	
		Assert.assertTrue(stagingArea.contents.empty)	
		
		// Apply backward 2
		Assert.assertTrue(resolvedChange2.applyBackward)
		
		Assert.assertEquals(resourceContent.size, oldSize - 1)
		Assert.assertTrue(resourceContent.contains(resolvedChange.createChange.affectedEObject))
		Assert.assertFalse(resourceContent.contains(resolvedChange2.createChange.affectedEObject))		
		Assert.assertTrue(stagingArea.contents.empty)	
		
		// Apply backward 1
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertEquals(resourceContent.size, oldSize - 2)
		Assert.assertFalse(resourceContent.contains(resolvedChange.createChange.affectedEObject))
		Assert.assertFalse(resourceContent.contains(resolvedChange2.createChange.affectedEObject))		
		Assert.assertTrue(stagingArea.contents.empty)	
	}
	
	
	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(CreateAndInsertRoot<Root> change, Root newRoot) {
		Assert.assertFalse(change.isResolved)
		Assert.assertFalse(change.createChange.isResolved)
		Assert.assertFalse(change.insertChange.isResolved)
		Assert.assertTrue(change.createChange.affectedEObject != newRoot)
		Assert.assertTrue(change.insertChange.newValue != newRoot)
		Assert.assertTrue(change.createChange.affectedEObject != change.insertChange.newValue)
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
	def private CreateAndInsertRoot<Root> createUnresolvedChange(Root newObject) {
		return compoundFactory.<Root>createCreateAndInsertRootChange
		(newObject, resource, index)	
	}
}
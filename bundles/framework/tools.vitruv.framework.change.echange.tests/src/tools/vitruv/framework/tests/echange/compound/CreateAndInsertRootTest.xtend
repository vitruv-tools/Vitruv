package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import tools.vitruv.framework.change.echange.util.EChangeUtil
import tools.vitruv.framework.tests.echange.EChangeTest

/**
 * Test class for the concrete {@link CreateAndInsertRoot} EChange,
 * which creates a new root EObject and inserts it in a resource.
 */
class CreateAndInsertRootTest extends EChangeTest {
	protected var Root newRootObject = null
	protected var Root newRootObject2 = null
	protected var EList<EObject> resourceContent = null;
	
	protected static val DEFAULT_INDEX = 2
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
		resourceContent = resourceSet1.getResource(fileUri, false).contents
	}
	
	/**
	 * Resolves the {@link CreateAndInsertRoot} EChange. The model is in state
	 * before the change is applied, so the staging area and object in progress are empty,
	 * and the root object is not in the resource.
	 */
	@Test
	def public void resolveCreateAndInsertRootTest() {
		Assert.assertTrue(stagingArea1.contents.empty)
		val size = resourceContent.size
		
		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root>createCreateAndInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true)	

		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.createChange.affectedEObject != newRootObject)
		Assert.assertTrue(unresolvedChange.insertChange.newValue != newRootObject)
		Assert.assertTrue(unresolvedChange.createChange.affectedEObject != 
			unresolvedChange.insertChange.newValue)

		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1) as CreateAndInsertRoot<Root>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		Assert.assertTrue(unresolvedChange.createChange != resolvedChange.createChange)
		Assert.assertTrue(unresolvedChange.insertChange != resolvedChange.insertChange)
		Assert.assertTrue(resolvedChange.createChange.affectedEObject == 
			resolvedChange.insertChange.newValue)
		Assert.assertTrue(resolvedChange.createChange.affectedEObject != newRootObject)
			
		// Resolving applies all changes and reverts them, so the model should be unaffected.
		Assert.assertEquals(resourceContent.size, size)
		Assert.assertTrue(stagingArea1.contents.empty)
	}
	
	/**
	 * Resolves the {@link CreateAndInsertRoot} EChange. The model is in state 
	 * after the change was applied, so the staging area and object in progress are empty,
	 * and the root object is in the resource.
	 */
	@Test
	def public void resolveCreateAndInsertRootTest2() {	
		Assert.assertTrue(stagingArea1.contents.empty)
		resourceContent.add(DEFAULT_INDEX, newRootObject)
		val size = resourceContent.size
		
		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root>createCreateAndInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true)
			
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.createChange.affectedEObject != newRootObject)
		Assert.assertTrue(unresolvedChange.insertChange.newValue != newRootObject)
		Assert.assertTrue(unresolvedChange.createChange.affectedEObject != 
			unresolvedChange.insertChange.newValue)
			
		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1) as CreateAndInsertRoot<Root>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		Assert.assertTrue(unresolvedChange.createChange != resolvedChange.createChange)
		Assert.assertTrue(unresolvedChange.insertChange != resolvedChange.insertChange)
		Assert.assertTrue(resolvedChange.createChange.affectedEObject == 
			resolvedChange.insertChange.newValue)
		Assert.assertTrue(resolvedChange.createChange.affectedEObject == newRootObject)
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.
		Assert.assertEquals(resourceContent.size, size)
		Assert.assertTrue(stagingArea1.contents.empty)
	}
	
	/**
	 * Tests whether resolving the {@link CreateAndInsertRoot} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root>createCreateAndInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true)
			
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1)
		
		assertDifferentChangeSameClass(unresolvedChange, resolvedChange)
	}
	
	/**
	 * Tests applying the {@link CreateAndInsertRoot} EChange forward
	 * by creating and inserting a new root object.
	 */
	@Test
	def public void createAndInsertRootApplyForwardTest() {
		Assert.assertTrue(stagingArea1.contents.empty)
		val oldSize = resourceContent.size
		
		// Create change 1
		val resolvedChange = TypeInferringCompoundEChangeFactory.
			<Root>createCreateAndInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true).
			copyAndResolveBefore(resourceSet1) as CreateAndInsertRoot<Root>
			
		// Apply 1
		Assert.assertTrue(resolvedChange.applyForward)
	
		Assert.assertEquals(resourceContent.size, oldSize + 1)
		Assert.assertTrue(resourceContent.contains(resolvedChange.createChange.affectedEObject))
		Assert.assertTrue(stagingArea1.contents.empty)
		
		// Create change 2
		val resolvedChange2 = TypeInferringCompoundEChangeFactory.
			<Root>createCreateAndInsertRootChange(newRootObject2, fileUri.toString, DEFAULT_INDEX, true).
			copyAndResolveBefore(resourceSet1) as CreateAndInsertRoot<Root>
			
		// Apply 2
		Assert.assertTrue(resolvedChange2.applyForward)
		
		Assert.assertEquals(resourceContent.size, oldSize + 2)
		Assert.assertTrue(resourceContent.contains(resolvedChange2.createChange.affectedEObject))
		Assert.assertTrue(stagingArea1.contents.empty)
	}
	
	/**
	 * Tests applying the {@link CreateAndInsertRoot} EChange backward 
	 * by reverting the change. It removes and deletes a root object. 
	 */
	@Test
	def public void createAndInsertRootApplyBackwardTest() {
		Assert.assertTrue(stagingArea1.contents.empty)
		val oldSize = resourceContent.size
		
		// Create Change 1
		val resolvedChange = TypeInferringCompoundEChangeFactory.
			<Root>createCreateAndInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true).
			copyAndResolveBefore(resourceSet1) as CreateAndInsertRoot<Root>
			
		// Apply forward 1
		Assert.assertTrue(resolvedChange.applyForward)

		// Create Change 2
		val resolvedChange2 = TypeInferringCompoundEChangeFactory.
			<Root>createCreateAndInsertRootChange(newRootObject2, fileUri.toString, DEFAULT_INDEX, true).
			copyAndResolveBefore(resourceSet1) as CreateAndInsertRoot<Root>
			
		// Apply forward 2
		Assert.assertTrue(resolvedChange2.applyForward)
		
		Assert.assertEquals(resourceContent.size, oldSize + 2)
		Assert.assertTrue(resourceContent.contains(resolvedChange.createChange.affectedEObject))		
		Assert.assertTrue(resourceContent.contains(resolvedChange2.createChange.affectedEObject))	
		Assert.assertTrue(stagingArea1.contents.empty)	
		
		// Apply backward 2
		Assert.assertTrue(resolvedChange2.applyBackward)
		
		Assert.assertEquals(resourceContent.size, oldSize + 1)
		Assert.assertTrue(resourceContent.contains(resolvedChange.createChange.affectedEObject))
		Assert.assertFalse(resourceContent.contains(resolvedChange2.createChange.affectedEObject))		
		Assert.assertTrue(stagingArea1.contents.empty)	
		
		// Apply backward 1
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertEquals(resourceContent.size, oldSize)
		Assert.assertFalse(resourceContent.contains(resolvedChange.createChange.affectedEObject))
		Assert.assertFalse(resourceContent.contains(resolvedChange2.createChange.affectedEObject))		
		Assert.assertTrue(stagingArea1.contents.empty)	
	}
}
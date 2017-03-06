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
		Assert.assertNull(EChangeUtil.objectInProgress)
		Assert.assertTrue(stagingArea1.contents.empty)
		
		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root>createCreateAndInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true)	

		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.createChange.affectedEObject != newRootObject)
		Assert.assertTrue(unresolvedChange.insertChange.newValue != newRootObject)
		Assert.assertTrue(unresolvedChange.createChange.affectedEObject != 
			unresolvedChange.insertChange.newValue)

		val resolvedChange = unresolvedChange.resolveApply(resourceSet1) as CreateAndInsertRoot<Root>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		Assert.assertTrue(unresolvedChange.createChange != resolvedChange.createChange)
		Assert.assertTrue(unresolvedChange.insertChange != resolvedChange.insertChange)
		Assert.assertTrue(resolvedChange.createChange.affectedEObject == 
			resolvedChange.insertChange.newValue)
		Assert.assertTrue(resolvedChange.createChange.affectedEObject != newRootObject)
			
		// The Object in progress must be empty because create fills it and insert takes it.
		Assert.assertNull(EChangeUtil.objectInProgress)
		// The staging area should be unaffected because no change was applied
		Assert.assertTrue(stagingArea1.contents.empty)
	}
	
	/**
	 * Resolves the {@link CreateAndInsertRoot} EChange. The model is in state 
	 * after the change was applied, so the staging area and object in progress are empty,
	 * and the root object is in the resource.
	 */
	@Test
	def public void resolveCreateAndInsertRootTest2() {	
		Assert.assertNull(EChangeUtil.objectInProgress)
		Assert.assertTrue(stagingArea1.contents.empty)
		
		resourceContent.add(DEFAULT_INDEX, newRootObject)
		
		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root>createCreateAndInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true)
			
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.createChange.affectedEObject != newRootObject)
		Assert.assertTrue(unresolvedChange.insertChange.newValue != newRootObject)
		Assert.assertTrue(unresolvedChange.createChange.affectedEObject != 
			unresolvedChange.insertChange.newValue)
			
		val resolvedChange = unresolvedChange.resolveRevert(resourceSet1) as CreateAndInsertRoot<Root>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		Assert.assertTrue(unresolvedChange.createChange != resolvedChange.createChange)
		Assert.assertTrue(unresolvedChange.insertChange != resolvedChange.insertChange)
		Assert.assertTrue(resolvedChange.createChange.affectedEObject == 
			resolvedChange.insertChange.newValue)
		Assert.assertTrue(resolvedChange.createChange.affectedEObject == newRootObject)
		
		// The Object in progress must be empty because reverting the insert change fills it 
		// and reverting the create change takes it.
		Assert.assertNull(EChangeUtil.objectInProgress)
		// The staging area should be unaffected because no change was applied
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
			
		val resolvedChange = unresolvedChange.resolveApply(resourceSet1)
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		Assert.assertEquals(unresolvedChange.getClass, resolvedChange.getClass)
	}
	
	/**
	 * Tests the {@link CreateAndInsertRoot} EChange by creating and inserting
	 * a new root object.
	 */
	@Test
	def public void createAndInsertRootApplyTest() {
		Assert.assertNull(EChangeUtil.objectInProgress)
		Assert.assertTrue(stagingArea1.contents.empty)
		val oldSize = resourceContent.size
		
		// Create change 1
		val resolvedChange = TypeInferringCompoundEChangeFactory.
			<Root>createCreateAndInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true).
			resolveApply(resourceSet1) as CreateAndInsertRoot<Root>
			
		// Apply 1
		Assert.assertTrue(resolvedChange.apply)
	
		Assert.assertEquals(resourceContent.size, oldSize + 1)
		Assert.assertTrue(resourceContent.contains(resolvedChange.createChange.affectedEObject))
		Assert.assertNull(EChangeUtil.objectInProgress)
		Assert.assertTrue(stagingArea1.contents.empty)
		
		// Create change 2
		val resolvedChange2 = TypeInferringCompoundEChangeFactory.
			<Root>createCreateAndInsertRootChange(newRootObject2, fileUri.toString, DEFAULT_INDEX, true).
			resolveApply(resourceSet1) as CreateAndInsertRoot<Root>
			
		// Apply 2
		Assert.assertTrue(resolvedChange2.apply)
		
		Assert.assertEquals(resourceContent.size, oldSize + 2)
		Assert.assertTrue(resourceContent.contains(resolvedChange2.createChange.affectedEObject))
		Assert.assertNull(EChangeUtil.objectInProgress)
		Assert.assertTrue(stagingArea1.contents.empty)
	}
	
	/**
	 * Tests the {@link CreateAndInsertRoot} EChange by reverting the change.
	 * It removes and deletes a root object. 
	 */
	@Test
	def public void createAndInsertRootRevertTest() {
		Assert.assertNull(EChangeUtil.objectInProgress)
		Assert.assertTrue(stagingArea1.contents.empty)
		val oldSize = resourceContent.size
		
		// Create Change 1
		val resolvedChange = TypeInferringCompoundEChangeFactory.
			<Root>createCreateAndInsertRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true).
			resolveApply(resourceSet1) as CreateAndInsertRoot<Root>
			
		// Apply 1
		Assert.assertTrue(resolvedChange.apply)

		// Create Change 2
		val resolvedChange2 = TypeInferringCompoundEChangeFactory.
			<Root>createCreateAndInsertRootChange(newRootObject2, fileUri.toString, DEFAULT_INDEX, true).
			resolveApply(resourceSet1) as CreateAndInsertRoot<Root>
			
		// Apply 2
		Assert.assertTrue(resolvedChange2.apply)
		
		Assert.assertEquals(resourceContent.size, oldSize + 2)
		Assert.assertTrue(resourceContent.contains(resolvedChange.createChange.affectedEObject))		
		Assert.assertTrue(resourceContent.contains(resolvedChange2.createChange.affectedEObject))	
		Assert.assertNull(EChangeUtil.objectInProgress)
		Assert.assertTrue(stagingArea1.contents.empty)	
		
		// Revert 2
		Assert.assertTrue(resolvedChange2.revert)
		
		Assert.assertEquals(resourceContent.size, oldSize + 1)
		Assert.assertTrue(resourceContent.contains(resolvedChange.createChange.affectedEObject))
		Assert.assertFalse(resourceContent.contains(resolvedChange2.createChange.affectedEObject))		
		Assert.assertNull(EChangeUtil.objectInProgress)
		Assert.assertTrue(stagingArea1.contents.empty)	
		
		// Revert 1
		Assert.assertTrue(resolvedChange.revert)
		
		Assert.assertEquals(resourceContent.size, oldSize)
		Assert.assertFalse(resourceContent.contains(resolvedChange.createChange.affectedEObject))
		Assert.assertFalse(resourceContent.contains(resolvedChange2.createChange.affectedEObject))		
		Assert.assertNull(EChangeUtil.objectInProgress)
		Assert.assertTrue(stagingArea1.contents.empty)	
	}
}
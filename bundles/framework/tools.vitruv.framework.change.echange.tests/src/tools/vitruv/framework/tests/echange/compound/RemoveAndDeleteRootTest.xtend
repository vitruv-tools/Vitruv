package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteRoot
import tools.vitruv.framework.change.echange.util.EChangeUtil
import tools.vitruv.framework.tests.echange.EChangeTest
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory

/**
 * Test class for the concrete {@link RemoveAndDeleteRoot} EChange,
 * which removes a root element from a resource and deletes it.
 */
class RemoveAndDeleteRootTest extends EChangeTest {
	protected var Root newRootObject = null
	protected var Root newRootObject2 = null
	protected var EList<EObject> resourceContent = null;
	
	protected static val DEFAULT_INDEX = 2
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
		resourceContent = resourceSet1.getResource(fileUri, false).contents
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
		val size = resourceContent.size
		Assert.assertTrue(stagingArea1.contents.empty)
		
		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root>createRemoveAndDeleteRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true)
		
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.deleteChange.affectedEObject != newRootObject)
		Assert.assertTrue(unresolvedChange.removeChange.oldValue != newRootObject)
		Assert.assertTrue(unresolvedChange.deleteChange.affectedEObject != 
			unresolvedChange.removeChange.oldValue)			
		
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1) as RemoveAndDeleteRoot<Root>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		Assert.assertTrue(resolvedChange.deleteChange.affectedEObject
			== resolvedChange.removeChange.oldValue)
		Assert.assertTrue(resolvedChange.deleteChange.affectedEObject 
			== newRootObject)
			
		// Resolving applies all changes and reverts them, so the model should be unaffected.		
		Assert.assertEquals(resourceContent.size, size)
		Assert.assertTrue(stagingArea1.contents.empty)
	}
	
	/**
	 * Resolves the {@link RemoveAndDeleteRoot} EChange. The model is in state
	 * after the change was applied, so the staging area and object in progress
	 * are empty, and the root element was deleted.
	 */
	@Test
	def public void resolveRemoveAndDeleteRootTest2() {
		val size = resourceContent.size
		Assert.assertTrue(stagingArea1.contents.empty)
		
		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root>createRemoveAndDeleteRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true)	
		
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.deleteChange.affectedEObject != newRootObject)
		Assert.assertTrue(unresolvedChange.removeChange.oldValue != newRootObject)
		Assert.assertTrue(unresolvedChange.deleteChange.affectedEObject != 
			unresolvedChange.removeChange.oldValue)
			
		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1) as RemoveAndDeleteRoot<Root>
		
		Assert.assertTrue(resolvedChange.isResolved)
		// New object is copy
		Assert.assertTrue(resolvedChange.deleteChange.affectedEObject != newRootObject)
		Assert.assertTrue(resolvedChange.deleteChange.affectedEObject ==
			resolvedChange.removeChange.oldValue)
		Assert.assertEquals(DEFAULT_CREATED_OBJECT_1_INTEGER_VALUE,
			resolvedChange.deleteChange.affectedEObject.singleValuedEAttribute)
			
		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		Assert.assertEquals(resourceContent.size, size)
		Assert.assertTrue(stagingArea1.contents.empty)		
	}
	
	/**
	 * Tests whether resolving the {@link RemoveAndDeleteRoot} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root>createRemoveAndDeleteRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true)	
				
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1) as RemoveAndDeleteRoot<Root>
	
		assertDifferentChangeSameClass(unresolvedChange, resolvedChange)
	}
	
	/**
	 * Tests the {@link RemoveAndDeleteRoot} EChange by removing
	 * and deleting a root object.
	 */
	@Test
	def public void removeAndDeleteRootApplyTest() {
		Assert.assertTrue(stagingArea1.contents.empty)	
		val oldSize = resourceContent.size	
		
		// Create change 1
		val resolvedChange = TypeInferringCompoundEChangeFactory.
			<Root>createRemoveAndDeleteRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true).
			copyAndResolveBefore(resourceSet1)
			
		// Apply 1
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertEquals(resourceContent.size, oldSize - 1)
		Assert.assertFalse(resourceContent.contains(newRootObject))
		Assert.assertTrue(stagingArea1.contents.empty)
		
		// Create change 2
		val resolvedChange2 = TypeInferringCompoundEChangeFactory.
			<Root>createRemoveAndDeleteRootChange(newRootObject2, fileUri.toString, DEFAULT_INDEX, true).
			copyAndResolveBefore(resourceSet1)
			
		// Apply 2
		Assert.assertTrue(resolvedChange2.applyForward)
		
		Assert.assertEquals(resourceContent.size, oldSize - 2)
		Assert.assertFalse(resourceContent.contains(newRootObject2))
		Assert.assertTrue(stagingArea1.contents.empty)
	}
	
	/**
	 * Tests the {@link RemoveAndDeleteRoot} EChange by reverting the change.
	 * It creates and inserts a root object.
	 */
	@Test
	def public void removeAndDeleteRootRevertTest() {
		Assert.assertTrue(stagingArea1.contents.empty)
		val oldSize = resourceContent.size	
		val index1 = resourceContent.indexOf(newRootObject)
		val index2 = resourceContent.indexOf(newRootObject2)

		// Create change 1
		val resolvedChange = TypeInferringCompoundEChangeFactory.
			<Root>createRemoveAndDeleteRootChange(newRootObject, fileUri.toString, DEFAULT_INDEX, true).
			copyAndResolveBefore(resourceSet1)
			
		// Apply 1
		Assert.assertTrue(resolvedChange.applyForward)	
		
		// Create change 2
		val resolvedChange2 = TypeInferringCompoundEChangeFactory.
			<Root>createRemoveAndDeleteRootChange(newRootObject2, fileUri.toString, DEFAULT_INDEX, true).
			copyAndResolveBefore(resourceSet1)
			
		// Apply 2
		Assert.assertTrue(resolvedChange2.applyForward)	
		
		Assert.assertEquals(resourceContent.size, oldSize - 2)
		Assert.assertTrue(stagingArea1.contents.empty)	
		
		// Revert 2
		Assert.assertTrue(resolvedChange2.applyBackward)

		Assert.assertEquals(resourceContent.size, oldSize - 1)
		Assert.assertTrue(resourceContent.contains(newRootObject2))
		Assert.assertTrue(stagingArea1.contents.empty)	
			
		// Revert 1
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertEquals(resourceContent.size, oldSize)
		Assert.assertTrue(resourceContent.contains(newRootObject))
		Assert.assertEquals(resourceContent.indexOf(newRootObject), index1)
		Assert.assertEquals(resourceContent.indexOf(newRootObject2), index2)
		Assert.assertTrue(stagingArea1.contents.empty)	
	}
}
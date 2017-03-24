package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EReference
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot
import tools.vitruv.framework.tests.echange.feature.reference.ReferenceEChangeTest

/**
 * Test class for the concrete {@link RemoveAndDeleteNonRoot} EChange,
 * which removes a non root element reference from a containment reference 
 * list and deletes it.
 */
public class RemoveAndDeleteNonRootTest extends ReferenceEChangeTest {
	protected var EReference affectedFeature = null
	protected var EList<NonRoot> referenceContent = null
	protected var int index = DEFAULT_INDEX
	
	protected static val Integer DEFAULT_INDEX = 0
	
	/**
	 * Calls setup of super class and sets the feature for the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		referenceContent.add(newValue)
		referenceContent.add(newValue2)
	}
	
	/**
	 * Resolves a {@link RemoveAndDeleteNonRoot} EChange. The model is in state
	 * before the change, so the non root element is in a containment reference.
	 */
	@Test
	def public void resolveRemoveAndDeleteNonRoot() {
		// State before
		val size = referenceContent.size
		Assert.assertTrue(stagingArea.contents.empty)
		
		// Create change
		val unresolvedChange = createUnresolvedChange(affectedEObject, newValue)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as RemoveAndDeleteNonRoot<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)		
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		Assert.assertEquals(referenceContent.size, size)
		Assert.assertTrue(stagingArea.contents.empty)					
	}
	
	/**
	 * Resolves a {@link RemoveAndDeleteNonRoot} EChange. The model is in state
	 * after the change, so the non root element was deleted.
	 */
	@Test
	def public void resolveRemoveAndDeleteNonRoot2() {
		// State before change
		Assert.assertTrue(stagingArea.contents.empty)	
				
		// Create change
		val unresolvedChange = createUnresolvedChange(affectedEObject, newValue)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)
			
		// Set state after change
		prepareStateAfter
		val size = referenceContent.size			
				
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) 
			as RemoveAndDeleteNonRoot<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)	
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		Assert.assertEquals(referenceContent.size, size)
		Assert.assertTrue(stagingArea.contents.empty)		
	}
	
	/**
	 * Tests whether resolving the {@link RemoveAndDeleteNonRoot} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(affectedEObject, newValue)
		
		// Resolve		
 		val resolvedChange = unresolvedChange.resolveBefore(resourceSet)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests the {@link RemoveAndDeleteNonRoot} EChange by applying it forward.
	 * Removes and deletes a non root element from a containment reference.
	 */
	@Test
	def public void removeAndDeleteNonRootApplyForwardTest() {
		// State before change
		Assert.assertTrue(stagingArea.contents.empty)
		Assert.assertTrue(referenceContent.contains(newValue))	
		Assert.assertTrue(referenceContent.contains(newValue2))	
		val oldSize = referenceContent.size
		
		// Create and resolve change 1
		val resolvedChange = createUnresolvedChange(affectedEObject, newValue).resolveBefore(resourceSet)
			 as RemoveAndDeleteNonRoot<Root, NonRoot>
		
		// Apply forward 1
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertEquals(referenceContent.size, oldSize - 1)
		Assert.assertFalse(referenceContent.contains(newValue))
		Assert.assertTrue(referenceContent.contains(newValue2))
		Assert.assertTrue(stagingArea.contents.empty)
	
		// Create and resolve change 2
		val resolvedChange2 = createUnresolvedChange(affectedEObject, newValue2).resolveBefore(resourceSet)
			 as RemoveAndDeleteNonRoot<Root, NonRoot>	
			
		// Apply forward 2
		Assert.assertTrue(resolvedChange2.applyForward)
		
		Assert.assertEquals(referenceContent.size, oldSize - 2)
		Assert.assertFalse(referenceContent.contains(newValue))
		Assert.assertFalse(referenceContent.contains(newValue2))
		Assert.assertTrue(stagingArea.contents.empty)		
	}
	
	/**
	 * Tests the {@link RemoveAndDeleteNonRoot} EChange by applying it backward.
	 * Creates and reinserts the removed object.
	 */
	@Test
	def public void removeAndDeleteNonRootApplyBackwardTest() {
		// State before
		Assert.assertTrue(stagingArea.contents.empty)
		val index1 = referenceContent.indexOf(newValue)
		val index2 = referenceContent.indexOf(newValue2)
		
		// Create and resolve and apply change 1
		val resolvedChange = createUnresolvedChange(affectedEObject, newValue).resolveBefore(resourceSet)
			 as RemoveAndDeleteNonRoot<Root, NonRoot>
		Assert.assertTrue(resolvedChange.applyForward)
		
		// Create and resolve and apply change 2
		val resolvedChange2 = createUnresolvedChange(affectedEObject, newValue2).resolveBefore(resourceSet)
			 as RemoveAndDeleteNonRoot<Root, NonRoot>
		Assert.assertTrue(resolvedChange2.applyForward)		
		
		// State after change
		val oldSize = referenceContent.size
		Assert.assertTrue(referenceContent.empty)		
		
		// Apply backward 2
		Assert.assertTrue(resolvedChange2.applyBackward)
		
		Assert.assertEquals(referenceContent.size, oldSize + 1)	
		Assert.assertTrue(referenceContent.contains(newValue2))
		Assert.assertTrue(stagingArea.contents.empty)
				
		// Apply backward 1
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertEquals(referenceContent.size, oldSize + 2)	
		Assert.assertEquals(referenceContent.indexOf(newValue), index1)
		Assert.assertEquals(referenceContent.indexOf(newValue2), index2)
		Assert.assertTrue(stagingArea.contents.empty)
	}
	
	/**
	 * Removes the new values from the affected reference
	 * to set state after.
	 */
	def private void prepareStateAfter() {
		referenceContent.remove(newValue)
		referenceContent.remove(newValue2)
	}

	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(RemoveAndDeleteNonRoot<Root, NonRoot> change, Root affectedRootObject,
		NonRoot removedNonRootObject) {
		Assert.assertFalse(change.isResolved)
		Assert.assertFalse(change.removeChange.isResolved)
		Assert.assertFalse(change.deleteChange.isResolved)
		Assert.assertTrue(change.removeChange.oldValue != removedNonRootObject)
		Assert.assertTrue(change.deleteChange.affectedEObject != removedNonRootObject)
		Assert.assertTrue(change.deleteChange.affectedEObject != change.removeChange.oldValue)
	}
	
	/**
	 * Change is resolved.
	 */
	def private static void assertIsResolved(RemoveAndDeleteNonRoot<Root, NonRoot> change, Root affectedRootObject,
		NonRoot removedNonRootObject) {
		Assert.assertTrue(change.isResolved)
		change.deleteChange.affectedEObject.assertEqualsOrCopy(removedNonRootObject)
		change.removeChange.oldValue.assertEqualsOrCopy(removedNonRootObject)		
		Assert.assertSame(change.removeChange.affectedEObject, affectedRootObject)	
		
	}
	
	/**
	 * Creates new unresolved change.
	 */
	def private RemoveAndDeleteNonRoot<Root, NonRoot> createUnresolvedChange(Root affectedRootObject, NonRoot newNonRoot) {
		return compoundFactory.<Root, NonRoot>createRemoveAndDeleteNonRootChange(affectedRootObject, affectedFeature, newNonRoot, index)	
	}	
		
}
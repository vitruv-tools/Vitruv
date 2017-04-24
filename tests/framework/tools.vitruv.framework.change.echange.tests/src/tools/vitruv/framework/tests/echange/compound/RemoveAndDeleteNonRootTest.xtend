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

import static extension tools.vitruv.framework.tests.echange.util.EChangeAssertHelper.*

/**
 * Test class for the concrete {@link RemoveAndDeleteNonRoot} EChange,
 * which removes a non root element reference from a containment reference 
 * list and deletes it.
 */
public class RemoveAndDeleteNonRootTest extends ReferenceEChangeTest {
	protected var EReference affectedFeature = null
	protected var EList<NonRoot> referenceContent = null

	@Before
	override public void beforeTest() {
		super.beforeTest()
		affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
		referenceContent = affectedEObject.eGet(affectedFeature) as EList<NonRoot>
		prepareStateBefore
	}
	
	/**
	 * Resolves a {@link RemoveAndDeleteNonRoot} EChange. The model is in state
	 * before the change, so the non root element is in a containment reference.
	 */
	@Test
	def public void resolveBeforeTest() {
		// Create change
		val unresolvedChange = createUnresolvedChange(affectedEObject, newValue, 0)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)
			
		// Resolve
		val resolvedChange = unresolvedChange.resolveBefore(resourceSet) 
			as RemoveAndDeleteNonRoot<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)		
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		assertIsStateBefore
	}
	
	/**
	 * Resolves a {@link RemoveAndDeleteNonRoot} EChange. The model is in state
	 * after the change, so the non root element was deleted.
	 */
	@Test
	def public void resolveAfterTest() {		
		// Create change
		val unresolvedChange = createUnresolvedChange(affectedEObject, newValue, 0)
		unresolvedChange.assertIsNotResolved(affectedEObject, newValue)
			
		// Set state after change
		prepareStateAfter		
				
		// Resolve
		val resolvedChange = unresolvedChange.resolveAfter(resourceSet) 
			as RemoveAndDeleteNonRoot<Root, NonRoot>
		resolvedChange.assertIsResolved(affectedEObject, newValue)	
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		assertIsStateAfter	
	}
	
	/**
	 * Tests whether resolving the {@link RemoveAndDeleteNonRoot} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Create change
		val unresolvedChange = createUnresolvedChange(affectedEObject, newValue, 0)
		
		// Resolve		
 		val resolvedChange = unresolvedChange.resolveBefore(resourceSet)
		unresolvedChange.assertDifferentChangeSameClass(resolvedChange)
	}
	
	/**
	 * Tests the {@link RemoveAndDeleteNonRoot} EChange by applying it forward.
	 * Removes and deletes a non root element from a containment reference.
	 */
	@Test
	def public void applyForwardTest() {
		// Create and resolve change 1
		val resolvedChange = createUnresolvedChange(affectedEObject, newValue, 0).resolveBefore(resourceSet)
			 as RemoveAndDeleteNonRoot<Root, NonRoot>
		
		// Apply forward 1
		resolvedChange.assertApplyForward
		
		Assert.assertEquals(referenceContent.size, 1)
		Assert.assertFalse(referenceContent.contains(newValue))
		Assert.assertTrue(referenceContent.contains(newValue2))
		Assert.assertTrue(stagingArea.empty)
	
		// Create and resolve change 2
		val resolvedChange2 = createUnresolvedChange(affectedEObject, newValue2, 0).resolveBefore(resourceSet)
			 as RemoveAndDeleteNonRoot<Root, NonRoot>	
			
		// Apply forward 2
		resolvedChange2.assertApplyForward
		
		// State after
		assertIsStateAfter		
	}
	
	/**
	 * Tests the {@link RemoveAndDeleteNonRoot} EChange by applying it backward.
	 * Creates and reinserts the removed object.
	 */
	@Test
	def public void applyBackwardTest() {
		// Create and resolve and apply change 1
		val resolvedChange = createUnresolvedChange(affectedEObject, newValue, 0).resolveBefore(resourceSet)
			 as RemoveAndDeleteNonRoot<Root, NonRoot>
		resolvedChange.assertApplyForward
		
		// Create and resolve and apply change 2
		val resolvedChange2 = createUnresolvedChange(affectedEObject, newValue2, 0).resolveBefore(resourceSet)
			 as RemoveAndDeleteNonRoot<Root, NonRoot>
		resolvedChange2.assertApplyForward
		
		// State after change
		assertIsStateAfter	
		
		// Apply backward 2
		resolvedChange2.assertApplyBackward
		
		Assert.assertEquals(referenceContent.size, 1)	
		Assert.assertTrue(referenceContent.contains(newValue2))
		Assert.assertTrue(stagingArea.empty)
				
		// Apply backward 1
		resolvedChange.assertApplyBackward
		
		// State before
		assertIsStateBefore
	}
	
	/**
	 * Sets the state of the model before the changes.
	 */
	def private void prepareStateBefore() {
		referenceContent.add(newValue)
		referenceContent.add(newValue2)	
		assertIsStateBefore	
	}
	
	/**
	 * Sets the state of the model after the changes.
	 */
	def private void prepareStateAfter() {
		referenceContent.remove(newValue)
		referenceContent.remove(newValue2)
		assertIsStateAfter
	}
	
	/**
	 * Model is in state before the changes.
	 */
	def private void assertIsStateBefore() {
		Assert.assertEquals(referenceContent.size, 2)
		newValue.assertEqualsOrCopy(referenceContent.get(0))
		newValue2.assertEqualsOrCopy(referenceContent.get(1))
		Assert.assertTrue(stagingArea.empty)
	}
	
	/**
	 * Model is in state after the changes.
	 */
	def private void assertIsStateAfter() {
		Assert.assertEquals(referenceContent.size, 0)
		Assert.assertTrue(stagingArea.empty)		
	}

	/**
	 * Change is not resolved.
	 */
	def private static void assertIsNotResolved(RemoveAndDeleteNonRoot<Root, NonRoot> change, Root affectedRootObject,
		NonRoot removedNonRootObject) {
		Assert.assertFalse(change.isResolved)
		Assert.assertFalse(change.removeChange.isResolved)
		Assert.assertFalse(change.deleteChange.isResolved)
		Assert.assertNotSame(change.removeChange.oldValue, removedNonRootObject)
		Assert.assertNotSame(change.deleteChange.affectedEObject, removedNonRootObject)
		Assert.assertNotSame(change.deleteChange.affectedEObject, change.removeChange.oldValue)
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
	def private RemoveAndDeleteNonRoot<Root, NonRoot> createUnresolvedChange(Root affectedRootObject, NonRoot newNonRoot, int index) {
		return compoundFactory.createRemoveAndDeleteNonRootChange(affectedRootObject, affectedFeature, newNonRoot, index)	
	}	
		
}
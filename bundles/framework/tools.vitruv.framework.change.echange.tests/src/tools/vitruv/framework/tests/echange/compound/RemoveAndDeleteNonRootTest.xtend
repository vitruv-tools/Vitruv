package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EReference
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.change.echange.compound.RemoveAndDeleteNonRoot
import tools.vitruv.framework.tests.echange.feature.reference.ReferenceEChangeTest

/**
 * Test class for the concrete {@link RemoveAndDeleteNonRoot} EChange,
 * which removes a non root element reference from a containment reference 
 * list and deletes it.
 */
public class RemoveAndDeleteNonRootTest extends ReferenceEChangeTest {
	protected var EReference defaultAffectedFeature = null
	protected var EList<NonRoot> referenceContent = null
	
	protected static val Integer DEFAULT_INDEX = 0
	
	/**
	 * Calls setup of super class and sets the feature for the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		defaultAffectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
		referenceContent = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<NonRoot>
	}
	
	/**
	 * Inserts new values in the reference to remove them in the tests.
	 */
	def private void prepareReference() {
		referenceContent.add(defaultNewValue)
		referenceContent.add(defaultNewValue2)
	}
	
	/**
	 * Resolves a {@link RemoveAndDeleteNonRoot} EChange. The model is in state
	 * before the change, so the non root element is in a containment reference.
	 */
	@Test
	def public void resolveRemoveAndDeleteNonRoot() {
		// Set state before change
		prepareReference
		val size = referenceContent.size
		Assert.assertTrue(stagingArea1.contents.empty)
		
		// Create change
		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createRemoveAndDeleteNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)
			
		Assert.assertFalse(unresolvedChange.isResolved)		
		Assert.assertTrue(unresolvedChange.removeChange.affectedEObject != defaultNewValue)
		Assert.assertTrue(unresolvedChange.deleteChange.affectedEObject != defaultNewValue)
		Assert.assertTrue(unresolvedChange.removeChange.affectedEObject != 
			unresolvedChange.deleteChange.affectedEObject)
			
		// Resolve
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1) as RemoveAndDeleteNonRoot<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		Assert.assertTrue(resolvedChange.removeChange.oldValue == defaultNewValue)
		Assert.assertTrue(resolvedChange.deleteChange.affectedEObject == defaultNewValue)	
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		Assert.assertEquals(referenceContent.size, size)
		Assert.assertTrue(stagingArea1.contents.empty)					
	}
	
	/**
	 * Resolves a {@link RemoveAndDeleteNonRoot} EChange. The model is in state
	 * after the change, so the non root element was deleted.
	 */
	@Test
	def public void resolveRemoveAndDeleteNonRoot2() {
		// Set state before change
		prepareReference
		
		// Create change
		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createRemoveAndDeleteNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)
			
		// Set state after change
		referenceContent.clear
		val size = referenceContent.size
						
		Assert.assertFalse(unresolvedChange.isResolved)		
		Assert.assertTrue(unresolvedChange.removeChange.affectedEObject != defaultNewValue)
		Assert.assertTrue(unresolvedChange.deleteChange.affectedEObject != defaultNewValue)
		Assert.assertTrue(unresolvedChange.removeChange.affectedEObject != 
			unresolvedChange.deleteChange.affectedEObject)				
				
		// Resolve
		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1) as RemoveAndDeleteNonRoot<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)	
		// Old object is copy
		Assert.assertTrue(resolvedChange.removeChange.oldValue != defaultNewValue)
		Assert.assertTrue(resolvedChange.deleteChange.affectedEObject != defaultNewValue)
		Assert.assertTrue(resolvedChange.removeChange.oldValue ==
			resolvedChange.deleteChange.affectedEObject)
		
		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		Assert.assertEquals(referenceContent.size, size)
		Assert.assertTrue(stagingArea1.contents.empty)		
	}
	
	/**
	 * Tests whether resolving the {@link RemoveAndDeleteNonRoot} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Set state before change
		prepareReference
		
		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createRemoveAndDeleteNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)
	
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1) as RemoveAndDeleteNonRoot<Root, NonRoot>
		
		assertDifferentChangeSameClass(unresolvedChange, resolvedChange)
	}
	
	/**
	 * Tests the {@link RemoveAndDeleteNonRoot} EChange by applying it forward.
	 * Removes and deletes a non root element from a containment reference.
	 */
	@Test
	def public void removeAndDeleteNonRootApplyForwardTest() {
		// Set state before change
		prepareReference
		Assert.assertTrue(stagingArea1.contents.empty)
		val oldSize = referenceContent.size
		
		// Create change 1
		val resolvedChange = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createRemoveAndDeleteNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true).
			copyAndResolveBefore(resourceSet1) as RemoveAndDeleteNonRoot<Root, NonRoot>
		
		// Apply forward 1
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertEquals(referenceContent.size, oldSize - 1)
		Assert.assertFalse(referenceContent.contains(defaultNewValue))
		Assert.assertTrue(referenceContent.contains(defaultNewValue2))
		Assert.assertTrue(stagingArea1.contents.empty)
	
		// Create change 2
		val resolvedChange2 = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createRemoveAndDeleteNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue2, DEFAULT_INDEX, true).
			copyAndResolveBefore(resourceSet1) as RemoveAndDeleteNonRoot<Root, NonRoot>	
			
		// Apply forward 2
		Assert.assertTrue(resolvedChange2.applyForward)
		
		Assert.assertEquals(referenceContent.size, oldSize - 2)
		Assert.assertFalse(referenceContent.contains(defaultNewValue))
		Assert.assertFalse(referenceContent.contains(defaultNewValue2))
		Assert.assertTrue(stagingArea1.contents.empty)		
	}
	
	/**
	 * Tests the {@link RemoveAndDeleteNonRoot} EChange by applying it backward.
	 * Creates and reinserts the removed object.
	 */
	@Test
	def public void removeAndDeleteNonRootApplyBackwardTest() {
		// Set state before change
		prepareReference
		
		// Create change 
		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createRemoveAndDeleteNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)
			
		// Set state after change
		referenceContent.clear
		Assert.assertTrue(referenceContent.empty)
		
		// Resolve
		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1) as RemoveAndDeleteNonRoot<Root, NonRoot>
		
		val oldSize = referenceContent.size
		
		// Apply backward
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertEquals(referenceContent.size, oldSize + 1)	
		// Contains copy of old value
		Assert.assertFalse(referenceContent.contains(defaultNewValue))
		Assert.assertTrue(referenceContent.contains(resolvedChange.removeChange.oldValue))
		Assert.assertEquals(resolvedChange.removeChange.oldValue.id, DEFAULT_NEW_NON_ROOT_NAME)
		Assert.assertTrue(stagingArea1.contents.empty)
	}
}
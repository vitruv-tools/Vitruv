package tools.vitruv.framework.tests.echange.compound

import allElementTypes.AllElementTypesFactory
import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.ecore.EReference
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceAndDeleteNonRoot
import tools.vitruv.framework.tests.echange.feature.reference.ReferenceEChangeTest

/**
 * Test class for the concrete {@link CreateAndReplaceAndDeleteNonRoot} EChange,
 * which creates a new non root EObject and replaces an existing non root object
 * in a single value containment reference. The existing one will be deleted.
 */
class CreateAndReplaceAndDeleteNonRootTest extends ReferenceEChangeTest {
	protected var NonRoot defaultOldValue = null	
	protected var EReference defaultAffectedFeature = null
	
	protected static val Integer DEFAULT_INDEX = 0
	
	/**
	 * Calls setup of super class and sets the feature for the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		defaultOldValue = AllElementTypesFactory.eINSTANCE.createNonRoot()
		defaultAffectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
	}
	
	/**
	 * Resolves a {@link CreateAndReplaceAndDeleteNonRoot} EChange. The 
	 * model is in state before the change, so new value doesn't exist and 
	 * the old value is in the containment reference.
	 */
	@Test
	def public void resolveCreateAndReplaceAndDeleteNonRootTest() {
		defaultAffectedEObject.eSet(defaultAffectedFeature, defaultOldValue)
		Assert.assertTrue(stagingArea1.contents.empty)
		
		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createCreateAndReplaceAndDeleteNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultOldValue, defaultNewValue, true)
			
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.createChange.affectedEObject != defaultNewValue)
		Assert.assertTrue(unresolvedChange.replaceChange.affectedEObject != defaultAffectedEObject)
		Assert.assertTrue(unresolvedChange.replaceChange.oldValue != defaultOldValue)
		Assert.assertTrue(unresolvedChange.replaceChange.newValue != defaultNewValue)
		Assert.assertTrue(unresolvedChange.deleteChange.affectedEObject != defaultOldValue)
		Assert.assertTrue(unresolvedChange.createChange.affectedEObject != 
			unresolvedChange.replaceChange.newValue)
		Assert.assertTrue(unresolvedChange.deleteChange.affectedEObject != 
			unresolvedChange.replaceChange.oldValue)
		
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1) as CreateAndReplaceAndDeleteNonRoot<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		// New value is copy of original object
		Assert.assertTrue(resolvedChange.createChange.affectedEObject != defaultNewValue)
		Assert.assertTrue(resolvedChange.replaceChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.replaceChange.oldValue == defaultOldValue)
		Assert.assertTrue(resolvedChange.replaceChange.newValue != defaultNewValue)
		Assert.assertTrue(resolvedChange.deleteChange.affectedEObject == defaultOldValue)
		Assert.assertTrue(resolvedChange.createChange.affectedEObject == 
			resolvedChange.replaceChange.newValue)
		Assert.assertTrue(resolvedChange.deleteChange.affectedEObject == 
			resolvedChange.replaceChange.oldValue)
			
		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		Assert.assertTrue(defaultAffectedEObject.eGet(defaultAffectedFeature) == defaultOldValue)
		Assert.assertTrue(stagingArea1.contents.empty)
	}
	
	/**
	 * Resolves a {@link CreateAndReplaceAndDeleteNonRoot} EChange. The 
	 * model is in state after the change, so old value doesn't exist and 
	 * the new value is in the containment reference.
	 */
	@Test
	def public void resolveCreateAndReplaceAndDeleteNonRootTest2() {
		// Set state before change.
		defaultAffectedEObject.eSet(defaultAffectedFeature, defaultOldValue)
		
		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createCreateAndReplaceAndDeleteNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultOldValue, defaultNewValue, true)
			
		// Set state after change	
		defaultAffectedEObject.eSet(defaultAffectedFeature, defaultNewValue)
		
		Assert.assertTrue(stagingArea1.contents.empty)
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.createChange.affectedEObject != defaultNewValue)
		Assert.assertTrue(unresolvedChange.replaceChange.affectedEObject != defaultAffectedEObject)
		Assert.assertTrue(unresolvedChange.replaceChange.oldValue != defaultOldValue)
		Assert.assertTrue(unresolvedChange.replaceChange.newValue != defaultNewValue)
		Assert.assertTrue(unresolvedChange.deleteChange.affectedEObject != defaultOldValue)
		Assert.assertTrue(unresolvedChange.createChange.affectedEObject != 
			unresolvedChange.replaceChange.newValue)
		Assert.assertTrue(unresolvedChange.deleteChange.affectedEObject != 
			unresolvedChange.replaceChange.oldValue)
			
		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1) as CreateAndReplaceAndDeleteNonRoot<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		
		Assert.assertTrue(resolvedChange.createChange.affectedEObject == defaultNewValue)
		Assert.assertTrue(resolvedChange.replaceChange.affectedEObject == defaultAffectedEObject)
		// Old value is copy of original object
		Assert.assertTrue(resolvedChange.replaceChange.oldValue != defaultOldValue)
		Assert.assertTrue(resolvedChange.replaceChange.newValue == defaultNewValue)
		Assert.assertTrue(resolvedChange.deleteChange.affectedEObject != defaultOldValue)
		Assert.assertTrue(resolvedChange.createChange.affectedEObject == 
			resolvedChange.replaceChange.newValue)
		Assert.assertTrue(resolvedChange.deleteChange.affectedEObject == 
			resolvedChange.replaceChange.oldValue)
			
		// Resolving applies all changes and reverts them, so the model should be unaffected.			
		Assert.assertTrue(defaultAffectedEObject.eGet(defaultAffectedFeature) == defaultNewValue)
		Assert.assertTrue(stagingArea1.contents.empty)
	}
	
	/**
	 * Tests whether resolving the {@link CreateAndReplaceAndDeleteNonRoot} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Set state before change.
		defaultAffectedEObject.eSet(defaultAffectedFeature, defaultOldValue)

		val unresolvedChange = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createCreateAndReplaceAndDeleteNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultOldValue, defaultNewValue, true)

		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1) as CreateAndReplaceAndDeleteNonRoot<Root, NonRoot>

		assertDifferentChangeSameClass(unresolvedChange, resolvedChange)				
	}
	
	/**
	 * Tests applying the {@link CreateAndReplaceAndDeleteNonRoot} EChange forward
	 * by creating a new non root and replacing an existing one.
	 */
	@Test
	def public void createAndReplaceAndDeleteNonRootApplyForwardTest() {
		// Set state before change.
		defaultAffectedEObject.eSet(defaultAffectedFeature, defaultOldValue)
		
		// Create change 1
		val resolvedChange = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createCreateAndReplaceAndDeleteNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultOldValue, defaultNewValue, true).
			copyAndResolveBefore(resourceSet1)
			
		var NonRoot newValue = defaultAffectedEObject.eGet(defaultAffectedFeature) as NonRoot
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(newValue == defaultOldValue)
		Assert.assertTrue(newValue != defaultNewValue)
		Assert.assertTrue(stagingArea1.contents.empty)
		
		// Apply change 1 forward
		Assert.assertTrue(resolvedChange.applyForward)
		
		newValue = defaultAffectedEObject.eGet(defaultAffectedFeature) as NonRoot
		Assert.assertTrue(newValue != defaultOldValue)
		// New value is copy
		Assert.assertTrue(newValue != defaultNewValue)
		Assert.assertEquals(newValue.id, DEFAULT_NEW_NON_ROOT_NAME)
		Assert.assertTrue(stagingArea1.contents.empty)
		
		// Create change 2
		val resolvedChange2 = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createCreateAndReplaceAndDeleteNonRootChange(defaultAffectedEObject, defaultAffectedFeature, newValue, defaultNewValue2, true).
			copyAndResolveBefore(resourceSet1)
			
		Assert.assertTrue(resolvedChange.isResolved)
		
		// Apply change 2 forward
		Assert.assertTrue(resolvedChange2.applyForward)	
		
		var NonRoot newValue2 = defaultAffectedEObject.eGet(defaultAffectedFeature) as NonRoot
		Assert.assertTrue(newValue2 != newValue)
		// New value 2 is copy
		Assert.assertTrue(newValue2 != defaultNewValue2)
		Assert.assertEquals(newValue2.id, DEFAULT_NEW_NON_ROOT_NAME_2)
		Assert.assertTrue(stagingArea1.contents.empty)			
	}
	
	/**
	 * Tests applying the {@link CreateAndReplaceAndDeleteNonRoot} EChange backward
	 * by replacing a single value containment reference with its old value.
	 */
	@Test
	def public void createAndReplaceAndDeleteNonRootApplyBackwardTest() {
		// Set state before change.
		defaultAffectedEObject.eSet(defaultAffectedFeature, defaultOldValue)
		
		// Create change 
		val resolvedChange = TypeInferringCompoundEChangeFactory.
			<Root, NonRoot>createCreateAndReplaceAndDeleteNonRootChange(defaultAffectedEObject, defaultAffectedFeature, defaultOldValue, defaultNewValue, true).
			copyAndResolveBefore(resourceSet1)
			
		// Set state after change 
		defaultAffectedEObject.eSet(defaultAffectedFeature, defaultNewValue)
	
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(stagingArea1.contents.empty)
		
		// Apply backward
		Assert.assertTrue(resolvedChange.applyBackward)
		
		val oldValue = defaultAffectedEObject.eGet(defaultAffectedFeature) as NonRoot
		Assert.assertTrue(defaultOldValue == oldValue)
		Assert.assertTrue(defaultNewValue != oldValue)
		Assert.assertTrue(stagingArea1.contents.empty)			
	}
}
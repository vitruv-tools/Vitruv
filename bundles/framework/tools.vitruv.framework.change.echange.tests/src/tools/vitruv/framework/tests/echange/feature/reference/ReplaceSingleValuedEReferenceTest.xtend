package tools.vitruv.framework.tests.echange.feature.reference

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference

/**
 * Test class for the concrete {@link ReplaceSingleValuedEReference} EChange,
 * which replaces the value of a reference with a new one.
 */
public class ReplaceSingleValuedEReferenceTest extends ReferenceEChangeTest {	
	protected var NonRoot defaultOldValue = null	
	protected var EReference defaultAffectedFeature = null
	
	/**
	 * Sets the default affected feature and old value for the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		defaultAffectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_NON_CONTAINMENT_EREFERENCE
		defaultOldValue = rootObject1.singleValuedNonContainmentEReference
	}
	
	/**
	 * Test resolves a {@link ReplaceSingleValuedEReference} EChange with correct parameters.
	 * The model is in the state before the change.
	 * The reference is a non containment reference so the new object is in
	 * the resource.
	 */
	@Test
	def public void resolveReplaceSingleValuedEReferenceTest() {
		prepareResource
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createReplaceSingleReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultOldValue, defaultNewValue, true)
			
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.affectedEObject != defaultAffectedEObject)
		Assert.assertTrue(unresolvedChange.oldValue != defaultOldValue)
		Assert.assertTrue(unresolvedChange.newValue != defaultNewValue)
		
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1) as ReplaceSingleValuedEReference<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(resolvedChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.oldValue == defaultOldValue)
		Assert.assertTrue(resolvedChange.newValue == defaultNewValue)
	}
	
	/**
	 * Test resolves a {@link ReplaceSingleValuedEReference} EChange with correct parameters.
	 * The model is in the state before the change.
	 * The reference is a containment reference, so the new object is in the staging area
	 */
	@Test
	def public void resolveReplaceSingleValuedEReferenceTest2() {
		val affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		// New value is in staging area
		prepareStagingArea(defaultNewValue)
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createReplaceSingleReferenceChange(defaultAffectedEObject, affectedFeature, defaultOldValue, defaultNewValue, true)
			
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.affectedEObject != defaultAffectedEObject)
		Assert.assertTrue(unresolvedChange.oldValue != defaultOldValue)
		Assert.assertTrue(unresolvedChange.newValue != defaultNewValue)
		
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1) as ReplaceSingleValuedEReference<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(resolvedChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.oldValue == defaultOldValue)
		Assert.assertTrue(resolvedChange.newValue == defaultNewValue)
	}
	
	/**
	 * Test resolves a {@link ReplaceSingleValuedEReference} EChange with correct parameters.
	 * The model is in the state after the change.
	 * The reference is a non containment reference, so the old value is in the resource.
	 */
	@Test
	def public void resolveReplaceSingleValuedEReferenceTest3() {
		prepareResource
		
		// Set state before
		defaultAffectedEObject.eSet(defaultAffectedFeature, defaultOldValue)
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createReplaceSingleReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultOldValue, defaultNewValue, true)
			
		// Set state after
		defaultAffectedEObject.eSet(defaultAffectedFeature, defaultNewValue)
		
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.affectedEObject != defaultAffectedEObject)
		Assert.assertTrue(unresolvedChange.oldValue != defaultOldValue)
		Assert.assertTrue(unresolvedChange.newValue != defaultNewValue)
		
		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1) as ReplaceSingleValuedEReference<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(resolvedChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.oldValue == defaultOldValue)
		Assert.assertTrue(resolvedChange.newValue == defaultNewValue)		
	}
	
	/**
	 * Test resolves a {@link ReplaceSingleValuedEReference} EChange with correct parameters.
	 * The model is in the state after the change.
	 * The reference is a containment reference, so the old value is in the staging area.
	 */
	 @Test
	 def public void resolveReplaceSingleValuedEReferenceTest4() {
		val affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE

		// Set state before
		defaultAffectedEObject.eSet(affectedFeature, defaultOldValue)
		prepareStagingArea(defaultNewValue)
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createReplaceSingleReferenceChange(defaultAffectedEObject, affectedFeature, defaultOldValue, defaultNewValue, true)

		// Set state after
		defaultAffectedEObject.eSet(affectedFeature, defaultNewValue)
		prepareStagingArea(defaultOldValue)
		
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.affectedEObject != defaultAffectedEObject)
		Assert.assertTrue(unresolvedChange.oldValue != defaultOldValue)
		Assert.assertTrue(unresolvedChange.newValue != defaultNewValue)
		
		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1) as ReplaceSingleValuedEReference<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(resolvedChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.oldValue == defaultOldValue)
		Assert.assertTrue(resolvedChange.newValue == defaultNewValue)		 	
	 }

	/**
	 * Tests whether resolving the {@link ReplaceSingleValuedEReference} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		prepareResource
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createReplaceSingleReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultOldValue, defaultNewValue, true)	
			
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1)
		
		assertDifferentChangeSameClass(unresolvedChange, resolvedChange)
	}
	
	/**
	 * Tests applying the {@link ReplaceSingleValuedEReference} EChange forward 
	 * by replacing a single value non containment reference in the root element.
	 */
	@Test
	def public void replaceSingleValuedEReferenceApplyForwardTest() {
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createReplaceSingleReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultOldValue, defaultNewValue, false)	
			
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(defaultOldValue != defaultNewValue)
		Assert.assertTrue(defaultAffectedEObject.eGet(defaultAffectedFeature) == defaultOldValue)
		Assert.assertEquals(stagingArea1.contents.size, 0)
		
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertTrue(defaultAffectedEObject.eGet(defaultAffectedFeature) == defaultNewValue)
		Assert.assertEquals(stagingArea1.contents.size, 0) // The staging area must be unaffected
	}
	
	/**
	 * Tests applying the {@link ReplaceSingleValuedEReference} EChange forward
	 * by replacing a single value containment reference in the root element.
	 */
	@Test
	def public void replaceSingleValuedEReferenceApplyForwardTest2() {
		val affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		defaultAffectedEObject.eSet(affectedFeature, defaultOldValue)
		prepareStagingArea(defaultNewValue)
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createReplaceSingleReferenceChange(defaultAffectedEObject, affectedFeature, defaultOldValue, defaultNewValue, false)
			
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(defaultOldValue != defaultNewValue)
		Assert.assertTrue(defaultAffectedEObject.eGet(affectedFeature) == defaultOldValue)
		Assert.assertEquals(stagingArea1.contents.size, 1)	
		Assert.assertTrue(stagingArea1.contents.contains(defaultNewValue))
		
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertTrue(defaultAffectedEObject.eGet(affectedFeature) == defaultNewValue)
		Assert.assertEquals(stagingArea1.contents.size, 1) 
		Assert.assertTrue(stagingArea1.contents.contains(defaultOldValue))
	}
	
	/**
	 * Tests applying a {@link ReplaceSingleValuedEReference} EChange backward
	 * by replacing a single value non containment reference with its old value.
	 */
	@Test
	def public void replaceSingleValuedEReferenceApplyBackwardTest() {
		defaultAffectedEObject.singleValuedNonContainmentEReference = defaultNewValue
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createReplaceSingleReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultOldValue, defaultNewValue, false)	
			
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(defaultOldValue != defaultNewValue)
		Assert.assertTrue(defaultAffectedEObject.eGet(defaultAffectedFeature) == defaultNewValue)
		Assert.assertEquals(stagingArea1.contents.size, 0)
				
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertTrue(defaultAffectedEObject.eGet(defaultAffectedFeature) == defaultOldValue)
		Assert.assertEquals(stagingArea1.contents.size, 0) // The staging area must be unaffected		
	}
	
	/**
	 * Tests applying a {@link ReplaceSingleValuedEReference} EChange backward
	 * by replacing a single value containment reference with its old value.
	 */
	@Test
	def public void replaceSingleValuedEReferenceApplyBackwardTest2() {
		val affectedFeature = AllElementTypesPackage.Literals.ROOT__SINGLE_VALUED_CONTAINMENT_EREFERENCE
		defaultAffectedEObject.eSet(affectedFeature, defaultNewValue)
		prepareStagingArea(defaultOldValue)
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createReplaceSingleReferenceChange(defaultAffectedEObject, affectedFeature, defaultOldValue, defaultNewValue, false)	
			
		Assert.assertTrue(resolvedChange.isResolved)	
		Assert.assertTrue(defaultOldValue != defaultNewValue)	
		Assert.assertTrue(defaultAffectedEObject.eGet(affectedFeature) == defaultNewValue)
		Assert.assertEquals(stagingArea1.contents.size, 1)
		Assert.assertTrue(stagingArea1.contents.contains(defaultOldValue))
		
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertTrue(defaultAffectedEObject.eGet(affectedFeature) == defaultOldValue)
		Assert.assertEquals(stagingArea1.contents.size, 1)
		Assert.assertTrue(stagingArea1.contents.contains(defaultNewValue))
	}
}
package tools.vitruv.framework.tests.echange.feature.reference

import allElementTypes.AllElementTypesFactory
import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.feature.reference.InsertEReference

/**
 * Test class for the concrete {@link InsertEReferenceValue} EChange,
 * which inserts a reference in a multivalued attribute.
 */
public class InsertEReferenceTest extends ReferenceEChangeTest {
	protected var EReference defaultAffectedFeature = null
	protected var EList<EObject> resourceContent = null
	protected var EList<NonRoot> referenceContent = null
	
	protected static val Integer DEFAULT_INDEX = 0
	
	/**
	 * Sets the default affected feature for the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		defaultAffectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE
		resourceContent = resource1.contents
		referenceContent = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<NonRoot>
	}
	
	/**
	 * Test resolves a {@link InsertEReference} EChange with correct parameters on
	 * a model which is in state before the change.
	 * The affected feature is a non containment reference, so the new reference
	 * is in the resource already.
	 */
	@Test
	def public void resolveInsertEReferenceTest() {
		prepareResource
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)
			
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.affectedEObject != defaultAffectedEObject)
		Assert.assertTrue(unresolvedChange.newValue != defaultNewValue)
		
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1) as InsertEReference<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(resolvedChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.newValue == defaultNewValue)
	}
	
	/**
	 * Test resolves a {@link InsertEReference} EChange with correct parameters on
	 * a model which is in state before the change.
	 * The affected feature is a containment reference, so the new reference 
	 * is in the staging area.
	 */
	@Test
	def public void resolveInsertEReferenceTest2() {
		prepareStagingArea(defaultNewValue)
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)
			
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.affectedEObject != defaultAffectedEObject)
		Assert.assertTrue(unresolvedChange.newValue != defaultNewValue)	
		Assert.assertFalse(resourceContent.contains(defaultNewValue))
		
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1) as InsertEReference<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(resolvedChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.newValue == defaultNewValue)				
	}
	
	/**
	 * Test resolves a {@link InsertEReference} EChange with correct parameters on 
	 * a model which is in state after the change.
	 * The affected feature is a non containment reference, so the inserted
	 * reference is already a root element.
	 */
	@Test
	def public void resolveInsertEReferenceTest3() {
		prepareResource
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)
			
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.affectedEObject != defaultAffectedEObject)
		Assert.assertTrue(unresolvedChange.newValue != defaultNewValue)		
		Assert.assertTrue(resourceContent.contains(defaultNewValue))	
		
		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1) as InsertEReference<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(resolvedChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.newValue == defaultNewValue)			
	}
	
	/**
	 * Test resolves a {@link InsertEReference} EChange with correct parameters on
	 * a model which is in the state after the change.
	 * The affected feature is a containment reference, so the inserted 
	 * reference is in the resource after the change.
	 */
	@Test
	def public void resolveInsertEReferenceTest4() {
		val affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
		referenceContent = defaultAffectedEObject.eGet(affectedFeature) as EList<NonRoot>
		referenceContent.add(defaultNewValue)
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, affectedFeature, defaultNewValue, DEFAULT_INDEX, true)		
		
		Assert.assertFalse(unresolvedChange.isResolved)	
		Assert.assertTrue(unresolvedChange.affectedEObject != defaultAffectedEObject)	
		Assert.assertTrue(unresolvedChange.newValue != defaultNewValue)
		Assert.assertTrue(referenceContent.contains(defaultNewValue))
		
		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1) as InsertEReference<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(resolvedChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.newValue == defaultNewValue)					
	}
	
	/**
	 * Tests whether resolving the {@link InsertEReference} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		resourceContent.add(defaultNewValue)
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)	
			
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1)
		
		assertDifferentChangeSameClass(unresolvedChange, resolvedChange)
	}
	
	 /**
	  * Tests applying the {@link InsertEReference} EChange forward by 
	  * inserting new values in a multivalued reference.
	  * The affected feature is a non containment reference, so the 
	  * new value is already in the resource.
	  */
	@Test
	def public void insertEReferenceApplyForwardTest() {
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, false)
	 	 	
	    Assert.assertEquals(referenceContent.size, 0)
	 	
	 	// Insert first value
	 	Assert.assertTrue(resolvedChange.applyForward)
	 	
	 	Assert.assertEquals(referenceContent.size, 1)
	 	Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue)

	 	
	 	val resolvedChange2 = TypeInferringAtomicEChangeFactory.
	 	 	<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue2, DEFAULT_INDEX, false)
	 	
	 	// Insert second value before first value (at index 0)
	 	Assert.assertTrue(resolvedChange2.applyForward)
	 	
	 	Assert.assertEquals(referenceContent.size, 2)
	 	Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue2)
	 	Assert.assertTrue(referenceContent.get(DEFAULT_INDEX + 1) == defaultNewValue)
	}
	
	/**
	 * Tests applying the {@link InsertEReference} EChange forward by 
	 * inserting new values from a multivalued reference.
	 * The affected feature is a containment reference, so the
	 * new value is from the staging area.
	 */
	@Test
	def public void insertEReferenceApplyForwardTest2() {
		prepareStagingArea(defaultNewValue)
		val affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
		referenceContent = defaultAffectedEObject.eGet(affectedFeature) as EList<NonRoot>
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, affectedFeature, defaultNewValue, DEFAULT_INDEX, false)
			
		Assert.assertEquals(stagingArea1.contents.size, 1)
		Assert.assertEquals(referenceContent.size, 0)
		
		// Insert first value
	 	Assert.assertTrue(resolvedChange.applyForward)
	 	
		Assert.assertEquals(stagingArea1.contents.size, 0)
		Assert.assertEquals(referenceContent.size, 1)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue)
		
		prepareStagingArea(defaultNewValue2)
		val resolvedChange2 = TypeInferringAtomicEChangeFactory.
	 	 	<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, affectedFeature, defaultNewValue2, DEFAULT_INDEX, false)
	 	
	 	// Insert second value before first value (at index 0)
	 	Assert.assertTrue(resolvedChange2.applyForward)
	 	
		Assert.assertEquals(stagingArea1.contents.size, 0)	 	
	 	Assert.assertEquals(referenceContent.size, 2)
	 	Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue2)
	 	Assert.assertTrue(referenceContent.get(DEFAULT_INDEX + 1) == defaultNewValue)
	}
	
	/**
	 * Tests applying two {@link InsertEReference} EChanges backward by
	 * removing new added values from a multivalued reference.
	 * The affected feature is a non containment reference, so the
	 * removed values are already in the resource.
	 */
	@Test
	def public void insertEReferenceApplyBackwardTest() {
		Assert.assertEquals(referenceContent.size, 0)
		
		// defaultNewValue2 was added first at index 0 (resolvedChange), then defaultNewValue at index 0 (resolvedChange2)
		referenceContent.add(defaultNewValue)
		referenceContent.add(defaultNewValue2)
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue2, DEFAULT_INDEX, false)
			
		val resolvedChange2 = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, false)
			
		Assert.assertEquals(referenceContent.size, 2)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX + 1) == defaultNewValue2)	
		
		Assert.assertTrue(resolvedChange2.applyBackward)
		
		Assert.assertEquals(referenceContent.size, 1)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue2)
		
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertEquals(referenceContent.size, 0)
	}
	
	/**
	 * Tests applying two {@link InsertEReference} EChanges backward by 
	 * removing new added values from a multivalued reference.
	 * The affected feature is a containment reference, so the
	 * removed values will be placed in the staging area after removing them.
	 */
	@Test
	def public void insertEReferenceApplyBackwardTest2() {
		val affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
		referenceContent = defaultAffectedEObject.eGet(affectedFeature) as EList<NonRoot>
		Assert.assertEquals(stagingArea1.contents.size, 0)
		Assert.assertEquals(referenceContent.size, 0)
		
		// defaultNewValue2 was added first at index 0 (resolvedChange), then defaultNewValue at index 0 (resolvedChange2)
		referenceContent.add(defaultNewValue)
		referenceContent.add(defaultNewValue2)
	
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, affectedFeature, defaultNewValue2, DEFAULT_INDEX, false)
			
		val resolvedChange2 = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, affectedFeature, defaultNewValue, DEFAULT_INDEX, false)	
			
		
		Assert.assertEquals(referenceContent.size, 2)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX + 1) == defaultNewValue2)	
		
		Assert.assertTrue(resolvedChange2.applyBackward)
		
		Assert.assertEquals(stagingArea1.contents.size, 1)
		Assert.assertEquals(referenceContent.size, 1)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue2)
		
		// Now another change would delete the element in the staging area (or reinsert)
		stagingArea1.contents.clear
		
		Assert.assertTrue(resolvedChange.applyBackward)
		
		Assert.assertEquals(stagingArea1.contents.size, 1)
		Assert.assertEquals(referenceContent.size, 0)		
	}
	
	/**
	 * Tests the {@link InsertEReference} EChange with invalid index.
	 */
	@Test
	def public void insertEReferenceInvalidIndexTest() {
		val index = 5 // Valid index in empty list is only 0
		val EList<NonRoot> multivaluedReference = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<NonRoot>
		Assert.assertEquals(multivaluedReference.size, 0)
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, index, false)
			
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertFalse(resolvedChange.applyForward)
		Assert.assertFalse(resolvedChange.applyBackward)
	}
	
	/**
	 * Tests an affected object which has no such reference.
	 */
	@Test
	def public void insertEReferenceInvalidAttribute() {
		val affectedEObject = rootObject1.singleValuedNonContainmentEReference // NonRoot element

		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<NonRoot, NonRoot>createInsertReferenceChange(affectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, false)
		
		// NonRoot has no such feature
		Assert.assertTrue(affectedEObject.eClass.getFeatureID(defaultAffectedFeature) == -1)
		
		Assert.assertFalse(resolvedChange.applyForward)
		Assert.assertFalse(resolvedChange.applyBackward)
	}
	
	/**
	 * Tests a {@link InsertEReference} EChange with the wrong value type.
	 */
	@Test
	def public void insertEReferenceInvalidValue() {
		val newValue = AllElementTypesFactory.eINSTANCE.createRoot() // value is Root, reference value is NonRoot
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, Root>createInsertReferenceChange(defaultAffectedEObject, defaultAffectedFeature, newValue, DEFAULT_INDEX, false)
			
		// Type of reference is NonRoot not Root
		Assert.assertEquals(defaultAffectedFeature.EType.name, "NonRoot")
		
		Assert.assertFalse(resolvedChange.applyForward)
		Assert.assertFalse(resolvedChange.applyBackward)
	}
}
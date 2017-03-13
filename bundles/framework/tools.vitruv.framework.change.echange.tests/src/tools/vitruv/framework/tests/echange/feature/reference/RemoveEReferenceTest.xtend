package tools.vitruv.framework.tests.echange.feature.reference

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EReference
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.feature.reference.RemoveEReference
import allElementTypes.AllElementTypesFactory
// TODO Stefan: incomplete
/**
 * Test class for the concrete {@link RemoveEReference} EChange, 
 * which removes a reference from a multivalued attribute.
 */
public class RemoveEReferenceTest extends ReferenceEChangeTest {
	protected var EReference defaultAffectedFeature = null
	protected var EList<NonRoot> referenceContent = null
	protected static val DEFAULT_INDEX = 0
	
	/**
	 * Inserts references into the list of the attribute
	 * to remove them in the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		defaultAffectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE
		referenceContent = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<NonRoot>
	}
	
	/**
	 * Prepares the multivalued reference used in the tests 
	 * and fills it with the new values.
	 */
	def private void prepareReference() {
		referenceContent.add(defaultNewValue)
		referenceContent.add(defaultNewValue2)
	}
	
	/**
	 * Test resolves a {@link RemoveEReference} EChange with correct parameters.
	 * The model is in state before the change was applied forward.
	 * The resource that will be removed is in a non containment reference,
	 * so the object needs to be in the resource.
	 */
	@Test
	def public void resolveRemoveEReferenceTest() {
		// Non containment reference => object needs to be in resource
		prepareReference
		prepareResource
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)
		
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.affectedEObject != defaultAffectedEObject)
		Assert.assertTrue(unresolvedChange.oldValue != defaultNewValue)
		
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1) as RemoveEReference<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(resolvedChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.oldValue == defaultNewValue)	
	}
	
	/**
	 * Test resolves a {@link RemoveEReference} EChange with correct parameters.
	 * The model is in state before the change was applied forward.
	 * The resource that will be removed is in a containment reference,
	 * so the object is not in the resource.
	 */
	@Test
	def public void resolveRemoveEReferenceTest2() {
		val affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE 
		referenceContent = defaultAffectedEObject.eGet(affectedFeature) as EList<NonRoot>
		prepareReference
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, affectedFeature, defaultNewValue, DEFAULT_INDEX, true)	
			
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.affectedEObject != defaultAffectedEObject)
		Assert.assertTrue(unresolvedChange.oldValue != defaultNewValue)	
		
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1) as RemoveEReference<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(resolvedChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.oldValue == defaultNewValue)	
	}
	
	/**
	 * Test resolves a {@link RemoveEReference} EChange with correct parameters.
	 * The model is in state after the change was applied forward.
	 * The value that was removed was in a non containment reference,
	 * so the object is a root object in the resource.
	 */
	@Test
	def public void resolveRemoveEReferenceTest3() {
		prepareResource
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)	
			
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.affectedEObject != defaultAffectedEObject)
		Assert.assertTrue(unresolvedChange.oldValue != defaultNewValue)
		
		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1) as RemoveEReference<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(resolvedChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.oldValue == defaultNewValue)				
	}
	
	/**
	 * Test resolves a {@link RemoveEReference} EChange with correct parameters.
	 * The model is in the state after the change was applied forward.
	 * The value that was removed was in a containment reference,
	 * so the object is in the staging area.
	 */
	@Test
	def public void resolveRemoveEReferenceTest4() {
		val affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE 
		referenceContent = defaultAffectedEObject.eGet(affectedFeature) as EList<NonRoot>
		stagingArea1.contents.add(defaultNewValue)
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, affectedFeature, defaultNewValue, DEFAULT_INDEX, true)	
			
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.affectedEObject != defaultAffectedEObject)
		Assert.assertTrue(unresolvedChange.oldValue != defaultNewValue)	
		Assert.assertTrue(referenceContent.empty)
		Assert.assertTrue(stagingArea1.contents.contains(defaultNewValue))	
		
		val resolvedChange = unresolvedChange.copyAndResolveAfter(resourceSet1) as RemoveEReference<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(resolvedChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.oldValue == defaultNewValue)	
		Assert.assertTrue(referenceContent.empty)
		Assert.assertTrue(stagingArea1.contents.contains(defaultNewValue))								
	}
	
	/**
	 * Tests whether resolving the {@link RemoveEReference} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrectType() {
		// Non containment reference => object needs to be in resource
		resource1.contents.add(defaultNewValue)
		
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)
			
		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1)
		
		assertDifferentChangeSameClass(unresolvedChange, resolvedChange)
	}
	
	/**
	 * Tests applying the {@link RemoveEReference} EChange forward by
	 * removing inserted values from a multivalued reference. 
	 * The reference is a non containment reference, so the values has
	 * to be a root object in the resource.
	 */
	@Test
	def public void removeEReferenceApplyForwardTest() {
		prepareReference
		prepareResource
		
		Assert.assertEquals(referenceContent.size, 2)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX + 1) == defaultNewValue2)
		
		// Remove first reference at index 0
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, false)
			
		Assert.assertTrue(resolvedChange.applyForward)
		Assert.assertEquals(referenceContent.size, 1)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue2)
		
		// Remove second reference at index 0
		val resolvedChange2 = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue2, DEFAULT_INDEX, false)
			
		Assert.assertTrue(resolvedChange2.applyForward)
		Assert.assertEquals(referenceContent.size, 0)
	}
	
	/**
	 * Tests applying the {@link RemoveEReference} EChange forward by 
	 * removing inserted values from a multivalued reference.
	 * The reference is a containment reference, so the values
	 * will be in the staging area after removing them.
	 */
	@Test
	def public void removeEReferenceApplyForwardTest2() {
		val affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
		referenceContent = defaultAffectedEObject.eGet(affectedFeature) as EList<NonRoot>
		prepareReference
		
		Assert.assertEquals(stagingArea1.contents.size, 0)
		Assert.assertEquals(referenceContent.size, 2)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX + 1) == defaultNewValue2)	
		
		// Remove first reference at index 0
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, affectedFeature, defaultNewValue, DEFAULT_INDEX, false)
			
		Assert.assertTrue(resolvedChange.applyForward)
		
		Assert.assertEquals(stagingArea1.contents.size, 1)
		Assert.assertEquals(referenceContent.size, 1)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue2)	
		
		// Now another change deletes the object from the staging area (or reinserts it)
		stagingArea1.contents.clear
		Assert.assertEquals(stagingArea1.contents.size, 0)	
		
		// Remove second reference at index 0
		val resolvedChange2 = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, affectedFeature, defaultNewValue2, DEFAULT_INDEX, false)
			
		Assert.assertTrue(resolvedChange2.applyForward)
		
		Assert.assertEquals(stagingArea1.contents.size, 1)
		Assert.assertEquals(referenceContent.size, 0)
	}
	
	/**
	 * Tests applying a {@link RemoveEReference} EChange backward. The reference is
	 * a non containment reference so the values has to be in the resource.
	 */
	@Test
	def public void removeEReferenceApplyBackwardTest() {
		prepareReference
		prepareResource 

		Assert.assertEquals(referenceContent.size, 2)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX + 1) == defaultNewValue2)

		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, false)
		
		// apply forward
		Assert.assertTrue(resolvedChange.applyForward)
		Assert.assertEquals(referenceContent.size, 1)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue2)
		
		// apply backward
		Assert.assertTrue(resolvedChange.applyBackward)
		Assert.assertEquals(referenceContent.size, 2)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX + 1) == defaultNewValue2)
	}
	
	/**
	 * Tests applying a {@link RemoveEReference} EChange backward. The reference is
	 * a containment reference so the values has to be in the staging area
	 * before they are reinserted.
	 */
	@Test
	def public void removeEReferenceApplyBackwardTest2() {
		val affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_CONTAINMENT_EREFERENCE
		referenceContent = defaultAffectedEObject.eGet(affectedFeature) as EList<NonRoot>
		prepareReference
		
		Assert.assertEquals(referenceContent.size, 2)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX + 1) == defaultNewValue2)
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, affectedFeature, defaultNewValue, DEFAULT_INDEX, false)	
			
		// apply forward
		Assert.assertTrue(resolvedChange.applyForward)
		Assert.assertEquals(stagingArea1.contents.size, 1)
		Assert.assertEquals(referenceContent.size, 1)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue2)	
		
		// apply backward
		Assert.assertTrue(resolvedChange.applyBackward)
		Assert.assertEquals(stagingArea1.contents.size, 0)
		Assert.assertEquals(referenceContent.size, 2)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX + 1) == defaultNewValue2)			
	}
	
	/**
	 * Tests a {@link RemoveEReference} EChange with invalid index.
	 */
	@Test
	def public void removeEReferenceInvalidIndexTest() {
		prepareReference
		prepareResource

		val index = 5; // Valid index is 0 or 1

		Assert.assertEquals(referenceContent.size, 2)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue)
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, index, false)
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertFalse(resolvedChange.applyForward)
		Assert.assertFalse(resolvedChange.applyBackward)
	}
	
	/**
	 * Tests a {@link RemoveEReference} EChange with with an affected object which has no 
	 * such reference.
	 */
	@Test
	def public void removeEReferenceInvalidAttribute() {
		val affectedEObject = rootObject1.singleValuedNonContainmentEReference // NonRoot element
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<NonRoot, NonRoot>createRemoveReferenceChange(affectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, false)
			
		// NonRoot has no such feature
		Assert.assertTrue(affectedEObject.eClass.getFeatureID(defaultAffectedFeature) == -1)
		
		Assert.assertFalse(resolvedChange.applyForward)
		Assert.assertFalse(resolvedChange.applyBackward)
	}
	
	/**
	 * Tests a {link RemoveEReference} EChange with the wrong value type.
	 */
	@Test
	def public void removeEReferenceInvalidValue() {
		prepareReference
		prepareResource
		
		val newValue = AllElementTypesFactory.eINSTANCE.createRoot() // value is Root, reference value is NonRoot
		
		Assert.assertEquals(referenceContent.size, 2)
		Assert.assertTrue(referenceContent.get(DEFAULT_INDEX) == defaultNewValue)
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, Root>createRemoveReferenceChange(defaultAffectedEObject, defaultAffectedFeature, newValue, DEFAULT_INDEX, false)
			
		// Type of reference is NonRoot not Root
		Assert.assertEquals(defaultAffectedFeature.EType.name, "NonRoot")
		
		Assert.assertFalse(resolvedChange.applyForward)
		Assert.assertFalse(resolvedChange.applyBackward)
	}
}
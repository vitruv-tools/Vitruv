package tools.vitruv.framework.tests.echange.feature.reference

import allElementTypes.AllElementTypesFactory
import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
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
	
	protected static val Integer DEFAULT_INDEX = 0
	
	/**
	 * Sets the default affected feature for the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		defaultAffectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE
	}
	
	/**
	 * Test resolves a {@link InsertEReference} EChange with correct parameters.
	 */
	@Test
	def public void resolveInsertEReferenceTest() {
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)
			
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.affectedEObject != defaultAffectedEObject)
		Assert.assertTrue(unresolvedChange.newValue != defaultNewValue)
		
		val resolvedChange = unresolvedChange.resolveApply(resourceSet1) as InsertEReference<Root, NonRoot>
		
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
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)	
			
		val resolvedChange = unresolvedChange.resolveApply(resourceSet1)
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		Assert.assertEquals(unresolvedChange.getClass, resolvedChange.getClass)
	}
	
	 /**
	  * Tests inserting new values in a multivalued reference.
	  */
	@Test
	def public void insertEReferenceApplyTest() {
	 	val EList<NonRoot> multivaluedReference = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<NonRoot>

		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, false)
	 	 	
	    Assert.assertEquals(multivaluedReference.size, 0)
	 	
	 	// Insert first value
	 	Assert.assertTrue(resolvedChange.apply)
	 	
	 	Assert.assertEquals(multivaluedReference.size, 1)
	 	Assert.assertTrue(multivaluedReference.get(DEFAULT_INDEX) == defaultNewValue)

	 	
	 	val resolvedChange2 = TypeInferringAtomicEChangeFactory.
	 	 	<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue2, DEFAULT_INDEX, false)
	 	
	 	// Insert second value before first value (at index 0)
	 	Assert.assertTrue(resolvedChange2.apply)
	 	
	 	Assert.assertEquals(multivaluedReference.size, 2)
	 	Assert.assertTrue(multivaluedReference.get(DEFAULT_INDEX) == defaultNewValue2)
	 	Assert.assertTrue(multivaluedReference.get(DEFAULT_INDEX + 1) == defaultNewValue)
	}
	
	/**
	 * Reverts two {@link InsertEReference} EChanges.
	 */
	@Test
	def public void insertEReferenceRevertTest() {
		val EList<NonRoot> multivaluedReference = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<NonRoot>
		Assert.assertEquals(multivaluedReference.size, 0)
		
		// defaultNewValue2 was added first at index 0 (resolvedChange), then defaultNewValue at index 0 (resolvedChange2)
		multivaluedReference.add(defaultNewValue)
		multivaluedReference.add(defaultNewValue2)
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue2, DEFAULT_INDEX, false)
			
		val resolvedChange2 = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createInsertReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, false)
			
		Assert.assertEquals(multivaluedReference.size, 2)
		Assert.assertTrue(multivaluedReference.get(DEFAULT_INDEX) == defaultNewValue)
		Assert.assertTrue(multivaluedReference.get(DEFAULT_INDEX + 1) == defaultNewValue2)	
		
		Assert.assertTrue(resolvedChange2.revert)
		
		Assert.assertEquals(multivaluedReference.size, 1)
		Assert.assertTrue(multivaluedReference.get(DEFAULT_INDEX) == defaultNewValue2)
		
		Assert.assertTrue(resolvedChange.revert)
		
		Assert.assertEquals(multivaluedReference.size, 0)
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
		Assert.assertFalse(resolvedChange.apply)
		Assert.assertFalse(resolvedChange.revert)
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
		
		Assert.assertFalse(resolvedChange.apply)
		Assert.assertFalse(resolvedChange.revert)
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
		
		Assert.assertFalse(resolvedChange.apply)
		Assert.assertFalse(resolvedChange.revert)
	}
}
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

/**
 * Test class for the concrete {@link RemoveEReference} EChange, 
 * which removes a reference from a multivalued attribute.
 */
public class RemoveEReferenceTest extends ReferenceEChangeTest {
	protected var EReference defaultAffectedFeature = null
	
	protected static val DEFAULT_INDEX = 0
	
	/**
	 * Inserts references into the list of the attribute
	 * to remove them in the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		defaultAffectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_NON_CONTAINMENT_EREFERENCE;
		val EList<NonRoot> multivaluedReference = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<NonRoot>
		multivaluedReference.add(defaultNewValue)
		multivaluedReference.add(defaultNewValue2)
	}
	
	/**
	 * Test resolves a {@link RemoveEReference} EChange with correct parameters.
	 */
	@Test
	def public void resolveRemoveEReferenceTest() {
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)
		
		Assert.assertFalse(unresolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange.affectedEObject != defaultAffectedEObject)
		Assert.assertTrue(unresolvedChange.oldValue != defaultNewValue)
		
		val resolvedChange = unresolvedChange.resolve(resourceSet1) as RemoveEReference<Root, NonRoot>
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(resolvedChange.affectedEObject == defaultAffectedEObject)
		Assert.assertTrue(resolvedChange.oldValue == defaultNewValue)	
	}
	
	/**
	 * Tests whether resolving the {@link RemoveEReference} EChange
	 * returns the same class.
	 */
	@Test
	def public void resolveToCorrektType() {
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, true)
			
		val resolvedChange = unresolvedChange.resolve(resourceSet1)
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertTrue(unresolvedChange != resolvedChange)
		Assert.assertEquals(unresolvedChange.getClass(), resolvedChange.getClass())
	}
	
	/**
	 * Tests removing inserted values in a multivalued reference.
	 */
	@Test
	def public void removeEReferenceApplyTest() {
		val EList<NonRoot> multivaluedReference = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<NonRoot>
		
		Assert.assertEquals(multivaluedReference.size, 2)
		Assert.assertTrue(multivaluedReference.get(DEFAULT_INDEX) == defaultNewValue)
		Assert.assertTrue(multivaluedReference.get(DEFAULT_INDEX + 1) == defaultNewValue2)
		
		// Remove first reference at index 0
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, false)
			
		Assert.assertTrue(resolvedChange.apply)
		Assert.assertEquals(multivaluedReference.size, 1)
		Assert.assertTrue(multivaluedReference.get(DEFAULT_INDEX) == defaultNewValue2)
		
		// Remove second reference at index 0
		val resolvedChange2 = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue2, DEFAULT_INDEX, false)
			
		Assert.assertTrue(resolvedChange2.apply)
		Assert.assertEquals(multivaluedReference.size, 0)
	}
	
	/**
	 * Reverts a {@link RemoveEReference} EChange.
	 */
	@Test
	def public void removeEReferenceRevertTest() {
		val EList<NonRoot> multivaluedReference = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<NonRoot>

		Assert.assertEquals(multivaluedReference.size, 2)
		Assert.assertTrue(multivaluedReference.get(DEFAULT_INDEX) == defaultNewValue)
		Assert.assertTrue(multivaluedReference.get(DEFAULT_INDEX + 1) == defaultNewValue2)

		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, DEFAULT_INDEX, false)
		
		// apply
		Assert.assertTrue(resolvedChange.apply)
		Assert.assertEquals(multivaluedReference.size, 1)
		Assert.assertTrue(multivaluedReference.get(DEFAULT_INDEX) == defaultNewValue2)
		
		// revert
		Assert.assertTrue(resolvedChange.revert)
		Assert.assertEquals(multivaluedReference.size, 2)
		Assert.assertTrue(multivaluedReference.get(DEFAULT_INDEX) == defaultNewValue)
		Assert.assertTrue(multivaluedReference.get(DEFAULT_INDEX + 1) == defaultNewValue2)
	}
	
	/**
	 * Tests a {@link RemoveEReference} EChange with invalid index.
	 */
	@Test
	def public void removeEReferenceInvalidIndexTest() {
		val index = 5; // Valid index is 0 or 1
		val EList<NonRoot> multivaluedReference = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<NonRoot>
		Assert.assertEquals(multivaluedReference.size, 2)
		Assert.assertTrue(multivaluedReference.get(DEFAULT_INDEX) == defaultNewValue)
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, NonRoot>createRemoveReferenceChange(defaultAffectedEObject, defaultAffectedFeature, defaultNewValue, index, false)
		
		Assert.assertTrue(resolvedChange.isResolved)
		Assert.assertFalse(resolvedChange.apply)
		Assert.assertFalse(resolvedChange.revert)
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
		
		Assert.assertFalse(resolvedChange.apply)
		Assert.assertFalse(resolvedChange.revert)
	}
	
	/**
	 * Tests a {link RemoveEReference} EChange with the wrong value type.
	 */
	@Test
	def public void removeEReferenceInvalidValue() {
		val newValue = AllElementTypesFactory.eINSTANCE.createRoot() // value is Root, reference value is NonRoot
		val EList<NonRoot> multivaluedReference = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<NonRoot>
		
		Assert.assertEquals(multivaluedReference.size, 2)
		Assert.assertTrue(multivaluedReference.get(DEFAULT_INDEX) == defaultNewValue)
		
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, Root>createRemoveReferenceChange(defaultAffectedEObject, defaultAffectedFeature, newValue, DEFAULT_INDEX, false)
			
		// Type of reference is NonRoot not Root
		Assert.assertEquals(defaultAffectedFeature.EType.name, "NonRoot")
		
		Assert.assertFalse(resolvedChange.apply)
		Assert.assertFalse(resolvedChange.revert)
	}
}
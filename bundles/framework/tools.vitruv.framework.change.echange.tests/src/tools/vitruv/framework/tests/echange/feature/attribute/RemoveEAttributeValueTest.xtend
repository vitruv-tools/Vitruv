package tools.vitruv.framework.tests.echange.feature.attribute

import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue

/**
 * Test class for the concrete {@link RemoveEAttributeValue} EChange,
 * which removes a value in a multivalued attribute.
 */
public class RemoveEAttributeValueTest extends InsertRemoveEAttributeTest {
	/**
	 * Inserts values in the default feature 
	 * so they can be removed by the tests.
	 */
	@Before
	override public void beforeTest() {
		super.beforeTest()
		val EList<Integer> multivaluedAttribute = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<Integer>
		multivaluedAttribute.add(DEFAULT_NEW_VALUE);
		multivaluedAttribute.add(DEFAULT_NEW_VALUE_2);
	}
	
	/**
	 * Tests whether resolving the {@link RemoveEAttributeValue} EChange
	 * returns the same class. 
	 */
	@Test
	def public void resolveToCorrectType() {
		val unresolvedChange = TypeInferringAtomicEChangeFactory.
 			<Root, Integer>createInsertAttributeChange(defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX, DEFAULT_NEW_VALUE, true)
 		
 		// Resolving the change will be tested in EFeatureChange	
 		val resolvedChange = unresolvedChange.resolveApply(resourceSet1)
 		
 		Assert.assertTrue(resolvedChange.isResolved)
 		Assert.assertTrue(unresolvedChange != resolvedChange)
 		Assert.assertEquals(unresolvedChange.getClass(), resolvedChange.getClass())
	}
	
	/**
	 * Tests removing two values from an multivalued attribute.
	 */
	@Test
	def public void removeEAttributeValueApplyTest() {
		val EList<Integer> multivaluedAttribute = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<Integer>
		
		Assert.assertEquals(multivaluedAttribute.size, 2)
		Assert.assertEquals(multivaluedAttribute.get(DEFAULT_INDEX), DEFAULT_NEW_VALUE)
		Assert.assertEquals(multivaluedAttribute.get(DEFAULT_INDEX + 1), DEFAULT_NEW_VALUE_2)
		
		// Resolving the change will be tested in EFeatureChange
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, Integer>createRemoveAttributeChange(defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX, DEFAULT_NEW_VALUE, false)
			
		Assert.assertTrue(resolvedChange.apply)
		Assert.assertEquals(multivaluedAttribute.size, 1)
		Assert.assertEquals(multivaluedAttribute.get(DEFAULT_INDEX), DEFAULT_NEW_VALUE_2)
		
		// Resolving the change will be tested in EFeatureChange
		val resolvedChange2 = TypeInferringAtomicEChangeFactory.
			<Root, Integer>createRemoveAttributeChange(defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX, DEFAULT_NEW_VALUE_2, false)
			
		Assert.assertTrue(resolvedChange2.apply)
		Assert.assertEquals(multivaluedAttribute.size, 0)
	}
	
	/**
	 * Revert a {@link RemoveEAttributeValue} EChange.
	 */
	@Test
	def public void removeEAttributeValueRevertTest() {
		val EList<Integer> multivaluedAttribute = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<Integer>
		
		Assert.assertEquals(multivaluedAttribute.size, 2)
		Assert.assertEquals(multivaluedAttribute.get(DEFAULT_INDEX), DEFAULT_NEW_VALUE)
		Assert.assertEquals(multivaluedAttribute.get(DEFAULT_INDEX + 1), DEFAULT_NEW_VALUE_2)
		
		// Resolving the change will be tested in EFeatureChange
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, Integer>createRemoveAttributeChange(defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX, DEFAULT_NEW_VALUE, false)
			
		Assert.assertTrue(resolvedChange.apply)
		Assert.assertEquals(multivaluedAttribute.size, 1)
		Assert.assertEquals(multivaluedAttribute.get(DEFAULT_INDEX), DEFAULT_NEW_VALUE_2)
		
		Assert.assertTrue(resolvedChange.revert)
		Assert.assertEquals(multivaluedAttribute.size, 2)
		Assert.assertEquals(multivaluedAttribute.get(DEFAULT_INDEX), DEFAULT_NEW_VALUE)
		Assert.assertEquals(multivaluedAttribute.get(DEFAULT_INDEX + 1), DEFAULT_NEW_VALUE_2)
	}
	
	/**
	 * Tests a {@link RemoveEAttributeValue} EChange with invalid index.
	 */
	@Test
	def public void removeEAttributeValueInvalidIndexTest() {
		val index = 5
		val EList<Integer> multivaluedAttribute = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<Integer>
		Assert.assertEquals(multivaluedAttribute.size, 2)
		
		// Resolving the change will be tested in EFeatureChange
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, Integer>createRemoveAttributeChange(defaultAffectedEObject, defaultAffectedFeature, index, DEFAULT_NEW_VALUE, false)		
	
		Assert.assertFalse(resolvedChange.apply)
		Assert.assertFalse(resolvedChange.revert)
	}
	
	/**
	 * Tests an affected object which has no such attribute.
	 */
	@Test
	def public void removeEAttributeValueInvalidAttribute() {
		val affectedEObject = rootObject1.singleValuedNonContainmentEReference // NonRoot element
		
		// Resolving the change will be tested in EFeatureChange
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<NonRoot, Integer>createRemoveAttributeChange(affectedEObject, defaultAffectedFeature, DEFAULT_INDEX, DEFAULT_NEW_VALUE, false)	
			
		// NonRoot has no such feature
	 	Assert.assertTrue(affectedEObject.eClass.getFeatureID(defaultAffectedFeature) == -1)	
	 	
	 	Assert.assertFalse(resolvedChange.apply)
	 	Assert.assertFalse(resolvedChange.revert)
	}
	
	/**
	 * Tests a {@link RemoveEAttributeValue} EChange with the wrong value type.
	 */
	@Test
	def public void removeEAttributeValueInvalidValue() {
		val newValue = "New String Value" // values are Strings, attribute value type is Integer
		
		// Resolving the change will be tested in EFeatureChange
		val resolvedChange = TypeInferringAtomicEChangeFactory.
			<Root, String>createRemoveAttributeChange(defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX, newValue, false)	
		
		// Type of attribute is Integer not String
	 	Assert.assertTrue(defaultAffectedFeature.EAttributeType.name == "EIntegerObject")
	 	
	 	Assert.assertFalse(resolvedChange.apply)	 	
	 	Assert.assertFalse(resolvedChange.revert)
	}
}
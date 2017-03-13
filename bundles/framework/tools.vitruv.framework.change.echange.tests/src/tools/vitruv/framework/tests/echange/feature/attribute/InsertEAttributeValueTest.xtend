package tools.vitruv.framework.tests.echange.feature.attribute

import allElementTypes.AllElementTypesPackage
import allElementTypes.NonRoot
import allElementTypes.Root
import org.eclipse.emf.common.util.EList
import org.junit.Assert
import org.junit.Test
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue

/**
 * Test class for the concrete {@link InsertEAttributeValue} EChange,
 * which inserts a value in a multivalued attribute.
 */
public class InsertEAttributeValueTest extends InsertRemoveEAttributeTest {

	 /**
	  * Tests whether resolving the {@link InsertEAttributeValue} EChange returns the same class.
	  */
	 @Test
	 def public void resolveToCorrectType() {
	 	// Resolving the change will be tested in EFeatureChange
	 	val unresolvedChange = TypeInferringAtomicEChangeFactory.
 			<Root, Integer>createInsertAttributeChange(defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX, DEFAULT_NEW_VALUE, true)
 			
 		val resolvedChange = unresolvedChange.copyAndResolveBefore(resourceSet1)
 		
		assertDifferentChangeSameClass(unresolvedChange, resolvedChange)
	 }
	 
	 /**
	  * Tests applying two {@link InsertEAttributeValue} EChanges forward by 
	  * inserting new values in a multivalued attribute.
	  */
	 @Test
	 def public void insertEAttributeValueApplyForwardTest() {
	 	val EList<Integer> multivaluedAttribute = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<Integer>

	 	// Resolving the change will be tested in EFeatureChange
	 	val resolvedChange = TypeInferringAtomicEChangeFactory.
	 	 	<Root, Integer>createInsertAttributeChange(defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX, DEFAULT_NEW_VALUE, false)
	 	 	
	    Assert.assertEquals(multivaluedAttribute.size, 0)
	 	
	 	// Insert first value
	 	Assert.assertTrue(resolvedChange.applyForward)
	 	
	 	Assert.assertEquals(multivaluedAttribute.size, 1)
	 	Assert.assertEquals(multivaluedAttribute.get(DEFAULT_INDEX), DEFAULT_NEW_VALUE)

	 	// Resolving the change will be tested in EFeatureChange	 	
	 	val resolvedChange2 = TypeInferringAtomicEChangeFactory.
	 	 	<Root, Integer>createInsertAttributeChange(defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX, DEFAULT_NEW_VALUE_2, false)
	 	
	 	// Insert second value before first value (at index 0)
	 	Assert.assertTrue(resolvedChange2.applyForward)
	 	
	 	Assert.assertEquals(multivaluedAttribute.size, 2)
	 	Assert.assertEquals(multivaluedAttribute.get(DEFAULT_INDEX), DEFAULT_NEW_VALUE_2)
	 	Assert.assertEquals(multivaluedAttribute.get(DEFAULT_INDEX + 1), DEFAULT_NEW_VALUE)
	 }
	 
	 /**
	  * Applies two {@link InsertEAttributeValue} EChanges backward.
	  */
	 @Test
	 def public void insertEAttributeValueApplyBackwardTest() {
	 	val EList<Integer> multivaluedAttribute = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<Integer>
	 	Assert.assertEquals(multivaluedAttribute.size, 0)
	 		
	 	// DEFAULT_NEW_VALUE_2 was added first at index 0 (resolvedChange), then DEFAULT_NEW_VALUE at index 0 (resolvedChange2)
	 	multivaluedAttribute.add(DEFAULT_NEW_VALUE)
	 	multivaluedAttribute.add(DEFAULT_NEW_VALUE_2)
	 	
	 	// Resolving the change will be tested in EFeatureChange
	 	val resolvedChange = TypeInferringAtomicEChangeFactory.
	 	 	<Root, Integer>createInsertAttributeChange(defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX, DEFAULT_NEW_VALUE_2, false)
	 	 	
	 	val resolvedChange2 = TypeInferringAtomicEChangeFactory.
	 	 	<Root, Integer>createInsertAttributeChange(defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX, DEFAULT_NEW_VALUE, false)
	 	
	 	Assert.assertEquals(multivaluedAttribute.size, 2) 	
	 	Assert.assertEquals(multivaluedAttribute.get(DEFAULT_INDEX), DEFAULT_NEW_VALUE)
	 	Assert.assertEquals(multivaluedAttribute.get(DEFAULT_INDEX + 1), DEFAULT_NEW_VALUE_2)
	 	
	 	Assert.assertTrue(resolvedChange2.applyBackward)
	 	
	 	Assert.assertEquals(multivaluedAttribute.size, 1)
	 	Assert.assertEquals(multivaluedAttribute.get(DEFAULT_INDEX), DEFAULT_NEW_VALUE_2)
	 	
	 	Assert.assertTrue(resolvedChange.applyBackward)
	 	
	 	Assert.assertEquals(multivaluedAttribute.size, 0)
	 }
	 
	 /**
	  * Tests the {@link InsertEAttributeValue} EChange with invalid index.
	  */
	 @Test
	 def public void insertEAttributeValueInvalidIndexTest() {
	 	val index = 5; // Valid index in empty list is only 0
	 	val EList<Integer> multivaluedAttribute = defaultAffectedEObject.eGet(defaultAffectedFeature) as EList<Integer>
	 	Assert.assertEquals(multivaluedAttribute.size, 0)	 
	 	
	 	// Resolving the change will be tested in EFeatureChange	 	
	 	val resolvedChange = TypeInferringAtomicEChangeFactory.
	 	 	<Root, Integer>createInsertAttributeChange(defaultAffectedEObject, defaultAffectedFeature, index, DEFAULT_NEW_VALUE, false)	
	 	 	
	 	Assert.assertTrue(resolvedChange.isResolved)
	 	Assert.assertFalse(resolvedChange.applyForward)
	 	Assert.assertFalse(resolvedChange.applyBackward)
	 }
	 
	 /**
	  * Tests an {@link InsertEAttributeValue} with an affected object which has no such attribute.
	  */
	 @Test
	 def public void insertEAttributeValueInvalidAttribute() {
	 	val affectedEObject = rootObject1.singleValuedNonContainmentEReference // NonRoot element
	 	val affectedFeature = AllElementTypesPackage.Literals.ROOT__MULTI_VALUED_EATTRIBUTE // Attribute of Root element
	 	
	 	// Resolving the change will be tested in EFeatureChange
	 	val resolvedChange = TypeInferringAtomicEChangeFactory.
	 		<NonRoot, Integer>createInsertAttributeChange(affectedEObject, affectedFeature, DEFAULT_INDEX, DEFAULT_NEW_VALUE, false)
	 	
	 	// NonRoot has no such feature
	 	Assert.assertTrue(affectedEObject.eClass.getFeatureID(affectedFeature) == -1)	
	 	
	 	Assert.assertFalse(resolvedChange.applyForward)
	 	Assert.assertFalse(resolvedChange.applyBackward)
	 }
	 
	 /**
	  * Tests an {@link InsertEAttributeValue} EChange with the wrong value type.
	  */
	 @Test
	 def public void insertEAttributeValueInvalidValue() {
	 	val newValue = "New String Value" // values are String, attribute value type is Integer
	 	
	 	// Resolving the change will be tested in EFeatureChange
	 	val resolvedChange = TypeInferringAtomicEChangeFactory.
	 		<Root, String>createInsertAttributeChange(defaultAffectedEObject, defaultAffectedFeature, DEFAULT_INDEX, newValue, false)
	 		
	 	// Type of attribute is Integer not String
	 	Assert.assertTrue(defaultAffectedFeature.EAttributeType.name == "EIntegerObject")
	 	
	 	Assert.assertFalse(resolvedChange.applyForward)	 	
	 	Assert.assertFalse(resolvedChange.applyBackward)
	 }
}